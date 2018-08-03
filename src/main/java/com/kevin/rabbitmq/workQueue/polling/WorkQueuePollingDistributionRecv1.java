package com.kevin.rabbitmq.workQueue.polling;

import com.kevin.common.utils.RabbitMqConnectionUtil;
import com.rabbitmq.client.*;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
/**
 * 工作队列-轮询分发-消费者1
 * @author lzk
 */
public class WorkQueuePollingDistributionRecv1 {
    private static final String QUEUE_NAME = "work_queue_polling_distribution";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 打开通道
        Connection connection = (Connection)RabbitMqConnectionUtil.getConnection();
        // 打开通道
        Channel channel = connection.createChannel();
        //声明一个要消费的队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 创建一个回调的消费者处理类
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 接收到的消息
                String message = new String(body);
                System.out.println(" [1] Received '" + message + "'");
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println(" [1] done ");
                }
            }
        };
        // 消费消息
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
