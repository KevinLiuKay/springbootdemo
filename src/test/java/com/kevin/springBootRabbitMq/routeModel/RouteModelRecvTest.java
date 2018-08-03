package com.kevin.springBootRabbitMq.routeModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RouteModelRecvTest {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void testRouteQueue() {
        //发送info路由键消息
        String infoMessage = "Hello RabbitMQ, route model info message ";
        amqpTemplate.convertSendAndReceive("springBoot_route_model_exchange_direct", "info", infoMessage);
        System.out.println(" [x] Sent routing info message : '" + infoMessage + "'");
        //发送warn路由键消息
        String warnMessage = "Hello RabbitMQ, route model warn message ";
        amqpTemplate.convertSendAndReceive("springBoot_route_model_exchange_direct", "warn", warnMessage);
        System.out.println(" [x] Sent routing warn message : '" + warnMessage + "'");
    }
}