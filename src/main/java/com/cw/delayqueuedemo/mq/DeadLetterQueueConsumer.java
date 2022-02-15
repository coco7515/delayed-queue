package com.cw.delayqueuedemo.mq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static com.cw.delayqueuedemo.config.rabbitmq.DelayedRabbitMQConfig.DELAYED_QUEUE_NAME;
import static com.cw.delayqueuedemo.config.rabbitmq.RabbitMQConfig.*;

@Slf4j
@Component
public class DeadLetterQueueConsumer {


    @RabbitListener(queues = DEAD_LETTER_QUEUE_NAME)
    public void receiveC(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("延时队列的RabbitMQ实现(一) ==> 当前时间：{},死信队列收到消息：{}", LocalDateTime.now(), msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = DELAYED_QUEUE_NAME)
    public void receiveD(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("延时队列的RabbitMQ实现(二) ==> 当前时间：{},延时队列收到消息：{}", LocalDateTime.now(), msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
