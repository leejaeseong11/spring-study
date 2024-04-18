package hellojpa;

import jakarta.persistence.*;

@Entity
// 상속 관계에서 테이블 생성 전략 설정
// @Inheritance(strategy = InheritanceType.JOINED) // 자식 클래스가 각각의 테이블로 생성됨, 보통 해당 전략 사용
// @Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 하나의 테이블에 다 들어감
// 자식 클래스에 부모 클래스가 컬럼으로 들어감, 부모 클래스는 추상 클래스여야 함
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn // 자식 클래스의 데이터 타입을 표시하는 DTYPE 컬럼 만듦
public abstract class Item {
    @Id @GeneratedValue private Long id;

    private String name;
    private int price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
