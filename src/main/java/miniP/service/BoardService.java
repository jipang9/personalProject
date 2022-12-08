package miniP.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniP.dto.BoardRequestDto;
import miniP.dto.BoardResponseDto;
import miniP.entity.Board;
import miniP.exception.BoardDeleteException;
import miniP.exception.BoardUpdateException;
import miniP.exception.NotExistMemberException;
import miniP.exception.PostException;
import miniP.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardResponseDto save(BoardRequestDto boardRequestDto) throws Exception {
        try {
            Board board = dtoToEntity(boardRequestDto);
            boardRepository.save(board);
            BoardResponseDto boardResponseDto = entityToDto(board);
            return boardResponseDto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PostException();
        }
    }

    public BoardResponseDto getOne(Long id) {
        try {
            Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));
            BoardResponseDto boardResponseDto = entityToDto(board);
            return boardResponseDto;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new NotExistMemberException();
        }
    }

    private Board dtoToEntity(BoardRequestDto boardRequestDto) {

        Board board = Board.builder()
                .name(boardRequestDto.getName())
                .password(boardRequestDto.getPassword())
                .content(boardRequestDto.getContent())
                .title(boardRequestDto.getTitle())
                .build();
        return board;
    }

    private BoardResponseDto entityToDto(Board board) {

        BoardResponseDto boardResponseDto = BoardResponseDto.builder()
                .content(board.getContent())
                .title(board.getTitle())
                .name(board.getName())
                .createDate(board.getCreateDate())
                .modDateTime(board.getModDate())
                .build();
        return boardResponseDto;
    }

    @Transactional
    public void deleteOne(Long id) {
        try {
            boardRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 게시물입니다."));
            boardRepository.deleteById(id);
        } catch (Exception e) {
            throw new BoardDeleteException();
        }
    }
// 체크 (1. )
    public boolean checkByPassword(Long id, String password) {
        String passwordCheck = boardRepository.findById(id).get().getPassword();
        if (passwordCheck.equals(password)) {
            return true;
        } else {
            return false;
        }
    }

//    @Transactional
//    public BoardRequestDto modify(Long id, String password, BoardRequestDto boardRequestDto) {
//        try {
//            Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("ID가 존재하지 않습니다."));
//            if (checkByPassword(id, password) == true) {
//                board.changeTitleAndContentAndName(boardRequestDto.getTitle(), board.getContent(), boardRequestDto.getName());
//                return boardRequestDto;
//            } else {
//                throw new BoardDeleteException("비밀번호 불일치");
//            }
//        } catch (Exception e) {
//            throw new BoardUpdateException();
//        }
//    }


    @Transactional(readOnly = true)
    public List<BoardResponseDto> findAll() {
//        List<Board> boardList = boardRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        List<Board> boardList = boardRepository.findAllByDateDesc();
        List<BoardResponseDto> boardResponseDtos = new ArrayList<>();
        boardList.forEach(entity -> {
            BoardResponseDto boardResponseDto = entityToDto(entity);
            boardResponseDtos.add(boardResponseDto);
        });
        return boardResponseDtos;
    }


    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto boardRequestDto) {
        try {
            Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("옼 ㅋ"));
            board.updateBoard(boardRequestDto);
            BoardResponseDto boardResponseDto = entityToDto(board);
//            boardRepository.save(board);///*****************///
//            throw new BoardUpdateException();
            return boardResponseDto;
        } catch (Exception e) {
            throw new BoardUpdateException();
        }
    }
}
