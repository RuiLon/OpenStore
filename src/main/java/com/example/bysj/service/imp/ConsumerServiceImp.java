package com.example.bysj.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bysj.constants.codeConstants;
import com.example.bysj.enity.Menu;
import com.example.bysj.enity.Rolemenu;
import com.example.bysj.mapper.ArrangerMapper;
import com.example.bysj.mapper.ConsumerMapper;
import com.example.bysj.mapper.MenuMapper;
import com.example.bysj.mapper.RoleMenuMapper;
import com.example.bysj.param.ConsumerParam;
import com.example.bysj.service.infc.ConsumerInfc;
import com.example.bysj.enity.Consumer;
import com.example.bysj.util.RedisUtil;
import com.example.bysj.util.RuleUtil;
import com.example.bysj.util.TokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
public class ConsumerServiceImp extends ServiceImpl<ConsumerMapper,Consumer> implements ConsumerInfc {


    @Autowired
    private ConsumerMapper consumerMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private ArrangerMapper arrangerMapper;

    @Autowired
    private MenuServiceImp menuServiceImp;

    @Autowired
    private MenuMapper menuMapper;

    public static Map<String,Integer> maps = new HashMap<String,Integer>();

    @Autowired
    private MapsServiceImp mapsServiceImp;

    //添加新用户
    public RuleUtil addConsumer( Consumer consumer)
    {
        if(!ObjectUtils.isEmpty(consumer))
        {
            List<Consumer> getlist = this.consumerMapper.readByPhone(consumer);
            if(CollectionUtils.isEmpty(getlist))
            {
                RedisUtil redisUtil = new RedisUtil();
                //生成流水号
                String scode = "ZCYH";
                //初始化map容器
                if(maps.isEmpty())
                {
                    this.mapsServiceImp.setMaps(0,maps);
                }
                String num = redisUtil.incr(maps,0,mapsServiceImp);
                scode = scode + num;
                consumer.setScode(scode);//设置流水号
                consumer.setDcreate(new Date());//设置创建日期
                if(ObjectUtils.isEmpty(consumer.getPassword()))
                    consumer.setPassword("123456");//设置初始密码
                if(ObjectUtils.isEmpty(consumer.getRole()))
                    consumer.setRole("ROLE_CONSUMER");
                this.consumerMapper.insert(deposit(consumer));
                return RuleUtil.success();
            }
            else
            {
                return RuleUtil.error("600","此用户号码已被注册");
            }
        }
        else
        {
            return RuleUtil.error("600","参数为空");
        }
    }

    //删除用户
    public RuleUtil delConsumer( Consumer consumer)
    {
        if(!ObjectUtils.isEmpty(consumer))
        {
            this.consumerMapper.delConsumer(consumer);
            return RuleUtil.success();
        }
        else
        {
            return RuleUtil.error("400","参数为空");
        }

    }
    //批量删除用户
    public RuleUtil delListConsumer(List<Consumer> delList)
    {
        if(!CollectionUtils.isEmpty(delList))
        {
            this.consumerMapper.delListConsumer(delList);
            return RuleUtil.success();
        }
        else
        {
            return RuleUtil.error("400","参数为空");
        }

    }

    //查询所有用户
    public RuleUtil readAll(Integer PageNumber,Integer PageSize,String sname,String phonenumber,String scode)
    {
        IPage page= new Page(PageNumber,PageSize);
        QueryWrapper<Consumer> queryWrapper = new QueryWrapper<>();
        if(sname !=null || sname !="")
            queryWrapper.like("sname" ,sname);
        if(phonenumber !=null || phonenumber !="")
            queryWrapper.like("phonenumber",phonenumber);
        if(scode !=null || scode !="")
            queryWrapper.like("scode",scode);
        queryWrapper.eq("idel",0);
        queryWrapper.eq("role","ROLE_CONSUMER");
        queryWrapper.orderByDesc("cid");
        return RuleUtil.success(this.consumerMapper.selectPage(page,queryWrapper));
    }

    //查询所有管理员
    public RuleUtil readAllAdmin(Integer PageNumber,Integer PageSize)
    {
        IPage page= new Page(PageNumber,PageSize);
        QueryWrapper<Consumer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("idel",0);
        queryWrapper.eq("role","ROLE_ADMIN");
        queryWrapper.orderByDesc("cid");
        return RuleUtil.success(this.consumerMapper.selectPage(page,queryWrapper));
    }
    //更新用户数据
    public RuleUtil updateConsumer( Consumer consumer)
    {
        if(!ObjectUtils.isEmpty(consumer))
        {
            this.consumerMapper.updateOne(consumer);
            return RuleUtil.success();
        }
        else
        {
            return RuleUtil.error("400","参数为空");
        }

    }

