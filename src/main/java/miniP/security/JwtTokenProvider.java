package miniP.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import miniP.exception.ExceptionStatus;
import miniP.exception.member.CustomException;
import miniP.security.member.MemberDetailsService;
import org.hibernate.query.criteria.internal.predicate.BooleanExpressionPredicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Setter
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {


    @Value("${spring.jwt.secretKey}")
    private String secretKey;

    private long tokenValidTime = 1000L * 60 * 60*24; // 토큰 만료시간 7일
    private long refreshTokenValidTime = 1000L * 60 * 60 * 24 * 7; // refresh token 기한 7일

    private Key key;
    private final UserDetailsService userDetailsService;
    private final MemberDetailsService memberDetailsService;
    private static final String BEARER_PREFIX = "Bearer ";

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 생성
    public String createToken(String email) {
        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();

        return BEARER_PREFIX + Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now) // 발급 시간
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }

    // refreshtoken 생성
    public String createRefreshToken() {
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰으로 인증객체(Authentication) 얻기
    public Authentication getAuthentication(String token) {
        log.info("===================================== 토큰 인증객체 얻기 시작");
        UserDetails userDetails = memberDetailsService.loadUserByUsername(getMemberEmail(token));
        if(userDetails.getUsername()==null)
            log.info(" 티비도 보지 마 !");
        else
            log.info("코끼리");// 여기엔 있음 사용자 데이터가
        log.info("=====================================토큰 인증객체 얻기 끝");
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 이메일을 얻기 위해 실제로 토큰을 디코딩-> 지정된 secretekey를 이용해 인증 객체를 끌고올 수 있음.
    public String getMemberEmail(String token) {
        try {
            log.info("===================================== 실제 토큰 디코딩 시작");  // 여기도 문제없이 통과함
            String subject = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
            log.info(subject);
            return subject;
        } catch (ExpiredJwtException e) {
            log.info("===================================== 실제 토큰 디코딩 오류 발생");
            return e.getClaims().getSubject();
        }
    }

    // 이걸 가지고 헤더에서 토큰을 꺼내옴
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰의 유효성 검사
    public Boolean validateTokenExpiration(String token) {
        try{
            log.info("===================================== 토큰 유효성 검사");
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            log.info("===================================== 토큰 유효성 검사 끝");
            return true;
    }catch (Exception e){
            e.printStackTrace();
            log.info("===================================== 토큰 유효성 오류 발생");
            return false;
        }
    }


}