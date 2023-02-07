package miniP.recommend.controller;


import lombok.RequiredArgsConstructor;
import miniP.recommend.service.RecommendService;
import miniP.security.member.MemberDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend/comment")
public class CommentRController {

    private final RecommendService recommendService;

    @PostMapping("/{id}")
    public ResponseEntity<Void> recommendUp(@PathVariable("id") Long id, @AuthenticationPrincipal MemberDetails memberDetails){
        recommendService.upRecommendByComment(id, memberDetails.getMember());
        return ResponseEntity.status(201).build();
    }

}
