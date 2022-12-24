package miniP.entity;


import lombok.*;
import miniP.dto.member.RegisterRequestDto;
import miniP.exception.login.LoginFailureException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
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


    private String refreshToken;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private List<RoleType> role = new ArrayList<>();


    @Builder
    public Member(String username, String password, List<RoleType> role) {
        this.username = username;
        this.password = password;
        this.role= Collections.singletonList(RoleType.ROLE_USER);
    }

    public void addRole(RoleType roleType){
        this.role.add(roleType);
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
