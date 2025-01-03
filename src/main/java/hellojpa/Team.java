package hellojpa;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    //양방향 연관관계에서 주인이 아닌쪽
    @OneToMany(mappedBy = "team") //팀은 1의 입장이니 OneToMany. member 클래스에 team과 연결되어 있어서 mappedBy = "team" (private Team team;)
    private List<Member> members = new ArrayList<>(); //arrayList로 만들면 add할 때 널포인터가 뜨지 않아서 관례로 많이 사용된다.

    public void addMember(Member member) { //양방향 관계 편의 메서드 (양쪽으로 값 세팅)
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
