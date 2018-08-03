package com.kevin.springBootRabbitMq.routeModel;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 * @author lzk
 */
@RabbitListener(queues = "springBoot_route_model_warn_queue")
@Component
public class RouteModelWarnRecv {

    @RabbitHandler
    public void process(String message) {
        System.out.println("[x]SpringBootRouteModelWarnRecv:" + message);
    }
}
