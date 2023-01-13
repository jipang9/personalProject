package miniP.comment.service;

import miniP.comment.dto.CommentRequestDto;
import miniP.comment.dto.CommentResponseDto;
import miniP.member.entity.Member;

import java.util.List;

public interface CommentService {


    void postComment(Long BoardId, CommentRequestDto commentRequestDto, Member member);

    void deleteOne(Long id, Member member);

    void modifyComment(Long id, CommentRequestDto commentRequestDto,Member member);

    void deleteCommentsByBoard(Long id);

    Long countCommentByBoardId(Long id); // comment 갯수 가지고 오기

    List<CommentResponseDto> comments(Long id);

}
