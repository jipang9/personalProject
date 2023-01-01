package miniP.entity;

import lombok.*;
import miniP.exception.member.IsNotWriterException;

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

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();


    public void updateBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void isWrite(Board board, String username) throws RuntimeException {
        if (board.getMember().getUsername().equals(username)) {
            }
        else
            throw new IsNotWriterException(); }

}
