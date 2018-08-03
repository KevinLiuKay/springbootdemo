package com.kevin.rabbitmq.subscriptionModel;

import com.kevin.common.utils.RabbitMqConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 订阅模式-生产者（fanout交换机）
 * 生产者发送消息给交换机，交换机分发绑定在交换机上的队列，每个队列对应一个消费者。
 * @author lzk
 */
public class SubscriptionModelSend {
    private static final String EXCHANGE_NAME = "subscription_model_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection connection = (Connection)RabbitMqConnectionUtil.getConnection();
        // 从连接开一个通道
        Channel channel = connection.createChannel();
        // 声明一个fanout分发交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        String message = "Hello, fanout";
        // 发送消息
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }
}
