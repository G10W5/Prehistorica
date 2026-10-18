# Entity Module Plan (M1)

This package is intentionally empty of game code until M1. When wiring lands in
order, the plan is:

1. `DinoEntity extends AgeableMob` — base class for ALL species; driven entirely
   by `SpeciesDefinition` (health/hitbox/scale/diet/temperament).
2. Synced data keys: `DATA_GENDER`, `DATA_GROWTH_STAGE`, `DATA_HUNGER`,
   `DATA_STRESS`.
3. One `EntityType<DinoEntity>` per species is built from
   `SpeciesRegistry.all()` inside each loader module's registrar
   (IDs live under `prehistorica:` prefix), then registered into
   `Registries.ENTITY_TYPE` / NeoForge DeferredRegister.
4. Client renderers: one GeckoLib renderer class parameterized by species.
   GeckoLib geo/animation JSON files are stored per species under
   `assets/prehistorica/geckolib/models/` + `assets/prehistorica/geckolib/animations/`.
5. No per-species AI classes: goals are enabled/disabled from
   `SpeciesDefinition.diet()` / `.temperament()` / pack settings.
