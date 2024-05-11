package com.example.bysj.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bysj.constants.codeConstants;
import com.example.bysj.enity.Goods;
import com.example.bysj.mapper.GoodsMapper;
import com.example.bysj.param.GoodsParam;
import com.example.bysj.service.infc.GoodsInfc;
import com.example.bysj.util.RedisUtil;
import com.example.bysj.util.RuleUtil;
import org.apache.tomcat.util.digester.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.print.attribute.standard.PagesPerMinute;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImp extends ServiceImpl<GoodsMapper, Goods> implements GoodsInfc {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private MapsServiceImp mapsServiceImp;

    public static Map<String,Integer> maps = new HashMap<String,Integer>();

    //按条件获取商品
    public RuleUtil getAllGoods(Integer PageNumber, Integer PageSize, String gname, String gdesciber, String gcode)
        {
        IPage page = new Page(PageNumber, PageSize);
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        if (!gname.isEmpty())
            queryWrapper.like("gname",gname);
        if (!gdesciber.isEmpty())
            queryWrapper.like("gdesciber",gdesciber);
        if (!gcode.isEmpty())
            queryWrapper.le("gcode",gcode);
        queryWrapper.eq("idel",0);
        return RuleUtil.success(this.goodsMapper.selectPage(page,queryWrapper));
    }


    //新增商品
    public RuleUtil addGoods(Goods goods)
    {
        if (!ObjectUtils.isEmpty(goods))
        {
            RedisUtil redisUtil = new RedisUtil();
            //生成流水号
            String scode = "ZCYH";
            //初始化map容器
            if(maps.isEmpty())
            {
                this.mapsServiceImp.setMaps(4,maps);
            }
            String num = redisUtil.incr(maps,4,mapsServiceImp);
            scode = scode + num;
            goods.setGcode(scode);
            goods.setGsum(goods.getGnumber());
            this.goodsMapper.insert(goods);
            return RuleUtil.success();
        }
        else
        {
            return RuleUtil.error(codeConstants.Code_600,"添加失败");
        }
    }

    //下架商品
    public RuleUtil delgoods(Goods goods)
    {
        Boolean flag =false;
        if(!ObjectUtils.isEmpty(goods))
        {
            this.goodsMapper.delgoods(goods);
            flag = true;
        }
        if(flag)
            return RuleUtil.success();
        else
            return RuleUtil.error(codeConstants.Code_500,"删除失败");
    }

    //更新商品信息
    public RuleUtil updateGoods(Goods goods)
    {
        Boolean flag = false;
        if(!ObjectUtils.isEmpty(goods))
        {
            this.goodsMapper.updateOne(goods);
            flag = true;
        }
        if(flag)
            return RuleUtil.success();
        else
            return RuleUtil.error(codeConstants.Code_500,"更新失败");
    }

    //商品补货
    public RuleUtil supplyGoods(Goods goods)
    {
        Boolean flag = false;
        if(!ObjectUtils.isEmpty(goods))
        {
            if(ObjectUtils.isEmpty(goods.getSupply()))
            {
                goods.setSupply(0);
            }
            goods.setGsum(goods.getGsum() + goods.getSupply());
            goods.setGnumber(goods.getGnumber() + goods.getSupply());
            if(goods.getGnumber() > 0)
            {
                goods.setSellstate("未售空");
            }
            this.goodsMapper.updateOne(goods);
            flag = true;
        }
        if (flag)
        {
            return RuleUtil.success();
        }
        else
        {
            return RuleUtil.error(codeConstants.Code_500,"补货失败");
        }
    }
    //批量下架
    public RuleUtil delListGoods(List<Goods> delList)
    {
        Boolean flag = false;
        if(!CollectionUtils.isEmpty(delList))
        {
            this.goodsMapper.delList(delList);
            flag = true;
        }
        if(flag)
            return RuleUtil.success();
        else
            return RuleUtil.error(codeConstants.Code_500,"删除失败");
    }

    public RuleUtil setDiscount(double discount) {

        discount = discount /100 ;
        this.goodsMapper.setDiscount(discount);
        return RuleUtil.success();
    }
}
