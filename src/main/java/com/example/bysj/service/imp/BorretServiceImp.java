package com.example.bysj.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bysj.constants.codeConstants;
import com.example.bysj.enity.Books;
import com.example.bysj.enity.Borret;
import com.example.bysj.enity.Consumer;
import com.example.bysj.mapper.BooksMapper;
import com.example.bysj.mapper.BorretMapper;
import com.example.bysj.mapper.ConsumerMapper;
import com.example.bysj.service.infc.BorretInfc;
import com.example.bysj.util.RedisUtil;
import com.example.bysj.util.RuleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BorretServiceImp extends ServiceImpl<BorretMapper, Borret> implements BorretInfc {

    @Autowired
    private BorretMapper borretMapper;

    @Autowired
    private BooksMapper booksMapper;

    @Autowired
    private ConsumerMapper consumerMapper;

    @Autowired
    private MapsServiceImp mapsServiceImp;

    public static Map<String,Integer> maps = new HashMap<String,Integer>();

    /**
     * 借书表详情
     * @param borretPageNumber
     * @param borretPageSize
     * @param bookname
     * @param borname
     * @param borphone
     * @return
     */
    public RuleUtil getAllborrets(Integer borretPageNumber,
                                  Integer borretPageSize,
                                  String bookname,
                                  String borname,
                                  String borphone)
    {
        IPage page = new Page(borretPageNumber,borretPageSize);
        QueryWrapper<Borret> queryWrapper = new QueryWrapper<>();
        if (!bookname.isEmpty())
            queryWrapper.like("bookname",bookname);
        if (!borname.isEmpty())
            queryWrapper.like("borname",borname);
        if (!borphone.isEmpty())
            queryWrapper.like("borphone",borphone);

        return RuleUtil.success(this.borretMapper.selectPage(page,queryWrapper));
    }

    /**
     * 借书操作
     * @param borret
     * @return
     */
    public RuleUtil borBook(Borret borret)
    {
        if(!ObjectUtils.isEmpty(borret))
        {
            Consumer consumer = this.consumerMapper.readOneByPhone(borret.getBorphone());
            if (!ObjectUtils.isEmpty(consumer))
            {
                Books books = this.booksMapper.selectOne(borret.getBookid());
                if (books.getBnumber() > 0)
                {
                    books.setBnumber(books.getBnumber()-1);
                    if (books.getBnumber() == 0) {
                        books.setBrostate("已借空");
                    }
                    this.booksMapper.updateOne(books);
                    borret.setBorrowcid(consumer.getCid());
                    borret.setBorscode(consumer.getScode());
                    borret.setDborrow(new Date());
                    //生成借书流水号
                    RedisUtil redisUtil = new RedisUtil();
                    String scode = "JS";
                    if(maps.isEmpty())
                    {
                        this.mapsServiceImp.setMaps(2,maps);
                    }
                    String num = redisUtil.incr(maps,2,mapsServiceImp);
                    scode = scode + num;
                    borret.setBorscode(scode);
                    this.borretMapper.insert(borret);
                    return RuleUtil.success();
                }
                else
                {
                    return RuleUtil.error(codeConstants.Code_400,"书籍已被借空");
                }

            }
            else
                return RuleUtil.error(codeConstants.Code_500,"用户不存在");
        }
        else
            return RuleUtil.error(codeConstants.Code_500,"参数不正确");
    }

    /**
     * 还书操作
     * @param borret
     * @return
     */
    public RuleUtil retBook(Borret borret)
    {
        if(!ObjectUtils.isEmpty(borret))
        {
            Books books = this.booksMapper.selectOne(borret.getBookid());
            books.setBnumber(books.getBnumber()+1);
            if (books.getBnumber() > 0)
            {
                books.setBrostate("未借空");
            }
            this.booksMapper.updateOne(books);
            borret.setDreturn(new Date());
            borret.setRetstate("已归还");
            RedisUtil redisUtil = new RedisUtil();
            String scode = "HS";
            if(maps.isEmpty())
            {
                this.mapsServiceImp.setMaps(3,maps);
            }
            String num = redisUtil.incr(maps,3,mapsServiceImp);
            scode = scode + num;
            borret.setRetscode(scode);
            this.borretMapper.updateBorret(borret);
            return RuleUtil.success();
        }
        else
            return RuleUtil.error(codeConstants.Code_500,"参数不正确");
    }

    /**
     * 未还书的列表
     * @param borretPageNumber
     * @param borretPageSize
     * @param bookname
     * @param borname
     * @param borphone
     * @return
     */
    public RuleUtil getAllNoRet(Integer borretPageNumber,
                                Integer borretPageSize,
                                String bookname,
                                String borname,
                                String borphone)
    {
        IPage page = new Page(borretPageNumber,borretPageSize);
        QueryWrapper<Borret> queryWrapper = new QueryWrapper<>();
        if (!bookname.isEmpty())
            queryWrapper.like("bookname",bookname);
        if (!borname.isEmpty())
            queryWrapper.like("borname",borname);
        if (!borphone.isEmpty())
            queryWrapper.like("borphone",borphone);
        queryWrapper.eq("retscode","");
        return RuleUtil.success(this.borretMapper.selectPage(page,queryWrapper));
    }
}
