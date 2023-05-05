package com.chat.config.rabbitmq;

import com.chat.config.websocket.WebSocketServer;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import javax.annotation.Resource;

/**
 * @author yyh
 * @date 2023/5/3 21:48
 * @description
 */
@Slf4j
//@Component
public class MessageListener implements ChannelAwareMessageListener {

    @Resource
    private WebSocketServer webSocketServer;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            byte[] body = message.getBody();
            log.info("消费的主题消息来自：" + message.getMessageProperties().getConsumerQueue());
            webSocketServer.onMessage(new String(body));

            channel.basicAck(deliveryTag,false);
        } catch (Exception e) {
            log.error("send rabbitmq message error", e);
            channel.basicReject(deliveryTag,false);
        }
    }
}
