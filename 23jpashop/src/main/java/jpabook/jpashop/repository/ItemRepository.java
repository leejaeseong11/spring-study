package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;

import jpabook.jpashop.domain.Item.Item;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            // merge는 준영속 상태의 엔티티를 영속 상태 객체로 반환해줌, 반환하지 않은 item은 영속 상태가 아님
            em.merge(item);
            // null 값도 모두 업데이트 해버리기 때문에 사용하기 위험함, 그냥 변경 감지하는 것을 권장
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
