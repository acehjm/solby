package me.solby.xtool.security;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 16进制转换
 *
 * @author majhdk
 * @date 2019-06-08
 */
public class Hex {

    /**
     * 默认编码
     */
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 用于将输出构建为Hex
     */
    private static final char[] DIGITS_LOWER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 用于将输出构建为Hex
     */
    private static final char[] DIGITS_UPPER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private final Charset charset;

    /**
     * 使用默认字符集名称创建新的编解码器{@link #DEFAULT_CHARSET}
     */
    public Hex() {
        this.charset = DEFAULT_CHARSET;
    }

    /**
     * 使用自定义字符集创建新的编解码器
     *
     * @param charset the charset.
     */
    public Hex(final Charset charset) {
        this.charset = charset;
    }

    /**
     * 使用自定义字符集名称创建新的编解码器
     *
     * @param charsetName the charset name.
     */
    public Hex(final String charsetName) {
        this(Charset.forName(charsetName));
    }

    /**
     * 将表示十六进制值的String转换为这些相同值的字节数组。
     * 返回的数组将是传递的String的一半长度，因为它需要两个字符来表示任何给定的字节。
     * 如果传递的String具有奇数个元素，则抛出异常。
     *
     * @param data
     * @return
     */
    public static byte[] decodeHex(final String data) throws SecurityException {
        return decodeHex(data.toCharArray());
    }

    /**
     * 将表示十六进制值的String转换为这些相同值的字节数组。
     * 返回的数组将是传递的String的一半长度，因为它需要两个字符来表示任何给定的字节。
     * 如果传递的String具有奇数个元素，则抛出异常。
     *
     * @param data
     * @return
     */
    public static byte[] decodeHex(final char[] data) throws SecurityException {

        final int len = data.length;

        if ((len & 0x01) != 0) {
            throw new SecurityException("Odd number of characters.");
        }

        final byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }

