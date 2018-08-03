package com.kevin.springBootRabbitMq.workQueue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkFairRecvTest {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void testWorkFairQueue() {
        for(int i = 0; i < 20; i++) {
            String message = "Hello RabbitMQ,WorkFair Message" + i;
            // 发送消息
            amqpTemplate.convertAndSend("springBoot_work_fair_queue", message);
            System.out.println(" [x] SpringBootWorkFairQueueSent '" + message + "'");
            try {
                Thread.sleep(i*100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}