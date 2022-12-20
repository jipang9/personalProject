package miniP.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniP.dto.member.MemberLoginResponse;
import miniP.dto.member.MemberRequestDto;
import miniP.entity.Member;
import miniP.exception.login.LoginFailureException;
import miniP.exception.member.MemberExistException;
import miniP.exception.member.NotMemberSaveException;
import miniP.jwt.JwtUtil;
import miniP.repository.MemberRepository;
import miniP.service.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtTokenProvider;


    //딱 이거만 놓고 보자 -> 여기에서 요구하는 것? -> 사용id와 비밀번호만 입력받는다
    // 사용자 검증 후 -> 사용자를 저장한다 -> 끝 -> 반환 데이터는 성공 유무 데이터와 상태 코드 (완료)

    // 회원가입
    @Transactional
    public void registerMember(MemberRequestDto memberRequestDto) throws RuntimeException{
        try {
            validateDuplicatedMember(memberRequestDto.getUsername());
            Member member = memberRequestDto.toEntity(memberRequestDto);
            memberRepository.save(member);
        } catch (RuntimeException e) {
            throw new NotMemberSaveException("이미 등록된 회원이 존재합니다");
        }
    }

    // 사용자 중복 검증 -> 근데 보면 사용자 중복도 결국 여기서밖에 안쓰일거 같은데 registerMember에 넣어도 되지 않을까? -> 결국 사용자가 회원가입 할 때만 사용할 것 같은데...
    private void validateDuplicatedMember(String username) throws RuntimeException{
        if (memberRepository.findByUsername(username).isPresent())
            // e.printStackTrace(); 사용하면 아래의 메시지도 던져줌  ( 12.16 - 공부할 것 )
            throw new MemberExistException("이미 등록된 사용자입니다");
    }

    // 로그인 하고자 하는 사용자의 username과 password를 body로 받는다.
    // -> 로그인 성공 시 유저의 정보를 활용해 JWT 토큰 발급 -> 이 토큰을 Headers에 추가 -> 성공에 대한 메시지와 상태코드 반환
    // 지금 사용자의 비밀번호 검증 로직을 login속에 넣었는데, 나중에 뭐 사용자의 정보를 수정한다던지 사용할 일이 생기면 그때는 또 분리해야함.

    @Transactional
    public MemberLoginResponse login(MemberRequestDto memberRegisterDto, HttpServletResponse httpServletResponse)throws RuntimeException {
        try{
            Member member = memberRegisterDto.toEntity(memberRegisterDto);
            Member checkMember = memberRepository.findByUsername(member.getUsername()).orElseThrow(()-> new IllegalArgumentException("존재하지 않은 사용자"));
            if (!checkMember.getPassword().equals(memberRegisterDto.getPassword())) {
                throw new LoginFailureException("비밀번호가 틀렸습니다.");
            }else {
                httpServletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtTokenProvider.createToken(member.getUsername()));
                return new MemberLoginResponse(memberRegisterDto.getUsername(), jwtTokenProvider.createToken(member.getUsername()));
            }
        }catch (RuntimeException e){
            throw new LoginFailureException();
        }
    }


}
