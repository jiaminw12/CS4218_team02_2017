package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

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
	public void testWcAppWithOneItemInArgs() throws WcException {
		String[] args = { "wc" };
		wcApp.run(args, System.in, System.out);
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

	@Test
	public void testIllegalFile() throws WcException {
		String actualResult = wcApp
				.printNewlineCountInFile("wc -l cybody40");
		assertEquals("cybody40 is not a file", actualResult);
	}

	@Test
	public void testIllegalFiles() throws WcException {
		String actualResult = wcApp.printNewlineCountInFile(
				"wc -l cybody40.txt invalidFileName#@#$$%");
		assertEquals(
				"4\t" + System.lineSeparator()
						+ "invalidFileName#@#$$% is not a file",
				actualResult);
	}

	@Test
	public void testPrintCharacterCountInFile() throws WcException {
		// wc -m cybody40.txt
		String actualResult = wcApp
				.printCharacterCountInFile("wc -m cybody40.txt");
		assertEquals("308", actualResult);
	}

	@Test
	public void testPrintWordCountInFile() throws WcException {
		// wc -w cybody40.txt
		String actualResult = wcApp.printWordCountInFile("wc -w cybody40.txt");
		assertEquals("54", actualResult);
	}

	@Test
	public void testPrintLineCountInFile() throws WcException {
		// wc -l cybody40.txt
		String actualResult = wcApp
				.printNewlineCountInFile("wc -l cybody40.txt");
		assertEquals("4", actualResult);
	}

	@Test
	public void testPrintAllCountsInFile() throws WcException {
		// wc -m -w -l cybody40.txt
		String actualResult = wcApp
				.printAllCountsInFile("wc -m -w -l cybody40.txt");
		assertEquals("308\t54\t4", actualResult);
	}

	@Test
	public void testPrintAllCountsInFileAllOptionsTogether()
			throws WcException {
		// wc -m -w -l cybody40.txt
		String actualResult = wcApp
				.printAllCountsInFile("wc -mwl cybody40.txt");
		assertEquals("308\t54\t4", actualResult);
	}

	@Test
	public void testPrintAllCharactersInTwoFiles() throws WcException {
		// wc -m cxintro02.txt cybody40.txt
		String actualResult = wcApp
				.printAllCountsInFile("wc -m cxintro02.txt cybody40.txt");
		assertEquals("48\t" + System.lineSeparator() + "308\t"
				+ System.lineSeparator() + "356", actualResult);
	}

	@Test
	public void testPrintAllCountsInTwoFiles() throws WcException {
		// wc -m -w -l cxintro02.txt cybody40.txt
		String actualResult = wcApp
				.printAllCountsInFile("wc -m -w -l cxintro02.txt cybody40.txt");
		assertEquals("48\t8\t1\t" + System.lineSeparator() + "308\t54\t4\t"
				+ System.lineSeparator() + "356\t62\t5", actualResult);
	}

	@Test
	public void testPrintTwoOptionsTogether() throws WcException {
		// wc -mw cxintro02.txt
		String actualResult = wcApp
				.printAllCountsInFile("wc -mw cxintro02.txt");
		assertEquals("48\t8", actualResult);
	}

	@Test
	public void testPrintAllOptionsTogether() throws WcException {
		// wc -lmw cxintro02.txt
		String actualResult = wcApp
				.printAllCountsInFile("wc -lmw cxintro02.txt");
		assertEquals("48\t8\t1", actualResult);
	}

	@Test
	public void testPrintAllOptionsTogetherInReverseOrder() throws WcException {
		// wc -wml cxintro02.txt
		String actualResult = wcApp
				.printAllCountsInFile("wc -wml cxintro02.txt");
		assertEquals("48\t8\t1", actualResult);
	}

	@Test
	public void testPrintCharacterCountInStdin() throws WcException {
		// wc -m
		ByteArrayInputStream in = new ByteArrayInputStream(
				"a\n".getBytes());
		String actualResult = wcApp.printCharacterCountInStdin("wc -m", in);
		assertEquals("2", actualResult);
	}

	@Test
	public void testPrintWordCountInStdin() throws WcException {
		// wc -w
		ByteArrayInputStream in = new ByteArrayInputStream("a\n".getBytes());
		String actualResult = wcApp.printWordCountInStdin("wc -w", in);
		assertEquals("1", actualResult);
	}

	@Test
	public void testPrintNewlineCountInStdin() throws WcException {
		// wc -l
		ByteArrayInputStream in = new ByteArrayInputStream("a\n".getBytes());
		String actualResult = wcApp.printNewlineCountInStdin("wc -l", in);
		assertEquals("1", actualResult);
	}

	@Test
	public void testPrintAllCountsInStdin() throws WcException {
		// wc -m -w -l
		ByteArrayInputStream in = new ByteArrayInputStream("a\n".getBytes());
		String actualResult = wcApp.printAllCountsInStdin("wc -m -w -l", in);
		assertEquals("2\t1\t1", actualResult);
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
