package miniP.member.service;

import lombok.RequiredArgsConstructor;
import miniP.exception.ExceptionStatus;
import miniP.exception.member.CustomException;
import miniP.member.dto.UserInfoResponseDto;
import miniP.member.dto.signupRequestDto;
import miniP.member.dto.loginResponseDto;
import miniP.member.entity.Member;
import miniP.security.JwtTokenProvider;
import miniP.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    @Override
    public void signup(signupRequestDto signupRequestDto) {
        checkByMemberDuplicated(signupRequestDto.getUsername());
        Member member = signupRequestDto.toEntity(passwordEncoder.encode(signupRequestDto.getPassword()));
        memberRepository.save(member);
    }

    // 동시성을 막는 방법 -> 제약조건 exception
    @Override
    public void checkByMemberDuplicated(String username) {
        if (memberRepository.findByUsername(username).isPresent())
            throw new CustomException(ExceptionStatus.USER_EMAIL_IS_EXIST);
    }

    @Transactional
    @Override
    public loginResponseDto login(signupRequestDto signupRequestDto) {
        Member member = memberRepository.findByUsername(signupRequestDto.getUsername()).orElseThrow(() -> new CustomException(ExceptionStatus.USER_IS_NOT_EXIST));
        if (!passwordEncoder.matches(signupRequestDto.getPassword(), member.getPassword()))
            throw new CustomException(ExceptionStatus.ID_OR_PASSWORD_DO_NOT_MATCH);
        member.updateRefreshToken(jwtTokenProvider.createRefreshToken());
        return loginResponseDto.of(member, jwtTokenProvider.createToken(signupRequestDto.getUsername()));
    }

    @Override
    public UserInfoResponseDto getMyInfo(String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(IllegalAccessError::new);
        return UserInfoResponseDto.of(member);
    }

}
