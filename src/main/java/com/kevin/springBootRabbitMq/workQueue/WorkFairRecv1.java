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
public class WorkFairRecv1 {

    @RabbitHandler
    public void process(String message) {
        System.out.println("[1]SpringBootWorkFairQueueRecvOne:" + message);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
