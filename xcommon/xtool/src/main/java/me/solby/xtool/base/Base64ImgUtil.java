package me.solby.xtool.base;

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
 * me.solby.xtool.base
 * TODO EDIT
 *
 * @author majhdk
 * @date 2019/10/19
 */
public class Base64ImgUtil {

    /**
     * 根据图片路径对图片进行BASE64编码
     *
     * @param imgFilePath 图片文件所在路径
     * @return 图片的base64编码字符串
     */
    public static String GetImageStr(String imgFilePath) {
        if (imgFilePath == null || ("").equals(imgFilePath)) {
            return null;
        }
        byte[] data;
        InputStream in = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFilePath);
            int count = -1;//in.available有可能为0所以不能用0做默认值,否则会死循环
            while (count == -1) {
                count = in.available();
            }
            if (count <= 0) {
                return null;
            }
            data = new byte[count];
            in.read(data);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        // 对字节数组Base64编码
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * 对BASE64字符串解码并保存成文件
     *
     * @param imgStr      base64编码的字符串
     * @param imgFilePath 保存图片的文件路径
     * @return
     */
    public static boolean GenerateImage(String imgStr, String imgFilePath) {
        if (imgStr == null || ("").equals(imgStr)
                || imgFilePath == null || ("").equals(imgFilePath))
            return false;
        OutputStream out = null;
        BufferedOutputStream bos = null;
        try {
            // Base64解码
            byte[] bytes = Base64.getDecoder().decode(imgStr);

            // 生成jpeg图片
            out = new FileOutputStream(imgFilePath);
//			out.write(bytes);
//			out.flush();
            bos = new BufferedOutputStream(out);
            // 使用缓冲区写二进制字节数据
            bos.write(bytes);
            // BufferedReader //读取
            // BufferedWriter //写入
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (bos != null)
                    bos.close();
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    /**
     * 对BASE64字符串解码并保存成字节数组
     *
     * @param imgStr base64编码的字符串
     * @return 返回Base64解码后的字节数组
     */
    public static byte[] GenerateImageByte(String imgStr) {
        if (imgStr == null || ("").equals(imgStr))
            return null;
        try {
            // Base64解码
            byte[] bytes = Base64.getDecoder().decode(imgStr);
            for (int i = 0, j = bytes.length; i < j; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param p_Str Base64编码的字符窜
     * @return byte[]类型的照片信息
     */
    public static byte[] fromBase64(String p_Str) {
        if (p_Str == null)
            return null;
        return Base64.getDecoder().decode(p_Str);
    }

    /**
     * @param bytes byte[]类型的照片信息
     * @return Java Image对象。
     */
    public static Image getImage(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        Image img = null;
        InputStream isPhoto = null;
        try {
            isPhoto = new ByteArrayInputStream(bytes);
            img = ImageIO.read(isPhoto);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (isPhoto != null) {
                    isPhoto.close();
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return img;
    }

}
