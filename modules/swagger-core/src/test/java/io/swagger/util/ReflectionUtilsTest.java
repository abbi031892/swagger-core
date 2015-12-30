package io.swagger.util;

/**
 * The <code>ReflectionUtilsTest</code> class provides the unit test cases
 * for getDeclaredFields() in the class </code>ReflectionUtils</code>.
 */

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ReflectionUtilsTest {

	class SuperClass {
		private int testValue;
		public int getTestValue() {
			return testValue;
		}
		public void setTestValue(int testValue) {
			this.testValue = testValue;
		}
	}

	class TestClass extends SuperClass {

		private String strVal;
		public String getStrVal() {
			return strVal;
		}
		public void setStrVal(String strVal) {
			this.strVal = strVal;
		}
	}

	@Test
	public void testGetDeclaredFields() {
		final ReflectionUtils testObj = new ReflectionUtils();
		final List<Field> fieldVals = testObj.getDeclaredFields(TestClass.class);
		Assert.assertNotNull(fieldVals);
	}
}
