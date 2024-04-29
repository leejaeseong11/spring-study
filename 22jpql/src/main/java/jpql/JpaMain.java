package jpql;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            for(int i = 0; i < 2; i++){
                Team team = new Team();
                team.setName("teamA");
                em.persist(team);

                Member member = new Member();
                member.setUsername("member" + i);
                member.setAge(i);
                member.changeTeam(team);
                member.setType(MemberType.ADMIN);
                em.persist(member);
            }
            em.flush();
            em.clear();
//            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
            // 반환 타입이 명확하지 않은 경우
//            Query query = em.createQuery("select m.username, m.age from Member m");
//            List<Member> result = query.getResultList();
            // 결과가 하나인 경우
//            Member result = query.getSingleResult();
//            for (Member m : result) {
//                System.out.println("m.getUsername() = " + m.getUsername());
//            }
            // 파라미터 바인딩, 메서드 체이닝 가능
//            TypedQuery<Member> query = em.createQuery("select m from Member m where m.username = :username", Member.class);
//            query.setParameter("username", "member1");
//            Member result = query.getSingleResult();
//            System.out.println("result = " + result.getUsername());

            // 임베디드 타입 프로젝션
//            em.createQuery("select o.address from Order o", Address.class).getResultList();

            // 스칼라 타입 프로젝션
//            em.createQuery("select distinct m.username, m.age from Member m").getResultList();
            // 스칼라 타입 new 명령어로 조회하기
//            List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class).getResultList();
//            System.out.println("username = " + resultList.get(0).getUsername());
//            System.out.println("age = " + resultList.get(0).getAge());

            // 페이징
//            List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class).setFirstResult(1).setMaxResults(10).getResultList();
//            System.out.println("size = " + result.size());
//            for (Member m : result) {
//                System.out.println("m = " + m);
//            }

            // 조인
//            String query = "select m from Member m inner join m.team t";
//            List<Member> result = em.createQuery(query, Member.class).getResultList();

//            String query2 = "select m from Member m left join m.team t on t.name = 'teamA'";
//            List<Member> result2 = em.createQuery(query2, Member.class).getResultList();
//            String query2 = "select m from Member m left join Team t on m.username = t.name";
//            List<Member> result2 = em.createQuery(query2, Member.class).getResultList();
            // 서브쿼리도 가능한데 from 절의 서브쿼리는 jpql에서 지원안하기 때문에 가능하면 조인으로 해결

            // jpql 타입 표현
//            String query = "select m.username, 'HELLO', true from Member m where m.type = jpql.MemberType.ADMIN";
//            List<Object[]> result = em.createQuery(query).getResultList();
//
//            for (Object[] objects : result) {
//                System.out.println("objects = " + objects[0]);
//                System.out.println("objects = " + objects[1]);
//                System.out.println("objects = " + objects[2]);
//            }

            // case 조건식
//            List<String> result = em.createQuery("select case when m.age <= 10 then '학생요금' when m.age >= 60 then '경로요금' else '일반요금' end from Member m", String.class).getResultList();
//            for (String s : result) {
//                System.out.println("s = " + s);
//            }

            // coalesce: 하나씩 조회해서 null이 아니면 반환
//            List<String> result = em.createQuery("select coalesce(m.username, '이름 없는 회원') from Member m", String.class).getResultList();
//            for (String s : result) {
//                System.out.println("s = " + s);
//            }
            // null: 두 값이 같으면 null, 다르면 첫번째 값 반환
//            List<String> result = em.createQuery("select nullif(m.username, 'member0') from Member m", String.class).getResultList();
//            for (String s : result) {
//                System.out.println("s = " + s);
//            }

            // jpql 기본 함수
            /*
            * concat: 문자 붙이기
            * substring: 문자 추출
            * trim: 공백 제거
            * lower, upper: 대소문자 변경
            * length: 문자 길이
            * locate: 문자의 위치 (숫자) 반환
            * */
//            List<Integer> result = em.createQuery("select locate('de', 'abcdefg') from Member m", Integer.class).getResultList();
//            System.out.println("result = " + result);

            // 컬렉션의 크기
//            List<Integer> result = em.createQuery("select size(t.members) from Team t", Integer.class).getResultList();
//            System.out.println("result = " + result);

            // 사용자 정의 함수
            List<String> resultList = em.createQuery("select group_concat(m.username) from Member m", String.class).getResultList();
            for (String s : resultList) {
                System.out.println("s = " + s);
            }

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
