package teclan.utils;

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class JsonBuilder {
    private static final Logger logger = LoggerFactory
            .getLogger(JsonBuilder.class);

    public String build(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(
                PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error(e.getLocalizedMessage(), e);
            return null;
        }
    }

    public String build(Object obj, String root) {
        Map<String, Object> singletonMap = Collections.singletonMap(root, obj);
        return build(singletonMap);
    }

}
