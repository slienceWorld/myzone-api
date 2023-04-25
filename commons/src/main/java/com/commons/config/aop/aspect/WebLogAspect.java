package com.commons.config.aop.aspect;

import com.commons.config.aop.annotation.WebLog;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author yyh
 * @date 2023/3/20 15:20
 */

@Slf4j
@Aspect
@Component
public class WebLogAspect {

    private static final String LINE_SEPARATOR = System.lineSeparator();


    @Pointcut("@annotation(com.commons.config.aop.annotation.WebLog)")
    public void webLogPointcut() {
    }


    @Around("webLogPointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 获取 @WebLog 注解的描述信息
        String methodDescription = getAspectLogDescription(proceedingJoinPoint);
        Object result = null;


        log.info("========================================== Start ==========================================");
        // 打印请求 url
        log.info("URL            : {}", request.getRequestURL().toString());
        // 打印描述信息
        log.info("Description    : {}", methodDescription);
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        log.info("Class Method   : {}.{}", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName());
        log.info("IP             : {}", request.getRemoteAddr());
        log.info("Request Args   : {}", new Gson().toJson(proceedingJoinPoint.getArgs()));


        long startTime = System.currentTimeMillis();
        result = proceedingJoinPoint.proceed();
        log.info("响应参数:   {}", new Gson().toJson(result));
        log.info("运行经过时间:{} ms", System.currentTimeMillis() - startTime);
        log.info("=========================================== End ===========================================" + LINE_SEPARATOR);


        return result;
    }


    /**
     * 获取切面注解的描述
     *
     * @param joinPoint 切点
     * @return 描述信息
     * @throws Exception
     */
    public String getAspectLogDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        StringBuilder description = new StringBuilder("");
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description.append(method.getAnnotation(WebLog.class).description());
                    break;
                }
            }
        }
        return description.toString();
    }
}
