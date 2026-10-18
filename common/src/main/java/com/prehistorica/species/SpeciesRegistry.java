package com.prehistorica.species;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class SpeciesRegistry {
    private static final String INDEX_PATH = "raw_data/species/index.json";
    private static final String SPECIES_DIR = "raw_data/species/";

    private static final Map<String, SpeciesDefinition> SPECIES = new LinkedHashMap<>();

    private SpeciesRegistry() {
    }

    public static void load() {
        SPECIES.clear();
        JsonObject index = readJson(INDEX_PATH);
        List<String> ids = parseIdList(index);

        for (String id : ids) {
            JsonObject json;
            try {
                json = readJson(SPECIES_DIR + id + ".json");
            } catch (RuntimeException e) {
                throw new IllegalStateException("Failed to load species file: " + SPECIES_DIR + id + ".json", e);
            }
            SpeciesDefinition definition = parseSpecies(id, json);
            SPECIES.put(definition.id(), definition);
        }
    }

    public static Collection<SpeciesDefinition> all() {
        return Collections.unmodifiableCollection(SPECIES.values());
    }

    public static SpeciesDefinition byId(String id) {
        return SPECIES.get(id);
    }

    public static int count() {
        return SPECIES.size();
    }

    private static List<String> parseIdList(JsonObject index) {
        List<String> ids = new java.util.ArrayList<>();
        index.getAsJsonArray("species").forEach(e -> ids.add(e.getAsString()));
        return ids;
    }

    private static SpeciesDefinition parseSpecies(String expectedId, JsonObject json) {
        String id = requiredString(json, "id");
        if (!id.equals(expectedId)) {
            throw new IllegalStateException("Species file path/id mismatch: expected '" + expectedId + "' but got '" + id + "'");
        }

        JsonObject growth = json.getAsJsonObject("growth");
        float[] hitbox = parseVec2(json.getAsJsonArray("hitbox"));

        return new SpeciesDefinition(
                id,
                requiredString(json, "name"),
                Diet.valueOf(json.get("diet").getAsString().toUpperCase()),
                Temperament.valueOf(json.get("temperament").getAsString().toUpperCase()),
                json.get("maxHealth").getAsFloat(),
                json.get("damage").getAsFloat(),
                json.get("speed").getAsFloat(),
                json.get("scale").getAsFloat(),
                hitbox[0],
                hitbox[1],
                growth.get("hatchling_ticks").getAsInt(),
                growth.get("juvenile_ticks").getAsInt(),
                growth.get("gestation_ticks").getAsInt()
        );
    }

    private static String requiredString(JsonObject json, String key) {
        var element = json.get(key);
        if (element == null || element.isJsonNull()) {
            throw new IllegalStateException("Missing required species field: '" + key + "' in: " + json);
        }
        return element.getAsString();
    }

    private static float[] parseVec2(com.google.gson.JsonArray array) {
        if (array == null || array.size() != 2) {
            throw new IllegalStateException("Expected JSON array with exactly 2 numbers, got: " + array);
        }
        return new float[] { array.get(0).getAsFloat(), array.get(1).getAsFloat() };
    }

    private static JsonObject readJson(String path) {
        try (InputStream in = SpeciesRegistry.class.getClassLoader().getResourceAsStream(path)) {
            if (in == null) {
                throw new IllegalStateException("Resource not found at: " + path);
            }
            try (Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
                return JsonParser.parseReader(reader).getAsJsonObject();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read resource at: " + path, e);
        }
    }
}
