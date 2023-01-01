package miniP.service.board;

import miniP.dto.board.BoardRequestDto;
import miniP.dto.board.BoardResponseDto;

import java.util.List;

public interface BoardService {

    BoardResponseDto BoardSave(BoardRequestDto boardRequestDto); // 게시글 저장


    BoardResponseDto getOne(Long id); // 게시글 단건 조회

    void deleteOne(Long id); // 게시글 단건 삭제

    List<BoardResponseDto> ListAll(); // 게시글 전체 조회

    BoardResponseDto updateBoard(Long id, BoardRequestDto boardRequestDto); // 게시글 수정
}
