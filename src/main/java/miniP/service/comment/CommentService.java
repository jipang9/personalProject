package miniP.service.comment;

import miniP.dto.comment.CommentRequestDto;
import miniP.dto.comment.CommentResponseDto;

public interface CommentService {


    CommentResponseDto postComment(Long BoardId, CommentRequestDto commentRequestDto);

    void deleteOne(Long id);

    CommentResponseDto modifyComment(Long id, CommentRequestDto commentRequestDto);

}
