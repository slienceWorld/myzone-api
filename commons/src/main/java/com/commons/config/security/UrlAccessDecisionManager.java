package com.commons.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author yyh
 * @date 2023/3/27 21:36
 * @description 对访问url进行权限处理认证
 */
@Slf4j
@Component
public class UrlAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)  {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        log.info("==========>>当前拥有角色:{}", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        for (ConfigAttribute ca : configAttributes) {
            String needRole = ca.getAttribute();
            if ("ROLE_LOGIN".equals(needRole)) {
                return;
            }

            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(needRole)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足,请联系管理员分配权限");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
