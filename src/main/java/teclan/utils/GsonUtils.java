package teclan.utils;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

public class GsonUtils {
    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Map.class, new MapDeserializer())
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .serializeNulls().create();

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }

    public static <T> T fromJson(String json, String root, Type type) {
        return GSON.fromJson(fromJsonToRootElement(json, root), type);
    }

    public static <T> T fromJson(String json, String root, Class<T> classOfT) {
        JsonElement jsonElement = fromJsonToRootElement(json, root);
        return GSON.fromJson(jsonElement, classOfT);
    }

    public static <T> T fromJson(JsonElement jsonElement, Class<T> classOfT) {
        return GSON.fromJson(jsonElement, classOfT);
    }

    public static <T> T fromJson(JsonElement rootElement, String element,
            Class<T> classOfT) {
        JsonElement jsonElement = rootElement.getAsJsonObject().get(element);
        return GSON.fromJson(jsonElement, classOfT);
    }

    public static <T> T fromJson(String json, String root, String element,
            Type type) {
        return fromJson(fromJsonToRootElement(json, root), element, type);
    }

    public static <T> T fromJson(JsonElement rootElement, String element,
            Type type) {
        JsonElement jsonElement = rootElement.getAsJsonObject().get(element);
        return GSON.fromJson(jsonElement, type);
    }

    public static String toJson(Object src) {
        return GSON.toJson(src);
    }

    public static String toJson(Object src, String root) {
        JsonObject json = new JsonObject();
        json.add(root, GSON.toJsonTree(src));
        return json.toString();
    }

    public static Map<String, Object> toMap(String json) {
        // See
        // https://sites.google.com/site/gson/gson-user-guide#TOC-Serializing-and-Deserializing-Generic-Types
        return GSON.fromJson(json, new TypeToken<Map<String, Object>>() {
        }.getType());
    }

    public static Map<String, Object> toMap(String json, String root) {
        return GSON.fromJson(fromJsonToRootElement(json, root),
                new TypeToken<Map<String, Object>>() {
                }.getType());
    }

    public static JsonElement fromJson(String json) {
        return PARSER.parse(json).getAsJsonObject();
    }

    public static JsonElement fromJsonToRootElement(String json, String root) {
        JsonObject jsonObject = PARSER.parse(json).getAsJsonObject();
        JsonElement jsonElement = jsonObject.get(root);
        return jsonElement;
    }

    protected static final JsonParser PARSER = new JsonParser();

    private static class MapDeserializer
            implements JsonDeserializer<Map<String, Object>> {

        public Map<String, Object> deserialize(JsonElement json, Type typeOfT,
                JsonDeserializationContext context) throws JsonParseException {
            Map<String, Object> m = new LinkedHashMap<String, Object>();
            JsonObject jo = json.getAsJsonObject();
            for (Entry<String, JsonElement> mx : jo.entrySet()) {
                String key = mx.getKey();
                JsonElement v = mx.getValue();
                if (v.isJsonArray()) {
                    m.put(key, GSON.fromJson(v, List.class));
                } else if (v.isJsonNull()) {
                    m.put(key, null);
                } else if (v.isJsonPrimitive()) {
                    JsonPrimitive prim = v.getAsJsonPrimitive();
                    if (prim.isBoolean()) {
                        m.put(key, prim.getAsBoolean());
                    } else if (prim.isString()) {
                        m.put(key, prim.getAsString());
                    } else {
                        Number num = null;
                        ParsePosition position = new ParsePosition(0);
                        String vString = v.getAsString();
                        try {
                            num = NumberFormat.getInstance(Locale.ROOT)
                                    .parse(vString, position);
                        } catch (Exception e) {
                        }
                        // Check if the position corresponds to the length of
                        // the
                        // string
                        if (position.getErrorIndex() < 0
                                && vString.length() == position.getIndex()) {
                            if (num != null) {
                                m.put(key, num);
                                continue;
                            }
                        }
                    }

                } else if (v.isJsonObject()) {
                    m.put(key, GSON.fromJson(v, Map.class));
                }

            }
            return m;
        }
    }

}
