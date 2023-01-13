//package miniP.recommend.entity;
//
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import miniP.board.entity.Board;
//import miniP.member.entity.Member;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class BookRecommend {
//
//    // 과연 엔티티를 2개로 나눌 것인가. 혹은 이 하나로 통일할 것인가?
//    // 좋아요라는 기능 속에서 2개의 카테고리로 나뉜다... 카테고리화?
//    // 만약 이 테이블에서 member에 unique 제약조건을 걸어버린다면 ? ->
//    // 동시성 문제를 어떻게 해결할 것인가?
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//
//    @Column(name="member_id")
//    private Long  member;
//
//
//    @Column(name="member_id")
//    private Long board;
//
//
//
//
//}
