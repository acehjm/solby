package me.solby.xtool.security;

import org.junit.jupiter.api.Test;

/**
 * me.solby.itool.security
 *
 * @author majhdk
 * @date 2019-06-08
 */
class SHAUtilTest {

    @Test
    void sha256HexWithString() {
        String data = "SHA-algorithm";

        System.out.println("Hex=====================================");
        System.out.println(SHAUtil.Digest.MD5.digest(data));
        System.out.println(SHAUtil.Digest.SHA1.digest(data));
        System.out.println(SHAUtil.Digest.SHA256.digest(data));

        System.out.println("Base64=====================================");
        System.out.println(SHAUtil.Digest.MD5.digestBase64(data));
        System.out.println(SHAUtil.Digest.SHA1.digestBase64(data));
        System.out.println(SHAUtil.Digest.SHA256.digestBase64(data));
    }

    @Test
    void shaAlgorithmHex() {
        System.out.println(SHAUtil.shaAlgorithmHex("SHA-256", "MD5"));
    }
}