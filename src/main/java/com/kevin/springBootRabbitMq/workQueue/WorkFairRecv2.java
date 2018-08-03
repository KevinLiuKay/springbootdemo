package com.kevin.springBootRabbitMq.workQueue;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 * @author lzk
 */
@RabbitListener(queues = "springBoot_work_fair_queue")
@Component
public class WorkFairRecv2 {

    @RabbitHandler
    public void process(String message) {
        System.out.println("[2]SpringBootWorkFairQueueRecvTwo:" + message);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
