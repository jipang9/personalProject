package miniP.service;

import lombok.RequiredArgsConstructor;
import miniP.dto.comment.CommentRequestDto;
import miniP.dto.comment.CommentResponseDto;
import miniP.entity.Board;
import miniP.entity.Comment;
import miniP.entity.Member;
import miniP.exception.NotExistMemberException;
import miniP.exception.board.NotFoundBoardException;
import miniP.exception.comment.CommentDeleteException;
import miniP.exception.comment.CommentUpdateException;
import miniP.exception.comment.FailPostComment;
import miniP.exception.comment.NotFoundCommentException;
import miniP.jwt.JwtUtil;
import miniP.repository.BoardRepository;
import miniP.repository.CommentRepository;
import miniP.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public CommentResponseDto postComment(Long BoardId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        try {
            String user = jwtUtil.validateToken(request);
            Member member = memberRepository.findByUsername(user).orElseThrow(() -> new NotExistMemberException());
            Board board = boardRepository.findById(BoardId).orElseThrow(() -> new NotFoundBoardException());
            Comment comment = commentRequestDto.toEntity(board, member);
            commentRepository.save(comment);
            return CommentResponseDto.of(comment);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new FailPostComment();
        }
    }

    @Transactional
    public void deleteOne(Long id, HttpServletRequest request) {
        try {
            Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundCommentException());
            String user = jwtUtil.validateToken(request);
            comment.isWrite(comment, user);
            commentRepository.delete(comment);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CommentDeleteException();
        }
    }

    @Transactional
    public CommentResponseDto modifyComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest httpServletRequest) {
        try {
            Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundCommentException());
            String user = jwtUtil.validateToken(httpServletRequest);
            comment.isWrite(comment, user);
            comment.updateComment(commentRequestDto);
            commentRepository.save(comment);
            return CommentResponseDto.of(comment);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CommentUpdateException();
        }
    }
}
