package miniP.comment.entity;

import lombok.*;
import miniP.board.entity.Board;
import miniP.exception.member.IsNotWriterException;
import miniP.member.entity.Member;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void isWrite(Comment comment, String user) {
        if(comment.getMember().getUsername().equals(user))
            return;
        else
            throw new IsNotWriterException();
    }

    public void updateComment(String comment) {
        this.comment=comment;
    }

}
