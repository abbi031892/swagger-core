package io.swagger.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import io.swagger.converter.ModelConverters;
import io.swagger.models.Model;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.AbstractSerializableParameter;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.properties.Property;

@RunWith(PowerMockRunner.class)
@PrepareForTest( { ParameterProcessor.class, AllowableValuesUtils.class, ModelConverters.class })
public class ParameterProcessorTest {

	@Test
	public void testApplyAnnotations() throws Exception {

		Swagger swagger = mock(Swagger.class);
		Parameter parameter = mock(AbstractSerializableParameter.class);
		Type type = mock(Type.class);
		List<Annotation> annotations = mock(List.class);

		Object spyObj = createAnnotationsHelper(annotations);

		ParameterProcessor.ParamWrapper wrapperObj = mock(ParameterProcessor.ParamWrapper.class);
		PowerMockito.when(spyObj, "getApiParam").thenReturn(wrapperObj);

		PowerMockito.when(wrapperObj, "isHidden").thenReturn(false);
		PowerMockito.when(wrapperObj, "isRequired").thenReturn(false);
		PowerMockito.when(wrapperObj, "getName").thenReturn(null);
		PowerMockito.when(wrapperObj, "getDescription").thenReturn(null);
		PowerMockito.when(wrapperObj, "getExample").thenReturn("ExampleObj");
		PowerMockito.when(wrapperObj, "getAccess").thenReturn(null);
		PowerMockito.when(wrapperObj, "getDataType").thenReturn("java.io.File");
		PowerMockito.when(wrapperObj, "getAllowableValues").thenReturn(null);

		PowerMockito.mockStatic(AllowableValuesUtils.class);
		PowerMockito.doReturn(null).when(AllowableValuesUtils.class, "create", null);

		ParameterProcessor testObj = new ParameterProcessor();
		Parameter returnVal = ParameterProcessor.applyAnnotations(swagger, parameter, type, annotations);
		Assert.assertNotNull(returnVal);
	}

	@Test
	public void testBodyParameter() throws Exception {

		Swagger swagger = mock(Swagger.class);
		Parameter parameter = mock(BodyParameter.class);
		Type type = mock(Type.class);
		List<Annotation> annotations = mock(List.class);

		Object spyObj = createAnnotationsHelper(annotations);

		Object apiParamSpyObj = createWrapperObject("ApiParamWrapper", spyObj);
		PowerMockito.when(spyObj, "getApiParam").thenReturn(apiParamSpyObj);

		Example exampleObj = mock(Example.class);
		ExampleProperty exPropObj = mock(ExampleProperty.class);

		PowerMockito.when(exPropObj, "mediaType").thenReturn("Media Type");
		PowerMockito.when(exPropObj, "value").thenReturn("Test Value");

		ExampleProperty[] exPropObjArray = {exPropObj};
		PowerMockito.when(exampleObj, "value").thenReturn(exPropObjArray);
		PowerMockito.when(apiParamSpyObj, "getExamples").thenReturn(exampleObj);

		Property property =mock(Property.class);
		PowerMockito.mockStatic(ModelConverters.class);
		ModelConverters converterObj = mock(ModelConverters.class);
		PowerMockito.when(ModelConverters.getInstance()).thenReturn(converterObj);
		PowerMockito.when(converterObj.readAsProperty(Matchers.eq(type))).thenReturn(property);

		Map<String, Model> mapObj = mock(Map.class);
		PowerMockito.when(converterObj.readAll(Matchers.eq(type))).thenReturn(mapObj);
		Map.Entry<String, Model> entry = mock(Map.Entry.class);
		Set<Map.Entry<String, Model>> setObj = mock(Set.class);
		PowerMockito.when(mapObj.entrySet()).thenReturn(setObj);

		Iterator iterObj = mock(Iterator.class);
		when(iterObj.hasNext()).thenReturn(true, false);
		when(iterObj.next()).thenReturn(entry);
		when(setObj.iterator()).thenReturn(iterObj);

		Parameter returnVal = ParameterProcessor.applyAnnotations(swagger, parameter, type, annotations);
		Assert.assertNotNull(returnVal);

		Object wrapperObj = createWrapperObject("ApiImplicitParamWrapper", spyObj);
		PowerMockito.when(spyObj, "getApiParam").thenReturn(wrapperObj);
		PowerMockito.when(wrapperObj, "getExamples").thenReturn(exampleObj);

		returnVal = ParameterProcessor.applyAnnotations(swagger, parameter, type, annotations);
		Assert.assertNotNull(returnVal);
	}

	private Object createAnnotationsHelper(List<Annotation> annotations) throws Exception {

		Class<Object> staticClassType = Whitebox.getInnerClassType(ParameterProcessor.class, "AnnotationsHelper");
		PowerMockito.mockStatic(staticClassType);
		Object helperObj = mock(staticClassType);
		Object spyObj = PowerMockito.spy(helperObj);
		whenNew(staticClassType).withArguments(Matchers.eq(annotations)).thenReturn(spyObj);

		PowerMockito.when(spyObj, "getDefaultValue").thenReturn("Default Value");
		PowerMockito.when(spyObj, "isContext").thenReturn(false);

		return spyObj;
	}

	private Object createWrapperObject(String className, Object spyObj) throws Exception {

		Class<Object> apiParamType = Whitebox.getInnerClassType(ParameterProcessor.class, className);
		PowerMockito.mockStatic(apiParamType);
		Object apiObj = mock(apiParamType);
		Object apiSpyObj = PowerMockito.spy(apiObj);
		PowerMockito.when(spyObj, "getApiParam").thenReturn(apiSpyObj);

		return apiSpyObj;
	}
}
