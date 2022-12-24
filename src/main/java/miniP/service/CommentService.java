package miniP.service;

import lombok.RequiredArgsConstructor;
import miniP.dto.comment.CommentRequestDto;
import miniP.dto.comment.CommentResponseDto;
import miniP.entity.Board;
import miniP.entity.Comment;
import miniP.entity.Member;
import miniP.exception.NotExistMemberException;
import miniP.exception.board.NotFoundBoardException;
import miniP.exception.comment.NotFoundCommentException;
import miniP.jwt.SecurityUtil;
import miniP.repository.BoardRepository;
import miniP.repository.CommentRepository;
import miniP.repository.MemberRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public CommentResponseDto postComment(Long BoardId, CommentRequestDto commentRequestDto) {
        String user = SecurityUtil.getCurrentMemberEmail();
        Member member = memberRepository.findByUsername(user).orElseThrow(() -> new NotExistMemberException());
        Board board = boardRepository.findById(BoardId).orElseThrow(() -> new NotFoundBoardException());
        Comment comment = commentRequestDto.toEntity(board, member);
        commentRepository.save(comment);
        return CommentResponseDto.of(comment);

    }

    @Transactional
    public void deleteOne(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundCommentException());
        String user = SecurityUtil.getCurrentMemberEmail();
        comment.isWrite(comment, user);
        commentRepository.delete(comment);
    }

    @Transactional
    public CommentResponseDto modifyComment(Long id, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundCommentException());
        String user = SecurityUtil.getCurrentMemberEmail();
        comment.isWrite(comment, user);
        comment.updateComment(commentRequestDto);
        commentRepository.save(comment);
        return CommentResponseDto.of(comment);
    }
}
