package com.example.bysj.enity;

import lombok.Data;

import java.util.Date;

@Data
public class Order {
    //订单主键
    private Integer oid;
    //订单流水号
    private String scode;
    //订单创建日期
    private Date dcreate;
    //订单用户id
    private Integer iconsumerid;
    //订单用户名称
    private String consumername;
    //订单总额
    private Double iconsum;
    //是否结清状态字
    private Integer isettle;
    //是否结清
    private String ssettle;
    //订单用户手机号
    private String cphone;

}
