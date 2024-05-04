package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;

import jpabook.jpashop.domain.Item.Book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ItemUpdateTest {
    @Autowired EntityManager em;

    @Test
    public void updateTest() throws Exception {
        Book book = em.find(Book.class, 1L);

        // 한 트랜잭션 내에서 엔티티의 값만 변경하면 JPA가 변경 감지를 통해 커밋시점에 DB 업데이트를 수행함 == dirty checking
        book.setName("change name");
    }
}
