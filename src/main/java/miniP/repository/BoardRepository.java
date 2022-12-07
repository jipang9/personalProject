package miniP.repository;

import miniP.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepository extends JpaRepository<Board, Long> {

//    @Query("select b.password from Board b where ")
//    Optional<String> checkPassword(Long id);
}
