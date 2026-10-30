package com.basicframework.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.basicframework.admin.entity.AdminRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    /**
     * 查询用户拥有的角色标识，供 Sa-Token 鉴权使用。
     */
    @Select("SELECT r.code FROM admin_role r INNER JOIN admin_user_role ur ON ur.role_id = r.id WHERE ur.user_id = #{userId}")
    List<String> selectRoleCodesByUserId(@Param("userId") Long userId);
}
