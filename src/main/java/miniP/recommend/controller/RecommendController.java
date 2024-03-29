package miniP.recommend.controller;

import lombok.RequiredArgsConstructor;
import miniP.recommend.service.RecommendService;
import miniP.security.member.MemberDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend/board")
public class RecommendController {

    private final RecommendService recommendService;

    @PostMapping("/{id}")
    public ResponseEntity<Void> recommendUp(@PathVariable("id") Long id, @AuthenticationPrincipal MemberDetails memberDetails){
        recommendService.upRecommendByBoard(id, memberDetails.getMember());
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/unlike/{id}")
    public ResponseEntity<Void> recommendDown(@PathVariable("id") Long id, @AuthenticationPrincipal MemberDetails memberDetails){
        recommendService.downRecommendByBoard(id, memberDetails.getMember());
        return ResponseEntity.status(201).build();
    }

}
