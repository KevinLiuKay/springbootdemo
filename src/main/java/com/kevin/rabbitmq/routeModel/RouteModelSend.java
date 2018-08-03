package com.kevin.rabbitmq.routeModel;

import com.kevin.common.utils.RabbitMqConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式-生产者（direct交换机）
 * 跟订阅模式类似，只不过在订阅模式的基础上加上了类型，
 * 订阅模式是分发到所有绑定到交换机的队列，路由模式只分发到绑定在交换机上面指定路由键的队列。
 * @author lzk
 */
public class RouteModelSend {
    private static final String EXCHANGE_NAME = "route_model_exchange_direct";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection connection = (Connection)RabbitMqConnectionUtil.getConnection();
        // 从连接开一个通道
        Channel channel = connection.createChannel();
        // 声明一个direct分发交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String message = "Hello, direct";
        /**
         * 多个消费者绑定相同路由键
         * 即消费者1绑定info，消费者2 绑定info, error
         * 那么生产者发送info路由键消息时，消费者1, 2都能接收到消息，发送error路由键消息时，只有消费者2能接收到消息。
         */
        // 发送info路由键消息
        String infoMessage = "hello, info";
        channel.basicPublish(EXCHANGE_NAME, "info", null, infoMessage.getBytes());
        System.out.println(" [x] Sent routing info message : '" + infoMessage + "'");
        // 发送warn路由键消息
        String warningMessage = "hello, warning";
        channel.basicPublish(EXCHANGE_NAME, "warn", null, warningMessage.getBytes());
        System.out.println(" [x] Sent routing warning message : '" + warningMessage + "'");
        // 发送error路由键消息
        String errorMessage = "hello, error";
        channel.basicPublish(EXCHANGE_NAME, "error", null, errorMessage.getBytes());
        System.out.println(" [x] Sent routing error message :  '" + errorMessage + "'");
        channel.close();
        connection.close();
    }
}
