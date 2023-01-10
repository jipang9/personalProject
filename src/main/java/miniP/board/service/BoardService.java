package miniP.board.service;

import miniP.board.dto.BoardRequestDto;
import miniP.board.dto.BoardResponseDto;
import miniP.member.entity.Member;

import java.util.List;

public interface BoardService {

    void boardSave(BoardRequestDto boardRequestDto, String username); // 게시글 저장 -> 반환값이 필요한가?

    BoardResponseDto getOne(Long id); // 게시글 단건 조회

    void deleteOne(Long id); // 게시글 단건 삭제

//    List<BoardResponseDto> ListAll(); // 게시글 전체 조회

//    BoardResponseDto updateBoard(Long id, BoardRequestDto boardRequestDto); // 게시글 수정

    List<BoardResponseDto> myBoardList(Member member); // 내가 작성한 게시글 리스트
}
