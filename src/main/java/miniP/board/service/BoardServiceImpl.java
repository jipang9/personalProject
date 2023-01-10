package miniP.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniP.board.dto.BoardRequestDto;
import miniP.board.dto.BoardResponseDto;
import miniP.board.entity.Board;
import miniP.comment.dto.CommentResponseDto;
import miniP.comment.entity.Comment;
import miniP.comment.repository.CommentRepository;
import miniP.member.entity.Member;
import miniP.exception.member.NotExistMemberException;
import miniP.exception.board.NotFoundBoardException;
import miniP.security.SecurityUtil;
import miniP.board.repository.BoardRepository;
import miniP.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public void boardSave(BoardRequestDto boardRequestDto, String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new NotExistMemberException());
        Board board = boardRequestDto.toEntity(member);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true) // 게시물 조회
    @Override
    public BoardResponseDto getOne(Long id) {
        Board getBoard = boardRepository.findById(id).orElseThrow(() -> new NotFoundBoardException());
        List<CommentResponseDto> comments = commentRepository.findCommentByBoardId(getBoard.getId());
        return BoardResponseDto.of(getBoard, comments);
    }

    @Transactional // 게시물 삭제
    @Override
    public void deleteOne(Long id) {
        String getUserName = SecurityUtil.getCurrentMemberEmail();
        Board board = boardRepository.findById(id).orElseThrow(() -> new NotFoundBoardException());
        board.isWrite(getUserName);
        boardRepository.deleteById(id);
    }

//    @Transactional(readOnly = true) // 전체 조회
//    @Override
//    public List<BoardResponseDto> ListAll() {
//        List<Board> boardList = boardRepository.findAllByDateDesc();
//        List<BoardResponseDto> getBoard = new ArrayList<>();
//        boardList.forEach(entity -> {
//            BoardResponseDto boardResponseDto = BoardResponseDto.of(entity);
//            getBoard.add(boardResponseDto);
//        });
//        return getBoard;
//    }

//    @Transactional //    게시물 수정
//    @Override
//    public BoardResponseDto updateBoard(Long id, BoardRequestDto boardRequestDto) {
//        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException(" 존재하지 않는 게시물 "));
//        String getUserName = SecurityUtil.getCurrentMemberEmail();
//        board.isWrite(getUserName);
//        board.updateBoard(boardRequestDto.getTitle(), boardRequestDto.getContent());
//        boardRepository.save(board);
//        return BoardResponseDto.of(board);
//    }

    @Override
    public List<BoardResponseDto> myBoardList(Member member) {
        List<BoardResponseDto> myboards = boardRepository.findAllByMember(member);
        return myboards;
    }
}
