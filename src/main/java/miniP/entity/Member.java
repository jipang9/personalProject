package miniP.entity;


import lombok.*;

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

    private String username;

    private String password;

    @OneToMany(mappedBy = "member")
    private List<Board> boardList = new ArrayList<>();


    @Builder
    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
