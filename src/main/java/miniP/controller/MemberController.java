package miniP.controller;

import lombok.RequiredArgsConstructor;
import miniP.dto.member.RegisterResponseDto;
import miniP.dto.member.RegisterRequestDto;
import miniP.service.MemberService;
import miniP.service.ResponseService;
import miniP.service.result.SingleResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign")
public class MemberController {

    private final MemberService memberService;
    private final ResponseService responseService;


    @PostMapping("/register")
    public SingleResult registerMember(@RequestBody @Valid RegisterRequestDto registerRequestDto) {
         memberService.registerMember(registerRequestDto);
        return responseService.getSingleResult(HttpStatus.valueOf(201));
    }

    @PostMapping("/login")
    public SingleResult<RegisterResponseDto> loginMember(@RequestBody RegisterRequestDto registerRequestDto){
        RegisterResponseDto data =memberService.loginMember(registerRequestDto);
        return responseService.getSingleResult(data);
    }


}
