package com.afengzi.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by winged fish on 2016/6/9.
 */
public class FastLife {
    private final static Logger log = LogManager.getLogger(FastLife.class);
    private String secretKey = "2099named2213";//加解密密钥
    private String charetSet = "utf-16";

    public String encryptStrWithAes(String str) {
        String resStr = null;
        try {
            byte[] raw = secretKey.getBytes();
            System.out.println(raw.length);
            SecretKeySpec key = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] byteContent = str.getBytes(charetSet);
            byte[] encrypted = cipher.doFinal(byteContent);
            BASE64Encoder encoder = new BASE64Encoder();
            resStr = encoder.encode(encrypted);
        }
        catch (Exception e) {
            log.error("AES加密失败", e);
            System.out.println(e);
        }
        return resStr;
    }
    /**
     * AES解密
     * @param str
     * @return
     */
    public String decryptStrWithAes(String str) {
        String resStr = null;
        try {
            byte[] raw = secretKey.getBytes();
            SecretKeySpec key = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] decrypted = cipher.doFinal(decoder.decodeBuffer(str));
            resStr = new String(decrypted, charetSet);
        }
        catch (Exception e) {
            log.error("AES解密失败", e);
            System.out.println(e);
        }
        return resStr;
    }

    public static void main(String[] args) {
        FastLife fastLife = new FastLife();
//        String encrypt = fastLife.encryptStrWithAes("");
//        System.out.println("encrypt  "+encrypt);

        String decrypt = fastLife.decryptStrWithAes("g2h4znhE+SFio02MNGvA/6U00CGt67ZBFX19QK0FHXw=") ;
        System.out.println(decrypt);
    }

}
