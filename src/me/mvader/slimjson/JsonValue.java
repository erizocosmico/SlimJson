package me.mvader.slimjson;

import java.util.Map;

public class JsonValue {
	private Object value;
	public int type;
	
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
}
