package com.example.bysj.param;

import com.example.bysj.enity.Goods;
import lombok.Data;

import java.util.List;

@Data
public class GoodsParam extends Goods {

    //批量删除商品
    private List<Goods> delList;

    //批量添加商品
    private List<Goods> addList;

    //批量更新商品
    private List<Goods> updateList;

    //价格的浮动区间
    //分页操作(页面尺寸)
    private Integer PageSize;

    //分页操作(页面游标)
    private Integer PageNumber;

    //低价格
    private Double lowprice;

    //高价格
    private Double highprice;

}
