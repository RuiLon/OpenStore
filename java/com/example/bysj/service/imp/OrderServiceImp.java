package com.example.bysj.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bysj.constants.codeConstants;
import com.example.bysj.enity.Consumer;
import com.example.bysj.enity.Goods;
import com.example.bysj.enity.Order;
import com.example.bysj.enity.OrderDetail;
import com.example.bysj.mapper.ConsumerMapper;
import com.example.bysj.mapper.GoodsMapper;
import com.example.bysj.mapper.OrderDetailMapper;
import com.example.bysj.mapper.OrderMapper;
import com.example.bysj.param.OrderParam;
import com.example.bysj.service.infc.OrderInfc;
import com.example.bysj.util.RedisUtil;
import com.example.bysj.util.RuleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImp extends ServiceImpl<OrderMapper, Order> implements OrderInfc {


    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ConsumerMapper consumerMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    public static Map<String,Integer> maps = new HashMap<String,Integer>();

    @Autowired
    private MapsServiceImp mapsServiceImp;

    @Autowired
    private OrderDetailMapper orderDetialMapper;

    /**
     * 获取全部订单信息
     * @param PageNumber
     * @param PageSize
     * @param cphone
     * @param consumername
     * @return
     */
    public RuleUtil getAllOrder(Integer PageNumber,
                                Integer PageSize,
                                String cphone,
                                String consumername,
                                Integer isettle)
    {
        IPage page = new Page(PageNumber,PageSize);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        if (!cphone.isEmpty())
            queryWrapper.like("cphone",cphone);
        if (!consumername.isEmpty())
            queryWrapper.like("consumername",consumername);
        if (isettle != null)
        {
            queryWrapper.eq("isettle",isettle);
            queryWrapper.orderByDesc("isettle");
        }
        else
        {
            queryWrapper.orderByDesc("dcreate");
        }
        return RuleUtil.success(this.orderMapper.selectPage(page,queryWrapper));
    }

    /**
     * 获取未结清的订单详情信息
     * @param PageNumber
     * @param PageSize
     * @return
     */
    public RuleUtil getAllSettleOrder(Integer PageNumber,
                                        Integer PageSize,
                                        String ODscode,
                                        Integer itype)
    {
        Page page = new Page(PageNumber,PageSize);
        QueryWrapper<OrderDetail> queryWrapper = new QueryWrapper<>();
        if (!ODscode.isEmpty())
            queryWrapper.like("ODscode",ODscode);
        if (itype != null)
        {
            queryWrapper.eq("itype",itype);
            queryWrapper.orderByDesc("itype");
        }
        else
        {
            queryWrapper.orderByDesc("dcreate");
        }
        return RuleUtil.success(this.orderDetialMapper.selectPage(page,queryWrapper));
    }

    /**
     * 结清单条订单
     * @param orderDetail
     * @return
     */
    public RuleUtil settleOrderDetail(OrderDetail orderDetail)
    {
        if (!ObjectUtils.isEmpty(orderDetail))
        {
            if (orderDetail.getItype() == 1)
            {
                Order order = this.orderMapper.readOrderByScode(orderDetail.getOdscode());
                Consumer consumer =new Consumer();
                consumer.setPhonenumber(order.getCphone());
                List<Consumer> consumers = this.consumerMapper.readByPhone(consumer);
                if (!CollectionUtils.isEmpty(consumers))
                {
                    consumer = consumers.get(0);
                }
                Double sum = orderDetail.getGoodprice() * orderDetail.getGoodnum() * (1.00 - (consumer.getIstage() * 0.05));
                if (sum <= consumer.getIaccount())
                {
                    consumer.setIaccount(consumer.getIaccount() - sum);
                    this.consumerMapper.updateOne(consumer);
                    orderDetail.setItype(0);
                    orderDetail.setStype("已结清");
                    this.orderDetialMapper.updateOne(orderDetail);
                    int count = 0;
                    List<OrderDetail> orderDetails = this.orderDetialMapper.readAllDetailByOdscode(orderDetail.getOdscode());
                    for (OrderDetail oD: orderDetails) {
                        if (oD.getItype() == 0)
                        {
                            count ++;
                        }
                    }
                    if (count == orderDetails.size())
                        this.orderMapper.updateOne(order);
                    return RuleUtil.success();
                }
                else
                {
                    return RuleUtil.error(codeConstants.Code_600,"余额不足,结账失败");
                }
            }
            else
            {
                return RuleUtil.error(codeConstants.Code_400,"此单据已结清");
            }

        }
        else
        {
            return RuleUtil.error(codeConstants.Code_500,"结账失败");
        }
    }

    /**
     * 创建订单
     * @param phone
     * @param goodList
     * @return
     */
    public RuleUtil setOrder(String phone, List<Goods> goodList)
    {
        Consumer consumer = this.consumerMapper.readOneByPhone(phone);
        if (!ObjectUtils.isEmpty(consumer))
        {
            if (!CollectionUtils.isEmpty(goodList))
            {
                Order order = new Order();
                List<OrderDetail> orderDetails = new ArrayList<>();
                RedisUtil redisUtil = new RedisUtil();
                //生成流水号
                String scode = "DD";
                //初始化map容器
                if(maps.isEmpty())
                {
                    this.mapsServiceImp.setMaps(1,maps);
                }
                String num = redisUtil.incr(maps,1,mapsServiceImp);
                scode = scode + num;
                order.setScode(scode);
                order.setDcreate(new Date());
                order.setIconsumerid(consumer.getCid());
                order.setConsumername(consumer.getSname());
                order.setCphone(phone);
                this.orderMapper.insert(order);
                order = this.orderMapper.readOrderByScode(scode);
                double consum = 0.0;
                for (Goods goods : goodList)
                {
                    if (this.goodsMapper.getOne(goods).getGnumber() > 0)
                    {
                        OrderDetail orderDetail = new OrderDetail();
                        orderDetail.setOdscode(scode);
                        orderDetail.setIlinkno(order.getOid());
                        orderDetail.setGoodnum(goods.getGpay());
                        orderDetail.setGoodid(goods.getGid());
                        orderDetail.setGoodname(goods.getGname());
                        orderDetail.setDcreate(order.getDcreate());
                        orderDetail.setGoodprice(goods.getGprice());
                        this.orderDetialMapper.insert(orderDetail);
                        consum = consum + goods.getGpay() * goods.getGprice();
                        goods.setGnumber(goods.getGnumber()-goods.getGpay());
                        this.goodsMapper.updateOne(goods);
                    }
                }
                if (consum == 0.0)
                {
                   return RuleUtil.error(codeConstants.Code_400,"库中无商品");
                }
                else
                {
                    order.setIconsum(consum);
                    this.orderMapper.updateOrder(order);
                    return RuleUtil.success();
                }
            }
            else
            {
                return RuleUtil.error(codeConstants.Code_400,"商品为空");
            }
        }
        else
        {
            return RuleUtil.error(codeConstants.Code_500,"此用户不存在");
        }
    }

    /**
     * 结清全部订单
     * @param order
     * @return
     */
    public RuleUtil settleAll(Order order)
    {
        Consumer consumer = this.consumerMapper.readOne(order.getIconsumerid());
        Double sum = order.getIconsum() * (1.00 - (consumer.getIstage() * 0.05));
        if (consumer.getIaccount() >= sum)
        {
            consumer.setIaccount(consumer.getIaccount() - sum);
            this.consumerMapper.updateOne(consumer);
            this.orderMapper.updateOne(order);
            List<OrderDetail> orderDetails = this.orderDetialMapper.readAllDetailByOdscode(order.getScode());
            for (OrderDetail orderDetail : orderDetails)
            {
                orderDetail.setItype(0);
                orderDetail.setStype("已结清");
                this.orderDetialMapper.updateOne(orderDetail);
            }
            return RuleUtil.success();
        }
        else
        {
            return RuleUtil.error(codeConstants.Code_600,"余额不足，请去吧台充值");
        }

    }
}
