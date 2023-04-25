package com.gateway.filter;

import com.commons.constant.CloudConstant;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author yyh
 * @date 2023/4/8 22:24
 * @description
 */
@Component
public class HeaderFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        byte[] encode = Base64Utils.encode(CloudConstant.GATEWAY_HEADER_VALUE.getBytes());
        String headerValue = new String(encode);

        ServerHttpRequest build = exchange.getRequest()
                .mutate()
                .header(CloudConstant.GATEWAY_HEADER_KEY, headerValue)
                .build();

        return chain.filter(exchange.mutate().request(build).build());
    }
}