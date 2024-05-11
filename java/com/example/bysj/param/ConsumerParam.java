package com.example.bysj.param;

import com.example.bysj.enity.Consumer;
import lombok.Data;


@Data
public class ConsumerParam extends Consumer {


    private String consumerCondition;

    private Integer total;

    private Integer PageNumber;

    private Integer PageSize;


}
