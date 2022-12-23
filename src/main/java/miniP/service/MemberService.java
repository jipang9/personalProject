package miniP.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniP.dto.member.RegisterResponseDto;
import miniP.dto.member.RegisterRequestDto;
import miniP.entity.Member;
import miniP.exception.member.MemberExistException;
import miniP.jwt.JwtUtil;
import miniP.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder


    // 회원가입
    @Transactional
    public void registerMember(RegisterRequestDto registerRequestDto) throws RuntimeException {
        validateDuplicatedMember(registerRequestDto.getUsername());
        Member member = registerRequestDto.toEntity(registerRequestDto);
        memberRepository.save(member);
    }

    private void validateDuplicatedMember(String username) throws RuntimeException {
        if (memberRepository.findByUsername(username).isPresent())
            throw new MemberExistException("이미 등록된 사용자입니다");
    }


    @Transactional
    public RegisterResponseDto login(RegisterRequestDto memberRegisterDto, HttpServletResponse httpServletResponse) {
            Member member = memberRepository.findByUsername(memberRegisterDto.getUsername()).orElseThrow(() -> new IllegalArgumentException("사용자 존재 x"));
            member.checkByPassword(member, memberRegisterDto);
            httpServletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtTokenProvider.createToken(member.getUsername()));
            return new RegisterResponseDto(memberRegisterDto.getUsername(), jwtTokenProvider.createToken(member.getUsername()));
        }
    }
