package com.a.a;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    public static final int[] f204a = {0, 1, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 48, 33, 48, 9, 6, 5, 43, 14, 3, 2, 26, 5, 0, 4, 20};
    public static byte[] b = new byte[f204a.length];
    private KeyPair c;
    private b d;

    static {
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) f204a[i];
        }
    }

    public static d a(b bVar) throws NoSuchAlgorithmException {
        d dVar = new d();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        dVar.c = keyPairGenerator.genKeyPair();
        dVar.d = bVar;
        return dVar;
    }

    public static d a(b bVar, File file, File file2) throws NoSuchAlgorithmException, IOException {
        d dVar = new d();
        byte[] bArr = new byte[(int) file.length()];
        byte[] bArr2 = new byte[(int) file2.length()];
        FileInputStream fileInputStream = new FileInputStream(file);
        FileInputStream fileInputStream2 = new FileInputStream(file2);
        fileInputStream.read(bArr);
        fileInputStream2.read(bArr2);
        fileInputStream.close();
        fileInputStream2.close();
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        dVar.c = new KeyPair(keyFactory.generatePublic(new X509EncodedKeySpec(bArr2)), keyFactory.generatePrivate(new PKCS8EncodedKeySpec(bArr)));
        dVar.d = bVar;
        return dVar;
    }

    private static byte[] a(RSAPublicKey rSAPublicKey) {
        BigInteger bit = BigInteger.ZERO.setBit(32);
        BigInteger modulus = rSAPublicKey.getModulus();
        BigInteger bigIntegerModPow = BigInteger.ZERO.setBit(2048).modPow(BigInteger.valueOf(2L), modulus);
        BigInteger bigIntegerModInverse = modulus.remainder(bit).modInverse(bit);
        int[] iArr = new int[64];
        int[] iArr2 = new int[64];
        BigInteger bigInteger = modulus;
        int i = 0;
        while (i < 64) {
            BigInteger[] bigIntegerArrDivideAndRemainder = bigIntegerModPow.divideAndRemainder(bit);
            BigInteger bigInteger2 = bigIntegerArrDivideAndRemainder[0];
            iArr2[i] = bigIntegerArrDivideAndRemainder[1].intValue();
            BigInteger[] bigIntegerArrDivideAndRemainder2 = bigInteger.divideAndRemainder(bit);
            bigInteger = bigIntegerArrDivideAndRemainder2[0];
            iArr[i] = bigIntegerArrDivideAndRemainder2[1].intValue();
            i++;
            bigIntegerModPow = bigInteger2;
        }
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(524).order(ByteOrder.LITTLE_ENDIAN);
        byteBufferOrder.putInt(64);
        byteBufferOrder.putInt(bigIntegerModInverse.negate().intValue());
        for (int i2 : iArr) {
            byteBufferOrder.putInt(i2);
        }
        for (int i3 : iArr2) {
            byteBufferOrder.putInt(i3);
        }
        byteBufferOrder.putInt(rSAPublicKey.getPublicExponent().intValue());
        return byteBufferOrder.array();
    }

    public void a(File file, File file2) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
        fileOutputStream.write(this.c.getPrivate().getEncoded());
        fileOutputStream2.write(this.c.getPublic().getEncoded());
        fileOutputStream.close();
        fileOutputStream2.close();
    }

    public byte[] a() {
        byte[] bArrA = a((RSAPublicKey) this.c.getPublic());
        StringBuilder sb = new StringBuilder(720);
        sb.append(this.d.a(bArrA));
        sb.append(" unknown@unknown");
        sb.append((char) 0);
        return sb.toString().getBytes("UTF-8");
    }

    public byte[] a(byte[] bArr) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
        cipher.init(1, this.c.getPrivate());
        cipher.update(b);
        return cipher.doFinal(bArr);
    }
}
