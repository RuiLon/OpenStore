package com.example.bysj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bysj.enity.OrderDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Repository
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {




    //根据外键查询所有订单细节
    List<OrderDetail> readAllDetailByOdscode(@Param ("Odscode")String Odscode);

    //跟新订单信息
    void updateOne(@Param("orderDetail") OrderDetail orderDetail);
}
