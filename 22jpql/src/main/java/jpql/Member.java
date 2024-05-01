package jpql;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;

@Entity
@NamedQuery(
        name = "Member.findByUsername",
        query = "select m from Member m where m.username = :username"
)
public class Member {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private int age;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    // 지연 로딩 설정을 하지 않으면 쿼리를 날릴 때 모든 팀의 상세 정보를 조회하게 됨 (N+1 문제)
    // 지연 로딩 설정해도 실제 팀 정보를 조회할 때 N+1 문제 발생함
    // 이를 해결하기 위해 fetch join을 해줄 수 있음
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }

    public Team getTeam() {
        return team;
    }


    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

    public MemberType getType() {
        return type;
    }

    public void setType(MemberType type) {
        this.type = type;
    }
}
