package miniP.dto.member;

import lombok.*;
import miniP.entity.Member;

@Getter
@AllArgsConstructor
public class MemberRequestDto {

    private Long id;

    private String username;
    private String password;


    public Member toEntity(MemberRequestDto memberRequestDto){
        return Member.builder()
                .username(memberRequestDto.getUsername())
                .password(memberRequestDto.getPassword())
                .build();
    }

}
