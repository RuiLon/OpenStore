package com.example.bysj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bysj.enity.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper extends BaseMapper<Order> {

    //查询所有订单
    List<Order> getAllOrder(@Param("order") Order order);

    //按购买者查询订单
    List<Order> readOrderByCid(@Param("order") Order order);

    //根据流水编号查询订单
    Order readOrderByScode(@Param("scode") String scode);

    //结清当前订单
    void updateOne(@Param("order") Order order);

    //更新当前订单
    void updateOrder(@Param("order") Order order);
}
