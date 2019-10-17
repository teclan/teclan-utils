package teclan.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 对一些常见值的处理
 * 
 * @author teclan
 * 
 *         email: tbj621@163.com
 *
 *         2017年10月27日
 */
public class Objects {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(Objects.class);

	public static boolean isNull(Object value) {
		return value == null;
	}

	public static boolean isNotNull(Object value) {
		return !isNull(value);
	}

	public static boolean isNotNullString(Object value) {
		return !isNullString(value);
	}

	/**
	 * 字符串是否是 null 或者 ""
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullString(Object value) {
		return (value == null || "".equals(value.toString().trim()));
	}

	public static JSONObject removeNullKey(JSONObject jsonObject) {

		List<String> deleted = new ArrayList<String>();

		for (String key : jsonObject.keySet()) {

			if (isNull(jsonObject.get(key))) {
				deleted.add(key);
			}
		}
		for (String key : deleted) {
			jsonObject.remove(key);
		}

		return jsonObject;
	}

	public static JSONObject setNull2EmptyString(JSONObject jsonObject) {

		List<String> keys = new ArrayList<String>();

		for (String key : jsonObject.keySet()) {

			if (isNull(jsonObject.get(key))) {
				keys.add(key);
			}
		}
		for (String key : keys) {
			jsonObject.put(key, "");
		}

		return jsonObject;
	}

	public static boolean hasNullValue(JSONObject jsonObject) {
		for (String key : jsonObject.keySet()) {
			if (isNullString(jsonObject.get(key))) {
				return true;
			}
		}
		return false;
	}

	public static List<String> getKeys(JSONObject jsonObject) {

		List<String> keys = new ArrayList<String>();

		for (String key : jsonObject.keySet()) {
			keys.add(key);
		}
		return keys;
	}

	public static String Joiner(String separator, List<String> collection) {

		if (collection.isEmpty() || collection.size() == 0) {
			return "";
		}

		StringBuffer sb = new StringBuffer();

		if (collection.size() == 1) {
			return collection.iterator().next();
		} else {
			Iterator<String> iterator = collection.iterator();
			while (iterator.hasNext()) {
				sb.append(iterator.next()).append(separator);
			}
		}
		String result = sb.toString();

		return result.substring(0, result.length() - separator.length());
	}

	public static String Joiner(String separator, String[] collection) {

		if (collection.length == 0) {
			return "";
		}

		StringBuffer sb = new StringBuffer();

		if (collection.length == 1) {
			return collection[0];
		} else {
			for (int i = 0; i < collection.length; i++) {
				sb.append(collection[i]).append(separator);
			}
		}
		String result = sb.toString();

		return result.substring(0, result.length() - separator.length());
	}
	
	public static String Joiner(String separator,JSONArray collection) {

		if (collection.size() == 0) {
			return "";
		}

		StringBuffer sb = new StringBuffer();

		if (collection.size() == 1) {
			return (String) collection.get(0);
		} else {
			for (int i = 0; i < collection.size(); i++) {
				sb.append(collection.get(i).toString()).append(separator);
			}
		}
		String result = sb.toString();

		return result.substring(0, result.length() - separator.length());
	}

	public static String Joiner(String separator, Set<String> collection) {

		if (collection.isEmpty() || collection.size() == 0) {
			return "";
		}

		StringBuffer sb = new StringBuffer();

		if (collection.size() == 1) {
			return collection.iterator().next();
		} else {
			Iterator<String> iterator = collection.iterator();
			while (iterator.hasNext()) {
				sb.append(iterator.next()).append(separator);
			}
		}
		String result = sb.toString();

		return result.substring(0, result.length() - separator.length());
	}

	public static String JoinerForSql(String logic, String column, Set<String> collection) {

		if (collection.isEmpty() || collection.size() == 0) {
			return " 1 = 1";
		}

		StringBuffer sb = new StringBuffer();

		if (collection.size() == 1) {
			return sb.append(String.format("( %s = '%s' )", column, collection.iterator().next())).toString();
		} else {
			sb.append(String.format(" %s = ", column));

			Iterator<String> iterator = collection.iterator();
			while (iterator.hasNext()) {
				sb.append("'").append(iterator.next()).append("'").append(String.format(" %s %s = ", logic, column));
			}
		}
		String result = sb.toString();

		return " ( " + result.substring(0, result.lastIndexOf(logic)) + " ) ";
	}

	public static String JoinerForSql(String logic, String column, JSONArray collection) {

		if (collection.isEmpty() || collection.size() == 0) {
			return " 1 = 1";
		}

		StringBuffer sb = new StringBuffer();

		if (collection.size() == 1) {
			return sb.append(String.format("( %s = '%s' )", column, collection.iterator().next())).toString();
		} else {
			sb.append(String.format(" %s = ", column));

			for (int i = 0; i < collection.size(); i++) {
				sb.append("'").append(collection.get(i).toString()).append("'")
						.append(String.format(" %s %s = ", logic, column));
			}
		}
		String result = sb.toString();

		return " ( " + result.substring(0, result.lastIndexOf(" " + logic + " ")) + " ) ";
	}

	public static boolean isNull(List<?> list) {
		return list == null || list.isEmpty();
	}

	public static boolean isNotNull(List<?> list) {
		return !isNull(list);
	}

	public static Map<String, Object> removeUnnecessaryColumns(List<String> columns,
			Map<String, Object> namesAndValues) {
		List<String> delete = new ArrayList<String>();

		for (String key : namesAndValues.keySet()) {
			if (!columns.contains(key)) {
				delete.add(key);
			}
		}

		for (String key : delete) {
			namesAndValues.remove(key);
		}

		return namesAndValues;

	}

	public static List<Map<String, Object>> setNull2EmptyString(List<Map<String, Object>> list) {

		for (Map<String, Object> map : list) {

			List<String> keys = new ArrayList<String>();

			for (String key : map.keySet()) {
				if (map.get(key) == null) {
					keys.add(key);
				}
			}

			for (String key : keys) {
				map.put(key, "");
			}

		}
		return list;
	}

	public static boolean equalString(Object value1, Object value2) {

		if (isNullString(value1) && isNullString(value2)) {
			return true;
		}

		return value1.equals(value2) || value1.toString().trim().equals(value2.toString().trim());
	}

	/**
	 * null or empty
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullMap(Map<String, String> value) {
		return isNull(value) || value.isEmpty();
	}

	/**
	 * not null and not empty
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNotNullMap(Map<String, String> value) {
		return !isNullMap(value);
	}

	public static boolean isNullMapWithObject(Map<String, Object> value) {
		return isNull(value) || value.isEmpty();
	}

	public static boolean isNotNullMapWithObject(Map<String, Object> value) {
		return !isNullMapWithObject(value);
	}

	public static Object[] merge(Object[] value1, Object[] value2) {

		Object[] result = new Object[value1.length + value2.length];

		System.arraycopy(value1, 0, result, 0, value1.length);
		System.arraycopy(value2, 0, result, value1.length, value2.length);

		return result;

	}

	/**
	 * 返回旧List有但是新List没有的
	 * 
	 * @param newValue
	 * @param oldValue
	 * @return
	 */

	public static List<String> getDiffFromOld(List<String> newValue, List<String> oldValue) {

		if (!isNull(newValue)) {
			oldValue.removeAll(newValue);
		}
		return oldValue;

	}

	/**
	 * 返回新List有但是旧List没有的
	 * 
	 * @param newValue
	 * @param oldValue
	 * @return
	 */

	public static List<String> getDiffFromNew(List<String> newValue, List<String> oldValue) {

		if (!isNull(oldValue)) {
			newValue.removeAll(oldValue);
		}
		return newValue;

	}
}
