package com.example.bysj.enity;

import lombok.Data;

import java.util.Date;

@Data
public class Borret {


    private Integer id;

    private String borscode;

    private Integer borrowcid;

    private String borphone;

    private Integer bordays;

    private String retscode;

    private Date dborrow;

    private Date dreturn;

    private String borname;

    private String bookname;

    private String bookscode;

    private Integer bookid;

    private String retstate;


}
