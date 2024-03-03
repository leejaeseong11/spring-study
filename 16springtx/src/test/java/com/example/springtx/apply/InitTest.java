package com.example.springtx.apply;

import jakarta.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@SpringBootTest
public class InitTest {
    @Autowired Hello hello;

    @Test
    void go() {
        // 초기화 코드는 스프링이 초기화 시점에 호출함
    }

    @TestConfiguration
    static class InitTestConfig {
        @Bean
        Hello hello() {
            return new Hello();
        }
    }

    @Slf4j
    static class Hello {
        @PostConstruct
        @Transactional // 초기화 시점에는 트랜잭션 획득 불가
        public void initV1() {
            boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("Hello init @PostConstruct tx active={}", isActive);
        }

        @EventListener(ApplicationReadyEvent.class)
        @Transactional
        public void initV2() {
            boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("Hello init ApplicationReadyEvent tx active={}", isActive);
        }
    }
}
