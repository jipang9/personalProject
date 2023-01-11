package miniP.member.dto;

import lombok.Getter;
import miniP.member.entity.Member;

@Getter
public class UserInfoResponseDto {

    private final Long id;
    private final String username;
//    private final String userRole;


    public UserInfoResponseDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
//        this.userRole = member.getRole().toString();
    }

    public static UserInfoResponseDto of(Member member){
        return  new UserInfoResponseDto(member);
    }

}
