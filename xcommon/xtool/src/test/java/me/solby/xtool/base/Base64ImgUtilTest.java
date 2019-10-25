package me.solby.xtool.base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * me.solby.xtool.base
 *
 * @author majhdk
 * @date 2019/10/24
 */
class Base64ImgUtilTest {

    @Test
    void getImageStr() {
        String str = Base64ImgUtil.getImageStr("/Users/majhdk/Downloads/pic.jpg");
        Assertions.assertNotNull(str, "not null");
    }

    @Test
    void generateImage() {
        String str = Base64ImgUtil.getImageStr("/Users/majhdk/Downloads/pic.jpg");
        boolean b = Base64ImgUtil.generateImage(str, "/Users/majhdk/Downloads/pic-copy.jpg");
        Assertions.assertTrue(b);
    }

    @Test
    void generateImageByte() {
        String str = Base64ImgUtil.getImageStr("/Users/majhdk/Downloads/pic.jpg");
        byte[] imageByte = Base64ImgUtil.generateImageByte(str);
        Assertions.assertNotNull(imageByte);
    }

    @Test
    void getImage() {
    }
}