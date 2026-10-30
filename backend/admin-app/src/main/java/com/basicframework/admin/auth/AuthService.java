package com.basicframework.admin.auth;

import com.basicframework.admin.auth.dto.LoginRequest;
import com.basicframework.admin.auth.dto.LoginResponse;
import com.basicframework.admin.entity.AdminUser;
import com.basicframework.admin.mapper.AdminUserMapper;
import com.basicframework.common.exception.BusinessException;
import com.basicframework.common.result.ErrorCode;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 登录服务：账号密码校验通过后由 Sa-Token 签发 token。
 */
@Service
public class AuthService {

    private final AdminUserMapper adminUserMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AdminUserMapper adminUserMapper, PasswordEncoder passwordEncoder) {
        this.adminUserMapper = adminUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest request) {
        AdminUser user = adminUserMapper.selectOne(
                Wrappers.<AdminUser>lambdaQuery().eq(AdminUser::getUsername, request.username()));

        if (user == null || !passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessException(ErrorCode.BAD_CREDENTIALS);
        }
        if (user.getStatus() == null || user.getStatus() != 1) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "账号已被禁用");
        }

        StpUtil.login(user.getId());
        return new LoginResponse(StpUtil.getTokenName(), StpUtil.getTokenValue(), user.getNickname());
    }
}
