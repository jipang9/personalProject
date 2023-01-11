package miniP.recommend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import miniP.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Recommend {

    // 과연 엔티티를 2개로 나눌 것인가. 혹은 이 하나로 통일할 것인가?
    // 좋아요라는 기능 속에서 2개의 카테고리로 나뉜다... 카테고리화?
    // board/ comment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Member member;

    private
}
