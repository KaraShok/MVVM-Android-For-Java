package com.karashok.library.module_util.utilcode.utils.base_util;

import android.os.Build;
import android.text.Html;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author Ralf(wanglixin)
 * DESCRIPTION
 * @name EncodeUtils
 * @date 2018/06/02 下午7:54
 **/
public final class EncodeUtils {

    private EncodeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * URL 编码
     * <p>若想自己指定字符集,可以使用{@link #urlEncode(String input, String charset)}方法</p>
     *
     * @param input 要编码的字符
     * @return 编码为 UTF-8 的字符串
     */
    public static String urlEncode(final String input) {
        return urlEncode(input, "UTF-8");
    }

    /**
     * URL 编码
     * <p>若系统不支持指定的编码字符集,则直接将 input 原样返回</p>
     *
     * @param input   要编码的字符
     * @param charset 字符集
     * @return 编码为字符集的字符串
     */
    public static String urlEncode(final String input, final String charset) {
        try {
            return URLEncoder.encode(input, charset);
        } catch (UnsupportedEncodingException e) {
            return input;
        }
    }

    /**
     * URL 解码
     * <p>若想自己指定字符集,可以使用 {@link #urlDecode(String input, String charset)}方法</p>
     *
     * @param input 要解码的字符串
     * @return URL 解码后的字符串
     */
    public static String urlDecode(final String input) {
        return urlDecode(input, "UTF-8");
    }

    /**
     * URL 解码
     * <p>若系统不支持指定的解码字符集,则直接将 input 原样返回</p>
     *
     * @param input   要解码的字符串
     * @param charset 字符集
     * @return URL 解码为指定字符集的字符串
     */
    public static String urlDecode(final String input, final String charset) {
        try {
            return URLDecoder.decode(input, charset);
        } catch (UnsupportedEncodingException e) {
            return input;
        }
    }

    /**
     * Base64 编码
     *
     * @param input 要编码的字符串
     * @return Base64 编码后的字符串
     */
    public static byte[] base64Encode(final String input) {
        return base64Encode(input.getBytes());
    }

    /**
     * Base64 编码
     *
     * @param input 要编码的字节数组
     * @return Base64 编码后的字符串
     */
    public static byte[] base64Encode(final byte[] input) {
        return Base64.encode(input, Base64.NO_WRAP);
    }

    /**
     * Base64 编码
     *
     * @param input 要编码的字节数组
     * @return Base64 编码后的字符串
     */
    public static String base64Encode2String(final byte[] input) {
        return Base64.encodeToString(input, Base64.NO_WRAP);
    }

    /**
     * Base64 解码
     *
     * @param input 要解码的字符串
     * @return Base64 解码后的字符串
     */
    public static byte[] base64Decode(final String input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    /**
     * Base64 解码
     *
     * @param input 要解码的字符串
     * @return Base64 解码后的字符串
     */
    public static byte[] base64Decode(final byte[] input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    /**
     * Base64URL 安全编码
     * <p>将 Base64 中的 URL 非法字符�?,/=转为其他字符, 见 RFC3548</p>
     *
     * @param input 要 Base64URL 安全编码的字符串
     * @return Base64URL 安全编码后的字符串
     */
    public static byte[] base64UrlSafeEncode(final String input) {
        return Base64.encode(input.getBytes(), Base64.URL_SAFE);
    }

    /**
     * Html 编码
     *
     * @param input 要 Html 编码的字符串
     * @return Html 编码后的字符串
     */
    public static String htmlEncode(final CharSequence input) {
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0, len = input.length(); i < len; i++) {
            c = input.charAt(i);
            switch (c) {
                case '<':
                    sb.append("&lt;"); //$NON-NLS-1$
                    break;
                case '>':
                    sb.append("&gt;"); //$NON-NLS-1$
                    break;
                case '&':
                    sb.append("&amp;"); //$NON-NLS-1$
                    break;
                case '\'':
                    //http://www.w3.org/TR/xhtml1
                    // The named character reference &apos; (the apostrophe, U+0027) was
                    // introduced in XML 1.0 but does not appear in HTML. Authors should
                    // therefore use &#39; instead of &apos; to work as expected in HTML 4
                    // user agents.
                    sb.append("&#39;"); //$NON-NLS-1$
                    break;
                case '"':
                    sb.append("&quot;"); //$NON-NLS-1$
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Html 解码
     *
     * @param input 待解码的字符串
     * @return Html 解码后的字符串
     */
    @SuppressWarnings("deprecation")
    public static CharSequence htmlDecode(final String input) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(input);
        }
    }

    /**
     * 中文转unicode编码
     */
    public static String encodeUnicode(final String gbString) {
        String prifix = "\\u";
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < gbString.length(); i++) {
            char c = gbString.charAt(i);
            String code = prifix + format(Integer.toHexString(c));
            unicode.append(code);
        }
        return unicode.toString();
    }

    /**
     * 为长度不足4位的unicode 值补零
     * @param str
     * @return
     */
    private static String format(String str) {
        for ( int i=0, l=4-str.length(); i<l; i++ )
            str = "0" + str;
        return str;
    }

    /**
     * unicode编码转中文
     */
    public static String decodeUnicode(final String dataStr) {
        try {
            byte[] bytes = dataStr.getBytes("utf-8");
            String ret = new String(bytes, "utf-8");
            return ret;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
        //        StringBuffer str = new StringBuffer();
//        String[] hex = dataStr.split("\\\\u");
//        for (int i = 1; i < hex.length; i++) {
//            int data = Integer.parseInt(hex[i], 16);
//            str.append((char) data);
//        }
//        return str.length() > 0 ? str.toString() : dataStr;
    }
}
