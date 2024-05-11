package com.example.bysj;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class BysjApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void testset()
    {
        redisTemplate.boundValueOps("name").set("zhangsan");
    }

    @Test
    public void testget()
    {
        Object name = redisTemplate.boundValueOps("name").get();
        System.out.println(name);
    }
    @Test
    void contextLoads() {
    }


}
