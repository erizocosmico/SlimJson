package me.mvader.slimjson;

public class JsonArray {
	private JsonValue[] values;
	
	public JsonArray(JsonValue[] values) {
		this.values = values;
	}
	
	public JsonValue get(int i) {
		if (i >= this.values.length)
			throw new IndexOutOfBoundsException();
		else if (i < 0)
			throw new IllegalArgumentException();
		return this.values[i];
	}
	
	public int size() {
		return this.values.length;
	}
	
	public boolean contains(String o) {
		for (int i = 0; i < this.values.length; i++) {
			if (this.values[i].value().equals(o))
				return true;
		}
		return false;
	}
	
	public boolean contains(Integer o) {
		for (int i = 0; i < this.values.length; i++) {
			if (this.values[i].value().equals(o))
				return true;
		}
		return false;
	}
	
	public boolean contains(Double o) {
		for (int i = 0; i < this.values.length; i++) {
			if (this.values[i].value().equals(o))
				return true;
		}
		return false;
	}
	
	public boolean contains(Boolean o) {
		for (int i = 0; i < this.values.length; i++) {
			if (this.values[i].value().equals(o))
				return true;
		}
		return false;
	}
	
	public boolean containsNull() {
		for (int i = 0; i < this.values.length; i++) {
			if (this.values[i].value() == null)
				return true;
		}
		return false;
	}
}
