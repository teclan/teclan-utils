package teclan.utils.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.ModelDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import teclan.utils.GsonUtils;
import teclan.utils.Strings;

public abstract class ActiveRecord extends Model {
    protected final Logger                                  LOGGER                              = LoggerFactory
            .getLogger(getClass());

    protected static final Map<String, String>              ACTIVE_RECORD_HUMAN_NAMES           = new HashMap<String, String>();
    protected static final Map<String, Map<String, String>> ACTIVE_RECORD_ATTRIBUTE_HUMAN_NAMES = new HashMap<String, Map<String, String>>();

    public String toJson() {
        return GsonUtils.toJson(toMap());
    }

    public String toJson(String root) {
        return GsonUtils.toJson(toMap(), root);
    }

    public String toJson(String... excludedAttributes) {
        return GsonUtils.toJson(toMap(excludedAttributes));
    }

    public String toJson(String root, String... excludedAttributes) {
        return GsonUtils.toJson(toMap(excludedAttributes), root);
    }

    public Map<String, Object> toMap(String... excludedAttributes) {
        Map<String, Object> map = super.toMap();

        for (String attribute : excludedAttributes) {
            map.remove(attribute);
        }

        return map;
    }

    public String getHumanName() {
        String humanName = ACTIVE_RECORD_HUMAN_NAMES.get(getClass().getName());

        if (humanName == null) {
            humanName = getClass().getSimpleName();
        }

        return humanName;
    }

    public String getAttributeHumanName(String attributeName) {
        String humanName = attributeName;

        Map<String, String> attributeHumanNames = ACTIVE_RECORD_ATTRIBUTE_HUMAN_NAMES
                .get(getClass().getName());
        if (attributeHumanNames != null
                && attributeHumanNames.containsKey(attributeName)) {
            humanName = attributeHumanNames.get(attributeName);
        }

        return humanName;
    }

    public Object getAttributeHumanValue(String attributeName) {
        Object value = get(attributeName);

        if (value == null) {
            return "空";
        }

        if (value instanceof Date) {
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            return formatter.format(value);
        }

        return value;
    }

    public String toString(String... excluded) {
        List<String> excludedAttributeNames = Arrays.asList(excluded);

        List<String> attributeStrings = new ArrayList<String>();
        for (String attributeName : ModelDelegate.attributeNames(getClass())) {
            String lowerCase = attributeName.toLowerCase();
            if (!excludedAttributeNames.contains(lowerCase)
                    && get(attributeName) != null) {
                attributeStrings.add(String.format("%s: %s",
                        getAttributeHumanName(lowerCase),
                        getAttributeHumanValue(attributeName)));
            }
        }

        return String.format("%s - [ %s ]", getHumanName(),
                String.join(", ", attributeStrings));
    }

    public static <T extends ActiveRecord> T fromJson(String json,
            Class<T> clazz) {
        return fromJson(json, null, clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T extends ActiveRecord> T fromJson(String json, String root,
            Class<T> clazz) {
        Map<String, Object> map = null;
        if (Strings.isEmpty(root)) {
            map = GsonUtils.fromJson(json, Map.class);
        } else {
            map = GsonUtils.fromJson(json, root, Map.class);
        }

        try {
            T instance = clazz.newInstance();
            instance.fromMap(map);

            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}