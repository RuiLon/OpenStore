package com.example.bysj.enity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Goods {

    //商品主键
    private Integer gid;
    //商品类型(目前系统中为非必要字段)
    private Integer gtype;
    //商品编码
    private String gcode;
    //商品名称
    private String gname;
    //商品描述
    private String gdesciber;
    //商品价格
    private Double gprice;
    //商品数量
    private Integer gnumber;
    //商品图片路径
    private String gpicture;
    //商品每日折扣
    private Double todaydiscount;
    //商品是否售空
    private String sellstate;
    //商品补货数量
    private Integer supply;
    //商品总数
    private Integer gsum;
    //商品购买数量
    @TableField(exist = false)
    private Integer gpay;


}
