package com.gateway.filter;

import com.commons.constant.CloudConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.socket.client.TomcatWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import org.springframework.web.reactive.socket.server.RequestUpgradeStrategy;
import org.springframework.web.reactive.socket.server.upgrade.TomcatRequestUpgradeStrategy;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author yyh
 * @date 2023/4/8 22:24
 * @description
 */
@Slf4j
@Component
public class HeaderFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        byte[] encode = Base64Utils.encode(CloudConstant.GATEWAY_HEADER_VALUE.getBytes());
        String headerValue = new String(encode);
        log.info("==========>>进入网关,请求路径:{}", request.getPath());
        ServerHttpRequest build = request
                .mutate()
                .header(CloudConstant.GATEWAY_HEADER_KEY, headerValue)
                .build();

        return chain.filter(exchange.mutate().request(build).build());
    }

    @Bean
    @Primary
    public RequestUpgradeStrategy requestUpgradeStrategy() {
        return new TomcatRequestUpgradeStrategy();
    }

    @Bean
    @Primary
    public WebSocketClient webSocketClient() {
        return new TomcatWebSocketClient();
    }
}