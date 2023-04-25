package com.commons.config.security;

import cn.hutool.core.util.ObjectUtil;
import com.commons.domain.Permission;
import com.commons.domain.Role;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.commons.service.PermissionService;
import com.commons.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yyh
 * @date 2023/3/21 22:03
 * @description 获取当前请求需要的角色
 */

@ComponentScan(basePackages = "com.commons.service")
@Slf4j
@Component
public class UrlFilterInvocationSecurityMetadataSource  implements FilterInvocationSecurityMetadataSource {


    @Resource
    private PermissionService permissionService;

    @Resource
    private RoleService roleService;


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        AntPathMatcher ant=new AntPathMatcher();
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        log.info("==========>>请求url:{}",requestUrl);

        String loginUrl = "/doLogin";
        if (ant.match(loginUrl,requestUrl)) {
            log.info("==========>>此为登录请求");
            return SecurityConfig.createList("ROLE_LOGIN");
        }


        LambdaQueryWrapper<Permission> permissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        permissionLambdaQueryWrapper.eq(Permission::getUrl, requestUrl);
        Permission permission = permissionService.getOne(permissionLambdaQueryWrapper);

        if (ObjectUtil.isNotNull(permission)) {
            List<Role> roles = roleService.listRolesByPermissionId(permission.getId());
            List<String> roleNames = roles.stream().map(Role::getRoleName).collect(Collectors.toList());
            log.info("==========>>所需角色：{}",roleNames);
            return SecurityConfig.createList(roleNames.toArray(
                    new String[roleNames.size()]
            ));
        } else {
            log.warn("==========>>不需要角色的路径");
            return SecurityConfig.createList("ROLE_LOGIN");
        }
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
