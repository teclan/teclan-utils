package teclan.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Strings {
    private static final Logger LOGGER     = LoggerFactory
            .getLogger(Strings.class);

    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static boolean equal(String left, String right) {
        if (left == null) {
            return right == null;
        }

        return left.equals(right);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 根据指定算法计算字符串的摘要
     *
     * @param str
     *            待摘要字符串
     * @param algorithm
     *            摘要算法， MD5,SHA-1,SHA-256
     *
     * @return 摘要
     */
    public static String toHexDigest(String str, String algorithm) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(str.getBytes());
            byte[] bytes = messageDigest.digest();
            int len = bytes.length;
            StringBuilder buf = new StringBuilder(len * 2);
            // 把密文转换成十六进制的字符串形式
            for (int j = 0; j < len; j++) {
                buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
                buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
            }
            return buf.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String toHexString(String str) {
        StringBuffer buffer = new StringBuffer();

        try {
            for (byte b : str.getBytes("UTF-8")) {
                buffer.append(Bytes.toHexString(b));
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return buffer.toString();
    }

    public static byte[] toBytesFromChar(String charString) {
        char[] chars = charString.toCharArray();
        byte[] bytes = new byte[chars.length];
        for (int i = 0; i < chars.length; i++) {
            bytes[i] = (byte) chars[i];
        }
        return bytes;
    }

    public static byte[] toBytesFromHex(String hexString) {

        if (isEmpty(hexString))
            throw new IllegalArgumentException(
                    "this hexString must not be empty");

        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {
            // 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16)
                    & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16)
                    & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    public static byte[] toBcdInHex(long number) {
        return toBcd(Long.toHexString(number));
    }

    public static byte[] toBcd(String number) {
        int len = number.length();
        int mod = len % 2;
        if (mod != 0) {
            number = "0" + number;
            len = number.length();
        }
        byte abt[] = new byte[len];
        if (len >= 2) {
            len = len / 2;
        }
        byte bbt[] = new byte[len];
        abt = number.getBytes();
        int j, k;
        for (int p = 0; p < number.length() / 2; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }
            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }
            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }

    /**
     * Format the double value with a single decimal points, trimming trailing
     * '.0'.
     */
    public static String format1Decimals(double value, String suffix) {
        String p = String.valueOf(value);
        int ix = p.indexOf('.') + 1;
        int ex = p.indexOf('E');
        char fraction = p.charAt(ix);
        if (fraction == '0') {
            if (ex != -1) {
                return p.substring(0, ix - 1) + p.substring(ex) + suffix;
            } else {
                return p.substring(0, ix - 1) + suffix;
            }
        } else {
            if (ex != -1) {
                return p.substring(0, ix) + fraction + p.substring(ex) + suffix;
            } else {
                return p.substring(0, ix) + fraction + suffix;
            }
        }
    }
}
