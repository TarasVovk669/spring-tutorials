package com.tutorial.graphql.graphqltutorials.util;

import org.bouncycastle.crypto.generators.OpenBSDBCrypt;

import java.nio.charset.StandardCharsets;

public class HashUtil {

    private static final String BCRYPT_SALT = "DOntDothatOnPROD";

    public static boolean isCryptMatch(String original, String hashValue) {
        return OpenBSDBCrypt.checkPassword(hashValue, original.getBytes(StandardCharsets.UTF_8));
    }

    public static String hashBcrypt(String target) {
        return OpenBSDBCrypt.generate(target.getBytes(StandardCharsets.UTF_8), BCRYPT_SALT.getBytes(StandardCharsets.UTF_8), 5);
    }
}
