package hellojpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {
    // cascade 영속성 전이, 자식과 라이프 사이클이 유사하고 소유자가 하나일 때만 사용
    //    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    // orphanRemoval은 부모가 사라진 고아 객체를 자동으로 삭제하는 옵션, CascadeType.REMOVE와 유사
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Child> childList = new ArrayList<>();

    @Id @GeneratedValue private Long id;
    private String name;

    public void addChild(Child child) {
        childList.add(child);
        child.setParent(this);
    }

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

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }
}
