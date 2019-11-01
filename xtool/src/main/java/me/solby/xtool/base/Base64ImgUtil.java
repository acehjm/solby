package me.solby.xtool.base;

import me.solby.xtool.verify.ObjectUtil;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

/**
 * 图片工具类
 *
 * @author majhdk
 * @date 2019/10/19
 */
public class Base64ImgUtil {

    /**
     * 根据图片路径对图片进行BASE64编码
     *
     * @param imagePath 图片文件所在路径
     * @return 图片的base64编码字符串
     */
    public static String getImageStr(final String imagePath) {
        if (StringUtils.isBlank(imagePath)) {
            return null;
        }
        byte[] data = null;
        try (InputStream in = new FileInputStream(imagePath)) {
            int count = -1;
            while (count == -1) {
                // in.available有可能为0所以不能用0做默认值,否则会死循环
                count = in.available();
            }
            if (count <= 0) {
                return null;
            }
            data = new byte[count];
            in.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ObjectUtil.isEmpty(data)) {
            return null;
        }
        // 对字节数组Base64编码
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * 对BASE64字符串解码并保存成文件
     *
     * @param imgStr    base64编码的字符串
     * @param imagePath 保存图片的文件路径
     * @return
     */
    public static boolean generateImage(final String imgStr, final String imagePath) {
        if (StringUtils.isBlank(imgStr) || StringUtils.isBlank(imagePath)) {
            return false;
        }
        try (  // 生成jpeg图片
               OutputStream out = new FileOutputStream(imagePath);
               BufferedOutputStream bos = new BufferedOutputStream(out)
        ) {
            // Base64解码
            byte[] bytes = Base64.getDecoder().decode(imgStr);
            // 使用缓冲区写二进制字节数据
            bos.write(bytes);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 对BASE64字符串解码并保存成字节数组
     *
     * @param imgStr base64编码的字符串
     * @return 返回Base64解码后的字节数组
     */
    public static byte[] generateImageByte(final String imgStr) {
        if (StringUtils.isBlank(imgStr)) {
            return null;
        }
        try {
            // Base64解码
            byte[] bytes = Base64.getDecoder().decode(imgStr);
            for (int i = 0, j = bytes.length; i < j; ++i) {
                if (bytes[i] < 0) {
                    // 调整异常数据
                    bytes[i] += 256;
                }
            }
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 流转图片
     *
     * @param bytes byte[]类型的照片信息
     * @return Java Image对象
     */
    public static Image getImage(final byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try (InputStream isPhoto = new ByteArrayInputStream(bytes)) {
            return ImageIO.read(isPhoto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
