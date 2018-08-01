package com.kevin.rabbitmq.TopicModel;

import com.kevin.common.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 主题模式-生产者（topic交换机）
 * 跟路由模式类似，只不过路由模式是指定固定的路由键，
 * 而主题模式是可以模糊匹配路由键，类似于SQL中=和like的关系。
 */
public class TopicModelSend {
    private static final String EXCHANGE_NAME = "topic_model_exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection connection = (Connection)RabbitMQConnectionUtil.getConnection();
        // 从连接开一个通道
        Channel channel = connection.createChannel();
        // 声明一个topic路由交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        //发送消息
        String message = "hello, quick.orange.rabbit";
        channel.basicPublish(EXCHANGE_NAME, "lazy.brown.fox", null, message.getBytes());
        System.out.println(" [x] Sent message : '" + message + "'");
        channel.close();
        connection.close();
    }
}
