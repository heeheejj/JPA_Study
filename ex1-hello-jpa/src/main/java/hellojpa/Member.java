package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToOne
    @JoinColumn(name = "locker_id")
    private Locker locker;

    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<MemberProduct>();

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

    public void setTeam(Team team) {
        this.team = team;
    }

    public void changeTeam(Team team) {    // 강사님 개인 취향이지만 로직 없이 set할때만 setXxx라 이름짓고,
                                        // jpa 상태를 변경하거나 연관관계 편의 메서드같이 로직이 들어갈 때는 changeXxx라고 이름 짓는다.
                                        // 단순한 getter, setter 관례에 의한 것이 아니라 뭔가 로직이 있구나 라는 것을 이름만 보고 알 수 있게!
        this.team = team;
        team.getMembers().add(this); // 연관관계 편의 메소드
    }
}
