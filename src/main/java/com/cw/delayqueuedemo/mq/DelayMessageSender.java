package com.cw.delayqueuedemo.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.cw.delayqueuedemo.config.rabbitmq.DelayedRabbitMQConfig.DELAYED_EXCHANGE_NAME;
import static com.cw.delayqueuedemo.config.rabbitmq.DelayedRabbitMQConfig.DELAYED_ROUTING_KEY;
import static com.cw.delayqueuedemo.config.rabbitmq.RabbitMQConfig.*;

@Component
public class DelayMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String msg, Integer delayTime) {
        rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, DELAY_QUEUE_ROUTING_KEY, msg, a ->{
            a.getMessageProperties().setExpiration(String.valueOf(delayTime));
            return a;
        });
    }

    public void sendDelayMsg(String msg, Integer delayTime) {
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, msg, a ->{
            a.getMessageProperties().setDelay(delayTime);
            return a;
        });
    }
}
