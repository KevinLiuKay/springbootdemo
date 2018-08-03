package com.kevin.springBootRabbitMq.subscriptionModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class SubscriptionTest {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void testFanoutQueue() {
        String message = "Hello RabbitMQ, subscription fanout message ";
        // 发送消息
        amqpTemplate.convertAndSend("springBoot_exchange_fanout", "", message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}