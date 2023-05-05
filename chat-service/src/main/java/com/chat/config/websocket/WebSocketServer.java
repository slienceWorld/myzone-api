package com.chat.config.websocket;

import com.chat.config.rabbitmq.RabbitConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yyh
 * @date 2023/5/4 12:28
 * @description
 */

@Slf4j
@ServerEndpoint(value = "/websocket/{name}")
@Component
public class WebSocketServer {

    private String name;
    /**
     * 用于存所有的连接服务的客户端，这个对象存储是安全的
     */
    private static ConcurrentHashMap<String, Session> WEB_SOCKET_SET = new ConcurrentHashMap<>();


    @RabbitListener(queues = RabbitConfig.MSG_QUEUE)
    @RabbitHandler
    public void process(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        channel.basicAck(tag, false);
        log.info("DirectReceiver消费者收到消息  :{} ", message);
        onMessage(message);

    }

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "name") String name) {
        this.name = name;
        // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
        WEB_SOCKET_SET.put(name, session);
        log.info("[WebSocket] 连接成功，当前连接人数为：={}", WEB_SOCKET_SET.size());
    }

    @OnClose
    public void onClose() {
        WEB_SOCKET_SET.remove(this.name);
        log.info("[WebSocket] 退出成功，当前连接人数为：={}", WEB_SOCKET_SET.size());
    }

    @OnMessage
    public void onMessage(String message) {

        //判断是否需要指定发送，具体规则自定义
        String receiver = message.substring(message.indexOf("To ") + 3, message.indexOf(":"));
        log.info("[WebSocket] 收到消息：{},接收者:{}", message, receiver);
        if (!"all".equals(receiver)) {
            appointSending(receiver, message);
        } else {
            groupSending(message);
        }
    }

    /**
     * 群发
     *
     * @param message
     */
    public void groupSending(String message) {
        for (String name : WEB_SOCKET_SET.keySet()) {
            try {
                WEB_SOCKET_SET.get(name).getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 指定发送
     *
     * @param name
     * @param message
     */
    public void appointSending(String name, String message) {
        try {
            WEB_SOCKET_SET.get(name).getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}