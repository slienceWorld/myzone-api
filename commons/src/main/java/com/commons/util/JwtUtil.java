package com.commons.util;

import com.commons.config.YamlPropertySourceFactory;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author yyh
 * @date 2023/4/3 17:19
 * @description
 */
@Slf4j
@Component
//@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class, encoding = "UTF-8")
public class JwtUtil {

    public static void main(String[] args) {
        boolean b = checkToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNjgwNTkxNzg1LCJleHAiOjE2ODA1OTUzODV9.VwdRwqnyolbb71Ii_6rB7Y_oxC3qtz7DotCXw_VIiF8");
        log.info(String.valueOf(b));
    }

//
//    private static final Long EXPIRE_TIME =3600L;
//    private static final String SECRET= UUID.fromString("secret").toString();

    private static Long EXPIRE_TIME;
    private static String SECRET;

    public static String generateToken(String username) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + EXPIRE_TIME * 1000);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public static Claims parseJwt(String jwt) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(jwt)
                .getBody();
    }

    public static JwsHeader getHeader(String jwt) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(jwt).getHeader();

    }

    public static String getUsernameFromToken(String token) {
        checkToken(token);
        return parseJwt(token).getSubject();
    }

    public static boolean checkToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authToken);
        } catch (SignatureException ex) {
            throw new SignatureException("无效JWT签名");
        } catch (MalformedJwtException ex) {
            throw new MalformedJwtException("无效JWT");
        } catch (ExpiredJwtException ex) {
            throw new ExpiredJwtException(null, null, "JWT过期");
        } catch (UnsupportedJwtException ex) {
            throw new UnsupportedJwtException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("JWT claims string is empty.");
        }
        return true;
    }

    @Value("${jwt.expire-time}")
    public void setExpireTime(Long expireTime) {
        EXPIRE_TIME = expireTime;
    }

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        SECRET = secret;
    }

}
