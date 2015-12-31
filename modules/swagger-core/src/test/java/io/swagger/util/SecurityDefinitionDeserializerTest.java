package io.swagger.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.models.auth.BasicAuthDefinition;

/**
 * The <code>SecurityDefinitionDeserializerTest</code> class provides the
 * unit test for deserialize() in the class SecurityDefinitionDeserializer class.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Json.class })
public class SecurityDefinitionDeserializerTest {

	@Test
	public void testDeserialize() throws Exception {

		final SecurityDefinitionDeserializer testObj = new SecurityDefinitionDeserializer();
		final JsonParser jp = mock(JsonParser.class);
		final ObjectCodec codecObj = mock(ObjectCodec.class);
		when(jp.getCodec()).thenReturn(codecObj);

		final JsonNode jsonNode = mock(JsonNode.class);
		when(codecObj.readTree(Matchers.any(JsonParser.class))).thenReturn(jsonNode);

		final JsonNode inNode = mock(JsonNode.class);
		when(jsonNode.get("type")).thenReturn(inNode);
		when(inNode.asText()).thenReturn("basic");

		final BasicAuthDefinition result = mock(BasicAuthDefinition.class);
		PowerMockito.mockStatic(Json.class);
		final ObjectMapper mapperObj = mock(ObjectMapper.class);
		when(Json.mapper()).thenReturn(mapperObj);
		when(mapperObj.convertValue(Matchers.anyObject(), Matchers.eq(BasicAuthDefinition.class))).thenReturn(result);

		Assert.assertNotNull(testObj.deserialize(jp, null));
	}

}
