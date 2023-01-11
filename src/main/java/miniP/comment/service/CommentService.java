package miniP.comment.service;

import miniP.comment.dto.CommentRequestDto;
import miniP.comment.dto.CommentResponseDto;
import miniP.member.entity.Member;

public interface CommentService {


    void postComment(Long BoardId, CommentRequestDto commentRequestDto, Member member);

    void deleteOne(Long id, Member member);

    void modifyComment(Long id, CommentRequestDto commentRequestDto,Member member);

}
