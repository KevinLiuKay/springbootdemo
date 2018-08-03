package com.kevin.rabbitmq.transaction;

import com.kevin.common.utils.RabbitMqConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者-事物机制
 * @author lzk
 */
public class TransactionSend {
    private static final String QUEUE_NAME = "transaction_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection connection = (Connection) RabbitMqConnectionUtil.getConnection();
        // 从连接开一个通道
        Channel channel = connection.createChannel();
        // 声明一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        try{
            channel.txSelect(); // 开启事务
            // 发送消息
            String message = "hello, transaction message";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent message : '" + message + "'");
            //int i = 1/0;  //测试生产者消息回滚
            channel.txCommit(); // 提交事务
        }catch (Exception e){
            channel.txRollback(); // 回滚事务
            System.out.println("send message transactionRollback");
        }
        channel.close();
        connection.close();
    }
}
