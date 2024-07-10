package fr.xephi.authme.util;

/**
 * StringUtil
 *
 * @author TheFloodDragon
 * @since 2024/7/10 19:51
 */
public final class StringUtil {

    private StringUtil() {
    }

    /**
     * 解码 Unicode
     */
    public static String decodeUnicode(String str) {
        String r = str;
        int i = r.indexOf("\\u");
        if (i != -1) {
            r = r.substring(0, i) + (char) Integer.parseInt(r.substring(i + 2, i + 6), 16) + r.substring(i + 6);
            decodeUnicode(r);
        }
        return r;
    }

}