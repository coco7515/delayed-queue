package com.cw.delayqueuedemo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    /**
     * 消息唯一标识
     */
    private String id;

    /**
     * 具体消息 json
     */
    private String body;

    /**
     * 延时时间 被消费时间  取当前时间戳 延迟时间
     */
    private Long delayTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
