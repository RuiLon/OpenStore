package com.example.bysj.enity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

@Data
public class Menu {

    private Integer id;

    private String name;

    private String path;

    private String icon;

    private String description;

    private Integer idel;

    private Integer pid;

    private String pagepath;

    @TableField(exist = false)
    private List<Menu> children;
}
