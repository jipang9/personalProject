package miniP.member.dto;

import lombok.*;
import miniP.member.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public final class signupRequestDto {

    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-z0-9]*$")
    private final String username;

    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private final String password;

    public Member toEntity(String password){
        return Member.builder()
                .username(this.getUsername())
                .password(password)
                .build();
    }


}
