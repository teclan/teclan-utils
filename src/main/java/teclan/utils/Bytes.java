package teclan.utils;

public class Bytes {
    public static String toHexString(byte b) {
        String hex = Integer.toHexString(b & 0xff);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }

        return hex;
    }

    public static String toHexString(byte[] bytes) {
        if (bytes == null || bytes.length < 1)
            throw new IllegalArgumentException(
                    "this bytes must not be null or empty");

        StringBuffer buffer = new StringBuffer();
        for (byte b : bytes) {
            buffer.append(toHexString(b));
        }
        return buffer.toString();
    }

    public static String toStringFromBcd(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            int h = ((bytes[i] & 0xff) >> 4) + 48;
            sb.append((char) h);
            int l = (bytes[i] & 0x0f) + 48;
            sb.append((char) l);
        }
        return sb.toString();
    }

    public static byte[] xor(byte[] message, byte[] key) {
        int messageLength = message.length;
        int keyLength = key.length;

        byte[] result = new byte[messageLength];

        for (int i = 0; i < ((messageLength - 1) / keyLength + 1); i++) {
            for (int j = 0; j < keyLength; j++) {
                int index = i * keyLength + j;
                if (index >= messageLength) {
                    break;
                }
                result[index] = (byte) (message[index] ^ key[j]);
            }
        }

        return result;
    }
}
