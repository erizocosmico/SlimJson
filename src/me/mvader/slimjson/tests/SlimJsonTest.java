package me.mvader.slimjson.tests;

import static org.junit.Assert.*;
import java.util.Map;
import me.mvader.slimjson.*;
import org.junit.Test;

public class SlimJsonTest {

	@Test
	public void test() {
		String exampleJson = "{\"test\" : \"value\"}";
		Map<String, JsonValue> parsed = SlimJson.parse(exampleJson);
		assertEquals(parsed.containsKey("test"), true);
		assertEquals(parsed.get("test").value(), "value");
	}

}
