package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.WcException;

public class WcApplicationTest {

	static WcApplication wcApp;

	@BeforeClass
	public static void setUpOnce() throws WcException {
		// one-time initialization code
	}

	@Before
	public void setUp() throws WcException, IOException {
		wcApp = new WcApplication();
	}

	@Test(expected = WcException.class)
	public void testWcAppWithNullArgument() throws WcException {
		wcApp.run(null, null, System.out);
	}

	@Test(expected = WcException.class)
	public void testWcAppWithEmptyArgsWithEmptyInput() throws WcException {
		String[] args = {};
		wcApp.run(args, null, System.out);
	}

	@Test(expected = WcException.class)
	public void testWcAppWithNullInputOutput() throws WcException {
		String[] args = { "wc", "-m", "cybody40.txt" };
		wcApp.run(args, null, null);
	}

	@Test(expected = WcException.class)
	public void testIllegalOption() throws WcException {
		String[] args = { "wc", "-z", "cybody40.txt" };
		wcApp.run(args, System.in, System.out);
	}

	@Test(expected = WcException.class)
	public void testIllegalFile() throws WcException {
		String[] args = { "wc", "-l", "cybody40" };
		wcApp.run(args, System.in, System.out);
	}

	@Test(expected = WcException.class)
	public void testIllegalFiles() throws WcException {
		String[] args = { "wc", "-l", "cybody40.txt", "invalidFileName#@#$$%" };
		wcApp.run(args, System.in, System.out);
	}

	@Test
	public void testPrintCharacterCountInFile() throws WcException {
		// wc -m cybody40.txt
		String expectedResult = wcApp
				.printCharacterCountInFile("wc -m cybody40.txt");
		assertEquals("304", expectedResult);
	}

	@Test
	public void testPrintWordCountInFile() throws WcException {
		// wc -w cybody40.txt
		String expectedResult = wcApp
				.printWordCountInFile("wc -w cybody40.txt");
		assertEquals("54", expectedResult);
	}

	@Test
	public void testPrintLineCountInFile() throws WcException {
		// wc -l cybody40.txt
		String expectedResult = wcApp
				.printNewlineCountInFile("wc -l cybody40.txt");
		assertEquals("4", expectedResult);
	}

	@Test
	public void testPrintAllCountsInFile() throws WcException {
		// wc -m -w -l cybody40.txt
		String expectedResult = wcApp
				.printAllCountsInFile("wc -m -w -l cybody40.txt");
		assertEquals("304\t54\t4", expectedResult);
	}

	@Test
	public void testPrintAllCountsInFileAllOptionsTogether()
			throws WcException {
		// wc -m -w -l cybody40.txt
		String expectedResult = wcApp
				.printAllCountsInFile("wc -m -w -l cybody40.txt");
		assertEquals("304\t54\t4", expectedResult);
	}

	@Test
	public void testPrintAllCharactersInTwoFiles() throws WcException {
		// wc -m cxintro02.txt cybody40.txt
		String expectedResult = wcApp
				.printAllCountsInFile("wc -m cxintro02.txt cybody40.txt");
		assertEquals("47\t" + System.lineSeparator() + "304\t"
				+ System.lineSeparator() + "351", expectedResult);
	}

	@Test
	public void testPrintAllCountsInTwoFiles() throws WcException {
		// wc -m -w -l cxintro02.txt cybody40.txt
		String expectedResult = wcApp
				.printAllCountsInFile("wc -m -w -l cxintro02.txt cybody40.txt");
		assertEquals(
				"47\t8\t1\t" + System.lineSeparator() + "304\t54\t4\t"
						+ System.lineSeparator() + "351\t62\t5",
				expectedResult);
	}

	@Test
	public void testPrintTwoOptionsTogether() throws WcException {
		// wc -mw cxintro02.txt
		String expectedResult = wcApp
				.printAllCountsInFile("wc -mw cxintro02.txt");
		assertEquals("47\t8", expectedResult);
	}

	@Test
	public void testPrintAllOptionsTogether() throws WcException {
		// wc -lmw cxintro02.txt
		String expectedResult = wcApp
				.printAllCountsInFile("wc -lmw cxintro02.txt");
		assertEquals("47\t8\t1", expectedResult);
	}

	@Test
	public void testPrintAllOptionsTogetherInReverseOrder() throws WcException {
		// wc -wml cxintro02.txt
		String expectedResult = wcApp
				.printAllCountsInFile("wc -wml cxintro02.txt");
		assertEquals("47\t8\t1", expectedResult);
	}

	@Test
	public void testPrintCharacterCountInStdin() throws WcException {
		// wc -m
		wcApp.setInputStream("a\nb\nc\n");
		String expectedResult = wcApp.printCharacterCountInStdin("wc -m");
		assertEquals("1", expectedResult);
	}

	@Test
	public void testPrintWordCountInStdin() throws WcException {
		// wc -w
		wcApp.setInputStream("a\n");
		String expectedResult = wcApp.printWordCountInStdin("wc -w");
		assertEquals("1", expectedResult);
	}

	@Test
	public void testPrintNewlineCountInStdin() throws WcException {
		// wc -l
		wcApp.setInputStream("a\n");
		String expectedResult = wcApp.printNewlineCountInStdin("wc -l");
		assertEquals("1", expectedResult);
	}

	@Test
	public void testPrintAllCountsInStdin() throws WcException {
		// wc -m -w -l
		wcApp.setInputStream("a\n");
		String expectedResult = wcApp.printAllCountsInStdin("wc -m -w -l");
		assertEquals("1\t1\t1", expectedResult);
	}

	@After
	public void tearDown() {
	}

	@AfterClass
	public static void tearDownOnce() {
		// one-time cleanup code
		wcApp = null;
	}

}
