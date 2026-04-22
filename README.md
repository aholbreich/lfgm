# Life Game

Conway's Game of Life — a JavaFX desktop application.

## Requirements

- Java 21+
- No other installation needed (Gradle wrapper included)

## Run

```bash
./gradlew run
```

## Controls

| Action | How |
|---|---|
| Toggle cell | Click or drag on the grid |
| Start / Stop simulation | **Start** / **Stop** button |
| Advance one generation | **Step** button |
| Clear the grid | **Reset** button |
| Change simulation speed | Speed slider (1–60 gen/s) |

## Project structure

```
src/main/java/org/holbreich/lfgm/
  Main.java              entry point
  GameApp.java           JavaFX window and toolbar
  model/GameField.java   Conway's Life logic (pure Java, no UI dependencies)
  ui/GameView.java       Canvas renderer and input handling
```

## Packaging

Creates a self-contained native installer with a bundled JRE — no Java required on the target machine.
Packaging is platform-specific: run the command on the OS you are targeting.

### Linux

```bash
./gradlew jpackage
```

Output: `build/dist/lfgm-1.0-1.x86_64.rpm` (Fedora/RHEL) or `lfgm_1.0_amd64.deb` (Debian/Ubuntu),
depending on which packaging tools are installed (`rpmbuild` or `dpkg`).

### Windows

Run on a Windows machine:

```
gradlew.bat jpackage
```

Output: `build\dist\lfgm-1.0.exe`

For `.msi` output, [WiX Toolset 3.x](https://wixtoolset.org/) must be installed and on the PATH.

---

## History

The original version was an Eclipse RCP plugin built with Maven Tycho (see `master` branch).
This branch rewrites the UI in JavaFX with a plain Gradle build.
