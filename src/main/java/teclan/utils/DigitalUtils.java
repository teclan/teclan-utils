package teclan.utils;

public class DigitalUtils {

    /**
     * 字节数组转 int
     * 
     * @param bytes
     * @return
     */
    public static int bytes2Int(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;
        }
        return value;
    }

    /**
     * int 转字节数组
     * 
     * @param i
     * @return
     */
    public static byte[] int2Bytes(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    /**
     * long 转字节数组
     * 
     * @param num
     * @return
     */
    public static byte[] long2Bytes(long num) {
        byte[] byteNum = new byte[8];
        for (int ix = 0; ix < 8; ++ix) {
            int offset = 64 - (ix + 1) * 8;
            byteNum[ix] = (byte) ((num >> offset) & 0xff);
        }
        return byteNum;
    }

    /**
     * 字节数组转 long
     * 
     * @param byteNum
     * @return
     */
    public static long bytes2Long(byte[] byteNum) {
        long num = 0;
        for (int ix = 0; ix < 8; ++ix) {
            num <<= 8;
            num |= (byteNum[ix] & 0xff);
        }
        return num;
    }

    /**
     * 10进制转2进制
     * 
     * @param source
     * @return
     */
    public static String dec2Binary(int source) {
        return Integer.toBinaryString(source);
    }

    /**
     * 10进制转8进制
     * 
     * @param source
     * @return
     */
    public static String dec2Oct(int source) {
        return Integer.toOctalString(source);
    }

    /**
     * 10进制转16进制
     * 
     * @param source
     * @return
     */
    public static String dec2Hex(int source) {
        return Integer.toHexString(source);
    }

    /**
     * 8进制转2进制
     * 
     * @param source
     * @return
     */
    public static String oct2Binary(String source) {
        return Integer.toBinaryString(Integer.valueOf(source, 8));
    }

    /**
     * 8进制转10进制
     * 
     * @param source
     * @return
     */
    public static String oct2Dec(String source) {
        return Integer.valueOf(source, 8).toString();
    }

    /**
     * 8进制转16进制
     * 
     * @param source
     * @return
     */
    public static String oct2Hex(String source) {
        return Integer.toHexString(Integer.valueOf(source, 8));
    }

    /**
     * 16进制转2进制
     * 
     * @param source
     * @return
     */
    public static String hex2Binary(String source) {
        source.replace("0x", "").replace("0X", "");
        return Integer.toBinaryString(Integer.valueOf(source, 16));
    }

    /**
     * 16进制转8进制
     * 
     * @param source
     * @return
     */
    public static String hex2Oct(String source) {
        return Integer.toOctalString(Integer.valueOf(source, 16));
    }

    /**
     * 16进制转10进制
     * 
     * @param source
     * @return
     */
    public static String hex2Dec(String source) {
        source.replace("0x", "").replace("0X", "");
        return Integer.valueOf(source, 16).toString();
    }

    /**
     * 2进制转8进制
     * 
     * @param source
     * @return
     */
    public static String binary2Oct(String source) {
        return Integer.toOctalString(Integer.parseInt(source, 2));
    }

    /**
     * 2进制转10进制
     * 
     * @param source
     * @return
     */
    public static String binary2Dec(String source) {
        return Integer.valueOf(source, 2).toString();
    }

    /**
     * 2进制转16进制
     * 
     * @param source
     * @return
     */
    public static String binary2Hex(String source) {
        return Integer.toHexString(Integer.parseInt(source, 16));
    }

}
