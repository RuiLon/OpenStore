package com.example.bysj.controller;

import com.example.bysj.enity.Arranger;
import com.example.bysj.service.imp.ArrangerServcieImp;
import com.example.bysj.service.imp.RoleMenuServiceImp;
import com.example.bysj.util.RuleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("arrangers")
public class ArrangerController {

    @Autowired
    private ArrangerServcieImp arrangerServcieImp;

    @Autowired
    private RoleMenuServiceImp roleMenuServiceImp;

    @GetMapping("/logincheck")
    public RuleUtil logincheck(@RequestParam String name,@RequestParam String password)
    {
        return this.arrangerServcieImp.logincheck(name,password);
    }

    @GetMapping("/readAllArranger")
    public RuleUtil readAllArranger(@RequestParam Integer PageNumber,
                                    @RequestParam Integer PageSize,
                                    @RequestParam String name)
    {
        return this.arrangerServcieImp.readAllArranger(PageNumber,PageSize,name);
    }

    @PostMapping("/addArranger")
    public RuleUtil addArranger(@RequestBody(required = false)Arranger arranger)
    {
        return this.arrangerServcieImp.addArranger(arranger);
    }

    @PutMapping("/updateArranger")
    public RuleUtil updateArranger(@RequestBody(required = false) Arranger arranger)
    {
        return this.arrangerServcieImp.updateArranger(arranger);
    }

    @PutMapping("/delArranger")
    public RuleUtil delArranger(@RequestBody(required = false) Arranger arranger)
    {
        return this.arrangerServcieImp.delArranger(arranger);
    }

    @PutMapping("/delList")
    public RuleUtil delList(@RequestBody(required = false) List<Arranger> delList)
    {
        return this.arrangerServcieImp.delList(delList);
    }

    @PostMapping("/setRoleMenu/{arrangerid}")
    public RuleUtil setRoleMenu(@PathVariable Integer arrangerid, @RequestBody List<Integer> menuids)
    {
        this.roleMenuServiceImp.setRoleMenu(arrangerid,menuids);
        return RuleUtil.success();
    }

    @GetMapping("/getRoleMenu")
    public RuleUtil getRoleMenu(@RequestParam Integer roleid)
    {
        return this.roleMenuServiceImp.getRoleMenu(roleid);
    }

    @GetMapping("/getKey")
    public RuleUtil getKey()
    {
        return this.arrangerServcieImp.getKey();
    }
}
