package miniP.dto.member;

import lombok.*;
import miniP.entity.Member;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public final class RegisterRequestDto {

    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-z0-9]*$")
    private final String username;

    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private final String password;



    public Member toEntity(String  password){
        return Member.builder()
                .username(this.getUsername())
                .password(password)
                .build();
    }


}
