package com.example.bysj.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bysj.constants.codeConstants;
import com.example.bysj.enity.Arranger;
import com.example.bysj.mapper.ArrangerMapper;
import com.example.bysj.service.infc.ArrangerInfc;
import com.example.bysj.util.RuleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArrangerServcieImp extends ServiceImpl<ArrangerMapper, Arranger> implements ArrangerInfc {

    @Autowired
    private ArrangerMapper arrangerMapper;

    /**
     * 登录校验
     * @param name
     * @param password
     * @return
     */
    public RuleUtil logincheck(String name, String password)
    {

        if (!name.isEmpty() && !password.isEmpty())
        {
            Arranger arranger = this.arrangerMapper.logincheck(name,password);
            if (!ObjectUtils.isEmpty(arranger))
            {
                return RuleUtil.success(arranger);
            }
            else
            {
                return RuleUtil.error(codeConstants.Code_401,"用户不存在或密码不对");
            }
        }
        else
        {
            return RuleUtil.error(codeConstants.Code_400,"参数为空");
        }
    }

    /**
     * 获取所有管理员
     * @param PageNumber
     * @param PageSize
     * @param name
     * @return
     */
    public RuleUtil readAllArranger(Integer PageNumber,Integer PageSize,String name)
    {
        IPage page= new Page(PageNumber,PageSize);
        QueryWrapper<Arranger> queryWrapper = new QueryWrapper<>();
        if (!name.isEmpty())
            queryWrapper.like("name",name);
        queryWrapper.eq("idel",0);
        queryWrapper.orderByDesc("id");
        return RuleUtil.success(this.arrangerMapper.selectPage(page,queryWrapper));
    }

    /**
     * 新增管理员
     * @param arranger
     * @return
     */
    public RuleUtil addArranger(Arranger arranger)
    {
        if (!ObjectUtils.isEmpty(arranger))
        {
            Arranger arranger1 = this.arrangerMapper.readByName(arranger.getName());
            if (ObjectUtils.isEmpty(arranger1))
            {
                arranger.setPassword(arranger.getName());
                this.arrangerMapper.insert(arranger);
                return RuleUtil.success();
            }
            else
            {
                return RuleUtil.error(codeConstants.Code_600,"已存在当前管理员");
            }
        }
        else
        {
            return RuleUtil.error(codeConstants.Code_400,"参数为空");
        }
    }

    /**
     * 管理员更新数据
     * @param arranger
     * @return
     */
    public RuleUtil updateArranger(Arranger arranger)
    {
        if (!ObjectUtils.isEmpty(arranger))
        {
            this.arrangerMapper.updateOne(arranger);
            return RuleUtil.success();
        }
        else
        {
            return RuleUtil.error(codeConstants.Code_400,"参数为空");
        }
    }

    /**
     * 注销管理员
     * @param arranger
     * @return
     */
    public RuleUtil delArranger(Arranger arranger)
    {
        if (!ObjectUtils.isEmpty(arranger))
        {
            this.arrangerMapper.delArranger(arranger);
            return RuleUtil.success();
        }
        else
        {
            return RuleUtil.error(codeConstants.Code_400,"参数为空");
        }
    }

    public RuleUtil delList(List<Arranger> delList)
    {
        if (!CollectionUtils.isEmpty(delList))
        {
            this.arrangerMapper.delList(delList);
            return RuleUtil.success();
        }
        else
        {
            return RuleUtil.error(codeConstants.Code_400,"参数为空");
        }
    }

    /**
     * 获取角色表
     * @return
     */
    public RuleUtil getKey()
    {
        List<Arranger> list = this.arrangerMapper.getKey();
        list.remove(0);
        return RuleUtil.success(list);
    }
}
