package com.handong.cra.crawebbackend.config;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ConstantDelay;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ReddisonConfig {
    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + host + ":" + port)
                .setConnectionPoolSize(10)       // 커넥션 풀 최대 크기
                .setConnectionMinimumIdleSize(5) // 최소 유지 커넥션
                .setConnectTimeout(3000)         // 연결 타임아웃 (ms)
                .setRetryAttempts(3)             // 락 획득 실패 시 재시도 횟수
                .setRetryDelay(new ConstantDelay(Duration.ofMillis(1500)));         // 재시도 간격 (ms)

        return Redisson.create(config);
    }
}
