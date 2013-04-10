package me.mvader.slimjson;

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
			if (this.values[i].isNull())
				return true;
		}
		return false;
	}
}
