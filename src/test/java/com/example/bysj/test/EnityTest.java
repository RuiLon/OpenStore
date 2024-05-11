package com.example.bysj.test;

import com.example.bysj.enity.Consumer;
import com.example.bysj.mapper.ConsumerMapper;
import org.apache.ibatis.annotations.Param;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EnityTest {

    @Autowired
    private ConsumerMapper consumerMapper;

    @Test
    void updateConsumer()
    {
        Consumer consumer =new Consumer();
        consumer.setCid(50);
        consumer.setPassword("123456");
        consumerMapper.updateById(consumer);
    }

}
