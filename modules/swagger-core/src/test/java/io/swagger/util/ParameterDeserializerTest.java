package io.swagger.util;

/**
 * The <code>ParameterDeserializerTest</code> class provides the
 * unit tests for various method in class <code>ParameterDeserializer</code>.
 */

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testng.Assert;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.models.parameters.CookieParameter;
import io.swagger.models.parameters.FormParameter;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Json.class })
public class ParameterDeserializerTest {

	@Test
	public void testFormData() throws Exception {

		final String inNodeText = "formData";
		testDeserializeObject(inNodeText, FormParameter.class);
	}

	@Test
	public void testCookie() throws Exception {

		final String inNodeText = "cookie";
		testDeserializeObject(inNodeText, CookieParameter.class);
	}

	private void testDeserializeObject(String inNodeText, Class className) throws Exception {

		final JsonParser jp = mock(JsonParser.class);
		final ObjectCodec codecObj = mock(ObjectCodec.class);
		when(jp.getCodec()).thenReturn(codecObj);

		final JsonNode node = mock(JsonNode.class);
		when(codecObj.readTree(Matchers.eq(jp))).thenReturn(node);
		when(node.get("$ref")).thenReturn(null);

		final JsonNode in = mock(JsonNode.class);
		when(node.get("in")).thenReturn(in);
		when(in.asText()).thenReturn(inNodeText);

		PowerMockito.mockStatic(Json.class);
		final ObjectMapper mapperObj = mock(ObjectMapper.class);
		when(Json.mapper()).thenReturn(mapperObj);
		final Object model = mock(className);
		when(mapperObj.convertValue(Matchers.anyObject(), Matchers.eq(className))).thenReturn(model);

		final ParameterDeserializer testObj = new ParameterDeserializer();
		final Object returnVal = testObj.deserialize(jp, null);
		Assert.assertNotNull(returnVal);
	}
}
