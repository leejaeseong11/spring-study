package hellojpa;

import jakarta.persistence.*;

@Entity
@Table
// @SequenceGenerator(name = "member_seq_generator", sequenceName = "member_esq")
// @TableGenerator(
//        name = "MEMBER_SEQ_GENERATOR",
//        table = "MY_SEQUENCE",
//        pkColumnName = "MEMBER_SEQ",
//        allocationSize = 1)
public class Member {
    @Id
    // 기본키 생성 전략을 DB에 위임, 이 경우 영속성 컨텍스트 관리를 위해 트랜잭션 커밋 전에 insert 쿼리가 발생함
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @SequenceGenerator와 함께 시퀀스 전략, insert할 때 영속성 컨텍스트 관리를 위해 DB에서 시퀀스 값을 먼저 가져옴
    // @SequenceGenerator에서 allocationSize 옵션을 쓰면 DB 시퀀스 값을 미리 올려두고 메모리에서 값을 가져와서 설정하기 때문에 시퀀스 값을 매번
    // 가져오지 않음
    //    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
    // 시퀀스로 쓸 TABLE을 생성하는 전략
    //    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_SEQ_GENERATOR")
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    //    @Column(name = "TEAM_ID")
    //    private Long teamId;
    // 외래키가 있는 곳이 연관관계의 주인
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    //    private Integer age;

    // EnumType.ORDINAL: enum 타입의 순서가 저장됨
    // EnumType.STRING: enum 타입의 실제 값이 저장됨
    //    @Enumerated(EnumType.STRING)
    //    private RoleType roleType;

    // @Temporal 대신 그냥 LocalDate 쓰면 됨
    //    @Temporal(TemporalType.TIMESTAMP)
    //    private Date createdDate;

    //    @Temporal(TemporalType.TIMESTAMP)
    //    private Date lastModifiedDate;

    // BLOB, CLOB 타입에 매핑
    //    @Lob private String description;

    //    @Transient // DB와 매핑 안 함
    //    private int temp;

    /*
    @Column 매핑 옵션
    - name: 필드와 매핑할 테이블 컬럼 이름
    - insertable, updatable: 등록, 변경 가능 여부
    - nullable: NULL 허용 여부
    - unique: 유니크 제약조건
    - columnDefinition: 컬럼을 직접 정의
    - precision, decimal: BigDecimal 타입 매핑에 사용
    */

    // JPA에서 리플렉션을 통한 객체를 생성하기 때문에 기본 생성자 필수
    public Member() {}

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

    public Team getTeam() {
        return team;
    }

    public void changeTeam(Team team) {
        this.team = team;
        // 양방향 관계에서는 양쪽 모두 데이터 넣어주기, 혹은 반대쪽에서 해도 괜찮음, 단 무한루프 조심
        team.getMembers().add(this);
    }
}
