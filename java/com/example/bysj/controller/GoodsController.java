package com.example.bysj.controller;


import com.example.bysj.enity.Goods;
import com.example.bysj.service.imp.GoodsServiceImp;
import com.example.bysj.util.RuleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsServiceImp goodsServiceImp;

    /**
     * 获取所有商品
     * @param PageNumber
     * @param PageSize
     * @param gname
     * @param gdesciber
     * @param gcode
     * @return
     */
    @GetMapping("/getAllGoods")
    public RuleUtil getAllGoods(@RequestParam Integer PageNumber,
                                @RequestParam Integer PageSize,
                                @RequestParam String gname,
                                @RequestParam String gdesciber,
                                @RequestParam String gcode) {
        return this.goodsServiceImp.getAllGoods(PageNumber, PageSize, gname, gdesciber, gcode);
    }

    /**
     * 批量下架商品
     * @param deList
     * @return
     */
    @PostMapping("/delListGoods")
    public RuleUtil delListGoods(@RequestBody List<Goods> deList)
    {
        return this.goodsServiceImp.delListGoods(deList);
    }

    /**
     * 下架当前商品
     * @param goods
     * @return
     */
    @PostMapping("/delgoods")
    public RuleUtil delgoods(@RequestBody(required = false) Goods goods)
    {
        return this.goodsServiceImp.delgoods(goods);
    }

    /**
     * 更新商品信息
     * @param goods
     * @return
     */
    @PutMapping("/updateGoods")
    public RuleUtil updateGoods(@RequestBody(required = false) Goods goods)
    {
        return this.goodsServiceImp.updateGoods(goods);
    }

    /**
     * 商品补货
     * @param goods
     * @return
     */
    @PutMapping("/supplyGoods")
    public RuleUtil supplyGoods(@RequestBody(required = false) Goods goods)
    {
        return this.goodsServiceImp.supplyGoods(goods);
    }

    @PostMapping("/addGoods")
    public RuleUtil addGoods(@RequestBody(required = false) Goods goods)
    {
        return this.goodsServiceImp.addGoods(goods);
    }

    @PutMapping("/setDiscount")
    public RuleUtil setDiscount(@RequestBody(required = false) Double discount)
    {
        return this.goodsServiceImp.setDiscount(discount);
    }
}
