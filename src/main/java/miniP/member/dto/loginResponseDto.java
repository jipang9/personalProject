package miniP.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import miniP.member.entity.Member;

@RequiredArgsConstructor
@Getter
@NoArgsConstructor(force = true)
public class loginResponseDto {

    private final Long id;
    private final String username;
    private final String accessToken;
    private final String refreshToken;


    public loginResponseDto(Member member, String accessToken) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.accessToken = accessToken;
        this.refreshToken = member.getRefreshToken();
    }

    public static loginResponseDto of(Member member, String token){
        return new loginResponseDto(member, token);
    }

}
