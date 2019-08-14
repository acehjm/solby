package me.solby.xoauth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import me.solby.xtool.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * jwt 工具类
 * - jjwt实现
 *
 * @author majhdk
 * @date 2019-05-26
 */
public class JwtTokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    /**
     * 加密Key
     */
    private static String SECRET_CONTEXT = "XOAUTH.ME.SOLBY";

    /**
     * 过期时间
     */
    private static long EXPIRED = 432_000_000L;

    /**
     * 生成Token（带超时时间）
     *
     * @param subject
     * @return
     */
    public static String token(String subject) {
        return Jwts.builder()
                .setClaims(null)
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRED))
                .signWith(SignatureAlgorithm.HS256, SECRET_CONTEXT)
                .compact();
    }

    /**
     * 解析Token
     *
     * @param token
     * @return
     */
    public static String parseToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_CONTEXT)
                .parseClaimsJws(token)
                .getBody();
        return claims == null ? null : claims.getSubject();
    }

    /**
     * 解析Token到对象中
     *
     * @param token
     * @return
     */
    public static <T> T parseToken(String token, Class<T> tClass) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_CONTEXT)
                .parseClaimsJws(token).getBody();
        return JsonUtil.fromJson(claims.getSubject(), tClass);
    }

    /**
     * 校验token是否有效
     *
     * @param token
     * @return
     */
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_CONTEXT).parseClaimsJws(token);   //通过密钥验证Token
            return true;
        } catch (SignatureException e) {                                     //签名异常
            logger.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {                                 //JWT格式错误
            logger.info("Invalid JWT token.");
        } catch (ExpiredJwtException e) {                                   //JWT过期
            logger.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {                               //不支持该JWT
            logger.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {                              //参数错误异常
            logger.info("JWT token compact of handler are invalid.");
        }
        return false;
    }

}
