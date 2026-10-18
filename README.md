# Prehistorica

A dinosaur revival & park-building mod for Minecraft Java **26.1.2 / 26.2 / 26.3**,
targeting **Fabric** and **NeoForge** from one codebase, published as a single
multi-version jar via Forgix.

Read [`DESIGN.md`](DESIGN.md) for the full design (species list, progression
loop, machines, data-driven species JSON, roadmap).

## Project layout

| Path | Contents |
|---|---|
| `common/` | All gameplay code + assets (entities, species JSON registry, machines, blocks/items) |
| `fabric/` | Fabric entrypoint module (Fabric Loom) |
| `neoforge/` | NeoForge entrypoint module (ModDevGradle) |
| `versions/` | Per-MC-version notes & overrides |
| `gradle/libs.versions.toml` | Version catalog (loader/toolchain pins) |

## Status

M0 milestone **complete** (2026-10-18): both loader modules build against real
MC 26.1.2 (Fabric via Loom 1.16.3, NeoForge via ModDevGradle 2.0.148), Forgix
merges them into one universal jar, and dev servers boot with the mod loaded
on both loaders. Next: M0.5 resource/block registration, then the Gallimimus
reference species (M1) — see `DESIGN.md` §10 Roadmap.

Build the universal jar:

```
gradlew mergeJars            # output: build/forgix/prehistorica-0.1.0-fabric-neoforge.jar
```

Verified pins and loader facts are tracked in `versions/loader-notes.md`.

## Requirements to build

- Git
- Java 21+ to run Gradle (a Java 25 toolchain is used for compilation — Gradle's
  foojay resolver downloads it automatically)
- Gradle wrapper (fetch with `gradle wrapper --gradle-version 9.4.0` or use the
  committed wrapper scripts once added)

## Loaders / versions supported at M0

| Minecraft | NeoForge | Fabric | Status |
|---|---|---|---|
| 26.1.2 | primary target | primary target | skeleton |
| 26.2 | secondary | secondary | planned (M7) |
| 26.3 | beta (loader `26.3.0.x-beta`) | supported | planned (M7) |

## Runtime dependencies (both loaders)

- Architectury API — v20.1.17 (26.1.2), v21.x (26.2), v22.x (26.3)
- GeckoLib 5.5.x on both loaders for all three MC versions
