package com.cw.delayqueuedemo.service;

import com.cw.delayqueuedemo.models.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RedisDelayingQueueService {

    private static ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 可以不同业务用不同的key
     */
    public static final String QUEUE_NAME = "message:queue";


    /**
     * 插入消息
     *
     * @param message
     * @return
     */
    @SneakyThrows
    public Boolean push(Message message) {
        Boolean addFlag = redisTemplate.opsForZSet().add(QUEUE_NAME, mapper.writeValueAsString(message), message.getDelayTime());
        return addFlag;
    }

    /**
     * 移除消息
     *
     * @param message
     * @return
     */
    @SneakyThrows
    public Boolean remove(Message message) {
        Long remove = redisTemplate.opsForZSet().remove(QUEUE_NAME, mapper.writeValueAsString(message));
        return remove > 0 ? true : false;
    }


    /**
     * 拉取最新需要
     * 被消费的消息
     * rangeByScore 根据score范围获取 0-当前时间戳可以拉取当前时间及以前的需要被消费的消息
     *
     * @return
     */
    public List<Message> pull() {
        Set<String> strings = redisTemplate.opsForZSet().rangeByScore(QUEUE_NAME, 0, System.currentTimeMillis());
        if (strings == null) {
            return null;
        }
        List<Message> msgList = strings.stream().map(msg -> {
            Message message = null;
            try {
                message = mapper.readValue(msg, Message.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return message;
        }).collect(Collectors.toList());
        return msgList;
    }


}
