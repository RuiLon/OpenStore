package com.example.bysj.enity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Consumer{

    //用户主键id
    private Integer cid;
    //用户名
    private String sname;
    //用户密码
    private String password;
    //用户手机号
    private String phonenumber;
    //用户账号
    private String scode;
    //用户账户
    private Double iaccount;
    //用户创建日期
    private Date dcreate;
    //用户地址
    private String address;
    //用户伪删除标记
    @JsonIgnore
    private Integer idel;
    //用户会员等级
    private Integer istage;
    //用户充值总金额
    private Double isumcount;
    //用户最近充值金额
    private Double idepositcount;
    //用户所享折扣
    private String discount;
    //用户登录验证令牌
    private String token;
    //用户头像地址
    private String avatarurl;
    //用户角色标记字
    private String role;
    //用户拥有页面路由集合
    @TableField(exist = false)
    private List<Menu> menuList;

}
