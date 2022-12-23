package miniP.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import miniP.entity.Member;

@RequiredArgsConstructor
@Getter
@NoArgsConstructor(force = true)
public class RegisterResponseDto {

    private final Long id;
    private final String username;
    private final String token;
    private final String refreshToken;


    public RegisterResponseDto(Member member, String token) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.token = token;
        this.refreshToken = member.getRefreshToken();
    }

    public static RegisterResponseDto of(Member member, String token){
        return new RegisterResponseDto(member, token);
    }

}
