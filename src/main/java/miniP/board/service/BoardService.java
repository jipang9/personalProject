package miniP.board.service;

import miniP.board.dto.BoardRequestDto;
import miniP.board.dto.BoardResponseDto;
import miniP.board.dto.BoardsResponseDto;
import miniP.member.entity.Member;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {

    void boardSave(BoardRequestDto boardRequestDto, String username); // 게시글 저장 -> 반환값이 필요한가?

    BoardResponseDto getOne(Long id); // 게시글 단건 조회

    void deleteOne(Long id, Member member); // 게시글 단건 삭제 -> 벌크연산

    void deleteOneV2(Long id, Member member);

    List<BoardsResponseDto> ListAll(); // 게시글 전체 조회

    List<BoardsResponseDto> ListPaging(Pageable pageable,int page);

    void updateBoard(Long id, BoardRequestDto boardRequestDto, Member member); // 게시글 수정

//    List<BoardResponseDto> myBoardList(Member member); // 내가 작성한 게시글 리스트 -> my service 이런 쪽으로 빼는 것이 맞을듯
}
