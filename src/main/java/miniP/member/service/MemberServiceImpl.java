package miniP.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 궁금한 점 1. 회원가입 로직에서 사용자의 체크를 해야할까??
     * 이 궁금한 부분의 시작점은 "사용자의 체크를 왜 회원가입 로직속에서 해야하냐"에서 시작되었다 (그냥 table을 유니크키로만들면 끝?)
     * <p>
     * 사용자 아이디의 중복 체크는 우리가 로그인 화면에서 보는 것처럼 따로 중복에 관한 컨트롤러를 하나 만들어서 check를 해야한다고 생각했다.
     * <p>
     * 나만의 이유를 추리해보자
     * 네이버 같은 경우엔 데이터를 작성하고 다음 칸으로 넘어가면 바로 check가 된다 -> 아마 onchange 겠지?
     * 예를들어서 회원가입 시 -> 가입가능한 아이디 -> 정보 작성중 누군가 이 아이디로 가입을 해버렸음 -> 이런걸 방지하고자 2중 보안장치가 필요하다 생각해서
     * entity에도 unique key를 걸었고, 또 중복체크 기능이 필요하다 생각함.
     * <p>
     * 저장시점에서 db랑 확인할 것이다 만약에 유니크 키만 걸어놨으면 (쿼리를 확인해보면 select 쿼리 vs insert 쿼리)
     * 성능면에선 데이터가 전달되는 시점으로부터 check하는 방식인 select 가 더 빠르다 -> 직접 확인함 -> 과연 이러한 방식이 유의미할까?
     */
    // 회원가입
    @Transactional
    @Override
    public void signup(signupRequestDto signupRequestDto) {
        checkByMemberDuplicated(signupRequestDto.getUsername()); // 해당 궁금증이 생긴 부분
        Member member = signupRequestDto.toEntity(passwordEncoder.encode(signupRequestDto.getPassword()));
        memberRepository.save(member);
        log.info(member.getUsername(), member.getPassword(), member.getRole());
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
    public UserInfoResponseDto getMyInfo(Long id) {
        Member member =memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return UserInfoResponseDto.of(member);
    }

}
