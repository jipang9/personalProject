package miniP.recommend.entity;

import lombok.*;
import miniP.board.entity.Board;
import miniP.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="BoardRecommend", uniqueConstraints = {@UniqueConstraint(columnNames = {"member_id","board_id"})})
public class BoardRecommend {

    // 과연 엔티티를 2개로 나눌 것인가. 혹은 이 하나로 통일할 것인가?
    // 좋아요라는 기능 속에서 2개의 카테고리로 나뉜다... 카테고리화?
    // 만약 이 테이블에서 member에 unique 제약조건을 걸어버린다면 ? ->
    // 동시성 문제를 어떻게 해결할 것인가?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member  member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

    @Builder
    public BoardRecommend(Member member, Board board) {
        this.board = board;
        this.member = member;
    }

}
