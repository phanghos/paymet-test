package org.taitasciore.android.auth;

import org.taitasciore.android.network.MarvelApi;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by roberto on 10/05/17.
 * Helper class used for MD5-encryption necessary for authentication on the Marvel API
 */

public final class AuthUtils {

    /**
     * Encrypts a string using md5
     * @param str String to encrypt
     * @return Encrypted string
     */
    private static String md5(String str) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(str.getBytes());
            final byte[] bytes = digest.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) sb.append(String.format("%02X", bytes[i]));
            return sb.toString().toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Generates hash required by the Marvel API, which must be of the form
     * md5(timestamp + private key + public key).
     * @return Encrypted string that will be sent as a query parameter in the HTTP request
     */
    public static AuthData generateHash() {
        AuthData authData = new AuthData();
        String hash = md5(authData.getTimestamp() + MarvelApi.API_PRIVATE_KEY +
                MarvelApi.API_PUBLIC_KEY);
        authData.setHash(hash);
        return authData;
    }
}