    //用户充值
    public RuleUtil consumerDeposit( Consumer consumer)
    {
        if(!ObjectUtils.isEmpty(consumer))
        {
            this.consumerMapper.updateOne(deposit(consumer));
            return RuleUtil.success();
        }
        else
        {
            return RuleUtil.error("400","参数为空");
        }

    }

    //登录
    public RuleUtil login(ConsumerParam consumerParam)
    {
        if(!ObjectUtils.isEmpty(consumerParam))//判断接受参数是否为空
        {
            List<Consumer> list = this.consumerMapper.getList(consumerParam);
            if(!CollectionUtils.isEmpty(list))//判断用户是否存在
            {
                Consumer consumer = this.consumerMapper.login(consumerParam);
                if (!ObjectUtils.isEmpty(consumer))//判断密码是否正确
                {
                    String token = TokenUtil.genToken(consumer.getCid().toString(),consumer.getPassword());
                    consumer.setToken(token);

                    String role = consumer.getRole();

                    //获取当前登陆人的身份id
                    int roleid = this.arrangerMapper.readRoleid(role);


                    consumer.setMenuList(this.menuServiceImp.filteMenu(this.menuMapper.getMenuByRoleId(roleid)));
                    return RuleUtil.success(consumer);
                }
                else
                {
                    return RuleUtil.error(codeConstants.Code_600,"密码错误");
                }
            }
           else
            {
                return RuleUtil.error(codeConstants.Code_600,"用户不存在");
            }
        }
        else
        {
            return RuleUtil.error(codeConstants.Code_400,"参数为空");
        }
    }

    //注册
    public RuleUtil register(Consumer consumer)
    {
        return this.addConsumer(consumer);
    }
    //充值方法函数
    public Consumer deposit( Consumer consumer)
    {
        //判断用户注册时是否充钱
        if(ObjectUtils.isEmpty(consumer.getIaccount()))
        {
            consumer.setIaccount(0.0);
            consumer.setIsumcount(0.0);
            consumer.setIdepositcount(0.0);
        }
        if(ObjectUtils.isEmpty(consumer.getIsumcount()) || ObjectUtils.isEmpty(consumer.getIdepositcount()))
        {
            consumer.setIsumcount(consumer.getIaccount());
            consumer.setIdepositcount(consumer.getIaccount());
        }
        else
        {
            consumer.setIsumcount(consumer.getIsumcount() + consumer.getIdepositcount());
            consumer.setIaccount(consumer.getIaccount()+ consumer.getIdepositcount());
        }
       double count = consumer.getIsumcount() + 0.50;
       int icount = (new Double(count).intValue()>=0)&(new Double(count).intValue()<100)?0:
                    (new Double(count).intValue()>=100)&(new Double(count).intValue()<200)?1:
                    (new Double(count).intValue()>=200)&(new Double(count).intValue()<500)?2:
                    (new Double(count).intValue()>=500)&(new Double(count).intValue()<1000)?3:
                    (new Double(count).intValue()>=1000)&(new Double(count).intValue()<2000)?4:5;
       //设置用户折扣等级
       switch (icount)
       {
           case 0:
                consumer.setIstage(0);
                break;
           case 1:
                consumer.setIstage(1);
                break;
           case 2:
                consumer.setIstage(2);
                break;
           case 3:
                consumer.setIstage(3);
                break;
           case 4:
                consumer.setIstage(4);
                break;
           case 5:
                consumer.setIstage(5);
                break;
       }
       consumer.setDiscount( 5*consumer.getIstage() + "%");
       return consumer;
    }

    //根据当前用户手机号查询用户
    public RuleUtil readOneByPhone(String phone)
    {
        if (!ObjectUtils.isEmpty(phone))
        {
            Consumer consumer =this.consumerMapper.readOneByPhone(phone);
            String role = consumer.getRole();
            int roleid = this.arrangerMapper.readRoleid(role);
            consumer.setMenuList(this.menuServiceImp.filteMenu(this.menuMapper.getMenuByRoleId(roleid)));
            String token = TokenUtil.genToken(consumer.getCid().toString(),consumer.getPassword());
            consumer.setToken(token);
            return RuleUtil.success(consumer);
        }
        else
        {
            return RuleUtil.error("400","参数为空");
        }

    }

    //忘记密码
    public RuleUtil forgetPassword(Consumer consumer)
    {
        Consumer consumer1 = this.consumerMapper.forgetPasswordCheck(consumer.getScode(),consumer.getPhonenumber());
        if (!ObjectUtils.isEmpty(consumer1))
        {
            consumer.setCid(consumer1.getCid());
            this.consumerMapper.updateOne(consumer);
            return RuleUtil.success();
        }
        else
        {
            return RuleUtil.error(codeConstants.Code_500,"用户不存在");
        }

    }
}
