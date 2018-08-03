package com.kevin.rabbitmq.workQueue.polling;

import com.kevin.common.utils.RabbitMqConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
/**
 * 工作队列-轮询分发-生产者
 * @author lzk
 */
public class WorkQueuePollingDistributionSend {
    private static final String QUEUE_NAME = "work_queue_polling_distribution";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection connection = (Connection)RabbitMqConnectionUtil.getConnection();
        // 从连接开一个通道
        Channel channel = connection.createChannel();
        // 申明这个通道连接的队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for (int i = 0; i < 20; i++) {
            String message = "Hello RabbitMQ " + i;
            // 发送消息
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
        // 关闭通道和连接
        channel.close();
        connection.close();
    }
}
