package hellojpa;

import jakarta.persistence.*;

@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne //멤버입장에선 Many, 팀은 One
    @JoinColumn(name = "TEAM_ID") //조인해야 하는 애가 누구인지 명시
    private Team team; //양방향 연관관계에서 주인

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

//    public void changeTeam(Team team) { //양방향 관계 편의 메서드. 양쪽에 다 있으면 문제가 생길 수 있어서 한쪽은 주석처리 했음
//        this.team = team;
//        team.getMembers().add(this);
//    }
}
