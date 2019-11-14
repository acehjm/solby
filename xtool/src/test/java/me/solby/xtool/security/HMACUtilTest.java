package me.solby.xtool.security;

import org.junit.jupiter.api.Test;

/**
 * me.solby.xtool.security
 *
 * @author majhdk
 * @date 2019/11/14
 */
class HMACUtilTest {

    @Test
    void hmac256Base64() {
        String key = "12key35nana";
        String plain = "{\"data\": \"oom\"}";

        System.out.println("Hex===============================================");

        System.out.println(HMACUtil.Signature.HMAC_SHA256.signature(key, plain));
        System.out.println(HMACUtil.Signature.HMAC_SHA1.signature(key, plain));
        System.out.println(HMACUtil.Signature.HMAC_MD5.signature(key, plain));

        System.out.println("Base64===============================================");

        System.out.println(HMACUtil.Signature.HMAC_SHA256.signatureBase64(key, plain));
        System.out.println(HMACUtil.Signature.HMAC_SHA1.signatureBase64(key, plain));
        System.out.println(HMACUtil.Signature.HMAC_MD5.signatureBase64(key, plain));
    }
}