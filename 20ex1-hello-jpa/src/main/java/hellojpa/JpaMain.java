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
            List<Member> result =
                    em.createQuery("select m from Member as m", Member.class)
                            .setFirstResult(0)
                            .setMaxResults(1)
                            .getResultList();
            for (Member member : result) {
                System.out.println("member.getName() = " + member.getName());
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
