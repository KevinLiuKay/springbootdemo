package com.kevin.springBootRabbitMq.topicModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicModelOneRecvTest {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void testTopicQueue() {
        //发送路由键消息
        String message = "Hello RabbitMQ, topic model message ";
        amqpTemplate.convertSendAndReceive("springBoot_topic_model_exchange_topic", "lazy.brown.rabbit", message);
        System.out.println(" [x] Sent topic message : '" + message + "'");

    }
}