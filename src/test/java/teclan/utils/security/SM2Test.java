package teclan.utils.security;

import teclan.utils.Strings;

public class SM2Test {

    public static void main(String[] args) throws Exception {

        SM2 sm2 = new SM2();
        // 生成密钥对
        sm2.generateKeyPair();

        String plainText = "sererfeiisgod";
        byte[] sourceData = plainText.getBytes();

        // 下面的秘钥可以使用generateKeyPair()生成的秘钥内容
        // 国密规范正式私钥
        String prik = "3690655E33D5EA3D9A4AE1A1ADD766FDEA045CDEAA43A9206FB8C430CEFE0D94";
        // 国密规范正式公钥
        String pubk = "04F6E0C3345AE42B51E06BF50B98834988D54EBC7460FE135A48171BC0629EAE205EEDE253A530608178A98F1E19BB737302813BA39ED3FA3C51639D7A20C7391A";

        System.out.println("加密: ");
        String cipherText = sm2.encrypt(Strings.toBytesFromHex(pubk),
                sourceData);
        System.out.println(cipherText);
        System.out.println("解密: ");
        plainText = new String(sm2.decrypt(Strings.toBytesFromHex(prik),
                Strings.toBytesFromHex(cipherText)));
        System.out.println(plainText);

    }

}
