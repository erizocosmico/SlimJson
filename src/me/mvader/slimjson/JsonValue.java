package me.mvader.slimjson;

public class JsonValue {
	private Object value;
	private int type;
	
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
			default: return (T) null;
		}
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
}
