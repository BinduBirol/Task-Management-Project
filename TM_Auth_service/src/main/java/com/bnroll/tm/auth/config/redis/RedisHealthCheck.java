package com.bnroll.tm.auth.config.redis;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisHealthCheck {

    @Autowired
    private StringRedisTemplate redis;

    @PostConstruct
    public void testConnection() {
        try {
            redis.opsForValue().set("health", "ok");
            String value = redis.opsForValue().get("health");

            System.out.println("Redis Connected ✔ -> " + value);

        } catch (Exception e) {
            System.err.println("Redis NOT connected ❌");
            e.printStackTrace();
        }
    }
}