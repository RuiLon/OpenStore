package com.example.bysj.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bysj.constants.codeConstants;
import com.example.bysj.enity.Books;
import com.example.bysj.mapper.BooksMapper;
import com.example.bysj.service.infc.BooksInfc;
import com.example.bysj.util.RedisUtil;
import com.example.bysj.util.RuleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BooksServiceImp extends ServiceImpl<BooksMapper, Books> implements BooksInfc {

    @Autowired
    private BooksMapper booksMapper;

    @Autowired
    private MapsServiceImp mapsServiceImp;

    public static Map<String,Integer> maps = new HashMap<String,Integer>();

    /**
     * 查询书籍
     * @param PageNumber
     * @param PageSize
     * @param bname
     * @param bscode
     * @param bauther
     * @return
     */
    public RuleUtil getAllBooks(Integer PageNumber,Integer PageSize,String bname,String bscode,String bauther)
    {
        IPage page = new Page(PageNumber, PageSize);
        QueryWrapper<Books> queryWrapper = new QueryWrapper<>();
        if(!bname.isEmpty())
            queryWrapper.like("bname",bname);
        if(!bscode.isEmpty())
            queryWrapper.like("bscode",bscode);
        if(!bauther.isEmpty())
            queryWrapper.like("bauther",bauther);
        queryWrapper.eq("idel",0);
        return RuleUtil.success(this.booksMapper.selectPage(page,queryWrapper));
    }

    /**
     * 修改书籍
     * @param books
     * @return
     */
    public RuleUtil updateBooks(Books books)
    {
        Boolean flag = false;
        if(!ObjectUtils.isEmpty(books))
        {
            this.booksMapper.updateOne(books);
            flag = true;
        }
        if (flag)
        {
            return RuleUtil.success();
        }
        else
        {
            return RuleUtil.error(codeConstants.Code_500,"修改失败");
        }
    }

    /**
     * 批量删除书籍
     * @param delList
     * @return
     */
    public RuleUtil delList(List<Books> delList)
    {
        Boolean flag = false;
        if (!CollectionUtils.isEmpty(delList))
        {
            this.booksMapper.delList(delList);
            flag = true;
        }
        if (flag)
        {
            return RuleUtil.success();
        }
        else
        {
            return RuleUtil.error(codeConstants.Code_500,"批量删除失败");
        }
    }

    /**
     * 删除书籍
     * @param books
     * @return
     */
    public RuleUtil delBooks(Books books)
    {
        Boolean flag = false;
        if(!ObjectUtils.isEmpty(books))
        {
            this.booksMapper.delbooks(books);
            flag = true;
        }
        if(flag)
        {
            return RuleUtil.success();
        }
        else
        {
            return  RuleUtil.error(codeConstants.Code_500,"删除失败");
        }
    }


    public RuleUtil addBooks(Books books)
    {
        if (!ObjectUtils.isEmpty(books))
        {
            RedisUtil redisUtil = new RedisUtil();
            //生成流水号
            String scode = "SJ";
            //初始化map容器
            if(maps.isEmpty())
            {
                this.mapsServiceImp.setMaps(5,maps);
            }
            String num = redisUtil.incr(maps,5,mapsServiceImp);
            scode = scode + num;
            books.setBscode(scode);
            books.setBrostate("未借空");
            this.booksMapper.insert(books);
            return RuleUtil.success();
        }
        else
        {
            return RuleUtil.error(codeConstants.Code_600,"添加失败");
        }
    }

}
