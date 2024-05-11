package com.example.bysj.param;

import com.example.bysj.enity.Books;
import com.example.bysj.enity.Goods;
import com.example.bysj.enity.Order;
import lombok.Data;

import java.util.List;

@Data
public class OrderParam extends Order {

    //批量删除订单
    private List<Order> delList;

    //批量伪删除订单
    private List<Order> vdelList;

    //商品集合
    private List<Goods> goodsList;

    //书籍集合
    private List<Books> booksList;

}
