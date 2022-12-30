package miniP.controller;


import lombok.RequiredArgsConstructor;
import miniP.dto.comment.CommentRequestDto;
import miniP.dto.comment.CommentResponseDto;
import miniP.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/post/{id}")
    public ResponseEntity<CommentResponseDto>postComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto){
        CommentResponseDto resultData=commentService.postComment(id, commentRequestDto);
        return ResponseEntity.status(201).body(resultData);
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteComment(@PathVariable("id") Long id,HttpServletRequest request){
        commentService.deleteOne(id);
        return HttpStatus.OK;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CommentResponseDto> modifyComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto){
        return ResponseEntity.status(201).body(commentService.modifyComment(id, commentRequestDto));
    }

}
