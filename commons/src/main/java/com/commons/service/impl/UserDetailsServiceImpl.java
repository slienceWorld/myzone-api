package com.commons.service.impl;

import com.commons.domain.Role;
import com.commons.domain.User;
import com.commons.dto.UserDto;
import com.commons.mapper.RoleMapper;
import com.commons.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yyh
 * @date 2023/2/26 20:52
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {


    @Resource
    @Lazy
    private UserService userService;

    @Resource
    private RoleMapper roleMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDto loadUserByUsername(String username) {
        User user = userService.getUserByName(username);

        log.info("==========>>查询到用户:{}", user.getUsername());

        List<Role> roles = roleMapper.listRolesByUsername(username);
        user.setRoles(roles);
        UserDto userDto = new UserDto(user);
        log.info("==========>>查询到用户角色:{}", userDto.getAuthorities());
        return userDto;
    }


}
