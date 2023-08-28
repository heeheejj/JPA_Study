package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

//• 엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
//• 엔티티 매니저는 쓰레드간에 공유X (사용하고 버려야 한다).
//• JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction(); // JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
        tx.begin();

        try{
            Team team = new Team();
            team.setName("TeamA");  // TeamA
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.changeTeam(team);   // 연관관계 편의 메서드 (member 기준으로 만들었을 때)
            em.persist(member);

//            team.addMember(member); // 연관관계 편의 메서드 (team 기준으로 만들었을 때)

//            team.getMembers().add(member); // 순수 객체 상태를 고려해서 항상 양쪽에 값을 입력하자.

//            em.flush(); // 이 아래에서 영속성 컨텍스트가 아닌 DB에 쿼리를 날리기 위해 flush()를 호출한다.
//            em.clear();

            Team findTeam = em.find(Team.class, team.getId());  // 위에서 flush, clear 한 적이 없으면 1차 캐시에서 가져온다. (select 쿼리 날아감)
                                                                // 따라서, Member Entity에서 연관관계 편의 메서드(setTeam)를 작성하지 않거나,
                                                                // team.getMembers().add(member); 를 하지 않으면
                                                                // member.setTeam(team)을 했어도 team에 member가 없다.
            List<Member> members = findTeam.getMembers();
            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }

            tx.commit();
        } catch(Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
