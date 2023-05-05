package com.chat.config.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yyh
 * @date 2023/5/3 11:07
 * @description
 */

@Slf4j
@Configuration
public class RabbitConfig {

    /**
     * websocket 消息队列
     */
    public static final String MSG_QUEUE = "msg_queue";


    /**
     * 消息交换机
     */
    public static final String MSG_EXCHANGE = "msg_exchange";

    /**
     * 消息路由键
     */

    public static final String MSG_ROUTING_KEY = "msg_routing_key";

//    @Resource
//    private MessageListener messageListener;

    /**
     * 消息队列
     */
    @Bean
    public Queue msgQueue() {
        return new Queue(MSG_QUEUE);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(MSG_EXCHANGE);
    }


    /**
     * 消息队列绑定消息交换机
     */
    @Bean
    public Binding msgBinding() {
        return BindingBuilder.bind(msgQueue()).to(directExchange()).with(MSG_ROUTING_KEY);
    }


    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) ->
        {
            assert correlationData != null;
            log.info("发送订阅消息确认,correlationData:{}, 确认情况：{}, 原因：{}", correlationData.getId(), ack, cause);
        });

        rabbitTemplate.setReturnsCallback(msg ->
                log.info("发送到订阅消息: {} , 回应码: {}, 回应信息: {}, 交换机: {}, 路由键: {}",
                        msg.getMessage(), msg.getReplyCode(), msg.getReplyText(), msg.getExchange(), msg.getRoutingKey())
        );
        return rabbitTemplate;
    }


//    @Bean
//    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setConcurrentConsumers(1);
//        container.setMaxConcurrentConsumers(100);
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//
//        container.setQueueNames(MSG_QUEUE);
//
//        container.setMessageListener(messageListener);
//
//        return container;
//
//    }

}
