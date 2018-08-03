package com.kevin.rabbitmq.confirm.commonConfirm;

import com.kevin.common.utils.RabbitMqConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者-commonConfirm
 * @author lzk
 */
public class CommonConfirmSend {
    private static final String QUEUE_NAME = "common_confirm_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 获取连接
        Connection connection = (Connection) RabbitMqConnectionUtil.getConnection();
        // 从连接开一个通道
        Channel channel = connection.createChannel();
        // 声明一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.confirmSelect(); // 开始confirm模式
        // 发送消息
        String message = "hello, commonConfirm message";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        if(channel.waitForConfirms()){
            System.out.println(" [x] Sent message : '" + message + "' ok ");
        } else {
            System.out.println(" [x] Sent message : '" + message + "' fail ");
        }
        channel.close();
        connection.close();
    }

}
