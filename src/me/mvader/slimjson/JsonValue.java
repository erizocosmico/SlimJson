package me.mvader.slimjson;

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

public class JsonValue {
	private Object value;
	public int type;
	public static final int T_STRING = 1,
			T_INTEGER = 2,
			T_DOUBLE = 3,
			T_JSONARRAY = 4,
			T_BOOLEAN = 5,
			T_OBJECT = 6,
			T_NULL = 7;
	
	public JsonValue(int type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T value() {
		switch (this.type) {
			case 1: return (T) (String) this.value;
			case 2: return (T) (Integer) this.value;
			case 3: return (T) (Double) this.value;
			case 4: return (T) (JsonArray) this.value;
			case 5: return (T) (Boolean) this.value;
			case 6: return (T) (Map<String, JsonValue>) this.value;
			default: return (T) null;
		}
	}
	
	public JsonValue get(int i) throws Exception {
		if (this.type != 4)
			throw new Exception("JsonValue is not a JsonArray, get(int) method is not allowed.");
		return ((JsonArray) this.value).get(i);
	}
	
	@SuppressWarnings("unchecked")
	public JsonValue get(String key) throws Exception {
		if (this.type != 6)
			throw new Exception("JsonValue is not an Object, get(String) method is not allowed.");
		return ((Map<String, JsonValue>) this.value).get(key);
	}
	
	public boolean isString() {
		return this.type == 1;
	}
	
	public boolean isInteger() {
		return this.type == 2;
	}
	
	public boolean isDouble() {
		return this.type == 3;
	}
	
	public boolean isArray() {
		return this.type == 4;
	}
	
	public boolean isBoolean() {
		return this.type == 5;
	}
	
	public boolean isObject() {
		return this.type == 6;
	}
	
	public boolean isNull() {
		return this.type == 7;
	}
}
