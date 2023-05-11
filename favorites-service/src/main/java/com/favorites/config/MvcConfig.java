package com.favorites.config;

import com.commons.config.aop.methodargumentresolver.CheckParamArgumentResolver;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author yyh
 * @date 2023/5/11 13:19
 * @description 参数校验
 */

@ComponentScan(basePackageClasses = CheckParamArgumentResolver.class)
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Resource
    private CheckParamArgumentResolver checkParamArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(checkParamArgumentResolver);
    }
}
