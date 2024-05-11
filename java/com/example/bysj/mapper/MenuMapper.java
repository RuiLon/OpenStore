package com.example.bysj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bysj.enity.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    //获取全部菜单数据
    List<Menu> readAll();

    //删除菜单
    void delMenu(@Param("menu") Menu menu);

    //批量删除
    void delList(@Param("delList") List<Menu> delList);

    //修改菜单
    void updateMenu(@Param("menu") Menu menu);

    List<Menu> getMenuByRoleId(@Param("roleid") Integer roleid);

    Menu readOneById(@Param("id") Integer id);
}
