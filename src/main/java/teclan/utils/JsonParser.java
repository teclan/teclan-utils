package teclan.utils;

import java.util.ArrayList;
import java.util.List;

import org.javalite.common.Inflector;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.JsonParseException;

public class JsonParser {

    private ObjectMapper objectMapper;

    public <T> T parse(String json, String root, Class<T> clazz) {
        try {
            JsonNode node = getObjectMapper().readTree(json);
            node = node.get(root);

            return getObjectMapper().readValue(node.traverse(), clazz);
        } catch (Exception e) {
            throw new JsonParseException(e);
        }
    }

    public <T> T parseWithoutRoot(String json, Class<T> clazz) {
        try {
            return getObjectMapper().readValue(json, clazz);
        } catch (Exception e) {
            throw new JsonParseException(e);
        }
    }

    public <T> List<T> parseCollectionWithoutRoot(String json, Class<T> clazz) {
        try {

            TypeFactory t = TypeFactory.defaultInstance();
            return getObjectMapper().readValue(json,
                    t.constructCollectionType(ArrayList.class, clazz));
        } catch (Exception e) {
            throw new JsonParseException(e);
        }
    }

    public <T> List<T> parseCollection(String json, Class<T> clazz) {
        return parseCollection(json, getRoot(clazz), clazz);
    }

    public <T> List<T> parseCollection(String json, String root,
            Class<T> clazz) {
        try {
            JsonNode node = getObjectMapper().readTree(json);
            node = node.get(root);

            TypeFactory t = TypeFactory.defaultInstance();
            return getObjectMapper().readValue(node.traverse(),
                    t.constructCollectionType(ArrayList.class, clazz));
        } catch (Exception e) {
            throw new JsonParseException(e);
        }
    }

    private <T> String getRoot(Class<T> clazz) {
        return Inflector.pluralize(Inflector.underscore(clazz.getSimpleName()));
    }

    private ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(
                    PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        }

        return objectMapper;
    }

}
