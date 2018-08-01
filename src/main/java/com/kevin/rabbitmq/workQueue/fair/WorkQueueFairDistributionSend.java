package com.kevin.rabbitmq.workQueue.fair;

import com.kevin.common.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 工作队列-公平分发-生产者
 */
public class WorkQueueFairDistributionSend {
    private static final String QUEUE_NAME = "work_queue_fair_distribution";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection connection = (Connection)RabbitMQConnectionUtil.getConnection();
        // 从连接开一个通道
        Channel channel = connection.createChannel();
        // 申明这个通道连接的队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 限制发送给同一个消费者不得超过1条消息，在这个消费者确认消息之前，不会发送下一条消息给这个消费者
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);
        for (int i = 0; i < 20; i++) {
            String message = "Hello RabbitMQ " + i;
            // 发送消息
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            try {
                Thread.sleep(i*100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 关闭通道和连接
        channel.close();
        connection.close();
    }
}
