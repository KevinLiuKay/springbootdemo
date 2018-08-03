package com.kevin.rabbitmq.confirm.asynConfirm;

import com.kevin.common.utils.RabbitMqConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者-asynConfirm
 * @author lzk
 */
public class AsynConfirmRecv {
    private static final String QUEUE_NAME = "asyn_confirm_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection connection = (Connection) RabbitMqConnectionUtil.getConnection();
        // 打开通道
        Channel channel = connection.createChannel();
        // 申明要消费的队列
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
    }
}
