package com.basicframework.admin.config;

import cn.dev33.satoken.stp.StpInterface;
import com.basicframework.admin.mapper.AdminRoleMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 向 Sa-Token 提供用户的角色集合（@SaCheckRole 依赖此实现）。
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    private final AdminRoleMapper adminRoleMapper;

    public StpInterfaceImpl(AdminRoleMapper adminRoleMapper) {
        this.adminRoleMapper = adminRoleMapper;
    }

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return List.of();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return adminRoleMapper.selectRoleCodesByUserId(Long.valueOf(loginId.toString()));
    }
}
