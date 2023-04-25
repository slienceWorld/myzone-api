package com.video;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author yyh
 * @date 2023/4/25 22:10
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.commons.mapper")
public class MediaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MediaApplication.class, args);
    }
}
