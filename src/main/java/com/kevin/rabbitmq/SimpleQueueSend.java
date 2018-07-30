package com.kevin.rabbitmq;

import com.kevin.common.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 */
public class SimpleQueueSend {
    private static final String QUEUE_NAME = "queue_simple";

    public static void main(String[] args) throws IOException, TimeoutException {
        /* 获取一个连接 */
        Connection connection = (Connection)RabbitMQConnectionUtil.getConnection();
        /*从连接中创建通道*/
        Channel channel = connection.createChannel();
        /*
        * Queue.DeclareOk queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments) throws IOException;
        * queue: 队列名称
        * durable: 是否持久化，true表示RabbitMQ重启后，队列仍然存在
        * exclusive: true表示当前连接的专用队列，在连接断开后，会自动删除该队列
        * autoDelete: true 表示当没有任何消费者使用时，自动删除该队列
        * arguments: 该队列其他配置参数
        */
        // 申明一个队列，没有就会创建
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello  Simple QUEUE !";
        //第一个参数是exchangeName(默认情况下代理服务器端是存在一个""名字的exchange的,
        //因此如果不创建exchange的话我们可以直接将该参数设置成"",如果创建了exchange的话
        //我们需要将该参数设置成创建的exchange的名字),第二个参数是路由键
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }
}
