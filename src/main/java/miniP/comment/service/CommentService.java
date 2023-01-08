package miniP.comment.service;

import miniP.comment.dto.CommentRequestDto;
import miniP.comment.dto.CommentResponseDto;

public interface CommentService {


    CommentResponseDto postComment(Long BoardId, CommentRequestDto commentRequestDto);

    void deleteOne(Long id);

    CommentResponseDto modifyComment(Long id, CommentRequestDto commentRequestDto);

}
