package com.example.bysj.controller;

import com.example.bysj.enity.Menu;
import com.example.bysj.service.imp.MenuServiceImp;
import com.example.bysj.util.RuleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("menus")
public class MenuController {

    @Autowired
    private MenuServiceImp menuServiceImp;


    @GetMapping("/readAllMenu")
    public RuleUtil readAllMenu(@RequestParam Integer PageNumber,@RequestParam Integer PageSize)
    {
        return this.menuServiceImp.readAllMenu(PageNumber,PageSize);
    }

    @GetMapping("readAll")
    public RuleUtil readAll()
    {
        return RuleUtil.success(this.menuServiceImp.readAll());
    }

    @PostMapping("/addNewMenu")
    public RuleUtil addNewMenu(@RequestBody(required = false) Menu menu)
    {
        return this.menuServiceImp.addNewMenu(menu);
    }

    @PutMapping("/updateMenu")
    public RuleUtil updateMenu(@RequestBody(required = false) Menu menu)
    {
        return this.menuServiceImp.updateMenu(menu);
    }

    @PutMapping("/delMenu")
    public RuleUtil delMenu(@RequestBody(required = false) Menu menu)
    {
        return this.menuServiceImp.delMenu(menu);
    }

    @PutMapping("/delList")
    public RuleUtil delList(@RequestBody(required = false) List<Menu> delList)
    {
        return this.menuServiceImp.delList(delList);
    }
}
