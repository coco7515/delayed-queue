package com.cw.delayqueuedemo.controller;

import com.cw.delayqueuedemo.mq.DelayMessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RequestMapping("rabbitmq")
@RestController
public class RabbitMQMsgController {

    @Autowired
    private DelayMessageSender sender;

    @RequestMapping("delayMsg")
    public void delayMsg(String msg, Integer delayTime) {
        log.info("MQ实现(一)放置Message --> 当前时间：{},收到请求，msg:{},delayTime:{}", LocalDateTime.now(), msg, delayTime);
        sender.sendMsg(msg, delayTime);
    }

    @RequestMapping("delayMsg2")
    public void delayMsg2(String msg, Integer delayTime) {
        log.info("MQ实现(二)放置Message --> 当前时间：{},收到请求，msg:{},delayTime:{}", LocalDateTime.now(), msg, delayTime);
        sender.sendDelayMsg(msg, delayTime);
    }
}
