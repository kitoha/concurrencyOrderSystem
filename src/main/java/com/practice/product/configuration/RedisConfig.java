package com.practice.product.configuration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

  @Value("${spring.data.redis.host")
  private String host;
  @Value("${spring,data.redis.port")
  private Integer port;

  private static final String REDIS_HOST_PREFIX = "redis://";

  @Bean
  public RedissonClient redissonClient(){
    Config config = new Config();
    config.useSingleServer()
        .setAddress(REDIS_HOST_PREFIX + host +":" + port);
    return Redisson.create(config);
  }

}
