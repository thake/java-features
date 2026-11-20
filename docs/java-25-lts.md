# Die wichtigsten Features: Java 21 → Java 25 LTS

Diese Übersicht fasst die wichtigsten neuen Features auf dem Weg von **Java 21 LTS** zu **Java 25 LTS** zusammen und verlinkt direkt auf die ausführbaren **Before/After**-Beispiele in [`java-21-to-25`](../java-21-to-25/), [`java-25-compact-source`](../java-25-compact-source/) und [`java-25-preview`](../java-25-preview/).

---

## Schnellübersicht: Finalisierte Features (GA, ohne Preview-Flags)

| Feature | JEP | JDK | Before | After |
| :--- | :---: | :---: | :--- | :--- |
| **Unnamed Variables & Patterns** | JEP 456 | 22 | [Before.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/unnamedvariables/Before.java) | [After.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/unnamedvariables/After.java) |
| **Foreign Function & Memory (FFM) API** | JEP 454 | 22 | [Before.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/ffmapi/Before.java) | [After.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/ffmapi/After.java) |
| **Markdown Documentation Comments** | JEP 467 | 23 | [Before.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/markdowndoc/Before.java) | [After.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/markdowndoc/After.java) |
| **Stream Gatherers** | JEP 485 | 24 | [Before.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/streamgatherers/Before.java) | [After.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/streamgatherers/After.java) |
| **Synchronize Virtual Threads without Pinning** | JEP 491 | 24 | [Before.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/virtualthreadspinfree/Before.java) | [After.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/virtualthreadspinfree/After.java) |
| **Scoped Values** | JEP 506 | 25 | [Before.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/scopedvalues/Before.java) | [After.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/scopedvalues/After.java) |
| **Key Derivation Function (KDF) API** | JEP 510 | 25 | [Before.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/kdfapi/Before.java) | [After.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/kdfapi/After.java) |
| **Module Import Declarations** | JEP 511 | 25 | [Before.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/moduleimports/Before.java) | [After.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/moduleimports/After.java) |
| **Compact Source Files & Instance Main Methods** | JEP 512 | 25 | [Before.java](../java-25-compact-source/src/main/java/Before.java) | [After.java](../java-25-compact-source/src/main/java/After.java) |
| **Flexible Constructor Bodies** | JEP 513 | 25 | [Before.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/flexibleconstructors/Before.java) | [After.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/flexibleconstructors/After.java) |

---

## Finalisierte Highlights im Detail

### 1. Unnamed Variables & Patterns (JEP 456, JDK 22)
Der Underscore `_` kennzeichnet bewusst ungenutzte Variablen in `catch`-Blöcken, Lambdas, Schleifen und Record Patterns — für klarere Intention und weniger Compiler-/Linter-Warnungen.
- **Beispiele:** [Before.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/unnamedvariables/Before.java) → [After.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/unnamedvariables/After.java)

### 2. Stream Gatherers (JEP 485, JDK 24)
Erweitert die Stream-API endlich um benutzerdefinierte Intermediate Operations (`Stream::gather`) samt praktischer Built-in Gatherers wie `windowFixed`, `windowSliding` und `scan`.
- **Beispiele:** [Before.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/streamgatherers/Before.java) → [After.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/streamgatherers/After.java)

### 3. Synchronize Virtual Threads without Pinning (JEP 491, JDK 24)
Behebt die größte Einschränkung von Virtual Threads aus Java 21: Blockierende Aufrufe innerhalb von `synchronized` pinnen den Carrier Thread nicht mehr — der `ReentrantLock`-Workaround entfällt.
- **Beispiele:** [Before.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/virtualthreadspinfree/Before.java) → [After.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/virtualthreadspinfree/After.java)

### 4. Scoped Values (JEP 506, JDK 25)
Der moderne, unveränderliche (immutable) und automatisch bereinigte Nachfolger von `ThreadLocal` — ideal für Request-Kontexte in Kombination mit Virtual Threads.
- **Beispiele:** [Before.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/scopedvalues/Before.java) → [After.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/scopedvalues/After.java)

### 5. Flexible Constructor Bodies (JEP 513, JDK 25)
Erlaubt Validierung, Vorberechnung und Initialisierung eigener Felder direkt im Konstruktor *vor* dem Aufruf von `super(...)` oder `this(...)` — ohne künstliche `private static`-Hilfsmethoden.
- **Beispiele:** [Before.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/flexibleconstructors/Before.java) → [After.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/flexibleconstructors/After.java)

