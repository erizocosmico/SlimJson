package me.mvader.slimjson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlimJson {
	public static Map<String, JsonValue> parse(String json) {
		Map<String, JsonValue> parsed = new HashMap<String, JsonValue>();
		List<JsonValue> valueList = new ArrayList<JsonValue>();
		boolean inArray = false, isValue = false, finished = false;
		String key = null, value = null;
		
		for (int i = 0; i < json.length() && !finished; i++) {
			char c = json.charAt(i);
			switch (c) {
				case '{':
				case ' ':
					continue;
					
				case '}':
					parsed.put(key, parseValue(value));
					finished = true;
				break;
				
				case '[':
					inArray = true;
				break;
				
				case ']':
					parsed.put(key, new JsonValue(4, new JsonArray((JsonValue[]) valueList.toArray())));
					inArray = false;
				break;
				
				case ':':
					isValue = true;
					valueList.clear();
				break;
				
				case ',':
					if (!inArray)
						isValue = false;
				break;
				
				default:
					int nearestDelim = nearestDelimiter(new StringBuffer(json), i);
					value = json.substring(i, (nearestDelim > 0) ? nearestDelim : json.length() - 1);
					i = (nearestDelim > 0) ? nearestDelim : json.length() - 1;
					if (isValue) {
						if (inArray)
							valueList.add(parseValue(value));
						else
							parsed.put(key, parseValue(value));
					} else {
						key = parseKey(value);
					}
					i--;
			}
		}
		
		return parsed;
	}
	
	private static int nearestDelimiter(StringBuffer str, int i) {
		int nearestPos = -1;
		char[] delimiters = {',', '[', ']', ':', '}'};
		for (int j = 0; j < delimiters.length; j++) {
			int pos = str.indexOf(new Character(delimiters[j]).toString(), i);
			if ((nearestPos < 0 || nearestPos > pos) && pos > 0)
				nearestPos = pos;
		}
		return nearestPos;
	}
	
	public static JsonValue parseValue(String value) {
		if (value.charAt(0) == '"') {
			if (value.lastIndexOf('"') > 0)
				return new JsonValue(1, value.substring(1, value.lastIndexOf('"')));
			else
				throw new IllegalArgumentException();
		} else if (value.equals("true"))
			return new JsonValue(5, (Boolean) true);
		else if (value.equals("false"))
			return new JsonValue(5, (Boolean) false);
		else if (value.equals("null"))
			return new JsonValue(6, null);
		else if (value.indexOf('.') >= 0) {
			try {
				return new JsonValue(3, Double.parseDouble(value));
			} catch (Exception e) {
				throw new IllegalArgumentException();
			}
		} else {
			try {
				return new JsonValue(2, Integer.parseInt(value));
			} catch (Exception e) {
				throw new IllegalArgumentException();
			}
		}
	}
	
	public static String parseKey(String value) {
		if (value.charAt(0) == '"') {
			if (value.lastIndexOf('"') > 0)
				return value.substring(1, value.lastIndexOf('"'));
			else
				throw new IllegalArgumentException();
		} else
			throw new IllegalArgumentException();
	}
}
