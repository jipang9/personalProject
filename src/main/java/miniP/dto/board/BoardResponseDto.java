package miniP.dto.board;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto {

    private String title;
    private String content;
    private String username;
    private LocalDateTime createTime;
    private LocalDateTime modDateTime;

    @Builder
    public BoardResponseDto(String title, String content, String name, LocalDateTime createDate, LocalDateTime modDateTime) {
        this.title = title;
        this.content = content;
        this.username = name;
        this.createTime = createDate;
        this.modDateTime = modDateTime;
    }


}
