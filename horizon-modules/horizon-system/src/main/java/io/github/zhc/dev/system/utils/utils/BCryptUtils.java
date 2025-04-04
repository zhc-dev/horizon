package io.github.zhc.dev.system.utils.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 加密算法⼯具类
 *
 * @author zhc.dev
 * @date 2025/3/26 22:27
 */
public class BCryptUtils {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * 对原文进行加密
     *
     * @param original 原文
     * @return 密文
     */
    public static String encrypt(String original) {
        return encoder.encode(original);
    }

    /**
     * 判断字符串加密后是否匹配密文
     *
     * @param original   原文
     * @param ciphertext 密⽂
     * @return 结果
     */
    public static boolean matches(String original, String ciphertext) {
        return encoder.matches(original, ciphertext);
    }

    public static void main(String[] args) {
        System.out.println(encrypt("content@horizon"));
    }
}