        return out;
    }

    /**
     * 将字节数组转换为字符数组，按顺序表示每个字节的十六进制值。
     * 返回的数组将是传递数组长度的两倍，因为它需要两个字符来表示任何给定的字节。
     *
     * @param data
     * @return
     */
    public static char[] encodeHex(final byte[] data) {
        return encodeHex(data, true);
    }

    /**
     * 将字符串转换为字符数组，按顺序表示每个字节的十六进制值。
     * 返回的数组将是传递数组长度的两倍，因为它需要两个字符来表示任何给定的字节。
     *
     * @param data
     * @return
     */
    public static char[] encodeHex(final ByteBuffer data) {
        return encodeHex(data, true);
    }

    /**
     * 将字节数组转换为字符数组，按顺序表示每个字节的十六进制值。
     * 返回的数组将是传递数组长度的两倍，因为它需要两个字符来表示任何给定的字节。
     *
     * @param data
     * @param toLowerCase
     * @return
     */
    public static char[] encodeHex(final byte[] data, final boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * 将字符串转换为字符数组，按顺序表示每个字节的十六进制值。
     * 返回的数组将是传递数组长度的两倍，因为它需要两个字符来表示任何给定的字节。
     *
     * @param data
     * @param toLowerCase
     * @return
     */
    public static char[] encodeHex(final ByteBuffer data, final boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * 将字节数组转换为字符数组，按顺序表示每个字节的十六进制值。
     * 返回的数组将是传递数组长度的两倍，因为它需要两个字符来表示任何给定的字节。
     *
     * @param data
     * @param toDigits 输出字母表（必须包含至少16个字符）
     * @return
     */
    protected static char[] encodeHex(final byte[] data, final char[] toDigits) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }

    /**
     * 将字符串转换为字符数组，按顺序表示每个字节的十六进制值。
     * 返回的数组将是传递数组长度的两倍，因为它需要两个字符来表示任何给定的字节。
     *
     * @param data
     * @param toDigits 输出字母表（必须包含至少16个字符）
     * @return
     */
    protected static char[] encodeHex(final ByteBuffer data, final char[] toDigits) {
        return encodeHex(data.array(), toDigits);
    }

    /**
     * 将字节数组转换为String，按顺序表示每个字节的十六进制值。
     * 返回的String将是传递数组长度的两倍，因为它需要两个字符来表示任何给定的字节。
     *
     * @param data
     * @return
     */
    public static String encodeHexString(final byte[] data) {
        return new String(encodeHex(data));
    }

    /**
     * 将字节数组转换为String，按顺序表示每个字节的十六进制值。
     * 返回的String将是传递数组长度的两倍，因为它需要两个字符来表示任何给定的字节。
     *
     * @param data
     * @param toLowerCase
     * @return
     */
    public static String encodeHexString(final byte[] data, final boolean toLowerCase) {
        return new String(encodeHex(data, toLowerCase));
    }

    /**
     * 将字节缓冲区转换为String，按顺序表示每个字节的十六进制值。
     * 返回的String将是传递数组长度的两倍，因为它需要两个字符来表示任何给定的字节。
     *
     * @param data
     * @return
     */
    public static String encodeHexString(final ByteBuffer data) {
        return new String(encodeHex(data));
    }

    /**
     * 将字节缓冲区转换为String，按顺序表示每个字节的十六进制值。
     * 返回的String将是传递数组长度的两倍，因为它需要两个字符来表示任何给定的字节。
     *
     * @param data
     * @return
     */
    public static String encodeHexString(final ByteBuffer data, final boolean toLowerCase) {
        return new String(encodeHex(data, toLowerCase));
    }

    /**
     * 将十六进制字符转换为整数。
     *
     * @param ch    要转换为整数位的字符
     * @param index 源中字符的索引
     * @return
     */
    protected static int toDigit(final char ch, final int index) throws SecurityException {
        final int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new SecurityException("Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }

    /**
     * 将表示十六进制值的字符字节数组转换为这些相同值的字节数组。
     * 返回的数组将是传递数组长度的一半，因为它需要两个字符来表示任何给定的字节。
     * 如果传递的char数组具有奇数个元素，则抛出异常。
     *
     * @param array
     * @return
     */
    public byte[] decode(final byte[] array) throws SecurityException {
        return decodeHex(new String(array, getCharset()).toCharArray());
    }

    /**
     * 将表示十六进制值的字符字节的缓冲区转换为这些相同值的字节数组。
     * 返回的数组将是传递数组长度的一半，因为它需要两个字符来表示任何给定的字节。
     * 如果传递的char数组具有奇数个元素，则抛出异常。
     *
     * @param buffer
     * @return
     */
    public byte[] decode(final ByteBuffer buffer) throws SecurityException {
        return decodeHex(new String(buffer.array(), getCharset()).toCharArray());
    }

    /**
     * 将表示十六进制值的字符串或字符数组数组转换为相同值的字节数组。
     * 返回的数组将是传递的String或数组长度的一半，因为它需要两个字符来表示任何给定的字节。
     * 如果传递的char数组具有奇数个元素，则抛出异常。
     *
     * @param object
     * @return
     */
    public Object decode(final Object object) throws SecurityException {
        if (object instanceof String) {
            return decode(((String) object).toCharArray());
        } else if (object instanceof byte[]) {
            return decode((byte[]) object);
        } else if (object instanceof ByteBuffer) {
            return decode((ByteBuffer) object);
        } else {
            try {
                return decodeHex((char[]) object);
            } catch (final ClassCastException e) {
                throw new SecurityException(e.getMessage(), e);
            }
        }
    }

    /**
     * 将字节数组转换为字节数组，用于按顺序表示每个字节的十六进制值的字符。
     * 返回的数组将是传递数组长度的两倍，因为它需要两个字符来表示任何给定的字节。
     *
     * @param array
     * @return
     */
    public byte[] encode(final byte[] array) {
        return encodeHexString(array).getBytes(this.getCharset());
    }

    /**
     * 将字节缓冲区转换为字节数组，用于按顺序表示每个字节的十六进制值的字符。
     * 返回的数组将是传递数组长度的两倍，因为它需要两个字符来表示任何给定的字节。
     *
     * @param array
     * @return
     */
    public byte[] encode(final ByteBuffer array) {
        return encodeHexString(array).getBytes(this.getCharset());
    }

    /**
     * 将字符串或字节数组转换为字符数组，按顺序表示每个字节的十六进制值。
     * 返回的数组将是传递的String或数组长度的两倍，因为它需要两个字符来表示任何给定的字节。
     *
     * @param object
     * @return
     */
    public Object encode(final Object object) throws SecurityException {
        byte[] byteArray;
        if (object instanceof String) {
            byteArray = ((String) object).getBytes(this.getCharset());
        } else if (object instanceof ByteBuffer) {
            byteArray = ((ByteBuffer) object).array();
        } else {
            try {
                byteArray = (byte[]) object;
            } catch (final ClassCastException e) {
                throw new SecurityException(e.getMessage(), e);
            }
        }
        return encodeHex(byteArray);
    }

    /**
     * 获取字符集
     *
     * @return
     */
    public Charset getCharset() {
        return this.charset;
    }

}
