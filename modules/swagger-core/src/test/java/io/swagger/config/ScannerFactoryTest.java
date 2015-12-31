package io.swagger.config;

import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.testng.Assert;

/**
 * The <code>ScannerFactoryTest</code> class provides the
 * unit tests for various methods in the class <code>ScannerFactory</code>.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ScannerFactory.class, Scanner.class })
public class ScannerFactoryTest {

	@Test
	public void testGetScanner() throws Exception {

		final ScannerFactory factoryObj = new ScannerFactory();

		final Scanner scannerObj = mock(Scanner.class);
		Whitebox.setInternalState(ScannerFactory.class, "SCANNER", scannerObj);
		final Scanner result = Whitebox.invokeMethod(ScannerFactory.class, "getScanner");
		Assert.assertNotNull(result);
	}

	@Test
	public void testSetScanner() throws Exception {

		final Scanner scannerObj = mock(Scanner.class);
		Whitebox.invokeMethod(ScannerFactory.class, "setScanner", scannerObj);
		final Scanner result = Whitebox.invokeMethod(ScannerFactory.class, "getScanner");
		Assert.assertNotNull(result);
	}
}
