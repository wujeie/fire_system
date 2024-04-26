package com.wzs.fire.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    private static final int STRENGTH = 12; // BCrypt strength
    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordEncoderUtil() {
        this.passwordEncoder = new BCryptPasswordEncoder(STRENGTH);
    }

    /**
     * Encodes a raw password with BCrypt.
     *
     * @param rawPassword The raw password to encode.
     * @return The encoded password.
     */
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * Checks whether the raw password matches the encoded password.
     *
     * @param rawPassword     The raw password to check.
     * @param encodedPassword The encoded password to compare with.
     * @return true if the raw password matches the encoded password, false otherwise.
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
