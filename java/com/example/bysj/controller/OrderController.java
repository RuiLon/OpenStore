package com.example.bysj.controller;

import com.example.bysj.enity.Goods;
import com.example.bysj.enity.Order;
import com.example.bysj.enity.OrderDetail;
import com.example.bysj.service.imp.OrderServiceImp;
import com.example.bysj.util.RuleUtil;
import org.apache.tomcat.util.digester.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderServiceImp orderServiceImp;

    @GetMapping("/getAllOrder")
    public RuleUtil getAllOrder(@RequestParam Integer PageNumber,
                                @RequestParam Integer PageSize,
                                @RequestParam String cphone,
                                @RequestParam String consumername,
                                @RequestParam(required = false) Integer isettle)
    {
        return this.orderServiceImp.getAllOrder(PageNumber,PageSize,cphone,consumername,isettle);
    }

    @GetMapping("/getAllSettleOrder")
    public RuleUtil getAllSettleOrder(@RequestParam Integer oDPageNumber,
                                      @RequestParam Integer oDPageSize,
                                      @RequestParam String oDscode,
                                      @RequestParam(required = false) Integer itype)
    {
        return this.orderServiceImp.getAllSettleOrder(oDPageNumber,oDPageSize,oDscode,itype);
    }

    @PutMapping("/settleOrderDetail")
    public RuleUtil settleOrderDetail(@RequestBody(required = false) OrderDetail orderDetail)
    {
        return this.orderServiceImp.settleOrderDetail(orderDetail);
    }

    @PostMapping("/setOrder/{phone}")
    public RuleUtil setOrder(@PathVariable String phone, @RequestBody List<Goods> goodList)
    {
        RuleUtil ruleUtil = new RuleUtil();
        synchronized (this)
        {
            ruleUtil = this.orderServiceImp.setOrder(phone,goodList);
        }
        return ruleUtil;
    }

    @PutMapping("/settleAll")
    public RuleUtil settleAll(@RequestBody(required = false) Order order)
    {
        return this.orderServiceImp.settleAll(order);
    }
}
