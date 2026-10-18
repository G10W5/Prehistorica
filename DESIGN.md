# Prehistorica — Design Document

A dinosaur revival & park-building mod for Minecraft Java 26.1.2 / 26.2 / 26.3.
Targets both **Fabric** and **NeoForge** via a shared common-code multi-loader setup, merged into a single jar with Forgix.

> Status: Outline skeleton. This doc is the single source of truth for scope and rules.
> Note: The "fossil → DNA → creature" loop is the genre-standard formula players know;
> it is a community template, not a copy of any specific mod. All names, textures,
> code, and tuning here are original.

---

## 1. Core Fantasy

You are a field paleontologist turned park warden.
The world contains **fossil dig sites** and buried **amber deposits**.
You excavate, sequence, incubate, hatch, raise, and contain living dinosaurs,
then build a park around them.

The gameplay pillar is **risk management**: what keeps a T. rex happy and fed
is the same skill that keeps it from eating your guests.

---

## 2. Progression Loop (7 stages)

| # | Stage | Player action | Key items |
|---|-------|---------------|-----------|
| 1 | Excavate | Find dig sites / underground fossil deposits; brush blocks open | `Brush`, fossiliferous dirt/sand/gravel |
| 2 | Authenticate | Clean bones at the Fossil Bench; get species chips + sediment | Fossil Bench, Bone Chips, Sediment Sample |
| 3 | Analyze | Process sediment/amber into DNA vials | Genetic Lab (block), Amber, DNA Vial |
| 4 | Sequence | Merge duplicate low-quality vials toward 100% purity | DNA Vial, Bio-Splicer |
| 5 | Gestate | Combine pure DNA + host egg + nutrients → fertilized egg → incubate | Host Egg, Incubator, Nutrient Paste |
| 6 | Hatch & Tame | Baby hatches; hand-feed it while small to imprint | Hatchling, favorite food |
| 7 | Park | Build enclosures, feed, breed, expand roster | Fences, Trough, Gate |

**Simplification vs genre:** no energy/RF networks. The four machines use
**fuel-style item consumption** (e.g., "Biotech Fuel" crafted from organic paste)
plus a "working tick" timer, like a furnace. This eliminates dependencies on
external tech APIs and keeps the first release focused.

---

## 3. Species Roster (v1 — 14 species)

Rules for choosing: iconic, gameplay-distinct, and castable with 3 growth stages
in vanilla-style model complexity (no feather-systems in v1).

| Species | Class | Diet | Temperament | Unique hook |
|---|---|---|---|---|
| Gallimimus   | Small herbivore | Plants | Skittish | Flocking — moves as a group, "scatter" panic |
| Dryosaurus   | Small herbivore | Plants | Docile | First species players hatch |
| Psittacosaurus | Small herbivore | Plants | Docile | Loves to rest under a Heat Lamp |
| Stegosaurus  | Mid herbivore   | Plants | Neutral    | Tail sweep AoE when agitated |
| Ankylosaurus | Mid herbivore   | Plants | Neutral    | Club swing — hard counter to raptors |
| Parasaurolophus | Mid herbivore | Plants | Skittish | Alarm call warns *all* dinos in 48 blocks |
| Triceratops  | Large herbivore | Plants | Territorial | Charge attack — breaks weak fences |
| Brachiosaurus | Large herbivore | Plants | Docile  | Highest feeding-trough consumption; needs huge enclosure |
| Velociraptor | Pack carnivore | Meat   | Aggressive | Pack coordination (3+ = organized hunts) |
| Deinonychus  | Pack carnivore | Meat   | Aggressive | Can leap 3 blocks — needs tall fences |
| Dilophosaurus| Lone carnivore  | Meat / fish | Aggressive | Ranged venom spit (blinds 3s) |
| Carnotaurus  | Lone carnivore  | Meat   | Aggressive | Fastest land carnivore; charges |
| Tyrannosaurus| Apex carnivore  | Meat   | Territorial | Escapes when stressed; breaks most fences |
| Spinosaurus  | Apex semi-aquatic | Fish/meat | Territorial | Comfortable in water; hunts in rivers |

v2 candidates (explicitly out of scope): Mosasaurus, Pteranodon, Compsognathus,
more hadrosaurs, hybrids, genetic modifier system, fishing cooperation.

---

## 4. Entity Systems (all dinos share one base class)

`DinoEntity` (common code) provides:

