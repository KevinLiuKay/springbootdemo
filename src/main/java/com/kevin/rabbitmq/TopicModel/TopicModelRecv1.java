package com.kevin.rabbitmq.TopicModel;

import com.kevin.common.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 主题模式-消费者1
 */
public class TopicModelRecv1 {
    /**
     * 主题队列
     */
    private static final String QUEUE_NAME = "topic_model_topic_queue1";
    private static final String EXCHANGE_NAME = "topic_model_exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection connection = (Connection) RabbitMQConnectionUtil.getConnection();
        // 打开通道
        Channel channel = connection.createChannel();
        // 申明要消费的队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 绑定队列到交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "*.orange.*");
        // 这样RabbitMQ就会使得每个Consumer在同一个时间点最多处理一个Message。换句话说，在接收到该Consumer的ack前，他它不会将新的Message分发给它。
        channel.basicQos(1);
        // 创建一个回调的消费者处理类
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 接收到的消息
                String message = new String(body);
                System.out.println(" [topic1] Received '" + message + "'");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(" [topic1] done ");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        // 消费消息
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
