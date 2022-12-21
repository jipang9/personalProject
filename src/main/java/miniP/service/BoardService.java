package miniP.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniP.dto.board.BoardRequestDto;
import miniP.dto.board.BoardResponseDto;
import miniP.entity.Board;
import miniP.entity.Comment;
import miniP.entity.Member;
import miniP.exception.board.BoardDeleteException;
import miniP.exception.board.BoardSaveException;
import miniP.exception.board.BoardUpdateException;
import miniP.exception.board.NotFoundBoardException;
import miniP.jwt.JwtUtil;
import miniP.repository.BoardRepository;
import miniP.repository.CommentRepository;
import miniP.repository.MemberRepository;
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
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;

    @Transactional    // 게시글 저장
    public BoardResponseDto save(BoardRequestDto boardRequestDto, HttpServletRequest request)  {
        try {
            String username = jwtUtil.validateToken(request);
            Member member = memberRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다"));
            Board board = boardRequestDto.toEntity(member);
            boardRepository.save(board);
            return BoardResponseDto.of(board);
        } catch (RuntimeException e) {
            throw new BoardSaveException();
        }
    }

    @Transactional(readOnly = true) // 게시물 조회
    public BoardResponseDto getOne(Long id){
        try {
            Board getBoard = boardRepository.findById(id).orElseThrow(() -> new RuntimeException(" 게시물을 찾을 수 없습니다 "));
            return BoardResponseDto.of(getBoard);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new NotFoundBoardException();
        }
    }

    @Transactional // 게시물 삭제
    public void deleteOne(Long id, HttpServletRequest request){
        try {
            Board board = boardRepository.findById(id).orElseThrow(()-> new NotFoundBoardException(" 게시물을 찾을 수 없습니다 "));
            String username = jwtUtil.validateToken(request);
            board.isWrite(board, username);
            boardRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BoardDeleteException();

        }
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> findAll(){
        List<Board> boardList = boardRepository.findAllByDateDesc();
        List<BoardResponseDto> getBoard = new ArrayList<>();
        boardList.forEach(entity -> {
            BoardResponseDto boardResponseDto = BoardResponseDto.of(entity);
            getBoard.add(boardResponseDto);
        });
        return getBoard;
    }

    @Transactional //    게시물 수정
            public BoardResponseDto updateBoard(Long id, BoardRequestDto boardRequestDto, HttpServletRequest request){
                try {
                    Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException(" 존재하지 않는 게시물 "));
                    String username = jwtUtil.validateToken(request);
                    board.updateBoard(boardRequestDto);
                    if (board.getMember().getUsername().equals(username)) {
                        boardRepository.saveAndFlush(board); // jpa에선 save()를 권장..
                        BoardResponseDto result = BoardResponseDto.of(board);
                        return result;
                    } else
                        throw new IllegalStateException("작성자가 아닙니다");
                }catch (Exception e) {
                    e.printStackTrace();
                    throw new BoardUpdateException();
        }
}
}
