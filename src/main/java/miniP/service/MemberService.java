package miniP.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniP.dto.member.RegisterRequestDto;
import miniP.dto.member.RegisterResponseDto;
import miniP.entity.Member;
import miniP.exception.NotExistMemberException;
import miniP.exception.login.LoginFailureException;
import miniP.exception.member.MemberExistException;
import miniP.jwt.JwtTokenProvider;
import miniP.repository.MemberRepository;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    // 회원가입
    @Transactional
    public void registerMember(RegisterRequestDto registerRequestDto) throws RuntimeException {
        validateDuplicatedMember(registerRequestDto.getUsername());
        Member member = registerRequestDto.toEntity(passwordEncoder.encode(registerRequestDto.getPassword()));
        memberRepository.save(member);
        log.info(member.getUsername(), member.getPassword(), member.getRole());
    }

    private void validateDuplicatedMember(String username) throws RuntimeException {
        if (memberRepository.findByUsername(username).isPresent())
            throw new MemberExistException("이미 등록된 사용자입니다");
    }


    @Transactional
    public RegisterResponseDto loginMember(RegisterRequestDto registerRequestDto) {
        Member member = memberRepository.findByUsername(registerRequestDto.getUsername()).orElseThrow(() -> new NotExistMemberException());
        if (!passwordEncoder.matches(registerRequestDto.getPassword(), member.getPassword()))
            throw new LoginFailureException();
        member.updateRefreshToken(jwtTokenProvider.createRefreshToken());
        return RegisterResponseDto.of(member, jwtTokenProvider.createToken(registerRequestDto.getUsername()));
    }
}
