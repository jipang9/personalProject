package miniP.service;

import lombok.RequiredArgsConstructor;
import miniP.dto.member.MemberRequestDto;
import miniP.entity.Member;
import miniP.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void registerMember(MemberRequestDto memberRegisterDto){
        Member member = memberRegisterDto.toEntity(memberRegisterDto);
        memberRepository.save(member);
    }
}