- **Gender** — male/female at hatch (1:1), slight texture tint delta
- **Growth stages** — `HATCHLING → JUVENILE → ADULT` w/ scale interpolation (hatchling 0.35× → adult 1.0×)
- **Hunger** — depletes over time; feeding restores; starving dinos take starvation damage
- **Stress meter** — rises on damage, crowding, hunger, predator proximity; falls when fed, spacious, with enrichment
- **Temperament class** — Docile / Neutral / Skittish / Aggressive / Territorial (per-species field)
- **Diet** — plants / meat / fish / mixed (drives AI and feeding interactions)
- **Pack AI** — optional per species; `PackCoordinator` in common AI
- **Breeding** — same-species male+female, well-fed (>=60% food), low stress, cooldown ~5min; parent DNA quality influences the child's traits
- **DNA quality inheritance** — average of parents filtered slightly upward or downward
- **Imprint** — hatchling hand-fed 3× by same player → that player is "imprinted" (never attacked, follows leash, defends owner if pack species)

Hand-tool interactions (right-click):
- **Feed** — eligible food → hunger restore + stress drop
- **Heal** — "Vet Kit" item → clears stress/injuries
- **Leash** — vanilla leads work on all stages except adult apexes (T. rex / Spinosaurus need the Reinforced Lead)

---

## 5. Machines

All machines are furnace-like: put input + fuel, wait, get output.
No energy network. Each has its own block entity + menu + screen (shared).

| Machine | Input | Process | Output |
|---|---|---|---|
| **Fossil Bench** | Fossil bones/chips | cleaning (40s) | Species Bone Chips + Sediment Sample |
| **Genetic Lab** | Sediment / Amber | analysis (80s) | random species DNA vial (30–70% quality) |
| **Bio-Splicer** | 2–4 matching DNA vials | sequencing (60s) | one higher-quality vial (average + 15%) |
| **Incubator** | Fertilized egg + heat fuel | gestation (per species 4–12 min) | hatchling spawn |

Per-species settings in `species.json`: gestation time, egg size class, heat level.

**Difficulty knobs (config):**
- `machineTimings` — multiplier for all four machines
- `stressEffects` — bool; off = dinos never escape
- `containmentBreaking` — bool; off = apexes cannot break blocks
- `naturalSpawning` — off by default (fossil loop is the intended path)

---

## 6. Blocks & Items

**Blocks (~20)**

| Block | Purpose |
|---|---|
| Fossiliferous Dirt/Sand/Gravel (3) | brushable deposits; drop fossil bones |
| Deepslate Fossil Deposit | rare deep variant (better fossils) |
| Fossil Bench | stage 2 cleaning |
| Genetic Lab | stage 3 analysis |
| Bio-Splicer | stage 4 sequencing |
| Incubator | stage 5 gestation |
| Feeding Trough | auto-feeds dinos in range; needs filling |
| Reinforced Fence (wood/iron) | stronger than vanilla fence |
| Electric Fence Post + Wire | apex-grade containment |
| Padded Glass / Viewing Dome | park viewing without stress spikes |
| Heat Lamp | placed near Incubator to boost gestation |
| Amber Block | decorative, drops amber chunks |
| Fossil Display Pedestal | shows a fossil piece for decoration / journal unlock |

**Items (~28)**
- `Bone Chip` per species (14) — fossil currency for crafting + DNA source
- `Sediment Sample`, `Amber` — DNA carrier items
- `Empty Vial`, `DNA Vial` (per species, stored quality in data component)
- `Host Egg` (small / medium / large)
- `Fertilized Egg` (per species, stored quality)
- `Nutrient Paste`, `Bio-Tech Fuel` consumables
- `Vet Kit`, `Reinforced Lead` (apex-safe leash), `Field Journal` (compendium item)
- Favorite food items (meat, fish, berries, leaves, feed mix)

---

## 7. Worldgen

- **Dig Site** structure (overworld surface): tent camp, supply chests, brushable fossil patches; ~1 per 48×48 chunks
- **Underground fossil deposits** in dirt/sand/gravel layers, y in [-32, 48]; deepslate variant below y=-20
- **Amber deposits** in badlands + jungle surface caves
- Natural dino spawning: **off by default** — opt-in via config for players who prefer wild encounters; the fossil loop is the intended path.
- Dinos do NOT despawn once hatched (persistent).

---

## 8. Data-Driven Species Pipeline

Every species is defined by a JSON spec at `raw_data/species/<id>/species.json`,
parsed at startup into a `SpeciesRegistry`. All 14 species share **one** entity
class hierarchy — no per-species AI classes.

`species.json` fields (draft schema):
```json
{
  "id": "tyrannosaurus",
  "class": "carnivore",
  "diet": "meat",
  "temperament": "territorial",
  "maxHealth": [60, 120],
  "damage": [8, 16],
  "speed": 0.30,
  "scale": 3.2,
  "hitbox": [3.5, 3.0],
  "hitboxJuvenile": [1.2, 1.0],
  "growth": { "hatchling_ticks": 24000, "juvenile_ticks": 48000 },
  "gestation_ticks": 7200,
  "egg_size": "large",
  "heat_level": 2,
  "pack_size": [1, 3],
  "habitat": { "biomes_tag": "prehistorica:dino_habitat_plains", "prefers_water": false },
  "food": { "items_tag": "prehistorica:meat_items", "hunger_per_item": 6, "stress_drop": 3 },
  "breaks_blocks": true,
  "pack": false,
  "imprintable": true,
  "sounds": { "idle": "prehistorica:entity.trex.idle", ... }
}
```

