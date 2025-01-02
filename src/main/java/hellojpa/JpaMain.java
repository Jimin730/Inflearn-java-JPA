package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        //EntityManagerFactory는 애플리케이션 로딩 시점에 딱 한개만 만들어준다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); //아까 설정 파일에서 있던 persistenceUnitName에서 설정해 둔 "hello"이름을 넘겨주면 된다. 설정 파일에 있는 설정정보를 읽어와 만든다.

        //실제 db에 저장하는 등의 트랜잭션의 단위를 할때마다 em을 꼭 만들어 주어야한다.
        EntityManager em = emf.createEntityManager(); //emf에서 createEntityManager를 꺼내본다.

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //필드와 컬럼 매핑 내용 추가
        try {
            Member member1 = new Member();
            member1.setUsername("A");

            Member member2 = new Member();
            member2.setUsername("B");

            Member member3 = new Member();
            member3.setUsername("C");

            System.out.println("===================");

            //시퀀스 호출 시
            //DB SEQ = 1     |   1
            //DB SEQ = 51    |   2
            //DB SEQ = 51    |   3

            em.persist(member1); //1, 51 db에서 호출
            em.persist(member2); //메모리에서 호출
            em.persist(member3); //메모리에서 호출

            System.out.println("member1 = " + member1.getId());
            System.out.println("member2 = " + member2.getId());
            System.out.println("member3 = " + member3.getId());


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }


        emf.close(); //전체 애플리케이션이 종료되면 entityManagerFactory를 닫아준다.
    }
}
