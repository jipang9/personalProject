package miniP.board.service;

import miniP.board.dto.BoardRequestDto;
import miniP.board.dto.BoardResponseDto;

import java.util.List;

public interface BoardService {

    BoardResponseDto BoardSave(BoardRequestDto boardRequestDto); // 게시글 저장

    BoardResponseDto getOne(Long id); // 게시글 단건 조회

    void deleteOne(Long id); // 게시글 단건 삭제

    List<BoardResponseDto> ListAll(); // 게시글 전체 조회

    BoardResponseDto updateBoard(Long id, BoardRequestDto boardRequestDto); // 게시글 수정
}
