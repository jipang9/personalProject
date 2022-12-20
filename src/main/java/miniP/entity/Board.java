package miniP.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import miniP.dto.board.BoardRequestDto;
import miniP.dto.board.BoardResponseDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @NotNull
    private String title;
    @NotNull
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    public void updateBoard(BoardRequestDto boardRequestDto){
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
    }


    public  BoardResponseDto toDto(Board board){
        BoardResponseDto boardResponseDto = BoardResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .username(board.getMember().getUsername())
                .createTime(board.getCreateDate())
                .modDateTime(board.getModDate())
                .build();
        return boardResponseDto;
    }

}
