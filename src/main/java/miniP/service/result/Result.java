package miniP.service.result;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
//@SuperBuilder(builderMethodName = "baseBuilder")
@Setter
public class Result implements Serializable {

    private int statusCode;  // 코드 >>
    private String message; // 메시지 >> 어떤 데이터를 던져주는게 좋을까?

}
