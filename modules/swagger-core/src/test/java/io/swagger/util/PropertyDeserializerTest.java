package io.swagger.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.Iterator;
import java.util.Map;

import org.junit.Test;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.models.Xml;
import io.swagger.models.properties.PropertyBuilder;
import io.swagger.models.properties.RefProperty;

/**
 * The <code>PropertyDeserializerTest</code> class provides the
 * unit test for getXML() & PropertyFromNode() in the class
 * <code>PropertyFromDeserializer</code> class.
 */

public class PropertyDeserializerTest {

	@Test
	public void  testGetXml() {
		final PropertyDeserializer testObj = new PropertyDeserializer();

		final JsonNode jsonNode = mock(ObjectNode.class);
		final ObjectNode objNode = mock(ObjectNode.class);
		when(jsonNode.get("xml")).thenReturn(objNode);

		final ObjectNode prefixObj = mock(ObjectNode.class);
		when(objNode.get("prefix")).thenReturn(prefixObj);
		when(prefixObj.asText()).thenReturn("Test Prefix");

		final ObjectNode namespaceObj = mock(ObjectNode.class);
		when(objNode.get("namespace")).thenReturn(namespaceObj);
		when(namespaceObj.asText()).thenReturn("Test Namespace");

		final Xml returnVal = testObj.getXml(jsonNode);
		assertEquals("Test Prefix", returnVal.getPrefix());
		assertEquals("Test Namespace", returnVal.getNamespace());
	}

	@Test
	public void testPropertyFromNode() throws Exception {

		final JsonNode jsonNode = createJsonNode();

		final JsonNode propertyNode = mock(ObjectNode.class);
		when(jsonNode.get("properties")).thenReturn(propertyNode);
		final Iterator<Map.Entry<String,JsonNode>> fieldsIter = mock(Iterator.class);
		when(propertyNode.fields()).thenReturn(fieldsIter);

		final Map.Entry<String, JsonNode> mapEntry1 = mock(Map.Entry.class);
		final Map.Entry<String, JsonNode> mapEntry2 = mock(Map.Entry.class);
		final Map.Entry<String, JsonNode> mapEntry3 = mock(Map.Entry.class);
		when(fieldsIter.hasNext()).thenReturn(true, true, true, false);
		when(fieldsIter.next()).thenReturn(mapEntry1, mapEntry2, mapEntry3);

		final JsonNode mapNode1 = createJsonNode();
		final Iterator<String> fieldNameIter = mock(Iterator.class);
		when(fieldNameIter.hasNext()).thenReturn(false);
		when(mapNode1.fieldNames()).thenReturn(fieldNameIter);
		when(mapEntry1.getKey()).thenReturn("type");
		when(mapEntry1.getValue()).thenReturn(mapNode1);
		when(mapNode1.asText()).thenReturn("array");

		final JsonNode mapNode2 = createJsonNode();
		when(mapNode2.fieldNames()).thenReturn(fieldNameIter);
		when(mapEntry2.getKey()).thenReturn("description");
		when(mapEntry2.getValue()).thenReturn(mapNode2);
		when(mapNode2.getNodeType()).thenReturn(JsonNodeType.STRING);

		final JsonNode mapNode3 = createJsonNode();
		when(mapNode3.fieldNames()).thenReturn(fieldNameIter);
		when(mapEntry3.getValue()).thenReturn(mapNode3);
		addRefProperty(mapNode3);

		final PropertyDeserializer testObj = new PropertyDeserializer();
		when(jsonNode.fieldNames()).thenReturn(fieldNameIter);
		testObj.propertyFromNode(jsonNode);
	}

	private JsonNode createJsonNode() {

		final JsonNode jsonNode = mock(ObjectNode.class);
		final ObjectNode typeNode = mock(ObjectNode.class);
		when(jsonNode.get(PropertyBuilder.PropertyId.TYPE.getPropertyName())).thenReturn(typeNode);

		final ObjectNode formatObj = mock(ObjectNode.class);
		when(jsonNode.get(PropertyBuilder.PropertyId.FORMAT.getPropertyName())).thenReturn(formatObj);

		final ObjectNode xmlNode = mock(ObjectNode.class);
		when(jsonNode.get("xml")).thenReturn(xmlNode);

		final ObjectNode descriptionObj = mock(ObjectNode.class);
		when(jsonNode.get(PropertyBuilder.PropertyId.DESCRIPTION.getPropertyName())).thenReturn(descriptionObj);

		return jsonNode;
	}

	private void addRefProperty(JsonNode node) throws Exception {

		final JsonNode detailNode = mock(JsonNode.class);
		when(node.get("$ref")).thenReturn(detailNode);
		when(detailNode.asText()).thenReturn("Ref Property Text");

		final RefProperty refProperty = mock(RefProperty.class);
		PowerMockito.whenNew(RefProperty.class).withArguments(Matchers.anyString()).thenReturn(refProperty);
		when(refProperty.description(Matchers.anyString())).thenReturn(refProperty);
	}
}
