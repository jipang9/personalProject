package miniP.comment.service;

import lombok.RequiredArgsConstructor;
import miniP.board.entity.Board;
import miniP.board.repository.BoardRepository;
import miniP.board.service.BoardService;
import miniP.comment.dto.CommentRequestDto;
import miniP.comment.dto.CommentResponseDto;
import miniP.comment.entity.Comment;
import miniP.comment.repository.CommentRepository;
import miniP.exception.ExceptionStatus;
import miniP.exception.board.NotFoundBoardException;
import miniP.exception.comment.NotFoundCommentException;
import miniP.exception.member.CustomException;
import miniP.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;


    @Transactional
    @Override
    public void postComment(Long BoardId, CommentRequestDto commentRequestDto, Member member) {
        Board board = boardRepository.findById(BoardId).orElseThrow(() -> new NotFoundBoardException());
        Comment comment = commentRequestDto.toEntity(board, member);
        commentRepository.save(comment);
    }

    // service에서 A entity를 받아왔을 때,  혹은 repository에서 가지고 오는

    @Transactional
    @Override
    public void deleteOne(Long id, Member member) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundCommentException());
        comment.isWrite(comment, member.getUsername());
        commentRepository.delete(comment);
    }

    @Transactional
    @Override
    public void modifyComment(Long id, CommentRequestDto commentRequestDto, Member member) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundCommentException());
        comment.isWrite(comment, member.getUsername());
        comment.updateComment(commentRequestDto.getComment());
        commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void deleteCommentsByBoard(Long id) {
        List<Long> boardIds = commentRepository.findAllByBoardId(id);
        commentRepository.deleteAllByBoardIds(boardIds);
    }

    @Override
    public void deleteCommentsByBoardV2(Board board) { // 관호님 버전
        commentRepository.deleteAllByBoard(board);
    }

    @Override
    public Long countCommentByBoardId(Long id) {
        return commentRepository.countCommentByBoardId(id);
    }

    @Override
    public List<CommentResponseDto> comments(Long id) {
        return commentRepository.findCommentByBoardId(id);
    }

    @Override
    public Comment getComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionStatus.COMMENT_IS_NOT_EXIST));
        return comment;
    }

}
