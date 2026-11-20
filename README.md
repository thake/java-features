# Java 17 → 25 LTS Feature-Showcase

Dieses Repository demonstriert die wichtigsten neuen Java-Features zwischen den LTS-Versionen **Java 17 → Java 21 → Java 25** (inklusive der wichtigsten **JDK 25 Preview-Features**).

Jedes Feature besteht aus zwei kurzen, eigenständig ausführbaren Beispielen:
- **`Before.java`**: Typische Umsetzung *vor* Einführung des Features
- **`After.java`**: Idiomatische Umsetzung *mit* dem neuen Feature

Alle Code-Bezeichner sind auf Englisch, alle Kommentare auf Deutsch mit englischen Fachbegriffen.

---

## Wichtigste Features pro LTS-Version

- **[Java 17 → Java 21 LTS Highlights](docs/java-21-lts.md)** — Pattern Matching for `switch`, Record Patterns, Sequenced Collections, Virtual Threads und Core-Library-Verbesserungen
- **[Java 21 → Java 25 LTS Highlights](docs/java-25-lts.md)** — Unnamed Variables & Patterns, Stream Gatherers, Flexible Constructor Bodies, Scoped Values, Compact Source Files, Module Imports, Pin-free Virtual Threads, FFM API, KDF API sowie JDK 25 Preview-Ausblick

---

## Projektstruktur (Multi-Module Maven)

| Modul | Compiler-Target | Inhalt |
| :--- | :--- | :--- |
| [`java-17-to-21`](java-17-to-21/) | `--release 21` | Finalisierte Features zwischen Java 17 und Java 21 LTS |
| [`java-21-to-25`](java-21-to-25/) | `--release 25` | Finalisierte Features zwischen Java 21 und Java 25 LTS (ohne Preview-Flags) |
| [`java-25-compact-source`](java-25-compact-source/) | `--release 25` | JEP 512: Compact Source Files & Instance Main Methods (im Default Package) |
| [`java-25-preview`](java-25-preview/) | `--release 25 --enable-preview` | Die wichtigsten JDK 25 Preview-Features (isoliert vom GA-Code) |

---

## Bauen & Ausführen

### Voraussetzungen

- **JDK 25** und **Maven 3.9+** (über [`mise.toml`](mise.toml) bereits vorgekonfiguriert)

### Alle Beispiele kompilieren und per JUnit 5 verifizieren

```bash
mise exec -- mvn clean test
```

Die JUnit-5-Tests führen jedes einzelne `Before`- und `After`-Beispiel aus und prüfen, dass beide Varianten fehlerfrei laufen und fachlich konsistente Ergebnisse liefern.

### Einzelne Beispiele direkt starten

```bash
# Beispiel aus Java 17 -> 21
mise exec -- java -cp java-17-to-21/target/classes dev.javafeatures.jdk21.patternmatching.Before
mise exec -- java -cp java-17-to-21/target/classes dev.javafeatures.jdk21.patternmatching.After

# Beispiel aus Java 21 -> 25 (FFM API ohne Native-Access-Warnung)
mise exec -- java --enable-native-access=ALL-UNNAMED -cp java-21-to-25/target/classes dev.javafeatures.jdk25.ffmapi.After

# Compact Source File (JEP 512) direkt als Source-Datei ausfuehren
mise exec -- java java-25-compact-source/src/main/java/After.java

# Preview-Beispiel aus JDK 25
mise exec -- java --enable-preview -cp java-25-preview/target/classes dev.javafeatures.jdk25.preview.structuredconcurrency.After
```
