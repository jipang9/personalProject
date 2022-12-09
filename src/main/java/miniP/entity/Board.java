package miniP.entity;

import lombok.*;
import miniP.dto.BoardRequestDto;

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

    @Column(name="user_name")
    private String name;

    @Column(name="user_password")
    private String password;

    public void updateBoard(BoardRequestDto boardRequestDto){
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.name = boardRequestDto.getName();
    }

    public boolean checkByPassword(String password){
        return this.password.equals(password);
    }

}
