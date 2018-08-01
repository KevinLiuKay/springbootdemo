package com.kevin.rabbitmq.workQueue.fair;

import com.kevin.common.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 工作队列-公平分发-消费者1
 */
public class WorkQueueFairDistributionRecv1 {
    private static final String QUEUE_NAME = "work_queue_fair_distribution";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 打开通道
        Connection connection = (Connection)RabbitMQConnectionUtil.getConnection();
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
                    //手动应答：DeliveryTag 用来标识信道中投递的消息， RabbitMQ 推送消息给 Consumer 时，会附带一个 Delivery Tag，以便 Consumer 可以在消息确认时告诉 RabbitMQ 到底是哪条消息被确认了。
                    channel.basicAck(envelope.getDeliveryTag(), false);
                  /*  当关闭自动应答autoAck=false之后，在消费者处理消费数据之后一定要对消息进行手动反馈处理，可以是basicAck，也可以是basicNack， basicReject
                    BasicReject一次只能拒绝接收一个消息，而BasicNack方法可以支持一次0个或多个消息的拒收，并且也可以设置是否requeue。
                    // 拒绝当前消息，并使这条消息重新返回到队列中
                    channel.basicNack(envelope.getDeliveryTag(), false, true);
                    相当于
                    channel.basicReject(envelope.getDeliveryTag(), true);*/
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
