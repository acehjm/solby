package me.solby.xoauth.jwt;

import me.solby.itool.json.JsonUtil;
import me.solby.itool.security.RSAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

import java.time.Instant;
import java.util.Map;

/**
 * JWT 工具类
 * - spring security 实现
 *
 * @author majhdk
 * @date 2019-05-25
 */
public class JwtTokenHelper {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenHelper.class);

    /**
     * 签名工具
     */
    private static RsaSigner signer;
    /**
     * 校验工具
     */
    private static RsaVerifier verifier;

    static {
        signer = new RsaSigner(RSAUtil.getPrivateKey());
        verifier = new RsaVerifier(RSAUtil.getPublicKey());
    }

    /**
     * 根据字符串信息生成Token信息
     *
     * @param context
     * @return
     */
    public static String token(CharSequence context) {
        return JwtHelper.encode(context, signer).getEncoded();
    }

    /**
     * 获取Token中用户信息
     *
     * @param token
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T getUserInfo(String token, Class<T> tClass) {
//       或 return JsonUtil.toPojo(decode(token).getClaims(), new TypeReference<T>() {
//        });
        return JsonUtil.fromJson(decode(token).getClaims(), tClass);
    }

    /**
     * 校验Token有效性
     *
     * @param token
     * @return
     */
    public static boolean validateToken(String token) {
        try {
            String claims = decode(token).getClaims();
            if (claims == null || claims.isBlank()) {
                logger.warn("Token内容为空");
                return false;
            }
//            Map<String, Object> map = JsonUtil.jsonToMap(claims);
//            Long expired = (Long) map.getOrDefault("expired", null);
//            if (expired == null || Instant.now().toEpochMilli() - expired <= 0) {
//                logger.warn("Token已过期，请重新申请");
//                return false;
//            }
        } catch (Exception e) {
            logger.warn("Token校验失败");
            return false;
        }
        return true;
    }

    /**
     * 解密Token
     *
     * @param token
     * @return
     */
    private static Jwt decode(String token) {
        return JwtHelper.decodeAndVerify(token, verifier);
    }

}
