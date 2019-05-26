package me.solby.itool.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Rsa 工具类测试
 *
 * @author majhdk
 * @date 2019-05-25
 */
class RSAUtilTest {

    private String publicKey;
    private String privateKey;

    @BeforeEach
    void setUp() {
        publicKey = RSAUtil.getPublicKeyContext();
        privateKey = RSAUtil.getPrivateKeyContext();
    }

    @Test
    void getKeyContext() {
        System.out.println("privateKey >> " + privateKey);
        System.out.println("publicKey >> " + publicKey);
    }

    @Test
    void encryptBypPublicKeyAndDecryptByPrivateKey() {
        String encryptData = RSAUtil.encryptByPublicKey("abc", publicKey);
        System.out.println(encryptData);

        String data = RSAUtil.decryptByPrivateKey(encryptData, privateKey);
        System.out.println(data);
    }

    @Test
    void encryptByPrivateKeyAndDecryptByPublicKey() {
        String encryptData = RSAUtil.encryptByPrivateKey("abc", privateKey);
        System.out.println(encryptData);

        String data = RSAUtil.decryptByPublicKey(encryptData, publicKey);
        System.out.println(data);
    }

}