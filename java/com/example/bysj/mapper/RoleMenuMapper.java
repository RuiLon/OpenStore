package com.example.bysj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bysj.enity.Rolemenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMenuMapper extends BaseMapper<Rolemenu> {


    //根据roleid删除菜单id
    void delMenuIdByRoleId(@Param("roleid") Integer roleid);

    //根据roleid获取菜单id
    List<Rolemenu> getlistByRoleId(@Param("roleid") Integer roleid);
}
