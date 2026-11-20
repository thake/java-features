package dev.javafeatures.jdk25.ffmapi;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

// Vorher (Java 21): Fuer Off-Heap Memory nutzte man ByteBuffer.allocateDirect(...)
// ohne deterministische Freigabe (erst der Garbage Collector raeumt den Speicher auf).
// Fuer echte Aufrufe nativer C-Funktionen (wie strlen) waere zusaetzlich JNI mit
// nativem C-Glue-Code, Header-Generierung und plattformabhaengiger Shared Library noetig gewesen.
public class Before {

    // Simuliert das Auslesen einer Null-terminierten C-Zeichenkette aus einem Direct ByteBuffer,
    // weil ein direkter Aufruf von C-strlen ohne JNI-Wrapper nicht moeglich war.
    static long measureNullTerminatedString(ByteBuffer directBuffer) {
        long length = 0;
        while (directBuffer.get((int) length) != 0) {
            length++;
        }
        return length;
    }

    public static void main(String[] args) {
        byte[] bytes = "Hello FFM API!".getBytes(StandardCharsets.UTF_8);

        // Off-Heap-Allokation: kein try-with-resources, Freigabe erfolgt erst irgendwann per GC
        ByteBuffer offHeapBuffer = ByteBuffer.allocateDirect(bytes.length + 1);
        offHeapBuffer.put(bytes);
        offHeapBuffer.put((byte) 0);
        offHeapBuffer.flip();

        long length = measureNullTerminatedString(offHeapBuffer);
        System.out.println("Off-heap C-string length: " + length);
    }
}
