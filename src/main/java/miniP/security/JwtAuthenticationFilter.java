package miniP.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miniP.exception.ErrorDto;
import miniP.exception.ExceptionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpStatus = (HttpServletResponse) response;
        try {
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
            if (token != null&& jwtTokenProvider.validateTokenExpiration(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            chain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {
            setErrorResponse(httpStatus, ExceptionStatus.TOKEN_EXPIRED); // 토큰 만료 시 발생
        }catch (JwtException|IllegalStateException e) {
            setErrorResponse(httpStatus, ExceptionStatus.TOKEN_INVALID); // 헤더에 있는 토큰 값이 잘못되었을 때. ex) 토큰 값에서 하나가 빠짐
        }
    }

    private void setErrorResponse(HttpServletResponse response,  ExceptionStatus exceptionStatus) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(exceptionStatus.getStatusCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorDto errorResponse = new ErrorDto(exceptionStatus.getStatusCode(), exceptionStatus.getMessage());
        try{
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}