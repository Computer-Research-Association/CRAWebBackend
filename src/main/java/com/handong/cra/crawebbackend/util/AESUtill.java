package com.handong.cra.crawebbackend.util;

import lombok.NoArgsConstructor;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtill {
    private static final String ALGORITHM = "AES";
    private static final String SECURITY_KEY = "testetsttesttest";


    public static String AESEncrypt(String input) throws Exception{
        SecretKeySpec keySpec = new SecretKeySpec(SECURITY_KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] bytes = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(bytes);

    }

    public static String AESDecrypt(String input) throws Exception{
        SecretKeySpec keySpec = new SecretKeySpec(SECURITY_KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] temp = Base64.getDecoder().decode(input);
        byte[] bytes = cipher.doFinal(temp);
        return new String(bytes);
    }


}
