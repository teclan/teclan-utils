package teclan.utils.security;

import java.math.BigInteger;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;

public class Cipher {
    private int     ct;
    private ECPoint p2;
    private SM3     sm3keybase;
    private SM3     sm3c3;
    private byte    key[];
    private byte    keyOff;

    public Cipher() {
        this.ct = 1;
        this.key = new byte[32];
        this.keyOff = 0;
    }

    private void Reset() {
        this.sm3keybase = new SM3();
        this.sm3c3 = new SM3();

        byte p[] = Util.byteConvert32Bytes(p2.getX().toBigInteger());
        SM3.update(p, 0, p.length);
        SM3.update(p, 0, p.length);

        p = Util.byteConvert32Bytes(p2.getY().toBigInteger());
        SM3.update(p, 0, p.length);
        this.ct = 1;
        NextKey();
    }

    private void NextKey() {
        SM3.update((byte) (ct >> 24 & 0xff));
        SM3.update((byte) (ct >> 16 & 0xff));
        SM3.update((byte) (ct >> 8 & 0xff));
        SM3.update((byte) (ct & 0xff));
        SM3.doFinal(key, 0);
        this.keyOff = 0;
        this.ct++;
    }

    public ECPoint Init_enc(SM2 sm2, ECPoint userKey) {
        AsymmetricCipherKeyPair key = sm2.ecc_key_pair_generator
                .generateKeyPair();
        ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) key
                .getPrivate();
        ECPublicKeyParameters ecpub = (ECPublicKeyParameters) key.getPublic();
        BigInteger k = ecpriv.getD();
        ECPoint c1 = ecpub.getQ();
        this.p2 = userKey.multiply(k);
        Reset();
        return c1;
    }

    public void Encrypt(byte data[]) {
        SM3.update(data, 0, data.length);
        for (int i = 0; i < data.length; i++) {
            if (keyOff == key.length) {
                NextKey();
            }
            data[i] ^= key[keyOff++];
        }
    }

    public void Init_dec(BigInteger userD, ECPoint c1) {
        this.p2 = c1.multiply(userD);
        Reset();
    }

    public void Decrypt(byte data[]) {
        for (int i = 0; i < data.length; i++) {
            if (keyOff == key.length) {
                NextKey();
            }
            data[i] ^= key[keyOff++];
        }

        SM3.update(data, 0, data.length);
    }

    public void Dofinal(byte c3[]) {
        byte p[] = Util.byteConvert32Bytes(p2.getY().toBigInteger());
        SM3.update(p, 0, p.length);
        SM3.doFinal(c3, 0);
        Reset();
    }
}
