# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run

```bash
./gradlew build               # compile + package JAR
./gradlew clean build         # full rebuild
./gradlew runClient           # launch Minecraft client with mod
./gradlew runServer           # launch dedicated server
./gradlew runData             # run data generators (models, blockstates, recipes)
./gradlew runGameTestServer   # run automated game tests
./gradlew --refresh-dependencies  # re-download deps if corrupted
```

VSCode launch configs (`.vscode/launch.json`) wrap these with debugger support.

## Architecture

**Stack:** NeoForge 21.0.167 + Minecraft 1.21 + Java 21. Build via NeoForge ModDev Gradle plugin with Parchment mappings for readable deobfuscated names.

**Two event buses:**
- `MOD_BUS` (IEventBus injected into constructor) — mod lifecycle events (registration, setup)
- `NeoForge.EVENT_BUS` — game runtime events (server start, player events, etc.)

**Registration pattern:** Always use `DeferredRegister` — never call registries directly. Register the `DeferredRegister` to the mod bus in the main class constructor.

```java
// Pattern in BlockRegistry.java
public static final DeferredRegister.Blocks BLOCKS =
    DeferredRegister.createBlocks(MODID);

public static final DeferredBlock<Block> MY_BLOCK =
    BLOCKS.registerSimpleBlock("my_block", BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT));

// In CustomMod constructor:
BlockRegistry.BLOCKS.register(modEventBus);
```

**Config:** `Config.java` uses `ModConfigSpec` builder. Values are validated at load time. Config is registered in `CustomMod` constructor via `ModLoadingContext`.

**Data generation:** Add datagen providers in `runData` configuration. Generates to `src/generated/resources/` — include this path in `sourceSets` if used.

## Key Files

| File                                                       | Purpose                                                            |
|------------------------------------------------------------|--------------------------------------------------------------------|
| `src/main/java/org/ex_nihilo/custommod/CustomMod.java`     | Mod entry point, event bus wiring                                  |
| `src/main/java/org/ex_nihilo/custommod/BlockRegistry.java` | Block DeferredRegister definitions                                 |
| `src/main/java/org/ex_nihilo/custommod/Config.java`        | ModConfigSpec — typed, validated config                            |
| `src/main/resources/assets/custommod/lang/en_us.json`      | Localization strings                                               |
| `src/main/resources/META-INF/neoforge.mods.toml`           | Mod metadata, dependency declarations (template — built by Gradle) |
| `build.gradle`                                             | NeoForge version, Parchment mappings, run configs                  |
| `gradle.properties`                                        | Mod ID, version, MC/NeoForge versions                              |

## Mod Info

- **Mod ID:** `diirtmod`
- **Package:** `org.ex_nihilo.diirt`
- **Version:** 0.0.1
