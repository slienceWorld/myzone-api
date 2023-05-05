package com.chat.controller;


import com.chat.config.rabbitmq.RabbitConfig;
import com.commons.config.aop.annotation.WebLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author admin  issuser
 * @title
 * @description 消息提供者
 * @updateTime 2022/11/10 15:44
 * @throws
 */
@Slf4j
@RestController
//@RequestMapping("/chat")
public class RabbitProduct {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @WebLog(description = "聊天模块测试")
    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage(String text) {
//        String messageId = String.valueOf(UUID.randomUUID());
//        String messageData = "test message, hello!";
//        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        Map<String,Object> map=new HashMap<>();
//        map.put("messageId",messageId);
//        map.put("messageData",messageData);
//        map.put("createTime",createTime);
        rabbitTemplate.convertAndSend(RabbitConfig.MSG_EXCHANGE, RabbitConfig.MSG_ROUTING_KEY, text, new CorrelationData(UUID.randomUUID().toString()));
        return "ok";
    }


}