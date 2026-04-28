package com.bnroll.tm.auth.config.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisJsonService {

	private final StringRedisTemplate redisTemplate;
	private final ObjectMapper objectMapper;

	public RedisJsonService(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
		this.redisTemplate = redisTemplate;
		this.objectMapper = objectMapper;
	}

	// SAVE OBJECT
	public <T> void set(String key, T value, Duration ttl) {
		try {
			String json = objectMapper.writeValueAsString(value);
			redisTemplate.opsForValue().set(key, json, ttl);
		} catch (Exception e) {
			throw new RuntimeException("Redis serialization error", e);
		}
	}

	// GET OBJECT
	public <T> T get(String key, Class<T> clazz) {
		try {
			String json = redisTemplate.opsForValue().get(key);

			if (json == null)
				return null;

			return objectMapper.readValue(json, clazz);
		} catch (Exception e) {
			throw new RuntimeException("Redis deserialization error", e);
		}
	}

	public void delete(String key) {
		redisTemplate.delete(key);
	}
}