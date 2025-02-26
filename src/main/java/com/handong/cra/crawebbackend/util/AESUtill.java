package com.handong.cra.crawebbackend.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class AESUtill {


    @Value("${aes.algorithm}")
    private void setAlgorithm(String algorithm) {
        ALGORITHM = algorithm;
    }

    @Value("${aes.security-key}")
    private void setSecurityKey(String securityKey) {
        SECURITY_KEY = securityKey;
    }

    @Value("${aes.iv}")
    private void setIv(String iv) {
        IV = iv;
    }

    private static String ALGORITHM;
    private static String SECURITY_KEY;
    private static String IV;


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
