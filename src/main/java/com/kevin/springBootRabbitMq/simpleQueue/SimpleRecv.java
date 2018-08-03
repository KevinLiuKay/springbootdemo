package com.kevin.springBootRabbitMq.simpleQueue;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 * @author lzk
 */
@RabbitListener(queues = "springBoot_simple_queue")
@Component
public class SimpleRecv {

    @RabbitHandler
    public void process(String message) {
        System.out.println("[x]SpringBootSimpleQueueRecv:" + message);
    }
}
