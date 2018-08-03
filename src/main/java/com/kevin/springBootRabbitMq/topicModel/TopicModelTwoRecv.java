package com.kevin.springBootRabbitMq.topicModel;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 * @author lzk
 */
@RabbitListener(queues = "springBoot_topic_model_two_queue")
@Component
public class TopicModelTwoRecv {

    @RabbitHandler
    public void process(String message) {
        System.out.println("[x]SpringBootTopicModelTwoRecv:" + message);
    }
}