### 6. Compact Source Files & Instance Main Methods (JEP 512, JDK 25)
Reduziert den Einstiegs-Boilerplate drastisch: kleine Skripte und Prototypen laufen ohne `class`-Deklaration, mit `void main()`, automatischem `java.base`-Import und `IO.println(...)`.
- **Beispiele:** [Before.java](../java-25-compact-source/src/main/java/Before.java) → [After.java](../java-25-compact-source/src/main/java/After.java)

### 7. Module Import Declarations (JEP 511, JDK 25)
Mit `import module java.base;` lassen sich alle exportierten Packages eines Moduls mit einer einzigen Zeile on-demand importieren.
- **Beispiele:** [Before.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/moduleimports/Before.java) → [After.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/moduleimports/After.java)

### 8. Markdown Documentation Comments (JEP 467, JDK 23)
`///`-Kommentare ersetzen HTML-Tags und `{@code}`/`{@link}`-Boilerplate in JavaDoc durch direkt lesbares Markdown.
- **Beispiele:** [Before.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/markdowndoc/Before.java) → [After.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/markdowndoc/After.java)

### 9. Foreign Function & Memory (FFM) API (JEP 454, JDK 22)
Typsichere, deterministische Off-Heap-Speicherverwaltung (`Arena`) und direkter Aufruf nativer C-Funktionen (`Linker`) aus reinem Java ohne JNI.
- **Beispiele:** [Before.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/ffmapi/Before.java) → [After.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/ffmapi/After.java)

### 10. Key Derivation Function (KDF) API (JEP 510, JDK 25)
Standardisierte Krypto-API (`javax.crypto.KDF`) für HKDF-SHA256 statt fehleranfälliger Eigenimplementierungen auf Basis von `javax.crypto.Mac`.
- **Beispiele:** [Before.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/kdfapi/Before.java) → [After.java](../java-21-to-25/src/main/java/dev/javafeatures/jdk25/kdfapi/After.java)

---

## Ausblick: Wichtigste JDK 25 Preview-Features (`--enable-preview`)

Diese Features befinden sich in Java 25 noch im **Preview**-Status und sind im separaten Modul [`java-25-preview`](../java-25-preview/) isoliert:

| Feature | JEP | Status in JDK 25 | Before | After |
| :--- | :---: | :---: | :--- | :--- |
| **Structured Concurrency** | JEP 505 | 5th Preview | [Before.java](../java-25-preview/src/main/java/dev/javafeatures/jdk25/preview/structuredconcurrency/Before.java) | [After.java](../java-25-preview/src/main/java/dev/javafeatures/jdk25/preview/structuredconcurrency/After.java) |
| **Primitive Types in Patterns, `instanceof`, and `switch`** | JEP 507 | 3rd Preview | [Before.java](../java-25-preview/src/main/java/dev/javafeatures/jdk25/preview/primitivepatterns/Before.java) | [After.java](../java-25-preview/src/main/java/dev/javafeatures/jdk25/preview/primitivepatterns/After.java) |
| **Stable Values** | JEP 502 | Preview | [Before.java](../java-25-preview/src/main/java/dev/javafeatures/jdk25/preview/stablevalues/Before.java) | [After.java](../java-25-preview/src/main/java/dev/javafeatures/jdk25/preview/stablevalues/After.java) |

- **Structured Concurrency (JEP 505):** Bindet parallele Subtasks über `StructuredTaskScope.open()` strikt an einen `try-with-resources`-Block — inkl. automatischer Cancellation von Geschwister-Tasks bei Fehlern.
- **Primitive Types in Patterns (JEP 507):** Ermöglicht `switch` direkt auf `long`/`double`/`boolean` sowie garantiert verlustfreie `instanceof`-Checks (z. B. `value instanceof byte exact`).
- **Stable Values (JEP 502):** Thread-safe Lazy Initialization ohne Double-Checked-Locking-Boilerplate, inklusive JVM Constant Folding nach der einmaligen Initialisierung.

---

Zurück zu den **[Java 17 → Java 21 LTS Highlights](java-21-lts.md)** oder zur **[README](../README.md)**.
