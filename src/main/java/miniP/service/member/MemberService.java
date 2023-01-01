package miniP.service.member;

import miniP.dto.member.RegisterRequestDto;
import miniP.dto.member.RegisterResponseDto;

public interface MemberService {


    void registerMember(RegisterRequestDto registerRequestDto); // 회원가입

    void checkByMemberDuplicated(String username); // 사용자 중복 확인

    RegisterResponseDto signupMember(RegisterRequestDto registerRequestDto); // 사용자 로그인
}
