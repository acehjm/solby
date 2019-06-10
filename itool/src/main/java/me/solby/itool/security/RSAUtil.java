package me.solby.itool.security;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA 工具类 //todo 添加分段式加密;
 * <p>
 * RSA: 既能用于数据加密也能用于数字签名的算法
 * RSA算法原理如下：
 * 1.随机选择两个大质数p和q，p不等于q，计算N=pq
 * 2.选择一个大于1小于N的自然数e，e必须与(p-1)(q-1)互素
 * 3.用公式计算出d：d×e = 1 (mod (p-1)(q-1))
 * 4.销毁p和q
 * 5.最终得到的N和e就是“公钥”，d就是“私钥”，发送方使用N去加密数据，接收方只有使用d才能解开数据内容
 * 基于大数计算，比DES要慢上几倍，通常只能用于加密少量数据或者加密密钥
 * 私钥加解密都很耗时，服务器要求解密效率高，客户端私钥加密，服务器公钥解密比较好一点
 *
 * @author majhdk
 * @date 2019-05-25
 */
public class RSAUtil {

    /**
     * 加密算法--Java中使用RSA
     */
    private static final String RSA_ALGORITHM = "RSA";
    /**
     * 加密填充方式
     */
    private static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";
    /**
     * 默认密钥长度
     */
    private static final int DEFAULT_KET_SIZE = 1024;
    /**
     * 密钥对
     */
    private static KeyPair keyPair;

    static {
        init();
    }

    private RSAUtil() {
    }

    /**
     * 初始化--随机生成密钥对
     */
    private static void init() {
        keyPair = getKeyPair();
    }

    /**
     * 获取私钥字符串
     *
     * @return
     */
    public static String getPrivateKeyContext() {
        return new String(Base64.getEncoder().encode(getPrivateKey().getEncoded()), StandardCharsets.UTF_8);
    }

    /**
     * 获取公钥字符串
     *
     * @return
     */
    public static String getPublicKeyContext() {
        return new String(Base64.getEncoder().encode(getPublicKey().getEncoded()), StandardCharsets.UTF_8);
    }

    /**
     * 获取私钥
     *
     * @return
     */
    public static RSAPrivateKey getPrivateKey() {
        return (RSAPrivateKey) keyPair.getPrivate();
    }

    /**
     * 获取公钥
     *
     * @return
     */
    public static RSAPublicKey getPublicKey() {
        return (RSAPublicKey) keyPair.getPublic();
    }

    /**
     * 公钥加密
     *
     * @param data      待加密数据
     * @param publicKey 公钥(Base64)
     * @return Base64字符串
     */
    public static String encryptByPublicKey(final String data, final String publicKey) {
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] keyBytes = Base64.getDecoder().decode(publicKey);

        try {
            byte[] bytes = encryptByPublicKey(dataBytes, keyBytes);
            return new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new SecurityException("公钥加密失败", e);
        }
    }

    /**
     * 私钥加密
     *
     * @param data       待加密数据
     * @param privateKey 私钥(Base64)
     * @return Base64字符串
     */
    public static String encryptByPrivateKey(final String data, final String privateKey) {
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);

        try {
            byte[] bytes = encryptByPrivateKey(dataBytes, keyBytes);
            return new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new SecurityException("私钥加密失败", e);
        }
    }

    /**
     * 公钥解密
     *
     * @param base64Data 待解密数据(Base64)
     * @param publicKey  公钥(Base64)
     * @return 字符串
     */
    public static String decryptByPublicKey(final String base64Data, final String publicKey) {
        byte[] dataBytes = Base64.getDecoder().decode(base64Data);
        byte[] keyBytes = Base64.getDecoder().decode(publicKey.getBytes(StandardCharsets.UTF_8));

        try {
            byte[] bytes = decryptByPublicKey(dataBytes, keyBytes);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new SecurityException("公钥解密失败", e);
        }
    }

    /**
     * 私钥解密
     *
     * @param base64Data 待解密数据(Base64)
     * @param privateKey 私钥(Base64)
     * @return 字符串
     */
    public static String decryptByPrivateKey(final String base64Data, final String privateKey) {
        byte[] dataBytes = Base64.getDecoder().decode(base64Data);
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);

        try {
            byte[] bytes = decryptByPrivateKey(dataBytes, keyBytes);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
//            logger.error("私钥解密失败");
            throw new SecurityException("私钥解密失败", e);
        }
    }

    /*---------------------------------------------------------------------------------------*/

    /**
     * 公钥加密
     *
     * @param data      待加密数据
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    private static byte[] encryptByPublicKey(final byte[] data, final byte[] publicKey) throws Exception {
        // 得到公钥
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PublicKey keyPublic = keyFactory.generatePublic(keySpec);
        // 加密数据
        Cipher cp = Cipher.getInstance(RSA_ALGORITHM);
        cp.init(Cipher.ENCRYPT_MODE, keyPublic);
        return cp.doFinal(data);
    }

    /**
     * 私钥加密
     *
     * @param data       待加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    private static byte[] encryptByPrivateKey(final byte[] data, final byte[] privateKey) throws Exception {
        // 得到私钥
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PrivateKey keyPrivate = keyFactory.generatePrivate(keySpec);
        // 数据加密
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keyPrivate);
        return cipher.doFinal(data);
    }

    /**
     * 公钥解密
     *
     * @param data      待解密数据
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    private static byte[] decryptByPublicKey(final byte[] data, final byte[] publicKey) throws Exception {
        // 得到公钥
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PublicKey keyPublic = keyFactory.generatePublic(keySpec);
        // 数据解密
        Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, keyPublic);
        return cipher.doFinal(data);
    }

    /**
     * 私钥解密
     *
     * @param data       待解密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    private static byte[] decryptByPrivateKey(final byte[] data, final byte[] privateKey) throws Exception {
        // 得到私钥
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PrivateKey keyPrivate = keyFactory.generatePrivate(keySpec);
        // 解密数据
        Cipher cp = Cipher.getInstance(ECB_PKCS1_PADDING);
        cp.init(Cipher.DECRYPT_MODE, keyPrivate);
        return cp.doFinal(data);
    }

    /**
     * 随机生成密钥对
     *
     * @return
     */
    private static KeyPair getKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
            //初始化密钥长度
            generator.initialize(DEFAULT_KET_SIZE);
            return generator.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException("生成RSA密钥对失败", e);
        }
    }

}
