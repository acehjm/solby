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
        String data = "SHA-256";
        System.out.println(SHAUtil.sha256Hex(data));
        System.out.println(SHAUtil.sha256HexBase64(data));
    }

    @Test
    void sha256HexWithBytes() {
    }

    @Test
    void shaAlgorithmHex() {
        System.out.println(SHAUtil.shaAlgorithmHex("SHA-256", "MD5"));
    }
}