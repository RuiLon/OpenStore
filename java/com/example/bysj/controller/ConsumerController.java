package com.example.bysj.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.bysj.config.WebMvcConfig;
import com.example.bysj.enity.Consumer;
import com.example.bysj.param.ConsumerParam;
import com.example.bysj.service.imp.ConsumerServiceImp;
import com.example.bysj.util.RuleUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/consumers")
public class ConsumerController {

    @Autowired
    private ConsumerServiceImp consumerServiceImp;


    /**
     * 获取所有用户信息
     * @param PageNumber
     * @param PageSize
     * @param sname
     * @param phonenumber
     * @param scode
     * @return
     */
    @GetMapping("/getAllConsumer")
    public RuleUtil getAllConsumer(
            @RequestParam Integer PageNumber ,
            @RequestParam Integer PageSize ,
            @RequestParam String sname,
            @RequestParam String phonenumber,
            @RequestParam String scode)
    {
        return this.consumerServiceImp.readAll(PageNumber,PageSize,sname,phonenumber,scode);
    }

    @GetMapping("/readAllAdmin")
    public RuleUtil readAllAdmin(@RequestParam Integer PageNumber ,
                                 @RequestParam Integer PageSize)
    {
        return this.consumerServiceImp.readAllAdmin(PageNumber,PageSize);
    }


    /**
     * 添加新用户
     * @return
     */
    @PostMapping("/addConsumer")
    public RuleUtil addConsumer(@RequestBody(required=false) Consumer consumer)
    {
         return consumerServiceImp.addConsumer(consumer);
    }

    /**
     *修改用户数据
     * @return
     */
    @PutMapping("/updateConsumer")
    public RuleUtil updateConsumer(@RequestBody(required=false) Consumer consumer)
    {
        return consumerServiceImp.updateConsumer(consumer);
    }

    /**
     * 用户充值
     * @param consumer
     * @return
     */
    @PutMapping("/consumerDeposit")
    public RuleUtil consumerDeposit(@RequestBody(required = false) Consumer consumer)
    {
        return consumerServiceImp.consumerDeposit(consumer);
    }

    @PostMapping("/delConsumer")
    public RuleUtil delConsumer(@RequestBody(required = false) Consumer consumer)
    {
        return consumerServiceImp.delConsumer(consumer);
    }

    /**
     * 批量删除用户数据
     * @return
     */
    @PostMapping("/delListConsumer")
    public RuleUtil delListConsumer(@RequestBody List<Consumer> delList)
    {
        return consumerServiceImp.delListConsumer(delList);
    }

    /**
     * 用户登录接口
     * @param consumerParam
     * @return
     */
    @PostMapping("/login")
    public RuleUtil login(@RequestBody ConsumerParam consumerParam)
    {
        return this.consumerServiceImp.login(consumerParam);
    }

    @PostMapping("/register")
    public RuleUtil register(@RequestBody Consumer consumer)
    {
        return this.consumerServiceImp.register(consumer);
    }

    @GetMapping("/readOneByPhone/{phone}")
    public RuleUtil readOneByPhone(@PathVariable String phone)
    {
        return this.consumerServiceImp.readOneByPhone(phone);
    }

    @PutMapping("/forgetPassword")
    public RuleUtil forgetPassWord(@RequestBody Consumer consumer) {
        return this.consumerServiceImp.forgetPassword(consumer);
    }
}
