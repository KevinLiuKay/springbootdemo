package com.kevin;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzk
 */
@Configuration
public class RabbitMqConfiguration {
    /**
     * 简单队列
     */
    private static final String QUEUE_SIMPLE_NAME = "springBoot_simple_queue";
    /**
     * 工作队列
     */
    private static final String QUEUE_WORK_NAME = "springBoot_work_fair_queue";

    /**
     *  订阅模式
     */
    private static final String EXCHANGE_FANNOUT_NAME = "springBoot_exchange_fanout";
    private static final String QUEUE_PS_SMS_NAME = "springBoot_queue_fanout_sms";
    private static final String QUEUE_PS_EMAIL_NAME = "springBoot_queue_fanout_email";
    /**
     *  路由模式
     */
    private static final String EXCHANGE_DIRECT_NAME = "springBoot_route_model_exchange_direct";
    private static final String QUEUE_INFO_NAME = "springBoot_route_model_info_queue";
    private static final String QUEUE_WARN_NAME = "springBoot_route_model_warn_queue";

    /**
     *  主题模式
     */
    private static final String EXCHANGE_TOPIC_NAME = "springBoot_topic_model_exchange_topic";
    private static final String QUEUE_ONE_NAME = "springBoot_topic_model_one_queue";
    private static final String QUEUE_TWO_NAME = "springBoot_topic_model_two_queue";


    @Bean
    public Queue queue(){
        return new Queue(QUEUE_SIMPLE_NAME, false, false, false, null);
    }

    @Bean
    public Queue workQueue(){
        return new Queue(QUEUE_WORK_NAME, false, false, false, null);
    }
    /**
     *  订阅模式-定义交换机、队列、以及队列与交换机的绑定关系
     */
    @Bean("fanoutExchange")
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(EXCHANGE_FANNOUT_NAME);
    }
    @Bean
    public Queue fanoutSmsQueue(){
        return new Queue(QUEUE_PS_SMS_NAME, false, false, false, null);
    }
    @Bean
    public Queue fanoutEmailQueue(){
        return new Queue(QUEUE_PS_EMAIL_NAME, false, false, false, null);
    }
    @Bean
    public Binding smsQueueExchangeBinding(FanoutExchange fanoutExchange, Queue fanoutSmsQueue){
        return BindingBuilder.bind(fanoutSmsQueue).to(fanoutExchange);
    }
    @Bean
    public Binding emailQueueExchangeBinding(FanoutExchange fanoutExchange, Queue fanoutEmailQueue){
        return BindingBuilder.bind(fanoutEmailQueue).to(fanoutExchange);
    }

    /**
     *  路由模式-定义交换机、队列、以及队列与交换机的绑定关系并设定路由键
     */
    @Bean("directExchange")
    public DirectExchange directExchange(){
        return new DirectExchange(EXCHANGE_DIRECT_NAME);
    }
    @Bean
    public Queue directInfoQueue(){
        return new Queue(QUEUE_INFO_NAME, false, false, false, null);
    }
    @Bean
    public Queue directWarnQueue(){
        return new Queue(QUEUE_WARN_NAME, false, false, false, null);
    }
    @Bean
    public Binding routeInfoQueueExchangeBinding(DirectExchange directExchange, Queue directInfoQueue) {
        return BindingBuilder.bind(directInfoQueue).to(directExchange).with("info");
    }
    @Bean
    public Binding routeWarnQueueExchangeBinding(DirectExchange directExchange, Queue directWarnQueue) {
        return BindingBuilder.bind(directWarnQueue).to(directExchange).with("warn");
    }

    /**
     *  主题模式-定义交换机、队列、以及队列与交换机的绑定关系并设定路由键
     */
    @Bean("topicExchange")
    public TopicExchange topicExchange(){
        return new TopicExchange(EXCHANGE_TOPIC_NAME);
    }
    @Bean
    public Queue topicOneQueue(){
        return new Queue(QUEUE_ONE_NAME, false, false, false, null);
    }
    @Bean
    public Queue topicTwoQueue(){
        return new Queue(QUEUE_TWO_NAME, false, false, false, null);
    }
    @Bean
    public Binding topicOneQueueExchangeBinding(TopicExchange topicExchange, Queue topicOneQueue) {
        return BindingBuilder.bind(topicOneQueue).to(topicExchange).with("*.orange.*");
    }
    @Bean
    public Binding topicTwoQueueExchangeBinding(TopicExchange topicExchange, Queue topicTwoQueue) {
        return BindingBuilder.bind(topicTwoQueue).to(topicExchange).with("*.*.rabbit");
    }
}
