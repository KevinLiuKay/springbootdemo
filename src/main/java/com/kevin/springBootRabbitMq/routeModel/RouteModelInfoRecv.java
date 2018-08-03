package com.kevin.springBootRabbitMq.routeModel;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 * @author lzk
 */
@RabbitListener(queues = "springBoot_route_model_info_queue")
@Component
public class RouteModelInfoRecv {

    @RabbitHandler
    public void process(String message) {
        System.out.println("[x]SpringBootRouteModelInfoRecv:" + message);
    }
}
