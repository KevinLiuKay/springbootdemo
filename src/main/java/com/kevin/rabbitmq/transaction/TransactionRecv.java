package com.kevin.rabbitmq.transaction;

import com.kevin.common.utils.RabbitMqConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者-事物机制
 * @author lzk
 */
public class TransactionRecv {
    private static final String QUEUE_NAME = "transaction_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection connection = (Connection) RabbitMqConnectionUtil.getConnection();
        // 从连接开一个通道
        Channel channel = connection.createChannel();
        // 声明一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 消费消息
        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 接收到的消息
                String message = new String(body);
                System.out.println(" [x] Received '" + message + "'");
            }
        });
        channel.close();
        connection.close();
    }
}