Same folder holds `model.geo.json` (GeckoLib geometry), `animations.json`,
`texture.png`, `texture_baby.png`. Species loading is **fail-fast**: missing
mesh/animations for a listed species = startup error naming the file.

At skeleton stage only the index has 2 species (`gallimimus`, `tyrannosaurus`)
in `raw_data/species/`; the other 12 species are added as their JSON+assets
land (M1/M5). Adding a species = append id to `index.json` + add its JSON.

---

## 9. Multi-Loader Architecture

```
Prehistorica/
├── DESIGN.md / README.md   # this doc + quickstart
├── common/                 # all gameplay code + assets
│   └── src/main/
│       ├── java/com/prehistorica/
│       │   ├── Prehistorica.java             # init entrypoint (called by both loaders)
│       │   ├── platform/                  # loader abstraction (registrars, helpers)
│       │   ├── registry/                # ModItems, ModBlocks, ModEntities, ModSounds, ModMenus
│       │   ├── species/                 # SpeciesRegistry, SpeciesSpec, codec
│       │   ├── entity/                  # DinoEntity + AI + goals + data components
│       │   ├── machine/                 # MachineBlockEntity base + per-machine logic
│       │   └── genetic/                 # DNA quality encoding, splicer math
│       └── resources/assets/prehistorica/    # textures, models, lang, geo, animations
├── fabric/
│   └── src/main/java/com/prehistorica/fabric/ # entrypoint + registrar impls
│   └── fabric.mod.json
├── neoforge/
│   └── src/main/java/com/prehistorica/neoforge/ # entrypoint + registrar impls
│   └── META-INF/neoforge.mods.toml
├── versions/               # per-MC-version overrides (pack formats, tweaks)
│   ├── v26.1/
│   ├── v26.2/
│   └── v26.3/
├── gradle/libs.versions.toml
├── build.gradle            # Forgix merge config
├── loader-notes.md         # version notes per loader/version
```

**Loader abstraction** uses a small hand-rolled `Platform` facade with
`@ExpectPlatform`-style static methods (via Architectury runtime dependency) —
registries, networking, config injection all hide behind it.

**Dependencies (both loaders):**
- Architectury API (v20.x for 26.1.2, v21.x for 26.2, v22.x for 26.3)
- GeckoLib 5.5.x (all three versions)
- (optional, later) YACL for config UI — not in v1

**Forgix**: merges `fabric/build/libs/*.jar` + `neoforge/build/libs/*.jar`
into `build/forgix/prehistorica-<version>.jar`, then `mergeVersions` across v26.1.2,
v26.2, v26.3 build products into a single `prehistorica-<modver>-multi.jar`.

---

## 10. Roadmap

| Milestone | Scope | Est. effort |
|---|---|---|
| **M0** | Multi-loader skeleton builds & runs on 26.1.2 (both loaders) | 1–2 days |
| **M1** | One species (Gallimimus) with GeckoLib model, animations, spawn rules, no AI beyond wander | 3–5 days |
| **M2** | Fossil blocks + brush interaction + Fossil Bench cleaning | 2–3 days |
| **M3** | Genetic Lab, DNA vials w/ quality, Bio-Splicer, Host Egg + Incubator + fertilized egg → hatch | 4–6 days |
| **M4** | Hunger, stress, growth stages, taming/imprint, breeding | 4–6 days |
| **M5** | Full 14-species roster (models/textures/animations) | 4–8 weeks (art heavy) |
| **M6** | Park blocks (fences, troughs, viewing glass), advancements, journal | 1–2 weeks |
| **M7** | 26.2/26.3 builds, Forgix multi-version jar, Modrinth/CurseForge publish | 1 week |

## 11. Risks

- **GeckoLib API churn** — pin 5.5.x; wrap behind `DinoAnimations` helper so a future 5.x→6.x bump is one class.
- **NeoForge 26.3 beta** — treat 26.3 as best-effort; ship 26.1.2 + 26.2 as the stable pair.
- **Per-version pack format drift** — data-driven JSON avoids hardcoding; keep `versions/v<mc>/overrides` for divergences.
- **Art bottleneck** — 14 species × 3 growth stages of GeckoLib geo/animations is the real cost; the roster can ship in waves (Gallimimus, Dryosaurus, Velociraptor, Triceratops first).
- **Stress/containment edge cases** — prototype early in M4 with a test pen; escape logic is the most bug-prone system in this genre.
