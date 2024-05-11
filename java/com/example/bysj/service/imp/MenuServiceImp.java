package com.example.bysj.service.imp;

import com.alibaba.druid.sql.ast.statement.SQLForeignKeyImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bysj.constants.codeConstants;
import com.example.bysj.enity.Menu;
import com.example.bysj.mapper.MenuMapper;
import com.example.bysj.service.infc.MenuInfc;
import com.example.bysj.util.RuleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImp extends ServiceImpl<MenuMapper, Menu> implements MenuInfc {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 新增菜单
     * @param menu
     * @return
     */
    public RuleUtil addNewMenu(Menu menu)
    {
        if (!ObjectUtils.isEmpty(menu))
        {
            this.menuMapper.insert(menu);
            return RuleUtil.success();
        }
        else
        {
            return RuleUtil.error(codeConstants.Code_400,"参数为空");
        }
    }

    /**
     * 更新菜单数据
     * @param menu
     * @return
     */
    public RuleUtil updateMenu(Menu menu)
    {
        if (!ObjectUtils.isEmpty(menu))
        {
            this.menuMapper.updateMenu(menu);
            return RuleUtil.success();
        }
        else
        {
            return RuleUtil.error(codeConstants.Code_400,"参数为空");
        }
    }

    /**
     * 删除菜单
     * @param menu
     * @return
     */
    public RuleUtil delMenu(Menu menu)
    {
        if (!ObjectUtils.isEmpty(menu))
        {
            this.menuMapper.delMenu(menu);
            return RuleUtil.success();
        }
        else
        {
            return RuleUtil.error(codeConstants.Code_400,"参数为空");
        }
    }

    /**
     * 批量删除菜单
     * @param delList
     * @return
     */
    public RuleUtil delList(List<Menu> delList)
    {
        if (!CollectionUtils.isEmpty(delList))
        {
            this.menuMapper.delList(delList);
            return RuleUtil.success();
        }
        else
        {
            return RuleUtil.error(codeConstants.Code_400,"参数为空");
        }
    }

    /**
     * 获取全部菜单
     * @param PageNumber
     * @param PageSize
     * @return
     */
    public RuleUtil readAllMenu(Integer PageNumber,Integer PageSize)
    {
        IPage page = new Page(PageNumber,PageSize);
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("idel",0);
        queryWrapper.orderByAsc("id");
        return RuleUtil.success(this.menuMapper.selectPage(page,queryWrapper));
    }

    public List<Menu> readAll()
    {
        List<Menu> list = this.menuMapper.readAll();
        return this.filteMenu(list);
    }
    /**
     * 过滤菜单
     * @param list
     * @return
     */
    List<Menu> filteMenu(List<Menu> list)
    {
        List<Menu> parentNode = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        for (Menu menu : parentNode)
        {
            menu.setChildren(list.stream().filter(mC -> menu.getId().equals(mC.getPid())).collect(Collectors.toList()));
            for (Menu sonMenu : menu.getChildren())
            {
                sonMenu.setChildren(list.stream().filter(mCs -> sonMenu.getId().equals(mCs.getPid())).collect(Collectors.toList()));
            }
        }
        return parentNode;
    }

}
