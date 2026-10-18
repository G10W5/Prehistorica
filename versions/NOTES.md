# Per-Version Notes (Minecraft)

| MC version | Release name | Data pack | Resource pack | Java | Loader status |
|---|---|---|---|---|---|
| 26.1.2 | Tiny Takeover patch | 96 | 77 | 25 | NeoForge stable, Fabric stable |
| 26.2 | Chaos Cubed | 107.1 | 88.0 | 25 | NeoForge stable (26.2.0.x), Fabric stable |
| 26.3 | Wilderness Bound | 121.0 | 97.1 | 25 | NeoForge **beta** (26.3.0.x-beta), Fabric stable |

Key facts:

- 26.x is **unobfuscated**; no intermediary mapping step for Fabric, no official
  mappings workaround for NeoForge. One shared codebase against official vanilla
  names works on both loaders.
- MC 26.1 was the first unobfuscated release; GeckoLib's artifact layout changed
  around 5.5.x (`geckolib/models/` + `geckolib/animations/` folders).
- Architectury API: v20.x for 26.1, v21.x for 26.2, v22.x for 26.3.

## Build matrix commands (once gradle wiring lands in M0.x)

| Target | Command |
|---|---|
| universal jar (all loaders, `mc_min`..`mc_max` window) | `powershell -File build-universal.ps1` |
| build against a specific MC stack (no merge) | `gradlew :fabric:jar :neoforge:jar -Pmc_version=26.2` |

## How the universal jar works (verified 2026-10-18)

`build-universal.ps1` compiles `:fabric`/`:neoforge` against the `-Pmc_version`
stack (default 26.3) and runs Forgix `mergeJars`, producing a single
`build/forgix/prehistorica-<ver>-fabric-neoforge.jar` whose manifests use a WIDE
minecraft window (`fabric.mod.json` `"minecraft": ">=26.1.2 <26.4"`,
`neoforge.mods.toml` `versionRange="[26.1.2,26.4)"`).

Because the mod touches no MC API yet, one binary serves 26.1.2/26.2/26.3 on
both loaders. Verified by booting REAL launcher servers (mods/ folder install,
not dev runs) — mod loads and `2 species definitions loaded` printed on every
combo: Fabric+NeoForge x 26.1.2/26.2/26.3.

Requirements for this to stay true:

- `gradle.properties` `mc_min`/`mc_max` define the window; widen when adding MC
  versions (and re-verify).
- The moment the mod touches a MC class that differs between versions, switch
  to per-version builds (see below) — wide-range single-binary stops being safe.
- `Forgix mergeJars` does NOT rebuild subproject jars; always include
  `:fabric:jar :neoforge:jar` in the invocation (build-universal.ps1 does).

## Forgix multiversion (mergeVersions) — NOT used, upstream bug

 Forgix 2.0.0 `mergeVersions` produced a structurally correct multiversion jar
(per-version sub-jars + Fabric nested-jar bootstrap). Fabric side worked on all
three versions. NeoForge side fails: Forgix-Multiversion-Mod's
`NeoForgeMultiversionLocator` calls `FMLLoader.versionInfo()`
(https://github.com/PacifistMC/Forgix-Multiversion-Mod),
removed in FancyModLoader 12 (NeoForge 26.x) ->
`NoSuchMethodError`, mod silently not loaded (server still boots). Upstream
hasn't shipped a fix (last submodule commit 2026-08-02). If a fixed release
lands, the per-version pin map in `gradle.properties` (`pin.<mc>.fabricapi`,
`pin.<mc>.neoforge`) plus narrow `pin.<mc>.mc_range` sets are still present for
re-enabling that pipeline.

## Per-version overrides

When a version changes APIs or resource-pack format, place the divergence under
`versions/v<mc>/` instead of editing shared assets; the Forgix `mergeVersions`
stage picks the right resource folder per MC version at package time.
