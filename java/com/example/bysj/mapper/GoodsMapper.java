package com.example.bysj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bysj.enity.Goods;
import com.example.bysj.param.GoodsParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Repository
public interface GoodsMapper extends BaseMapper<Goods> {

    //按条件查询商品
    List<Goods> getGoodsByCondition(@Param("goods") GoodsParam goods);

    //批量下架商品
    void delList(@Param("delList") List<Goods> delList);

    //下架当前商品
    void delgoods(@Param("goods") Goods goods);

    //更新当前商品
    void updateOne(@Param("goods") Goods goods);

    //查找当前商品信息
    Goods getOne(@Param("goods") Goods goods);

    void setDiscount(@Param("discount") Double discount);
}
