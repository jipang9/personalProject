package miniP.entity;

import lombok.*;
import miniP.dto.comment.CommentRequestDto;
import miniP.exception.member.IsNotWriterException;

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

    public void updateComment(CommentRequestDto commentRequestDto) {
        this.comment=commentRequestDto.getComment();
    }
}
