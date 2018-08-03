package com.kevin.rabbitmq.workQueue.fair;

import com.kevin.common.utils.RabbitMqConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 工作队列-公平分发-消费者2
 * @author lzk
 */
public class WorkQueueFairDistributionRecv2 {
    private static final String QUEUE_NAME = "work_queue_fair_distribution";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 打开通道
        Connection connection = (Connection)RabbitMqConnectionUtil.getConnection();
        // 打开通道
        Channel channel = connection.createChannel();
        //声明一个要消费的队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 这样RabbitMQ就会使得每个Consumer在同一个时间点最多处理一个Message。换句话说，在接收到该Consumer的ack前，他它不会将新的Message分发给它。
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);
        // 创建一个回调的消费者处理类
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
                // 接收到的消息
                String message = new String(body);
                System.out.println(" [2] Received '" + message + "'");
                try {
                    Thread.sleep(2000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println(" [2] done ");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        /*
         *  关闭自动应答
         * boolean autoAck = false;
         * channel.basicConsume(QUEUE_NAME, autoAck, consumer);
        */
        // 消费消息
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
