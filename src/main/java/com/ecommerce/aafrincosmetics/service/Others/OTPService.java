package com.ecommerce.aafrincosmetics.service.Others;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OTPService {
    private static final Map<String, Long> otpStorage = new HashMap<>();
    private static final long OTP_EXPIRATION_DURATION_MS = 2 * 60 * 1000; // 2 minutes

    public void storeOTP(String otp) {
        long expirationTime = System.currentTimeMillis() + OTP_EXPIRATION_DURATION_MS;
        otpStorage.put(otp, expirationTime);
    }

    public boolean isOTPValid(String otp) {
        Long expirationTime = otpStorage.get(otp);
        if (expirationTime == null) {
            return false; // OTP does not exist
        }
        return expirationTime >= System.currentTimeMillis();
    }
}
