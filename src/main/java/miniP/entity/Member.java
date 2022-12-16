package miniP.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import miniP.repository.MemberRepository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    // 사용자 이름
    private String username;

    // 사용자 비밀번호
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Board> boardList = new ArrayList<>();


    @Builder
    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
