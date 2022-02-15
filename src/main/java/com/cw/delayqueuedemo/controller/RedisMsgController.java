package com.cw.delayqueuedemo.controller;

import com.cw.delayqueuedemo.models.Message;
import com.cw.delayqueuedemo.service.RedisDelayingQueueService;
import com.cw.delayqueuedemo.service.RedissonService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequestMapping("redis")
@RestController
public class RedisMsgController {


    @Autowired
    RedisDelayingQueueService redisDelayingQueueService;

    @Autowired
    RedissonService redissonService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @RequestMapping("delayMsg")
    public void delayMsg(String msg, Long delayTime) {
        log.info("Redis实现(一)放置Message --> 当前时间：{},收到请求，msg:{},delayTime:{}", LocalDateTime.now(), msg, delayTime);
        redisTemplate.opsForValue().set(java.util.UUID.randomUUID()+":"+ msg, msg, delayTime, TimeUnit.MILLISECONDS);
    }

    @RequestMapping("delayMsg2")
    public void delayMsg2(String msg, Long delayTime) {
        log.info("Redis实现(二)放置Message --> 当前时间：{},收到请求，msg:{},delayTime:{}", LocalDateTime.now(), msg, delayTime);
        redisDelayingQueueService.push(new Message(java.util.UUID.randomUUID().toString(), msg, System.currentTimeMillis()+ delayTime, LocalDateTime.now()));
    }

    @RequestMapping("delayMsg3")
    public void delayMsg3(String msg, Long delayTime) {
        log.info("Redis实现(三)放置Message --> 当前时间：{},收到请求，msg:{},delayTime:{}", LocalDateTime.now(), msg, delayTime);
        redissonService.addDelayQueue(msg,delayTime,TimeUnit.MILLISECONDS,RedissonService.REDISSONKEY);
    }

}