package com.basicframework.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 菜单。本期仅落数据模型，不做菜单管理接口（见 proposal 非目标）。
 */
@Data
@TableName("admin_menu")
public class AdminMenu {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long parentId;

    private String name;

    private String path;

    private Integer sort;
}
