package me.solby.itool.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * JDK MessageDigest 实现
 *
 * @author majhdk
 * @date 2019-05-25
 */
public class SHAUtil {

    private SHAUtil() {
    }

    /**
     * 字符串做SHA256摘要,输出Base64
     *
     * @param data
     * @return
     */
    public static String sha256HexBase64(final String data) {
        byte[] bytes = sha256Hex(data.getBytes(StandardCharsets.UTF_8));
        return new String(Base64Codec.encode(bytes), StandardCharsets.UTF_8);
    }

    /**
     * 字符串做SHA256摘要
     *
     * @param data
     * @return
     */
    public static String sha256Hex(final String data) {
        byte[] bytes = sha256Hex(data.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(bytes);
    }

    /**
     * 字节数组做SHA256摘要
     *
     * @param bytes
     * @return
     */
    public static byte[] sha256Hex(final byte[] bytes) {
        return shaAlgorithmHex(bytes, "SHA-256");
    }

    /**
     * 自定义算法摘要
     *
     * @param data
     * @param algorithm
     * @return
     */
    public static String shaAlgorithmHex(final String data, final String algorithm) {
        byte[] bytes = shaAlgorithmHex(data.getBytes(StandardCharsets.UTF_8), algorithm);
        return Hex.encodeHexString(bytes);
    }

    /**
     * 自定义算法摘要,返回Base64
     *
     * @param data
     * @param algorithm
     * @return
     */
    public static String shaAlgorithmHexBase64(final String data, final String algorithm) {
        byte[] bytes = shaAlgorithmHex(data.getBytes(StandardCharsets.UTF_8), algorithm);
        return new String(Base64Codec.encode(bytes), StandardCharsets.UTF_8);
    }

    /**
     * 自定义算法摘要
     *
     * @param bytes
     * @param algorithm
     * @return
     */
    public static byte[] shaAlgorithmHex(final byte[] bytes, final String algorithm) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            return digest.digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException(algorithm + " hash failure", e);
        }
    }

}
