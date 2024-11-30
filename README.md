## 상품 주문 시 동시성 테스트 

### 시나리오

- 10개의 사람(스레드)가 동시에 요청을 하였을 때 상품이 순차적으로 주문이 성공

### 동시성 제어 방법

아래 방법들 중 1 번 방법을 선택하여 동시성 제어를 수행

1. Redisson 분산락

```java
  try{
      if(lock.tryLock(LOCK_WAIT_TIME, LOCK_RELEASE_TIME, TimeUnit.SECONDS)) {
        return lock;
      }else{
        throw new IllegalStateException("Unable to acquire lock for key: " + lockKey);
      }
    }catch (InterruptedException e){
      log.error("Redisson Lock Failed Lock Key : {}", lockKey, e);
      throw new IllegalStateException("Lock acquisition interrupted", e);
  }
```

2. 비관적 락 & 낙관적 락

비관적 락 : 충돌이 난다는 가정하에 사용하며 하나의 리소스에 대해 락을 이용해 잠금을 처리

난곽적 락 : 충돌이 별로 나지 않는다는 가정에서 사용하며 version정보를 바탕으로 충돌이 날 시 롤백

3. Transaction 격리 레벨 

- UNCOMMITED_READ
- COMMITED_READ
- REPEATABLE_READ
- SERIALIZED

