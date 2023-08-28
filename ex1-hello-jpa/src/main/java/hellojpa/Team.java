package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team") // 연관관계의 주인이 아니다. (난 FK가 아니에요) DB에 컬럼을 만들지 마세요.
    private List<Member> members = new ArrayList<Member>(); // 관례로 초기화를 해준다. add할 때 NPE 방지

    public void addMember(Member member) {
        member.setTeam(this);
        members.add(member);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
