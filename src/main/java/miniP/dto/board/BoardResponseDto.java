package miniP.dto.board;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardResponseDto {

    private String title;
    private String content;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime modDateTime;

    @Builder
    public BoardResponseDto(String title, String content, String name, LocalDateTime createDate, LocalDateTime modDateTime) {
        this.title = title;
        this.content = content;
        this.name = name;
        this.createTime = createDate;
        this.modDateTime = modDateTime;
    }

}
