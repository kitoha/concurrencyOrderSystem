package com.practice.product.repository;

import com.practice.product.entity.Product;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Page<Product> findAll(Pageable pageable);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select p from Product p where p.id = :id")
  Optional<Product> findByIdWithLock(Long id);

  @Lock(LockModeType.OPTIMISTIC)
  @Query("select p from Product p where p.id = :id")
  Optional<Product> findByIdWithOptimisticLock(Long id);
}
