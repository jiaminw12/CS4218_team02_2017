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
		String[] args = { "wc", "-m", "farfaraway.txt" };
		wcApp.run(args, null, null);
	}

	@Test(expected = WcException.class)
	public void testIllegalOption() throws WcException {
		String[] args = { "wc", "-z", "farfaraway.txt" };
		wcApp.run(args, System.in, System.out);
	}

	@Test
	public void testIllegalFile() throws WcException {
		String actualResult = wcApp
				.printNewlineCountInFile("wc -l farfaraway");
		assertEquals("farfaraway is not a file" + System.lineSeparator(), actualResult);
	}

	@Test
	public void testIllegalFiles() throws WcException {
		String actualResult = wcApp.printNewlineCountInFile(
				"wc -l farfaraway.txt invalidFileName#@#$$%");
		assertEquals(
				"       4 farfaraway.txt" + System.lineSeparator()
						+ "invalidFileName#@#$$% is not a file" + System.lineSeparator(),
				actualResult);
	}

	@Test
	public void testPrintCharacterCountInFile() throws WcException {
		String actualResult = wcApp
				.printCharacterCountInFile("wc -m farfaraway.txt");
		assertEquals("     565 farfaraway.txt" + System.lineSeparator(), actualResult);
	}

	@Test
	public void testPrintWordCountInFile() throws WcException {
		String actualResult = wcApp.printWordCountInFile("wc -w farfaraway.txt");
		assertEquals("      90 farfaraway.txt" + System.lineSeparator(), actualResult);
	}

	@Test
	public void testPrintLineCountInFile() throws WcException {
		String actualResult = wcApp
				.printNewlineCountInFile("wc -l farfaraway.txt");
		assertEquals("       4 farfaraway.txt" + System.lineSeparator(), actualResult);
	}

	@Test
	public void testPrintAllCountsInFile() throws WcException {
		String actualResult = wcApp
				.printAllCountsInFile("wc -m -w -l farfaraway.txt");
		assertEquals("     565      90       4 farfaraway.txt" + System.lineSeparator(), actualResult);
	}

	@Test
	public void testPrintAllCountsInFileAllOptionsTogether()
			throws WcException {
		String actualResult = wcApp
				.printAllCountsInFile("wc -mwl farfaraway.txt");
		assertEquals("     565      90       4 farfaraway.txt" + System.lineSeparator(), actualResult);
	}

	@Test
	public void testPrintAllCharactersInTwoFiles() throws WcException {
		String actualResult = wcApp
				.printAllCountsInFile("wc -m slicing.txt farfaraway.txt");
		assertEquals("      80 slicing.txt" + System.lineSeparator() + "     565 farfaraway.txt"
				+ System.lineSeparator() + "     645 total" + System.lineSeparator(), actualResult);
	}

	@Test
	public void testPrintAllCountsInTwoFiles() throws WcException {
		String actualResult = wcApp
				.printAllCountsInFile("wc -m -w -l slicing.txt farfaraway.txt");
		assertEquals("      80      14       0 slicing.txt" + System.lineSeparator() + "     565      90       4 farfaraway.txt"
				+ System.lineSeparator() + "     645     104       4 total" + System.lineSeparator(), actualResult);
	}

	@Test
	public void testPrintTwoOptionsTogether() throws WcException {
		String actualResult = wcApp
				.printAllCountsInFile("wc -mw slicing.txt");
		assertEquals("      80      14 slicing.txt" + System.lineSeparator(), actualResult);
	}

	@Test
	public void testPrintAllOptionsTogether() throws WcException {
		String actualResult = wcApp
				.printAllCountsInFile("wc -lmw slicing.txt");
		assertEquals("      80      14       0 slicing.txt" + System.lineSeparator(), actualResult);
	}

	@Test
	public void testPrintAllOptionsTogetherInReverseOrder() throws WcException {
		String actualResult = wcApp
				.printAllCountsInFile("wc -wml slicing.txt");
		assertEquals("      80      14       0 slicing.txt" + System.lineSeparator(), actualResult);
	}

	@Test
	public void testPrintCharacterCountInStdin() throws WcException {
		ByteArrayInputStream in = new ByteArrayInputStream(
				"a\n".getBytes());
		String actualResult = wcApp.printCharacterCountInStdin("wc -m", in);
		assertEquals("       2 " + System.lineSeparator(), actualResult);
	}

	@Test
	public void testPrintWordCountInStdin() throws WcException {
		ByteArrayInputStream in = new ByteArrayInputStream("a\n".getBytes());
		String actualResult = wcApp.printWordCountInStdin("wc -w", in);
		assertEquals("       1 " + System.lineSeparator(), actualResult);
	}

	@Test
	public void testPrintNewlineCountInStdin() throws WcException {
		ByteArrayInputStream in = new ByteArrayInputStream("a\n".getBytes());
		String actualResult = wcApp.printNewlineCountInStdin("wc -l", in);
		assertEquals("       1 " + System.lineSeparator(), actualResult);
	}

	@Test
	public void testPrintAllCountsInStdin() throws WcException {
		ByteArrayInputStream in = new ByteArrayInputStream("a\n".getBytes());
		String actualResult = wcApp.printAllCountsInStdin("wc -m -w -l", in);
		assertEquals("       2       1       1 " + System.lineSeparator(), actualResult);
	}
	
	@Test
	public void testPrintAllCountsInStdinAllOptionsStickTogether() throws WcException {
		ByteArrayInputStream in = new ByteArrayInputStream("a\n".getBytes());
		String actualResult = wcApp.printAllCountsInStdin("wc -mwl", in);
		assertEquals("       2       1       1 " + System.lineSeparator(), actualResult);
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
