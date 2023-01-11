package miniP.recommend.repository;

import miniP.recommend.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {
}
