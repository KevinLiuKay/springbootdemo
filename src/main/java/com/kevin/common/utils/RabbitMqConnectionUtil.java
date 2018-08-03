package com.kevin.common.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.util.concurrent.TimeoutException;
import java.io.IOException;

/**
 * 获取RabbitMQ Connection连接
 * @author lzk
 * @return
 * @throws IOException
 * @throws TimeoutException
 */
public class RabbitMqConnectionUtil {
    private static final String HOST = "localhost";
    private static final int PORT = 5672;

    public static Connection getConnection() throws IOException, TimeoutException {
        //定义连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置服务地址
        connectionFactory.setHost(HOST);
        //端口(amqp协议 端口 类似与mysql的3306)
        connectionFactory.setPort(PORT);
        //设置账号信息，用户名、密码、vhost
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        // 通过工程获取连接
        Connection connection = connectionFactory.newConnection();
        return connection;
    }

}
