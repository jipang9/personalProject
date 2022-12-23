package miniP.entity;


import lombok.*;
import miniP.dto.member.RegisterRequestDto;
import miniP.exception.login.LoginFailureException;

import javax.persistence.*;

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


    @Builder
    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void checkByPassword(Member member, RegisterRequestDto memberRegisterDto) {
        if(member.getPassword().equals(memberRegisterDto.getPassword()))
            return ;
        else
            throw new LoginFailureException();
    }
}
