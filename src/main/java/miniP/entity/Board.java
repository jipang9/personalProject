package miniP.entity;

import lombok.*;
import miniP.dto.board.BoardRequestDto;
import miniP.dto.board.BoardResponseDto;

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

    private String title;
    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    public void updateBoard(BoardRequestDto boardRequestDto){
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
    }

    public BoardResponseDto toDto(Board board){
        BoardResponseDto boardResponseDto = BoardResponseDto.builder()
                .content(board.getContent())
                .title(board.getTitle())
                .name(board.getMember().getUsername())
                .createDate(board.getCreateDate())
                .modDateTime(board.getModDate())
                .build();
        return boardResponseDto;
    }

}
