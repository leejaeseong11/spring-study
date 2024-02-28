package hello.itemservice.domain;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
// @Table(name = "item") // 생략 가능
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", length = 10) // 컬럼 이름도 생략 가능
    private String itemName;

    private Integer price;
    private Integer quantity;

    public Item() {} // JPA 사용할 경우 기본 생성자 필수

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
