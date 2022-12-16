package miniP.controller;

import lombok.RequiredArgsConstructor;
import miniP.dto.member.MemberLoginResponse;
import miniP.dto.member.MemberRequestDto;
import miniP.service.MemberService;
import miniP.service.ResponseService;
import miniP.service.result.SingleResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign")
public class MemberController {

    private final MemberService memberService;
    private final ResponseService responseService;


    @PostMapping("/register")
    public SingleResult registerMember(@RequestBody @Valid MemberRequestDto memberRequestDto) {
         memberService.registerMember(memberRequestDto);
        return responseService.getSingleResult(HttpStatus.valueOf(201));
    }

    @PostMapping("/login")
    public SingleResult<MemberLoginResponse> loginMember(@RequestBody MemberRequestDto memberRequestDto, HttpServletResponse httpServletResponse){
        MemberLoginResponse data =memberService.login(memberRequestDto, httpServletResponse);
        return responseService.getSingleResult(data);
    }


}
