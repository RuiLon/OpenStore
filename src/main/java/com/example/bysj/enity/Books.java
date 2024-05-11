package com.example.bysj.enity;

import lombok.Data;

@Data
public class Books {

    //书籍主键id
    private Integer bid;
    //书籍名称
    private String bname;
    //书籍价格
    private Double bprice;
    //书籍数量
    private Integer bnumber;
    //书籍作者
    private String bauther;
    //书籍编号
    private String bscode;
    //借空状态
    private String brostate;
    //书籍封面
    private String bpicture;
    //伪删除
    private Integer idel;
}
