package jpabook.jpashop.api;

import static java.util.stream.Collectors.*;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.query.OrderFlatDto;
import jpabook.jpashop.repository.order.query.OrderItemQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryRepository;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDto> result = orders.stream().map(o -> new OrderDto(o)).collect(toList());
        return result;
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDto> result = orders.stream().map(o -> new OrderDto(o)).collect(toList());
        return result;
    }

    // XToOne 관계는 fetch join, XToMany 관계는 batch size로 N+1 문제를 해결
    // batch size는 WAS와 DB(IN절 파라미터 수)의 성능을 고려해야 하는데 100~1000이 적당함
    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);

        List<OrderDto> result = orders.stream().map(o -> new OrderDto(o)).collect(toList());
        return result;
    }

    // 컬렉션 조회 최적화, order 조회에 따른 orderItem을 N번 조회하는 N+1 문제 발생함
    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findIrderQueryDtos();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5() {
        return orderQueryRepository.findAllByDto_optimization();
    }

    @GetMapping("/api/v6/orders")
    public List<OrderQueryDto> ordersV6() {
        List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();

        return flats.stream()
                .collect(
                        groupingBy(
                                o ->
                                        new OrderQueryDto(
                                                o.getOrderId(),
                                                o.getName(),
                                                o.getOrderDate(),
                                                o.getOrderStatus(),
                                                o.getAddress()),
                                mapping(
                                        o ->
                                                new OrderItemQueryDto(
                                                        o.getOrderId(),
                                                        o.getItemName(),
                                                        o.getOrderPrice(),
                                                        o.getCount()),
                                        toList())))
                .entrySet()
                .stream()
                .map(
                        e ->
                                new OrderQueryDto(
                                        e.getKey().getOrderId(),
                                        e.getKey().getName(),
                                        e.getKey().getOrderDate(),
                                        e.getKey().getOrderStatus(),
                                        e.getKey().getAddress(),
                                        e.getValue()))
                .collect(toList());
    }

    @Getter
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getMember().getAddress();
            order.getOrderItems().stream().forEach(o -> o.getItem().getName());
            orderItems = order.getOrderItems().stream().map(OrderItemDto::new).collect(toList());
        }
    }

    @Data
    static class OrderItemDto {
        private String itemName; // 상품명
        private int orderPrice; // 주문 가격
        private int count; // 주문 수량

        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getItem().getPrice();
            count = orderItem.getCount();
        }
    }
}
/*
 * 권장 개발 순서
 * 1. 엔티티 조회 방식으로 구현
 * 1-1. 페치 조인 활용
 * 1-2. 페이징 필요한 컬렉션 조회시 배치 사이즈 활용
 * 2. DTO 조회 방식으로 구현
 * 3. 최후의 방식은 NativeSql, 스프링 JdbcTemplate 활용
 */
