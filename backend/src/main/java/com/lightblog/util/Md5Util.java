package com.lightblog.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public final class Md5Util {
    private Md5Util() {
    }

    public static String md5(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (byte b : bytes) {
                String hex = Integer.toHexString(b & 0xff);
                if (hex.length() == 1) {
                    builder.append('0');
                }
                builder.append(hex);
            }
            return builder.toString();
        } catch (Exception ex) {
            throw new IllegalStateException("MD5 加密失败", ex);
        }
    }
}
