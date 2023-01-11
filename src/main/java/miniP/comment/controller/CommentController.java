package miniP.comment.controller;


import lombok.RequiredArgsConstructor;
import miniP.comment.dto.CommentRequestDto;
import miniP.comment.dto.CommentResponseDto;
import miniP.comment.service.CommentService;
import miniP.security.member.MemberDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/post/{id}")
    public void postComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal MemberDetails memberDetails){
        commentService.postComment(id, commentRequestDto, memberDetails.getMember());
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteComment(@PathVariable("id") Long id, @AuthenticationPrincipal MemberDetails memberDetails){
        commentService.deleteOne(id, memberDetails.getMember());
        return HttpStatus.OK;
    }

    @PutMapping("/{id}")
    public void modifyComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal MemberDetails memberDetails){
        commentService.modifyComment(id, commentRequestDto,memberDetails.getMember());
    }

}
