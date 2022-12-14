package miniP.board.entity;

import lombok.*;
import miniP.comment.entity.Comment;
import miniP.exception.ExceptionStatus;
import miniP.exception.member.CustomException;
import miniP.exception.member.IsNotWriterException;
import miniP.member.entity.Member;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @NotNull
    private String title;
    @NotNull
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public void updateBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }


    public void isWrite(String username) throws RuntimeException {
        if (this.getMember().getUsername().equals(username)) {
            }
        else
            throw new CustomException(ExceptionStatus.NOT_AUTHOR);
    }



}
