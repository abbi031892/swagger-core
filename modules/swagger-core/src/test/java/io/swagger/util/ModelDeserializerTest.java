package io.swagger.util;

/**
* The <code>ModelDeserializerTest</code> class provides the
* unit test for deserialize() in the class
* <code>ModelDeserializer</ModelDeserializer> class.
*/

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

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

import io.swagger.models.ComposedModel;
import io.swagger.models.Model;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Json.class })
public class ModelDeserializerTest {

	@Test
	public void testDeserialize() throws Exception {
		final JsonParser jp = mock(JsonParser.class);
		final ObjectCodec codecObj = mock(ObjectCodec.class);
		when(jp.getCodec()).thenReturn(codecObj);

		final JsonNode jsonNode = mock(JsonNode.class);
		when(codecObj.readTree(Matchers.any(JsonParser.class))).thenReturn(jsonNode);
		when(jsonNode.get("$ref")).thenReturn(null);

		final JsonNode allOfNode = mock(JsonNode.class);
		when(jsonNode.get("allOf")).thenReturn(allOfNode);

		PowerMockito.mockStatic(Json.class);
		final ObjectMapper mapperObj = mock(ObjectMapper.class);
		when(Json.mapper()).thenReturn(mapperObj);
		final ComposedModel model = mock(ComposedModel.class);
		when(mapperObj.convertValue(Matchers.anyObject(), Matchers.eq(ComposedModel.class))).thenReturn(model);

		final List<Model> allComponents = mock(List.class);
		when(model.getAllOf()).thenReturn(allComponents);
		when(allComponents.size()).thenReturn(1);
		final Model modelObj = mock(Model.class);
		when(allComponents.get(Matchers.anyInt())).thenReturn(modelObj);

		final ModelDeserializer testObj = new ModelDeserializer();
		final Model returnVal = testObj.deserialize(jp, null);
		Assert.assertNotNull(returnVal);
	}
}
