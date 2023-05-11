package com.commons.service.impl;

import com.commons.dto.UserDto;
import com.commons.service.LoginService;
import com.commons.util.JwtUtil;
import com.commons.util.RedisUtil;
import com.commons.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yyh
 * @date 2023/4/3 18:44
 * @description
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisUtil redisUtil;


    @Override
    public UserVo login(String username, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDto userDto = (UserDto) authenticate.getPrincipal();
        String token = JwtUtil.generateToken(username);
        userDto.setJwtToken(token);
        redisUtil.setExpire("login:" + username, userDto, 3600);

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDto.getUser(),userVo);
        userVo.setJwtToken(token);
        return userVo;
    }

    @Override
    public void logout() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        try {
            redisUtil.del("login:" + username);
        } catch (Exception e) {
            log.error("==========>>{}", e.getMessage());
        }
    }
}
