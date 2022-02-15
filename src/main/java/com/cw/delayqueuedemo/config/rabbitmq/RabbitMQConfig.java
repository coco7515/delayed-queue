package com.cw.delayqueuedemo.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String DELAY_EXCHANGE_NAME = "delay.queue.demo.business.exchange";
    public static final String DELAY_QUEUE_NAME = "delay.queue.demo.business.queue";
    public static final String DELAY_QUEUE_ROUTING_KEY = "delay.queue.demo.business.queue.routingkey";
    public static final String DEAD_LETTER_EXCHANGE = "delay.queue.demo.deadletter.exchange";
    public static final String DEAD_LETTER_QUEUE_ROUTING_KEY = "delay.queue.demo.deadletter.delay_anytime.routingkey";
    public static final String DEAD_LETTER_QUEUE_NAME = "delay.queue.demo.deadletter.queue";

    // 声明延时Exchange
    @Bean("delayExchange")
    public DirectExchange delayExchange(){
        return new DirectExchange(DELAY_EXCHANGE_NAME);
    }

    // 声明死信Exchange
    @Bean("deadLetterExchange")
    public DirectExchange deadLetterExchange(){
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }


    // 声明延时队列 不设置TTL
    // 并绑定到对应的死信交换机
    @Bean("delayQueue")
    public Queue delayQueue(){
        Map<String, Object> args = new HashMap<>(3);
        // x-dead-letter-exchange    这里声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        // x-dead-letter-routing-key  这里声明当前队列的死信路由key
        args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUE_ROUTING_KEY);
        return QueueBuilder.durable(DELAY_QUEUE_NAME).withArguments(args).build();
    }

    // 声明死信队列 用于接收延时任意时长处理的消息
    @Bean("deadLetterQueue")
    public Queue deadLetterQueue(){
        return new Queue(DEAD_LETTER_QUEUE_NAME);
    }


    // 声明延时队列 绑定关系
    @Bean
    public Binding delayBinding(@Qualifier("delayQueue") Queue queue,
                                @Qualifier("delayExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(DELAY_QUEUE_ROUTING_KEY);
    }


    // 声明死信队列 绑定关系
    @Bean
    public Binding deadLetterBinding(@Qualifier("deadLetterQueue") Queue queue,
                                      @Qualifier("deadLetterExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(DEAD_LETTER_QUEUE_ROUTING_KEY);
    }
}
