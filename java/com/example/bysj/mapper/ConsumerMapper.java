package com.example.bysj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bysj.enity.Consumer;
import com.example.bysj.param.ConsumerParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Repository
public interface ConsumerMapper extends BaseMapper<Consumer> {

    /**
     * 按id单个查询
     * @param id
     * @return
     */
    Consumer readOne(@Param("id") Integer id);

    /**
     * 根据手机号查询
     * @param consumer
     * @return
     */
    List<Consumer> readByPhone(@Param("consumer") Consumer consumer);
    /**
     * 查询全部消费者
     * @return
     */
    List<Consumer> readAllConsumer(@Param("PageNumber") Integer PageNumber, @Param("PageSize") Integer PageSize);

    /**
     * 获取总条数
     * @return
     */
    Integer readCount();

    /**
     *
     * @param consumer
     */
    void updateOne(@Param("consumer") Consumer consumer);

    /**
     * 删除接口
     * @param consumer
     */
    void delConsumer(@Param("consumer") Consumer consumer);

    /**
     * 批量删除接口
     * @param delList
     */
    void delListConsumer(@Param("delList") List<Consumer> delList);

    /**
     * 登录校验
     * @param consumerParam
     * @return
     */
    Consumer login(@Param("consumerParam")ConsumerParam consumerParam);

    /**
     * 查询登陆人信息
     * @param consumerParam
     * @return
     */
    List<Consumer> getList(@Param("consumerParam") ConsumerParam consumerParam);

    /**
     * 根据电话查询用户
     * @param phone
     * @return
     */
    Consumer readOneByPhone(@Param("phone") String phone);

    /**
     * 用户忘记密码的条件检测
     * @param scode
     * @param phonenumber
     * @return
     */
    Consumer forgetPasswordCheck(@Param("scode") String scode,@Param("phonenumber") String phonenumber);
}
