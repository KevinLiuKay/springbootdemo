package com.kevin.rabbitmq.confirm.batchConfirm;

import com.kevin.common.utils.RabbitMqConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者-batchConfirm
 * @author lzk
 */
public class BatchConfirmSend {
    private static final String QUEUE_NAME = "batch_confirm_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 获取连接
        Connection connection = (Connection) RabbitMqConnectionUtil.getConnection();
        // 从连接开一个通道
        Channel channel = connection.createChannel();
        // 声明一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.confirmSelect(); // 开始confirm模式
        for (int i = 0; i < 20; i++) {
            // 发送消息
            String message = "hello, batchConfirm message " + i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        }
        if(channel.waitForConfirms()){
            System.out.println(" [x] Sent message ok ");
        } else {
            System.out.println(" [x] Sent message fail ");
        }
        channel.close();
        connection.close();
    }

}
