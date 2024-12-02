package com.practice.product.service

import com.practice.product.dto.OrderDto
import com.practice.product.entity.Product
import com.practice.product.repository.ProductRepository
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationEventPublisher
import org.springframework.test.context.junit.jupiter.SpringExtension
import spock.lang.Specification

import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderServiceTest extends Specification {

    @Autowired
    ProductRepository productRepository
    @Autowired
    OrderService orderService

    def setup(){
        Product product = Product.builder()
        .name("testProduct")
        .price(1000L)
        .quantity(10)
        .build()

        productRepository.save(product)
    }

    /**
     * 제품 번호 1번의 주문이 동시에 10개가 들어온다. 기존에 제품 갯수는 10개로 되어있다.
     * 이럴 경우 제품의 결과는 0개가 되어야한다.
     */
    def "주문 동시성 테스트"(){
        given:
        ExecutorService executorService = Executors.newFixedThreadPool(10)
        CountDownLatch countDownLatch = new CountDownLatch(10)
        OrderDto orderDto = OrderDto.builder()
        .productId(1L)
        .quantity(1)
        .build()

        when:
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    orderService.order(List.of(orderDto))
                } catch (Exception e) {
                    throw new RuntimeException()
                } finally {
                    countDownLatch.countDown()
                }
            })
        }

        countDownLatch.await()
        executorService.shutdown()

        Product product = productRepository.findById(1L).map {it->it}.orElse(null)

        then:
        product.getQuantity() == 0
    }

    /**
     * 비관적 락
     * 제품 번호 1번의 주문이 동시에 10개가 들어온다. 기존에 제품 갯수는 10개로 되어있다.
     * 이럴 경우 제품의 결과는 0개가 되어야한다.
     */
    def "비관적 락 주문 동시성 테스트"(){
        given:
        ExecutorService executorService = Executors.newFixedThreadPool(10)
        CountDownLatch countDownLatch = new CountDownLatch(10)
        OrderDto orderDto = OrderDto.builder()
                .productId(1L)
                .quantity(1)
                .build()

        when:
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    orderService.orderWithLock(orderDto)
                } catch (Exception e) {
                    throw new RuntimeException()
                } finally {
                    countDownLatch.countDown()
                }
            })
        }

        countDownLatch.await()
        executorService.shutdown()

        Product product = productRepository.findById(1L).map {it->it}.orElse(null)

        then:
        product.getQuantity() == 0
    }

}
