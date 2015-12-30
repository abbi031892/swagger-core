package io.swagger.util;

import java.lang.reflect.Constructor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.testng.Assert;

/**
 * The <code>ConstructorTest</code> class tests the constructor
 * logic for various classes.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({ BaseReaderUtils.class })
public class ConstructorTest {

	private final String[] classNames = {"io.swagger.config.ConfigFactory", "io.swagger.util.DeserializationModule",
			"io.swagger.util.ObjectMapperFactory", "io.swagger.util.Json", "io.swagger.util.AllowableValuesUtils",
			"io.swagger.util.PathUtils", "io.swagger.util.ReflectionUtils", "io.swagger.util.ParameterProcessor"};
	@Test
	public void testConstructor() throws Exception {
		for(String className : classNames) {
			final Class objectToCreate = Class.forName(className);
			final Constructor constructor = objectToCreate.getConstructor();
			final Object newInstance = constructor.newInstance();
			Assert.assertNotNull(newInstance);
		}
	}

	@Test
	public void testPrivateConstructor() throws Exception {
		final BaseReaderUtils object = Whitebox.invokeConstructor(BaseReaderUtils.class);
		Assert.assertNotNull(object);
	}
}
