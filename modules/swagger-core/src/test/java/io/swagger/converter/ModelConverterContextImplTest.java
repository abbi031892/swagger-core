package io.swagger.converter;

/**
 * The <code>ModelConverterContextImplTest</code> class provides the
 * unit tests for various methods in class <code>ModelConverterContextImpl</code>.
 */

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.slf4j.Logger;
import org.testng.Assert;

import io.swagger.models.Model;
import io.swagger.models.properties.Property;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Logger.class, ModelConverterContextImpl.class })
public class ModelConverterContextImplTest {

	@Test
	public void testDefineModel() {

		final ModelConverterContextImpl testObj = createTestObject();

        final Logger mockLogger = mock(Logger.class);
        Whitebox.setInternalState(ModelConverterContextImpl.class, "LOGGER", mockLogger);

        when(mockLogger.isDebugEnabled()).thenReturn(true);
		final Model modelObj = mock(Model.class);
		final Type typeObj = mock(Type.class);
		testObj.defineModel("TestName", modelObj, typeObj, "");
	}

	@Test
	public void testResolve() {

		final ModelConverterContextImpl testObj = createTestObject();

        final Logger mockLogger = mock(Logger.class);
        Whitebox.setInternalState(ModelConverterContextImpl.class, "LOGGER", mockLogger);
        when(mockLogger.isDebugEnabled()).thenReturn(true);

        final Type typeObj = mock(Type.class);
        final Model resolved = testObj.resolve(typeObj);

        Assert.assertNull(resolved);
	}

	@Test
	public void testResolveProperty() {

		final ModelConverterContextImpl testObj = createTestObject();

        final Logger mockLogger = mock(Logger.class);
        Whitebox.setInternalState(ModelConverterContextImpl.class, "LOGGER", mockLogger);
        when(mockLogger.isDebugEnabled()).thenReturn(true);

        final Type typeObj = mock(Type.class);
        final Annotation[] annotations = new Annotation[10];
        final Property result = testObj.resolveProperty(typeObj, annotations);
        Assert.assertNull(result);
	}

	private ModelConverterContextImpl createTestObject() {

		final ModelConverter converterObj = mock(ModelConverter.class);
		final ModelConverterContextImpl testObj = new ModelConverterContextImpl(converterObj);

		final List<ModelConverter> converters = new ArrayList<ModelConverter>();
	    final Map<String, Model> modelByName = mock(Map.class);
	    final HashMap<Type, Model> modelByType = mock(HashMap.class);
	    final Set<Type> processedTypes = mock(Set.class);

	    Whitebox.setInternalState(testObj, "converters", converters);
	    Whitebox.setInternalState(testObj, "modelByName", modelByName);
	    Whitebox.setInternalState(testObj, "modelByType", modelByType);
	    Whitebox.setInternalState(testObj, "processedTypes", processedTypes);

		return testObj;
	}

}
