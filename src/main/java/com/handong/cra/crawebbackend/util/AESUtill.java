package com.handong.cra.crawebbackend.util;

import lombok.NoArgsConstructor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtill {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    // TODO : 변경 ㄱㄱ
    private static final String SECURITY_KEY = "1234567890123456";
    private static final String IV = "1234567890123456";

    public static String AESDecrypt(String input) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(SECURITY_KEY.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] temp = Base64.getDecoder().decode(input);
        byte[] bytes = cipher.doFinal(temp);
        return new String(bytes);
    }


}
