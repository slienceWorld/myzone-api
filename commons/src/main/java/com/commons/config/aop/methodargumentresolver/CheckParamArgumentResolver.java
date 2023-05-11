package com.commons.config.aop.methodargumentresolver;

import com.commons.config.aop.annotation.CheckParam;
import org.checkerframework.checker.units.qual.A;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;

/**
 * @author yyh
 * @date 2023/5/11 15:19
 * @description
 */
@Component
public class CheckParamArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CheckParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        CheckParam annotation = parameter.getParameterAnnotation(CheckParam.class);
        String paramValue = webRequest.getParameter(parameter.getParameterName());
        if (!Arrays.asList(annotation.value()).contains(paramValue)) {
            throw new IllegalArgumentException("参数" + paramValue + "的值必须是" + Arrays.toString(annotation.value()) + "中的一个");
        }
        return paramValue;
    }
}