package miniP.recommend.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import miniP.board.entity.Board;
import miniP.comment.entity.Comment;
import miniP.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="CommentRecommend", uniqueConstraints = {@UniqueConstraint(columnNames = {"member_id","comment_id"})})
public class CommentRecommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="comment_id")
    private Comment comment;


    @Builder
    public CommentRecommend(Member member, Comment comment) {
        this.member = member;
        this.comment = comment;
    }

}
