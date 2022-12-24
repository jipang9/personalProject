package miniP.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniP.dto.board.BoardRequestDto;
import miniP.dto.board.BoardResponseDto;
import miniP.entity.Board;
import miniP.entity.Member;
import miniP.exception.board.NotFoundBoardException;
import miniP.jwt.SecurityUtil;
import miniP.repository.BoardRepository;
import miniP.repository.MemberRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional    // 게시글 저장
    public BoardResponseDto save(BoardRequestDto boardRequestDto) {
        String getUserName = SecurityUtil.getCurrentMemberEmail();
        Member member = memberRepository.findByUsername(getUserName).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다"));

        Board board = boardRequestDto.toEntity(member);
        boardRepository.save(board);
        return BoardResponseDto.of(board);
    }

    @Transactional(readOnly = true) // 게시물 조회
    public BoardResponseDto getOne(Long id) {
        Board getBoard = boardRepository.findById(id).orElseThrow(() -> new RuntimeException(" 게시물을 찾을 수 없습니다 "));
        return BoardResponseDto.of(getBoard);
    }

    @Transactional // 게시물 삭제
    public void deleteOne(Long id) {
        String getUserName = SecurityUtil.getCurrentMemberEmail();
        Board board = boardRepository.findById(id).orElseThrow(() -> new NotFoundBoardException(" 게시물을 찾을 수 없습니다 "));
        board.isWrite(board, getUserName);
        boardRepository.deleteById(id);
    }

    @Transactional(readOnly = true) // 전체 조회
    public List<BoardResponseDto> findAll() {
        List<Board> boardList = boardRepository.findAllByDateDesc();
        List<BoardResponseDto> getBoard = new ArrayList<>();
        boardList.forEach(entity -> {
            BoardResponseDto boardResponseDto = BoardResponseDto.of(entity);
            getBoard.add(boardResponseDto);
        });
        return getBoard;
    }

    @Transactional //    게시물 수정
    public BoardResponseDto updateBoard(Long id, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException(" 존재하지 않는 게시물 "));
        String getUserName = SecurityUtil.getCurrentMemberEmail();
        board.isWrite(board, getUserName);
        board.updateBoard(boardRequestDto);
        boardRepository.save(board);
        return BoardResponseDto.of(board);
    }
}
