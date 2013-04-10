package me.mvader.slimjson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @package me.mvader.slimjson
 * @version 0.5.3
 * @author mvader <hi@mvader.me>
 * @license MIT License
 * Copyright (C) <2013> <mvader>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

public class SlimJson {
	
	/**
	 * Parses a JSON passed as a string.
	 * @param json String The json string
	 * @return Map<String, JsonValue> A HashMap which contains all the items
	 */
	
	public static Map<String, JsonValue> parse(String json) {
		Map<String, JsonValue> parsed = new HashMap<String, JsonValue>();
		List<JsonValue> valueList = new ArrayList<JsonValue>();
		boolean inArray = false, isValue = false, finished = false, wasArray = false;
		String key = null, value = null;
		
		for (int i = 0; i < json.length() && !finished; i++) {
			char c = json.charAt(i);
			switch (c) {
				case '{':
					isValue = false;
					if (i > 0) {
						if (inArray) {
							valueList.add(new JsonValue(6, parse(getNextObject(json, i))));
						} else {
							parsed.put(key, new JsonValue(6, parse(getNextObject(json, i))));
						}
					}
				case ' ':
					continue;
					
				case '}':
					if (!wasArray)
						parsed.put(key, parseValue(value));
					wasArray = false;
				break;
				
				case '[':
					inArray = true;
					isValue = true;
				break;
				
				case ']':
					parsed.put(key, new JsonValue(4, new JsonArray(valueList.toArray(new JsonValue[valueList.size()]))));
					inArray = false;
					isValue = false;
					wasArray = true;
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
	
	/**
	 * Finds the position of the nearest delimiter of the string from a start position.
	 * @param str StringBuffer The JSON String passed as StringBuffer for performance
	 * @param i int The current current item position
	 * @return int Returns the position of the nearest delimiter
	 */
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
	
	/**
	 * Parses the value and returns a JsonValue object.
	 * @param value String
	 * @return JsonValue 
	 */
	
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
			return new JsonValue(7, null);
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
	
	/**
	 * Parses the key.
	 * @param value String
	 * @return String
	 */
	
	public static String parseKey(String value) {
		if (value.charAt(0) == '"') {
			if (value.lastIndexOf('"') > 0)
				return value.substring(1, value.lastIndexOf('"'));
			else
				throw new IllegalArgumentException();
		} else
			throw new IllegalArgumentException();
	}
	
	/**
	 * Returns the next Object in the JSON string.
	 * @param obj String
	 * @param objStartPos int
	 * @return String
	 */
	
	public static String getNextObject(String obj, int objStartPos) {
		int depth = 0, objEndPos = 0;
		char[] objChars = obj.toCharArray();
		for (int i = objStartPos; i < obj.length(); i++) {
			if (objChars[i] == '{')
				depth++;
			else if (objChars[i] == '}')
				depth--;
			
			if (depth == 0) {
				objEndPos = ++i;
				i = objChars.length;
			}
		}
		return obj.substring(objStartPos, objEndPos);
	}
}
