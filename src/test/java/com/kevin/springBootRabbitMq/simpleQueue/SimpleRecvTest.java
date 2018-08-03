package com.kevin.springBootRabbitMq.simpleQueue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleRecvTest {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void testSimpleQueue() {
        try{
            String message = "Hello RabbitMQ,Simple Message!";
            amqpTemplate.convertAndSend("springBoot_simple_queue", message);
            System.out.println("[x] SpringBootSimpleQueueSend " + message + " ok");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}