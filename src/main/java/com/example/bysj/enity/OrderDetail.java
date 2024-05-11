package com.example.bysj.enity;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDetail {
    //订单细节主键id
    private Integer odid;
    //订单细节编号(同父表中订单流水号)
    private String odscode;
    //商品类型
    private Integer goodtype;
    //外键
    private Integer ilinkno;
    //商品数目
    private Integer goodnum;
    //商品id
    private Integer goodid;
    //商品名称
    private String goodname;
    //订单细节创建日期
    private Date dcreate;
    //是否结清状态字
    private Integer itype;
    //是否结清
    private String stype;
    //商品价格
    private Double goodprice;

}
