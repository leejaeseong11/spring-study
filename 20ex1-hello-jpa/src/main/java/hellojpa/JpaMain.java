package hellojpa;

import jakarta.persistence.*;

import java.util.List;

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

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            //            member.setTeamId(team.getId());
            member.changeTeam(team);
            em.persist(member);

            // 영속성 컨텍스트를 정리하지 않으면 팀에 속한 유저 정보를 제대로 가져오지 못함
            //            em.flush();
            //            em.clear();
            // 양방향 관계에서는 양쪽 모두 데이터를 넣어주는 것이 좋음, 위 과정 없어도 데이터 조회됨
            //            연

            Member findMember = em.find(Member.class, member.getId());
            List<Member> members = findMember.getTeam().getMembers();

            for (Member m : members) {
                System.out.println("m.getUsername() = " + m.getUsername());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
