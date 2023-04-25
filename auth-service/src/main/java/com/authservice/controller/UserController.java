package com.authservice.controller;

import com.commons.constant.UserConstants;
import com.commons.domain.User;
import com.commons.service.UserService;
import com.commons.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author yyh
 * @date 2023/2/26 20:29
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    @RequestMapping("/create")
    public Result createUser(@RequestBody User user) {
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkUsernameUnique(user))) {
            return Result.fail("用户名已存在");
        }
        return userService.registerUser(user);
    }

    @RequestMapping("/hello")
    public String hello() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        log.info("==========>>当前角色:{}", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return "hello," + name;
    }

    @RequestMapping("/error")
    public String error() {
        return "error";
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }
}
