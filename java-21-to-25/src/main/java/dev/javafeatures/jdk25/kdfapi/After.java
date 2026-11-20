package dev.javafeatures.jdk25.kdfapi;

import javax.crypto.KDF;
import javax.crypto.SecretKey;
import javax.crypto.spec.HKDFParameterSpec;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

// Nachher (Java 25, JEP 510): Die neue Standard-KDF-API (javax.crypto.KDF) bietet
// eine sichere, deklarative Implementierung fuer HKDF-SHA256 — ohne manuelle Mac-Schleifen.
public class After {

    public static void main(String[] args) throws Exception {
        byte[] initialKeyMaterial = "shared-master-secret".getBytes(StandardCharsets.UTF_8);
        byte[] salt = "random-salt-2025".getBytes(StandardCharsets.UTF_8);
        byte[] info = "aes-256-session-key".getBytes(StandardCharsets.UTF_8);

        // Deklarative Extract-and-Expand-Spezifikation ueber die Standard-KDF-API
        KDF hkdf = KDF.getInstance("HKDF-SHA256");
        var params = HKDFParameterSpec.ofExtract()
                .addIKM(initialKeyMaterial)
                .addSalt(salt)
                .thenExpand(info, 32);

        SecretKey derivedKey = hkdf.deriveKey("AES", params);
        System.out.println("Derived AES-256 key: " + HexFormat.of().formatHex(derivedKey.getEncoded()));
    }
}
