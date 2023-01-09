package miniP.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniP.member.dto.UserInfoResponseDto;
import miniP.member.dto.loginResponseDto;
import miniP.member.dto.signupRequestDto;
import miniP.member.service.MemberService;
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
        return ResponseEntity.status(HttpStatus.OK).body(memberService.login(signupRequestDto));
    }

    @GetMapping("/info/me")
    public ResponseEntity<UserInfoResponseDto> getMyInfo(@AuthenticationPrincipal MemberDetails memberDetails) {
        log.info("start");
        UserInfoResponseDto myInfo = memberService.getMyInfo(memberDetails.getMember().getId());
        log.info("end");
        return ResponseEntity.status(200).body(myInfo);
    }

}
