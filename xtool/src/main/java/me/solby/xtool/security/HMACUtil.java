package me.solby.xtool.security;

import com.google.common.base.Charsets;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * me.solby.xtool.security
 *
 * @author majhdk
 * @date 2019/11/14
 */
public class HMACUtil {

    public static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    public static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    public static final String HMAC_MD5_ALGORITHM = "HmacMD5";

    private HMACUtil() {
    }

    /**
     * 根据Key和Data生成签名
     *
     * @param key       签名密钥
     * @param plain     待签名数据
     * @param algorithm 算法
     * @return
     */
    public static String hmacBase64(String key, String plain, String algorithm) {
        return hmacBase64(key.getBytes(Charsets.UTF_8), plain.getBytes(Charsets.UTF_8), algorithm);
    }

    /**
     * 根据Key和Data生成签名
     *
     * @param key       签名密钥
     * @param plain     待签名数据
     * @param algorithm 算法
     * @return
     */
    public static String hmac(String key, String plain, String algorithm) {
        return Hex.encodeHexString(hmac(key.getBytes(Charsets.UTF_8), plain.getBytes(Charsets.UTF_8), algorithm));
    }

    /**
     * 根据Key和Data生成签名
     *
     * @param key   签名密钥
     * @param plain 待签名数据
     * @return
     */
    public static String hmacBase64(byte[] key, byte[] plain, String algorithm) {
        return new String(Base64.getEncoder().encode(hmac(key, plain, algorithm)), Charsets.UTF_8);
    }

    /**
     * 根据Key和Data生成签名
     *
     * @param key   签名密钥
     * @param plain 待签名数据
     * @return
     */
    public static byte[] hmac(byte[] key, byte[] plain, String algorithm) {
        try {
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key, algorithm);
            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance(algorithm);
            mac.init(signingKey);
            // compute the hmac on input data bytes
            return mac.doFinal(plain);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 签名
     */
    public enum Signature {

        /**
         * HMAC_MD5 签名
         */
        HMAC_MD5() {
            @Override
            public String signatureBase64(String key, String plain) {
                return HMACUtil.hmacBase64(key, plain, HMAC_MD5_ALGORITHM);
            }

            @Override
            public String signature(String key, String plain) {
                return HMACUtil.hmac(key, plain, HMAC_MD5_ALGORITHM);
            }
        },

        /**
         * HMAC_SHA1 签名
         */
        HMAC_SHA1() {
            @Override
            public String signatureBase64(String key, String plain) {
                return HMACUtil.hmacBase64(key, plain, HMAC_SHA1_ALGORITHM);
            }

            @Override
            public String signature(String key, String plain) {
                return HMACUtil.hmac(key, plain, HMAC_SHA1_ALGORITHM);
            }
        },

        /**
         * HMAC_SHA256 签名
         */
        HMAC_SHA256() {
            @Override
            public String signatureBase64(String key, String plain) {
                return HMACUtil.hmacBase64(key, plain, HMAC_SHA256_ALGORITHM);
            }

            @Override
            public String signature(String key, String plain) {
                return HMACUtil.hmac(key, plain, HMAC_SHA256_ALGORITHM);
            }
        },

        ;

        Signature() {
        }

        public abstract String signatureBase64(String key, String plain);

        public abstract String signature(String key, String plain);
    }
}
