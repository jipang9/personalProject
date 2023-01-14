package miniP.comment.service;

import lombok.RequiredArgsConstructor;
import miniP.board.entity.Board;
import miniP.board.repository.BoardRepository;
import miniP.comment.dto.CommentRequestDto;
import miniP.comment.dto.CommentResponseDto;
import miniP.comment.entity.Comment;
import miniP.comment.repository.CommentRepository;
import miniP.exception.board.NotFoundBoardException;
import miniP.exception.comment.NotFoundCommentException;
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
    public void deleteCommentsByBoardV2(Board board) {
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

}
