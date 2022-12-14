package miniP.entity;

import lombok.*;
import miniP.dto.board.BoardRequestDto;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long id;


    // 글 제목
    private String title;
    // 내용
    private String content;


    // 작성자 -> pk값 만 가지고 올 것임
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    public void updateBoard(BoardRequestDto boardRequestDto){
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
    }

    public boolean checkByPassword(String password){
        return this.member.getPassword().equals(password);
    }

}
