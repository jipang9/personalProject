package miniP.board.repository;

import miniP.board.dto.BoardResponseDto;
import miniP.board.dto.BoardsResponseDto;
import miniP.board.entity.Board;
import miniP.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BoardRepository extends JpaRepository<Board, Long> {


    @Query("select b from Board b order by b.createDate desc")
    List<Board> findAllByDateDesc();

    List<BoardResponseDto> findAllByMember(Member member);

}
