package dev.javafeatures.jdk25.kdfapi;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HexFormat;

// Vorher (Java 21): Es gab keine Standard-API fuer Key Derivation Functions (KDF) wie HKDF.
// Entwickler mussten HKDF (RFC 5869: Extract- und Expand-Phase) manuell mit javax.crypto.Mac
// implementieren oder externe Crypto-Libraries einbinden — fehleranfaelliges Low-Level-Boilerplate.
public class Before {

    private static final String HMAC_SHA256 = "HmacSHA256";

    static byte[] hkdfSha256(byte[] ikm, byte[] salt, byte[] info, int outputLength) throws Exception {
        // 1. Extract-Phase: PRK = HMAC-Hash(salt, IKM)
        Mac extractMac = Mac.getInstance(HMAC_SHA256);
        extractMac.init(new SecretKeySpec(salt, HMAC_SHA256));
        byte[] prk = extractMac.doFinal(ikm);

        // 2. Expand-Phase: T(i) = HMAC-Hash(PRK, T(i-1) | info | counter)
        Mac expandMac = Mac.getInstance(HMAC_SHA256);
        expandMac.init(new SecretKeySpec(prk, HMAC_SHA256));

        byte[] result = new byte[outputLength];
        byte[] previousBlock = new byte[0];
        int offset = 0;
        byte counter = 1;

        while (offset < outputLength) {
            expandMac.reset();
            expandMac.update(previousBlock);
            expandMac.update(info);
            expandMac.update(counter++);
            previousBlock = expandMac.doFinal();

            int bytesToCopy = Math.min(previousBlock.length, outputLength - offset);
            System.arraycopy(previousBlock, 0, result, offset, bytesToCopy);
            offset += bytesToCopy;
        }
        return Arrays.copyOf(result, outputLength);
    }

    public static void main(String[] args) throws Exception {
        byte[] initialKeyMaterial = "shared-master-secret".getBytes(StandardCharsets.UTF_8);
        byte[] salt = "random-salt-2025".getBytes(StandardCharsets.UTF_8);
        byte[] info = "aes-256-session-key".getBytes(StandardCharsets.UTF_8);

        byte[] derivedKey = hkdfSha256(initialKeyMaterial, salt, info, 32);
        System.out.println("Derived AES-256 key: " + HexFormat.of().formatHex(derivedKey));
    }
}
