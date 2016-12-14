package teclan.utils.security;

import java.io.IOException;

public class SM4Test {

    public static void main(String[] args) throws IOException {
        String plainText = "ererfeiisgod";

        SM4 sm4 = new SM4();

        sm4.setSecretKey("0123456789ABCD中");

        System.out.println("ECB模式");
        String cipherText = sm4.encryptData_ECB(plainText);
        System.out.println("密文: " + cipherText);
        System.out.println("");

        plainText = sm4.decryptData_ECB(cipherText);
        System.out.println("明文: " + plainText);
        System.out.println("");

        System.out.println("CBC模式");
        sm4.setIv("1qazxsw23edcvfr4");
        cipherText = sm4.encryptData_CBC(plainText);
        System.out.println("密文: " + cipherText);
        System.out.println("");

        plainText = sm4.decryptData_CBC(cipherText);
        System.out.println("明文: " + plainText);
    }

}
