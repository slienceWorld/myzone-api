package com.favorites;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author yyh
 * @date 2023/4/18 12:29
 * @description
 */

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan({"com.favorites.mapper","com.commons.mapper"})
public class FavoritesApplication {
    public static void main(String[] args) {
        SpringApplication.run(FavoritesApplication.class, args);
    }
}
