package com.practice.product.service;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DistributedLock {

  private final RedissonClient redissonClient;
  private static final String LOCK_PREFIX = "Lock:";
  private static final Long LOCK_WAIT_TIME = 5L;
  private static final Long LOCK_RELEASE_TIME = 10L;

  public boolean getLock(String key){
    String lockKey = LOCK_PREFIX + key;
    RLock lock = redissonClient.getLock(lockKey);

    try{
      return lock.tryLock(LOCK_WAIT_TIME, LOCK_RELEASE_TIME, TimeUnit.SECONDS);
    }catch (InterruptedException e){
      log.error("Redisson Lock Failed Lock Key : {}",lockKey,e);
      return false;
    } finally {
      if(lock.isHeldByCurrentThread()){
        lock.unlock();
      }
    }
  }

}
