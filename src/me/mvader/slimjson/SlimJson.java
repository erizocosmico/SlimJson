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
		String key = null;
		
		for (int i = 0; i < json.length() && !finished; i++) {
			char c = json.charAt(i);
			switch (c) {
				case '{':
				break;
				
				case '}':
					finished = true;
				break;
				
				case '[':
					inArray = true;
				break;
				
				case ']':
					inArray = false;
				break;
				
				case ':':
					isValue = true;
					parsed.put(key, new JsonValue(4, new JsonArray((JsonValue[]) valueList.toArray())));
					valueList.clear();
				break;
				
				case ',':
					if (!inArray)
						isValue = false;
				break;
				
				default:
					int nearestDelim = nearestDelimiter(new StringBuffer(json), i);
					String value = json.substring(i, nearestDelim);
					i = nearestDelim;
					if (isValue) {
						if (inArray)
							valueList.add(parseValue(value));
						else
							parsed.put(key, parseValue(value));
					} else {
						key = value;
					}
			}
		}
		
		return parsed;
	}
	
	private static int nearestDelimiter(StringBuffer str, int i) {
		int nearestPos = -1;
		char[] delimiters = {',', '[', ']', ':', '}'};
		for (int j = 0; i < delimiters.length; i++) {
			int pos = str.indexOf(new Character(delimiters[j]).toString(), i);
			if (nearestPos < 0 || nearestPos > pos)
				nearestPos = pos;
		}
		return nearestPos;
	}
	
	public static JsonValue parseValue(String value) {
		if (value.charAt(0) == '"') {
			if (value.charAt(value.length() - 1) == '"')
				return new JsonValue(1, value);
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
}
