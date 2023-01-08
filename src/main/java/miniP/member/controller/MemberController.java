package miniP.member.controller;

import lombok.RequiredArgsConstructor;
import miniP.member.dto.loginResponseDto;
import miniP.member.dto.signupRequestDto;
import miniP.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/signup") // 회원가입
    public ResponseEntity<Void> signupUser(@RequestBody @Valid signupRequestDto signupRequestDto) {
         memberService.signup(signupRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/checkSignup") // 이렇게 하는 방식이 맞나?
    public void checkUserSign(@RequestBody @Valid signupRequestDto signupRequestDto){
        memberService.checkByMemberDuplicated(signupRequestDto.getUsername());
    }


    // 로그인 시 반환 데이터 -> 토큰을 헤더에 담아서 보내야할까, body에 담아서 보내야 할 까.. -> 카카오는 바디에 실어서 보내네?
    @PostMapping("/login") // 로그인
    public ResponseEntity<loginResponseDto> login(@RequestBody signupRequestDto signupRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(memberService.login(signupRequestDto));
    }
}
