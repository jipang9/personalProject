package miniP.exception.member;


import lombok.AllArgsConstructor;
import lombok.Getter;
import miniP.exception.ExceptionStatus;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {

    private final ExceptionStatus exceptionStatus;
}
