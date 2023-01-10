package miniP.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniP.member.dto.UserInfoResponseDto;
import miniP.member.dto.loginResponseDto;
import miniP.member.dto.signupRequestDto;
import miniP.member.service.MemberService;
import miniP.security.SecurityUtil;
import miniP.security.member.MemberDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup") // 회원가입
    public ResponseEntity<Void> signupUser(@RequestBody @Valid signupRequestDto signupRequestDto) {
        memberService.signup(signupRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/login") // 로그인
    public ResponseEntity<loginResponseDto> login(@RequestBody signupRequestDto signupRequestDto) {
        loginResponseDto login = memberService.login(signupRequestDto);
        return ResponseEntity.status(200).body(login);
    }

    @GetMapping("/info/me") // 여기에서만 문제가 생긴다 이 말 // 뺴고 개발해야겠다 일단.
    public ResponseEntity<UserInfoResponseDto> getMyInfo(@AuthenticationPrincipal MemberDetails memberDetails) {
        UserInfoResponseDto myInfo = memberService.getMyInfo(memberDetails.getMember().getId()); // 발생지점 -> getUsername으로 넘어온 정보가 없으니까 null 일 것으로 예상함
        return ResponseEntity.status(200).body(myInfo);
    }

}
