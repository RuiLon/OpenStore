package com.example.bysj.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bysj.enity.Menu;
import com.example.bysj.enity.Rolemenu;
import com.example.bysj.mapper.MenuMapper;
import com.example.bysj.mapper.RoleMenuMapper;
import com.example.bysj.service.infc.RoleMenuInfc;
import com.example.bysj.util.RuleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleMenuServiceImp extends ServiceImpl<RoleMenuMapper, Rolemenu> implements RoleMenuInfc {


    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuMapper menuMapper;

    public void setRoleMenu(Integer roleid, List<Integer> list)
    {
        this.roleMenuMapper.delMenuIdByRoleId(roleid);
        for (Integer menuid : list)
        {
            Rolemenu rolemenu = new Rolemenu();
            rolemenu.setRoleid(roleid);
            rolemenu.setMenuid(menuid);
            this.roleMenuMapper.insert(rolemenu);
        }
    }

    public RuleUtil getRoleMenu(Integer roleid)
    {
        List<Integer> list = new ArrayList<>();
        for (Rolemenu rolemenu : this.roleMenuMapper.getlistByRoleId(roleid))
        {
            list.add(rolemenu.getMenuid());
        }
        return RuleUtil.success(list);
    }


}
