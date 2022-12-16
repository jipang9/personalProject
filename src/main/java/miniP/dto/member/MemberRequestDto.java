package miniP.dto.member;

import lombok.*;
import miniP.entity.Member;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-z0-9]*$")
    private String username;

    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String password;


    public Member toEntity(MemberRequestDto memberRequestDto){
        return Member.builder()
                .username(memberRequestDto.getUsername())
                .password(memberRequestDto.getPassword())
                .build();
    }
}
