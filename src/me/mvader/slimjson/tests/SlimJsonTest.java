package me.mvader.slimjson.tests;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Map;
import me.mvader.slimjson.SlimJson;
import me.mvader.slimjson.JsonValue;

public class SlimJsonTest {

	@Test
	public void tests() {
		String exampleJson = "{\"test\" : \"value\", \"test2\" : 9, \"test3\" : 9.1, \"test4\" : true, \"test5\" : null, \"test6\" : [1, 2, 3, 4]}";
		Map<String, JsonValue> parsed = SlimJson.parse(exampleJson);
		assertEquals(parsed.containsKey("test"), true);
		assertEquals(parsed.get("test").value(), "value");
		assertEquals(parsed.get("test2").value(), 9);
		assertEquals(parsed.get("test3").value(), new Double(9.1));
		assertEquals(parsed.get("test4").value(), true);
		assertEquals(parsed.get("test5").value(), null);
		assertEquals(parsed.get("test6").isArray(), true);
	}

}
