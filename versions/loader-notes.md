# Loader Notes

## Pins verified working on 2026-10-18 (built + booted dev servers)

| Component | Version |
|---|---|
| Minecraft | 26.1.2 (unobfuscated) |
| Java toolchain | 25 (JDK 25.0.2 Temurin, Gradle foojay resolver) |
| Gradle | 9.4.0 (wrapper) |
| Fabric Loader | 0.20.0 |
| Fabric API | 0.155.3+26.1.2 |
| Fabric Loom plugin | `net.fabricmc.fabric-loom` 1.16.3 |
| NeoForge | 26.1.2.122 |
| ModDevGradle plugin | `net.neoforged.moddev` 2.0.148 |
| Forgix plugin | `io.github.pacifistmc.forgix` 2.0.0 |

## Next-version pins (not built yet)

| MC | Fabric API | NeoForge |
|---|---|---|
| 26.2 (Chaos Cubed) | 0.161.0+26.2 | 26.2.0.93 |
| 26.3 (Wilderness Bound) | 0.163.0+26.3 | 26.3.0.89 (has moved past beta) |

## Facts proven by the first build

- One `-Pmc`-style pin change in `gradle.properties` is expected to port a
  loader build across MC versions; 26.x data/resource pack deltas live in the
  mod's own JSON, not the build.
- Loom on unobfuscated 26.x: no `mappings` line, plain `implementation` deps,
  `fabric.loom.disableObfuscation=true` set in root gradle.properties.
- NeoForge `javafml` loaderVersion is `[1,)` on 26.x (NOT the MC major).
- Forgix 2.0.0 config needs explicit `inputJar` (`tasks.jar.archiveFile`) per
  loader unless you also run shadowJar.
- `mergeJars` output: `build/forgix/prehistorica-<version>-fabric-neoforge.jar`.

## Commands

| Target | Command |
|---|---|
| lint/build everything | `gradlew build` |
| merged loader jar (fabric+neoforge) | `gradlew mergeJars` |
| Fabric dev server smoke test | `gradlew :fabric:runServer` |
| NeoForge dev server smoke test | `gradlew :neoforge:runServer` |
| multi-MC-version jar (later, M7) | `gradlew mergeVersions` (needs per-version builds) |
