package io.swagger.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
* The <code>PathUtilsTest</code> class provides the
* unit test for parsePath() and cutParameter() in
* <code>PathUtils</code> class.
*/

@RunWith(PowerMockRunner.class)
@PrepareForTest( {StringUtils.class, CharacterIterator.class, StringCharacterIterator.class})
public class PathUtilsTest {

	@Test
	public void testParsePath() throws Exception {

		final PathUtils testObj = PowerMockito.spy(new PathUtils());
		final String URI = CharacterIterator.DONE + "/";
		final Map<String, String> patterns = mock(Map.class);

		final StringCharacterIterator ci = PowerMockito.mock(StringCharacterIterator.class);
		PowerMockito.whenNew(StringCharacterIterator.class).withAnyArguments().
							thenReturn(ci);

		PowerMockito.mockStatic(CharacterIterator.class);
		Mockito.when(ci.first()).thenReturn(CharacterIterator.DONE);

		final String returnVal = testObj.parsePath(URI, patterns);
		Assert.assertEquals(returnVal, "/");
	}

	@Test
	public void testCutParameter() throws Exception {

		final CharacterIterator ci = mock(CharacterIterator.class);
		when(ci.next()).thenReturn('}');

		final PathUtils testObj = PowerMockito.spy(new PathUtils());
		final Object returnVal = Whitebox.invokeMethod(testObj, "cutParameter", ci, null);
		Assert.assertNull(returnVal);
	}

}

