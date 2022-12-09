package miniP.service.result;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class Result implements Serializable {

    private boolean success;
    private int code;
    private String msg;
}
