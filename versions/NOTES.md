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
| 26.1.2 both loaders | `gradlew build -Pmc=26.1` |
| 26.2 both loaders | `gradlew build -Pmc=26.2` |
| 26.3 both loaders | `gradlew build -Pmc=26.3` |
| merged multi-version jar | `gradlew mergeVersions` |

## Per-version overrides

When a version changes APIs or resource-pack format, place the divergence under
`versions/v<mc>/` instead of editing shared assets; the Forgix `mergeVersions`
stage picks the right resource folder per MC version at package time.
