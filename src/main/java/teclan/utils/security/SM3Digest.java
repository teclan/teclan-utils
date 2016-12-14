package teclan.utils.security;

public class SM3Digest {

    public static void main(String[] args) {
        SM3 sm3 = new SM3();

        System.out.println(sm3.summary("文本信息"));

    }
}
