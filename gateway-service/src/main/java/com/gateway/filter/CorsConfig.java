package com.gateway.filter;

import com.commons.constant.CloudConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.Base64Utils;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.reactive.socket.client.TomcatWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import org.springframework.web.reactive.socket.server.RequestUpgradeStrategy;
import org.springframework.web.reactive.socket.server.upgrade.TomcatRequestUpgradeStrategy;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
//@Configuration
public class CorsConfig {

    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            // 使用SpringMvc自带的跨域检测工具类判断当前请求是否跨域
            if (!CorsUtils.isCorsRequest(request)) {
                return chain.filter(ctx);
            }
            HttpHeaders requestHeaders = request.getHeaders();                                  // 获取请求头
            ServerHttpResponse response = ctx.getResponse();                                    // 获取响应对象
            HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();          // 获取请求方式对象
            HttpHeaders headers = response.getHeaders();                                        // 获取响应头
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());   // 把请求头中的请求源（协议+ip+端口）添加到响应头中（相当于yml中的allowedOrigins）
            headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders.getAccessControlRequestHeaders());
            if (requestMethod != null) {
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());    // 允许被响应的方法（GET/POST等，相当于yml中的allowedMethods）
            }
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");       // 允许在请求中携带cookie（相当于yml中的allowCredentials）
            headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");             // 允许在请求中携带的头信息（相当于yml中的allowedHeaders）
            headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "18000L");               // 本次跨域检测的有效期(单位毫秒，相当于yml中的maxAge)
            log.info("==========>>进入网关");
            byte[] encode = Base64Utils.encode(CloudConstant.GATEWAY_HEADER_VALUE.getBytes());
            String headerValue = new String(encode);
            headers.add(CloudConstant.GATEWAY_HEADER_KEY, headerValue);
            if (request.getMethod() == HttpMethod.OPTIONS) {                                    // 直接给option请求反回结果
                response.setStatusCode(HttpStatus.OK);
                return Mono.empty();
            }
            return chain.filter(ctx);                                                           // 不是option请求则放行
        };
    }


}
