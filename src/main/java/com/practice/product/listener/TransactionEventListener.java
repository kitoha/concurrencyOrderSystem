package com.practice.product.listener;

import org.redisson.api.RLock;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TransactionEventListener {

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
  public void handleTransactionEvent(RLock lock) {
    if (lock.isHeldByCurrentThread()) {
      lock.unlock();
    }
  }
}
