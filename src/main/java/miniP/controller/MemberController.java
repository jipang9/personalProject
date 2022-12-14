package miniP.controller;

import lombok.RequiredArgsConstructor;
import miniP.dto.member.MemberRequestDto;
import miniP.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public HttpStatus registerMember(@RequestBody MemberRequestDto memberRequestDto){
        memberService.registerMember(memberRequestDto);
        return HttpStatus.OK;
    }
}
