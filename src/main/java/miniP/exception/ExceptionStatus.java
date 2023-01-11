package miniP.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionStatus {

    // 400 ->  BAD _ REQUEST : 잘못된 요청 (ex. 파라미터 값을 확인해주세요 )
    ID_OR_PASSWORD_DO_NOT_MATCH(400, " 아이디 또는 비밀번호가 일치하지 않습니다. "),
    TOKEN_EXPIRED(401," Token is Expire "),
    TOKEN_INVALID(401," Token is not Invalid  "),
    NOT_AUTHOR(401, " 작성자가 아닙니다. "),

    // 404 ->  NOT _ FOUND : 잘못된 리소스 접근 (ex. 존재하지 않는 값)

    USER_IS_NOT_EXIST(404, " 존재하지 않는 회원입니다. "),
    BOARD_IS_NOT_EXIST(404, " 해당 게시물이 존재하지 않습니다. "),

    // 409 ->  CONFLICT : 중복 데이터 (ex. 이미 중복된 값)
    USER_EMAIL_IS_EXIST(409, " 이미 사용중인 아이디입니다. 다시 확인해주세요 "),

    // 500 -> INTERNAL SERVER ERROR : 서버에러
    ERROR_BY_TOKEN(500," 토큰 문제가 발생했습니다. 관리자에게 문의하세요" );

    private final int statusCode;
    private final String message;

}


