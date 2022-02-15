package com.cw.delayqueuedemo.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        String url = "redis://localhost:6379";
        // 这里以单台redis服务器为例
        config.useSingleServer()
                .setAddress(url)
                .setPassword("password")
                .setDatabase(0)
                .setPingConnectionInterval(2000);
        config.setLockWatchdogTimeout(10000L);

        try {
            return Redisson.create(config);
        } catch (Exception e) {
            log.error("RedissonClient init redis url:[{}], Exception:", url, e);
            return null;
        }
    }
}