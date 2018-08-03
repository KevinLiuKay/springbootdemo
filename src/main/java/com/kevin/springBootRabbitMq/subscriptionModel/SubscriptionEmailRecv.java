package com.kevin.springBootRabbitMq.subscriptionModel;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 * @author lzk
 */
@RabbitListener(queues = "springBoot_queue_fanout_email")
@Component
public class SubscriptionEmailRecv {

    @RabbitHandler
    public void process(String message) {
        System.out.println("[x]SpringBootEmailRecv:" + message);
    }
}
