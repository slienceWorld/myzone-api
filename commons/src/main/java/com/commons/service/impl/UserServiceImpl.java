package com.commons.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.commons.constant.UserConstants;
import com.commons.domain.Role;
import com.commons.domain.User;
import com.commons.domain.UserRole;
import com.commons.mapper.RoleMapper;
import com.commons.mapper.UserMapper;
import com.commons.mapper.UserRoleMapper;
import com.commons.service.UserService;
import com.commons.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author DELL
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-02-26 17:06:43
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserByName(String username) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, username);
        return this.getOne(lambdaQueryWrapper);
    }

    @Override
    public String checkUsernameUnique(User user) {
        Long userId = ObjectUtil.isEmpty(user.getId()) ? -1 : user.getId();
        User unique = userMapper.checkUsernameUnique(user.getUsername());
        if (ObjectUtil.isNotEmpty(unique) && !unique.getId().equals(userId)) {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    @Override
    public Result registerUser(User user) {
        List<Role> roles = user.getRoles();
        if (CollUtil.isNotEmpty(roles)) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userMapper.insert(user);
            Stream<Long> roleIds = roles.stream().map(Role::getId);
            roleIds.forEach(roleId -> {
                Role role = roleMapper.selectById(roleId);
                if (role != null) {
                    Long userId = user.getId();
                    UserRole userRole = new UserRole();
                    userRole.setUid(userId);
                    userRole.setRid(roleId);
                    userRoleMapper.insert(userRole);
                }
            });
            return Result.success("添加成功");
        }
        return Result.fail("添加失败，角色信息为空");
    }


}




