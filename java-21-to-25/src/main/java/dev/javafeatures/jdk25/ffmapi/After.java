package dev.javafeatures.jdk25.ffmapi;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

// Nachher (Java 22, JEP 454): Die Foreign Function & Memory (FFM) API ermoeglicht
// typsichere, deterministische Off-Heap-Speicherverwaltung per Arena und direkte
// Aufrufe nativer C-Funktionen aus reinem Java — ganz ohne JNI-Glue-Code.
// Hinweis beim direkten Ausfuehren: java --enable-native-access=ALL-UNNAMED ...
public class After {

    public static void main(String[] args) throws Throwable {
        // 1. Standard-C-Library ueber den Native Linker nachschlagen
        Linker linker = Linker.nativeLinker();
        SymbolLookup stdlib = linker.defaultLookup();

        // 2. Downcall MethodHandle fuer die C-Funktion `size_t strlen(const char *s)` erzeugen
        MemorySegment strlenAddress = stdlib.find("strlen").orElseThrow();
        FunctionDescriptor descriptor = FunctionDescriptor.of(
                ValueLayout.JAVA_LONG,
                ValueLayout.ADDRESS
        );
        MethodHandle strlen = linker.downcallHandle(strlenAddress, descriptor);

        // 3. Confined Arena verwaltet den Off-Heap-Speicher deterministisch per try-with-resources
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment cString = arena.allocateFrom("Hello FFM API!");
            long length = (long) strlen.invokeExact(cString);
            System.out.println("Off-heap C-string length: " + length);
        }
        // Hier ist der Off-Heap-Speicher garantiert sofort wieder freigegeben
    }
}
