package com.basicframework.admin.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.basicframework.admin.entity.AdminUser;
import com.basicframework.admin.mapper.AdminUserMapper;
import com.basicframework.admin.user.dto.UserCreateRequest;
import com.basicframework.admin.user.dto.UserUpdateRequest;
import com.basicframework.admin.user.dto.UserVO;
import com.basicframework.common.exception.BusinessException;
import com.basicframework.common.result.ErrorCode;
import com.basicframework.common.result.PageResult;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 用户管理业务：分页查询、创建、更新、删除。
 */
@Service
public class AdminUserService {

    private final AdminUserMapper adminUserMapper;
    private final PasswordEncoder passwordEncoder;

    public AdminUserService(AdminUserMapper adminUserMapper, PasswordEncoder passwordEncoder) {
        this.adminUserMapper = adminUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public PageResult<UserVO> page(long page, long size) {
        IPage<AdminUser> result = adminUserMapper.selectPage(
                new Page<>(page, size),
                Wrappers.<AdminUser>lambdaQuery().orderByAsc(AdminUser::getId));
        return PageResult.of(result.getRecords().stream().map(this::toVO).toList(),
                result.getTotal(), result.getCurrent(), result.getSize());
    }

    public UserVO get(Long id) {
        return toVO(requireUser(id));
    }

    public UserVO create(UserCreateRequest request) {
        Long existing = adminUserMapper.selectCount(
                Wrappers.<AdminUser>lambdaQuery().eq(AdminUser::getUsername, request.username()));
        if (existing != null && existing > 0) {
            throw new BusinessException(ErrorCode.USERNAME_EXISTS);
        }

        AdminUser user = new AdminUser();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setNickname(request.nickname());
        user.setStatus(1);
        user.setCreatedAt(LocalDateTime.now());
        adminUserMapper.insert(user);
        return toVO(user);
    }

    public UserVO update(Long id, UserUpdateRequest request) {
        AdminUser user = requireUser(id);
        if (request.nickname() != null) {
            user.setNickname(request.nickname());
        }
        if (request.status() != null) {
            user.setStatus(request.status());
        }
        adminUserMapper.updateById(user);
        return toVO(user);
    }

    public void delete(Long id) {
        requireUser(id);
        adminUserMapper.deleteById(id);
    }

    private AdminUser requireUser(Long id) {
        AdminUser user = adminUserMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "用户不存在：id=" + id);
        }
        return user;
    }

    private UserVO toVO(AdminUser user) {
        return new UserVO(user.getId(), user.getUsername(), user.getNickname(),
                user.getStatus(), user.getCreatedAt());
    }
}
