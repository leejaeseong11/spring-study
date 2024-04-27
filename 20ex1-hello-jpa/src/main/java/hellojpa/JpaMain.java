package hellojpa;

import jakarta.persistence.*;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            /*==========jpa==========*/
            // insert
            //            Member member = new Member();
            //            member.setId(1L);
            //            member.setName("HelloA");
            //            em.persist(member);

            // find
            //            Member findMember = em.find(Member.class, 1L);
            //            System.out.println("findMember.getId() = " + findMember.getId());
            //            System.out.println("findMember.getName() = " + findMember.getName());

            // remove
            //            em.remove(findMember);

            // update
            //            findMember.setName("HelloJPA");

            // JPQL
            //            List<Member> result =
            //                    em.createQuery("select m from Member as m", Member.class)
            //                            .setFirstResult(0)
            //                            .setMaxResults(1)
            //                            .getResultList();
            //            for (Member member : result) {
            //                System.out.println("member.getName() = " + member.getName());
            //            }

            /*==========영속성 컨텍스트==========*/
            // 비영속
            //            Member member = new Member();
            //            member.setId(1L);
            //            member.setName("HelloA");

            // 영속, DB에 저장되지는 않음
            //            em.persist(member);

            // 준영속, 영속성 컨텍스트에서 분리
            //            em.detach(member);

            // 삭제
            //            em.remove(member);

            /*==========영속성 컨텍스트 이점==========*/
            // 1차 캐시에서 조회
            //            Member member = new Member();
            //            member.setId(3L);
            //            member.setName("HelloA");
            //            em.persist(member);
            // select 쿼리 발생하지 않음
            //            Member findMember = em.find(Member.class, 3L);
            //            System.out.println("findMember.getName() = " + findMember.getName());

            // select 쿼리가 한 번만 발생함
            //            Member findMember1 = em.find(Member.class, 3L);
            // 1차 캐시에서 조회
            //            Member findMember2 = em.find(Member.class, 3L);
            // 영속성 엔티티의 동일성 보장
            //            System.out.println("result = " + (findMember1 == findMember2));

            // 쓰기 지연
            //            Member member1 = new Member(4L, "A");
            //            Member member2 = new Member(5L, "B");
            //            em.persist(member1);
            //            em.persist(member2);
            //            System.out.println("===================="); // 이후에 insert 쿼리 발생함

            // 엔티티 수정, 변경 감지(Dirty Checking)
            //            Member member = em.find(Member.class, 4L);
            //            member.setName("ZZZ"); // 영속 엔티티 데이터 수정

            /*==========플러시==========*/
            //            Member member = new Member(6L, "member6");
            //            em.persist(member);
            //
            //            em.flush(); // 1차 캐시는 유지됨
            //            System.out.println("===================="); // 이전에 insert 쿼리 발생함

            /*==========준영속==========*/
            //            Member member = em.find(Member.class, 6L);
            //            member.setName("ZZZ"); // 영속 엔티티 데이터 수정

            //            //            em.detach(member); // 영속성 컨텍스트에서 더 이상 관리하지 않음
            //            em.clear(); // 영속성 컨텍스트를 완전히 비움
            //            Member member2 = em.find(Member.class, 6L); // select 쿼리 두 번 발생

            //            Member member = new Member();
            //            member.setId(1L);
            //            member.setUsername("A");
            //            member.setRoleType(RoleType.USER);
            //            em.persist(member);

            //            Team team = new Team();
            //            team.setName("teamA");
            //            em.persist(team);
            //
            //            Member member = new Member();
            //            member.setUsername("member1");
            //            member.setTeamId(team.getId());
            //            member.changeTeam(team);
            //            member.setTeam(team);
            //            em.persist(member);

            // 영속성 컨텍스트를 정리하지 않으면 팀에 속한 유저 정보를 제대로 가져오지 못함
            //            em.flush();
            //            em.clear();
            // 양방향 관계에서는 양쪽 모두 데이터를 넣어주는 것이 좋음, 위 과정 없어도 데이터 조회됨

            //            Member findMember = em.find(Member.class, member.getId());
            //            List<Member> members = findMember.getTeam().getMembers();
            //            Team findTeam = em.find(Team.class, team.getId());
            //            System.out.println("teamName = " + findMember.getTeam().getName());
            //            System.out.println("teamMembers = " + findTeam.getMembers());
            //            for (Member m : members) {
            //                System.out.println("m.getUsername() = " + m.getUsername());

            //            Member member = new Member();
            //            member.setUsername("member1");
            //            em.persist(member);
            //
            //            Team team = new Team();
            //            team.setName("teamA");
            //            team.getMembers().add(member);
            //            em.persist(team);

            // JOIN 전략으로 테이블을 생성했기 때문에 부모와 자식 테이블에 각각 데이터가 들어감
            //            Movie movie = new Movie();
            //            movie.setDirector("A");
            //            movie.setActor("B");
            //            movie.setName("바람과함께사라지다");
            //            movie.setPrice(10000);
            //
            //            em.persist(movie);
            //
            //            em.flush();
            //            em.clear();

            //            Movie findMovie = em.find(Movie.class, movie.getId());
            //            System.out.println("findMovie = " + findMovie);

            // TABLE_PER_CLASS 전략을 쓰는 경우 부모 타입으로 조회하면 union 쿼리를 써서 성능이 안 좋음
            //            Item findItem = em.find(Item.class, movie.getId());

            //            Member member = new Member();
            //            member.setUsername("user1");
            //            member.setCreatedBy("kim");
            //            member.setCreatedDate(LocalDateTime.now());

            //            Member member2 = new Member();
            //            member2.setUsername("user1");
            //            member2.setCreatedBy("kim");
            //            member2.setCreatedDate(LocalDateTime.now());

            //            em.persist(member);
            //            em.persist(member2);
            //            em.flush();
            //            em.clear();

            //            Member findMember = em.find(Member.class, member.getId());
            //            Member findMember = em.find(Member.class, member.getId());
            //            Member findMember2 = em.getReference(Member.class, member2.getId());
            // 프록시 객체
            // 영속성 컨텍스트에 엔티티가 이미 있으면 getReference()를 쓰더라도 실제 엔티티를 조회함
            //            Member findMember2 = em.getReference(Member.class, member.getId());
            // 반대로 getReference()를 쓴 뒤에 find()를 쓰면 프록시 객체를 조회함, jpa는 한 트랜잭션 내에서 같은 객체의 동등성을 보장하려고 함

            //            System.out.println("findMember.getId() = " + findMember.getId());
            //            System.out.println("findMember.getUsername() = " +
            // findMember.getUsername());
            //            System.out.println("findMember.getClass() = " + findMember.getClass());
            //            System.out.println("findMember2.getClass() = " + findMember2.getClass());
            // 타입 비교 시에는 instanceof를 사용할 것
            //            System.out.println(
            //                    "(findMember == findMember2) = "
            //                            + (findMember.getClass() == findMember2.getClass()));
            //            System.out.println(
            //                    "(findMember instanceof Member) = " + (findMember instanceof
            // Member));

            //            Member findMember = em.getReference(Member.class, member.getId());
            // 영속성 컨텍스트에서 없으면 프록시 객체 사용 불가능
            //            em.detach(findMember);
            //            findMember.getUsername();
            //            System.out.println(
            //                    "프록시 초기호 여부 확인 =  " +
            // emf.getPersistenceUnitUtil().isLoaded(findMember));

            // 가급적 지연 로딩 사용
            //            Team team = new Team();
            //            team.setName("teamA");
            //            em.persist(team);
            //            Member member = new Member();
            //            member.setUsername("user1");
            //            member.setTeam(team);
            //            em.persist(member);
            //
            //            Team teamB = new Team();
            //            team.setName("teamB");
            //            em.persist(teamB);
            //            Member member2 = new Member();
            //            member2.setUsername("user2");
            //            member2.setTeam(teamB);
            //            em.persist(member2);
            //
            //            em.flush();
            //            em.clear();

            //            Member findMember = em.find(Member.class, member.getId());
            //            System.out.println("findMember.getTeam() = " +
            // findMember.getTeam().getClass());
            //            System.out.println(
            //                    "findMember.getTeam().getName() = " +
            // findMember.getTeam().getName());

            // JPQL N+1 problem
            //            List<Member> members =
            //                    em.createQuery("select m from Member m",
            // Member.class).getResultList();
            // N+1 문제 해결하려면 지연 로딩 설정 후 jpql fetch join을 사용할 수 있음
            //            List<Member> members =
            //                    em.createQuery("select m from Member m join fetch m.team",
            // Member.class)
            //                            .getResultList();

            //            Child child1 = new Child();
            //            Child child2 = new Child();
            //            Parent parent = new Parent();
            //            parent.addChild(child1);
            //            parent.addChild(child2);
            //            em.persist(parent);
            //
            //            em.flush();
            //            em.clear();
            //
            //            Parent findParent = em.find(Parent.class, parent.getId());
            //            findParent.getChildList().remove(0);
            //
            //            List<Child> childList =
            //                    em.createQuery("select c from Child c",
            // Child.class).getResultList();
            //            System.out.println("child size= " + childList.size());

            //            MemberEmbedded member = new MemberEmbedded();
            //            member.setName("hello");
            //            member.setHomeAddress(new Address("city", "street", "100"));
            //            member.setWorkPeriod(new Period());
            //            em.persist(member);

            // 임베디드 값 타입을 여러 엔티티가 공유하면 위험하기 때문에 복사해서 사용해야 함
            //            Address address = new Address("city", "street", "100");
            //            MemberEmbedded member = new MemberEmbedded();
            //            member.setName("member1");
            //            member.setHomeAddress(address);
            //            em.persist(member);
            //
            //            Address copyAddress =
            //                    new Address(address.getCity(), address.getStreet(),
            // address.getZipcode());
            //            MemberEmbedded member2 = new MemberEmbedded();
            //            member2.setName("member2");
            //            member2.setHomeAddress(copyAddress);
            //            em.persist(member2);

            // 객체는 차라리 setter를 없애고 불변 객체로 만드는 것이 좋음
            //            address.setCity("newCity");

            // 동등 비교
            //            Address address1 = new Address("city", "street", "100");
            //            Address address2 = new Address("city", "street", "100");
            //            System.out.println("address1==address2 : " + (address1.equals(address2)));

            // 값타입 컬렉션
            MemberEmbedded member = new MemberEmbedded();
            member.setName("member1");
            member.setHomeAddress(new Address("home", "street", "100"));
            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            //            member.getAddressHistory().add(new Address("old1", "street1", "100"));
            //            member.getAddressHistory().add(new Address("old2", "street1", "100"));

            //            em.persist(member);
            //
            //            em.flush();
            //            em.clear();
            // 값 타입 컬렉션은 지연로딩
            //            MemberEmbedded findMember = em.find(MemberEmbedded.class, member.getId());
            //            List<Address> addressHistory = findMember.getAddressHistory();
            //            for (Address address : addressHistory) {
            //                System.out.println("address.getCity() = " + address.getCity());
            //            }
            //            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            //            for (String favoriteFood : favoriteFoods) {
            //                System.out.println("favoriteFood = " + favoriteFood);
            //            }

            // 값타입 변경 시 새로운 객체를 넣어줄 것
            //            Address a = findMember.getHomeAddress();
            //            findMember.setHomeAddress(new Address("newCity", a.getStreet(),
            // a.getZipcode()));
            //
            //            findMember.getFavoriteFoods().remove("치킨");
            //            findMember.getFavoriteFoods().add("한식");

            // 값타입 컬렉션 변경 사항 발생 시 주인 엔티티와 연관된 모든 데이터 삭제 후 컬렉션에 있는 현재 값을 모두 다시 저장함
            //            findMember.getAddressHistory().remove(new Address("old1", "street1",
            // "100"));
            //            findMember.getAddressHistory().add(new Address("newCity1", "street1",
            // "100"));

            // 값타입 컬렉션 대신 일대다 관계 사용 고려
            member.getAddressHistory().add(new AddressEntity("old1", "street1", "100"));
            member.getAddressHistory().add(new AddressEntity("old2", "street1", "100"));

            em.persist(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
