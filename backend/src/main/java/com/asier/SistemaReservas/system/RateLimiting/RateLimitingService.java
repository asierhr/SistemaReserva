package com.asier.SistemaReservas.system.RateLimiting;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.asier.SistemaReservas.system.RateLimiting.exception.RateLimitedExceededException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RateLimitingService {
    private final RedisTemplate<String, String> redisTemplate;
    public void checkRateLimit(String key, int limit, long windowSeconds){
        Long count = redisTemplate.opsForValue().increment(key);
        if(count == 1) redisTemplate.expire(key, Duration.ofSeconds(windowSeconds));
        if(count != null && count > limit) throw new RateLimitedExceededException("Too many requests");
    }
}
