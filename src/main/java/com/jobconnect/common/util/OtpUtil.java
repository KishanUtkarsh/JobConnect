package com.jobconnect.common.util;

import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Base32;

public class OtpUtil {

    private OtpUtil()  {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String generateOtpSecret() {
        return Base32.random();
    }

    public static String generateOtp(String secretKey) {
        Totp totp = new Totp(secretKey);
        return totp.now();
    }

    public static boolean validateOtp(String secretKey, String otp) {
        Totp totp = new Totp(secretKey);
        return totp.verify(otp);
    }
}
