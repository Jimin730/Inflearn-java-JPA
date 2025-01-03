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


        try {

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
//            member.setTeamId(team.getId()); //객체지향스럽지 않은 코드
            member.setTeam(team);
            em.persist(member);

            //직접 db에 쿼리 날리는 것 보고싶을 때
//            em.flush();
//            em.clear();

            Member findMember = em.find(Member.class, member.getId());

            Team findTeam = findMember.getTeam();
            System.out.println("findTeam = " + findTeam.getName());


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }


        emf.close(); //전체 애플리케이션이 종료되면 entityManagerFactory를 닫아준다.
    }
}
