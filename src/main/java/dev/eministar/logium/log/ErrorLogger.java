package dev.eministar.logium.log;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class ErrorLogger {
    private static final String secretKey = "LogiumIsOPAF123";

    private static final File logFile = new File("plugins/Logium/error.logium");

    public static void logError(String errorText) {
        try {
            if (!logFile.exists())
                logFile.createNewFile();
            String encrypted = encrypt(errorText);
            String binary = toBinary(encrypted);
            FileOutputStream fos = new FileOutputStream(logFile, true);
            try {
                fos.write((binary + "\n").getBytes(StandardCharsets.UTF_8));
                fos.close();
            } catch (Throwable throwable) {
                try {
                    fos.close();
                } catch (Throwable throwable1) {
                    throwable.addSuppressed(throwable1);
                }
                throw throwable;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String encrypt(String plainText) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest("LogiumIsOPAF123".getBytes(StandardCharsets.UTF_8));
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(1, secretKeySpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    private static String toBinary(String input) {
        StringBuilder binary = new StringBuilder();
        for (char c : input.toCharArray()) {
            binary.append(String.format("%8s", new Object[] { Integer.toBinaryString(c) }).replace(' ', '0'));
        }
        return binary.toString();
    }
}
