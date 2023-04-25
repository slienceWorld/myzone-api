package com.authservice.config.jwt;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yyh
 * @date 2023/4/17 14:23
 * @description
 */
@Data
@Slf4j
@Deprecated
//@Component
//@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    private Long EXPIRE_TIME;
    private String SECRET;

//    @PostConstruct
//    private void init(){
//        log.info("EXPIRE_TIME {} SECRET {}",EXPIRE_TIME,SECRET);
//    }
//
//    @Value("${jwt.expire-time}")
//    public void setExpireTime(Long expireTime) {
//        EXPIRE_TIME = expireTime;
//    }
//
//    @Value("${jwt.secret}")
//    public void setSecret(String secret) {
//       SECRET = secret;
//    }
}
