package com.authservice.controller;

import com.commons.config.aop.annotation.WebLog;
import com.commons.dto.LoginUserDto;
import com.commons.service.LoginService;
import com.commons.util.JwtUtil;
import com.commons.vo.Result;
import com.commons.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yyh
 * @date 2022/12/24 21:52
 */

@Slf4j
@RestController
public class AuthController {

    @Resource
    private LoginService loginService;

    @WebLog(description = "测试方法")
    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    @WebLog(description = "测试方法2")
    @RequestMapping("/test2")
    public String test2() {
        return "test2";
    }

    @WebLog(description = "管理员方法")
    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    @WebLog(description = "登录接口")
    @PostMapping("/doLogin")
    public Result login(@RequestBody LoginUserDto user) {
        UserVo userVo = loginService.login(user.getUsername(), user.getPassword());

        return Result.success(200, "登录成功", userVo);
    }

    @WebLog(description = "是否过期")
    @PostMapping("/ex")
    public Result login(String token) {
        String username = JwtUtil.getUsernameFromToken(token);

        return Result.success(200, username);
    }

    @PostMapping("/doLogout")
    public Result login() {
        loginService.logout();
        return Result.success(200, "注销成功");
    }

}
