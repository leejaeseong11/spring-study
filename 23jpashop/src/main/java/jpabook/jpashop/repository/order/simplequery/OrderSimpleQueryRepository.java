package jpabook.jpashop.repository.order.simplequery;

import jakarta.persistence.EntityManager;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.List;

// 레포지토리에서 순수 엔티티를 조회하는 것이 아닌 dto를 직접 가져와 api 스펙에 영향을 주게되는 경우 이와 같이 분리하여 관리하는 것을 권장
@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {
    private final EntityManager em;

    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery(
                        "select new jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o join o.member m join o.delivery d",
                        OrderSimpleQueryDto.class)
                .getResultList();
    }
}
