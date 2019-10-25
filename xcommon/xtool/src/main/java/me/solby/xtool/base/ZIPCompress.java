package me.solby.xtool.base;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

/**
 * 解压缩工具类
 * JDK deflate ——这是JDK中的又一个算法（zip文件用的就是这一算法）
 * 可选的级别有0（不压缩），以及1(快速压缩)到9（慢速压缩）
 * 它与gzip的不同之处在于，你可以指定算法的压缩级别，这样你可以在压缩时间和输出文件大小上进行平衡
 *
 * @author majhdk
 * @date 2019/10/19
 */
public class ZIPCompress {

    /**
     * 压缩
     *
     * @param str   原始字符串
     * @param level 压缩级别({"1", "2", "3", "4", "5", "6", "7", "8", "9"})
     * @return
     */
    public static String compress(@NotNull String str, @Nullable Integer level) {
        level = null == level || 1 > level ? 6 : level;

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             DeflaterOutputStream deflaterOutputStream =
                     new DeflaterOutputStream(out, new Deflater(level, true), true);) {
            // 待压缩数据为str字符串得到的字节数组
            deflaterOutputStream.write(str.getBytes());
            // 必须要关闭，让最后一次未缓冲满的数据也写入。
            deflaterOutputStream.close();
            byte[] compressed = out.toByteArray();
            out.close();
            return new String(compressed, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解压缩
     *
     * @param compressed 压缩字符串
     * @return
     */
    public static String deCompress(@NotNull String compressed) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // 压缩后写到out流中
            Inflater inf = new Inflater(true);
            InflaterOutputStream inflaterOutputStream = new InflaterOutputStream(out, inf);
            inflaterOutputStream.write(compressed.getBytes(StandardCharsets.UTF_8));
            inflaterOutputStream.close();
            return new String(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 返回原来的字符串
        return compressed;
    }

}
