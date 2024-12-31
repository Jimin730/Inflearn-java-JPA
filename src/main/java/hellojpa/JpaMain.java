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

        //회원 등록
//        try {
//            Member member = new Member();
//
              //비영속
//            member.setId(1L);
//            member.setName("HelloA");
//
              //영속
//            em.persist(member);//member를 저장할때 persist()를 사용한다.
//            tx.commit(); //정상적으로 실행될 때만 트랜잭션 커밋을 해준다. 커밋을 안하면 db에 반영이 안됨.
//        } catch (Exception e) {
//            tx.rollback(); //트랜잭션 실행 중 문제가 생기면 롤백을 해준다.
//        } finally {
//            em.close(); //모두 다 끝나면 em을 종료한다. (중요) 사용을 다하면 꼭 닫아주어야 한다.
//        }

        //회원 정보 조회 및 삭제
//        try {
//
//            Member findMember = em.find(Member.class, 1L); //회원 정보를 조회할 때 find 사용
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.name = " + findMember.getName());
//
//            em.remove(findMember); //회원을 삭제하고 싶을 땐 찾은 회원을 remove안에 넣어주면 된다.
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }

        //회원 정보 수정
//        try {
//
//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("HelloJPA");
//
//            //회원 정보 수정 후 em.persist() 안해도 된다.
//            //jpa를 통해서 가져온 정보는 jpa가 관리하게 되는데, 뭔가 바뀐 것 같으면 jpa가 알아서 update 쿼리를 날리게 된다.
//            //즉, 우리는 수정 후 따로 별도의 저장을 하지 않아도 되는 것이다.
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }

        try {

            //Member findMember = em.find(Member.class, 1L);
            //JPQL
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            //setFirstResult(1), setMaxResult(10) 는 페이징 할때 1부터 10까지 데이터 가져오고 싶을 때 사용하는 것이다.

            for (Member member : result) {
                System.out.println("member.name = " + member.getName());
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }


        // 준영속 내용 추가
        try {

            Member member = em.find(Member.class, 150L);
            member.setName("AAAAA");

            //em.detach(member); //해당 엔티티를 영속성 컨텍스트에서 제외 (준영속)
            em.clear(); //엔티티 매니저 안에 있는 영속성 컨텍스트를 모두 초기화

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); //영속성 컨텍스트를 종료
        }

        emf.close(); //전체 애플리케이션이 종료되면 entityManagerFactory를 닫아준다.
    }
}
