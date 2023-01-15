package miniP.recommend.repository;

import miniP.recommend.entity.BoardRecommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface BoardRecommendRepository extends JpaRepository<BoardRecommend, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select b from BoardRecommend b where b.board.id=:boardId and b.member.id=:memberId")
    Optional<BoardRecommend> findByMemberAndBoard(@Param("boardId") Long boardId, @Param("memberId") Long memberId);

}
