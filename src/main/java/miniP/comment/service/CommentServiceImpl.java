package miniP.comment.service;

import lombok.RequiredArgsConstructor;
import miniP.comment.dto.CommentRequestDto;
import miniP.comment.dto.CommentResponseDto;
import miniP.board.entity.Board;
import miniP.comment.entity.Comment;
import miniP.member.entity.Member;
import miniP.exception.member.NotExistMemberException;
import miniP.exception.board.NotFoundBoardException;
import miniP.exception.comment.NotFoundCommentException;
import miniP.security.SecurityUtil;
import miniP.board.repository.BoardRepository;
import miniP.comment.repository.CommentRepository;
import miniP.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


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

}
