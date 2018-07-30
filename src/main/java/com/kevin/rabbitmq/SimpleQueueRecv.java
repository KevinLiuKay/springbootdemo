package com.kevin.rabbitmq;

import com.kevin.common.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.*;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 */
public class SimpleQueueRecv {
    private static final String QUEUE_NAME = "queue_simple";

    public static void main(String[] args) throws IOException, TimeoutException {

        // 获取连接
        Connection connection = (Connection)RabbitMQConnectionUtil.getConnection();
        // 打开通道
        Channel channel = connection.createChannel();

        // 申明要消费的队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 创建一个回调的消费者处理类
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 接收到的消息
                String message = new String(body);
                System.out.println(" [x] Received '" + message + "'");
            }
        };
       /* String basicConsume(String queue, boolean autoAck, Consumer callback) throws IOException;
        * queue: 队列名称
        * autoAck: 是否自动应答，即生产者发送消息即认为该消息被消费
        * callback: 回调处理类，即消息被消费时进行回调处理
        */
        // 消费消息
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }

}
