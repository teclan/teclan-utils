package teclan.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.JsonObject;

public class JacksonUtils {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(JacksonUtils.class);

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static XmlMapper    xmlMapper    = new XmlMapper();

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return new JsonObject().getAsString();
    }

    public static String json2xml(String json) {
        try {
            JsonNode root = objectMapper.readTree(json);
            String xml = xmlMapper.writeValueAsString(root);
            return xml;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return "";
    }

    public static String object2xml(Object object) {
        try {
            return xmlMapper.writeValueAsString(object);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return "";
    }

    public static String object2xml(Object object, String filePath) {
        try {
            String xml = xmlMapper.writeValueAsString(object);
            FileUtils.write2File(filePath, FileUtils.formatXml(xml));
            return xml;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return "";
    }

    public static String xml2json(String xml) {
        StringWriter writer = new StringWriter();
        com.fasterxml.jackson.core.JsonParser jsonParser;
        try {
            jsonParser = xmlMapper.getFactory().createParser(xml);
            JsonGenerator jsonGenerator = objectMapper.getFactory()
                    .createGenerator(writer);
            while (jsonParser.nextToken() != null) {
                jsonGenerator.copyCurrentEvent(jsonParser);
            }
            jsonParser.close();
            jsonGenerator.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return writer.toString();
    }

    public static <T> T fromXml(String xml, Class<T> classOfT) {
        try {
            return xmlMapper.readValue(xml, classOfT);
        } catch (JsonParseException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T fromXmlFile(String filePath, Class<T> classOfT) {
        try {
            return xmlMapper.readValue(FileUtils.getContent(new File(filePath)),
                    classOfT);
        } catch (JsonParseException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
}
