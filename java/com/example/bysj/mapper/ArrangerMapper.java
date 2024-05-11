package com.example.bysj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bysj.enity.Arranger;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.ARG_IN;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArrangerMapper extends BaseMapper<Arranger> {

    //管理员登录校验
    Arranger logincheck(@Param("name") String name, @Param("password") String password);

    //根据管理员名称查找管理员
    Arranger readByName(@Param("name") String name);

    //修改管理员信息
    void updateOne(@Param("arranger") Arranger arranger);

    //注销管理员
    void delArranger(@Param("arranger") Arranger arranger);

    //批量注销管理员
    void delList(@Param("delList") List<Arranger> delList);

    //获取角色唯一标识
    List<Arranger> getKey();

    //获取身份id
    Integer readRoleid(@Param("stage") String stage);
}
