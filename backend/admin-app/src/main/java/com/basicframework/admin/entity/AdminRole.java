package com.basicframework.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色。code 为权限判定标识（如 admin / user），被 Sa-Token 的角色注解使用。
 */
@Data
@TableName("admin_role")
public class AdminRole {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String code;

    private String name;
}
