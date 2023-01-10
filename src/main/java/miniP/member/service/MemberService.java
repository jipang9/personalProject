package miniP.member.service;

import miniP.member.dto.UserInfoResponseDto;
import miniP.member.dto.loginResponseDto;
import miniP.member.dto.signupRequestDto;

public interface MemberService {

    void signup(signupRequestDto signupRequestDto); // 회원가입

    void checkByMemberDuplicated(String username); // 사용자 중복 확인

    loginResponseDto login(signupRequestDto signupRequestDto); // 사용자 로그인

    UserInfoResponseDto getMyInfo(Long id);

}
