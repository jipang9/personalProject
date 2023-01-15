package miniP.comment.service;

import miniP.board.entity.Board;
import miniP.comment.dto.CommentRequestDto;
import miniP.comment.dto.CommentResponseDto;
import miniP.comment.entity.Comment;
import miniP.member.entity.Member;

import java.util.List;

public interface CommentService {


    void postComment(Long BoardId, CommentRequestDto commentRequestDto, Member member); // 댓글 작성하기.

    void deleteOne(Long id, Member member); // 댓글 단건 삭제

    void modifyComment(Long id, CommentRequestDto commentRequestDto,Member member); // 댓글 수정하기

    void deleteCommentsByBoard(Long id); // 댓글 지우기 - in query - 게시물 지우기 - 해당 게시물에 달린 댓글 다 지움

    void deleteCommentsByBoardV2(Board board); // 댓글 지우기 - entity -

    Long countCommentByBoardId(Long id); // comment 갯수 가지고 오기

    List<CommentResponseDto> comments(Long id); // 댓글 여러개 찾아오기

    Comment getComment(Long id);

}
