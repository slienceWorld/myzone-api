package com.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yyh
 * @date 2023/1/8 20:17
 */
@RestController
public class GatewayController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}

