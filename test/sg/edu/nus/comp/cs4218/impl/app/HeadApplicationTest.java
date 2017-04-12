package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.HeadException;

public class HeadApplicationTest {

	static HeadApplication headApp;
	private ByteArrayOutputStream outContent;
	private final static String LINE_SEPARATOR = System.getProperty("line.separator");
	private final static String TEST_STRING_11_LINES = "1" + LINE_SEPARATOR + "2" + LINE_SEPARATOR + "3"
			+ LINE_SEPARATOR + "4" + LINE_SEPARATOR + "5" + LINE_SEPARATOR + "6" + LINE_SEPARATOR + "7" + LINE_SEPARATOR
			+ "8" + LINE_SEPARATOR + "9" + LINE_SEPARATOR + "10" + LINE_SEPARATOR + "11" + LINE_SEPARATOR;

	private final static String TEST_STRING_10_LINES = "1" + LINE_SEPARATOR + "2" + LINE_SEPARATOR + "3"
			+ LINE_SEPARATOR + "4" + LINE_SEPARATOR + "5" + LINE_SEPARATOR + "6" + LINE_SEPARATOR + "7" + LINE_SEPARATOR
			+ "8" + LINE_SEPARATOR + "9" + LINE_SEPARATOR + "10" + LINE_SEPARATOR;
	
	@BeforeClass
	public static void setUpOnce() {

	}

	@Before
	public void setUp() {
		headApp = new HeadApplication();
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void tearDown() {
		System.setOut(null);
	}

	@AfterClass
	public static void tearDownOnce() {
		headApp = null;
	}

	@Test
	public void testCheckTxtFile() throws HeadException {
		assertTrue(headApp.checkValidFile(new File("slicing.txt").toPath()));
	}

	@Test
	public void testOtherFileType() throws HeadException {
		assertTrue(headApp.checkValidFile(new File("README.md").toPath()));
	}

	@Test(expected = HeadException.class)
	public void testHeadAppWithNullArgument() throws HeadException {
		String[] args = {};
		headApp.run(args, System.in, outContent);
	}

	@Test(expected = HeadException.class)
	public void testHeadAppWithIllegalOption() throws HeadException {
		String[] args = { "head", "-a", "15", "muttest.txt" };
		headApp.run(args, System.in, outContent);
	}

	@Test(expected = HeadException.class)
	public void testHeadAppWith5Arguments() throws HeadException {
		String[] args = { "head", "-n", "15", "slicing.txt", "muttest.txt" };
		headApp.run(args, System.in, outContent);
	}

	@Test(expected = HeadException.class)
	public void testHeadAppWithIllegalNumOfLinesUseNegativeDigits()
			throws HeadException {
		String[] args = { "head", "-n", "-2", "muttest.txt" };
		headApp.run(args, System.in, outContent);
	}

	@Test
	public void testHeadAppWithIllegalNumOfLinesUseZero() throws HeadException {
		String[] args = { "head", "-n", "0", "muttest.txt" };
		headApp.run(args, System.in, outContent);
	}

	@Test(expected = HeadException.class)
	public void testHeadAppWithIllegalNumOfLinesUseChar() throws HeadException {
		String[] args = { "head", "-n", "aaa", "muttest.txt" };
		headApp.run(args, System.in, outContent);
	}

	@Test(expected = HeadException.class)
	public void testHeadAppWithIllegalFile() throws HeadException {
		String[] args = { "head", "-n", "15", "text2.txt" };
		headApp.run(args, System.in, outContent);
	}

	@Test(expected = HeadException.class)
	public void testHeadAppWith3Arguments() throws HeadException {
		String[] args = { "head", "-n", "muttest.txt" };
		headApp.run(args, System.in, outContent);
	}

	@Test
	public void testHeadAppWithoutOption() throws HeadException {
		String[] args = { "head", "slicing.txt" };
		headApp.run(args, System.in, outContent);
		assertEquals(
				"Program slicing can be used in debugging to locate source of errors more easily." + System.lineSeparator(),
				outContent.toString());
	}

	@Test
	public void testHeadAppWithOption() throws HeadException {
		String[] args = { "head", "-n", "2", "muttest.txt" };
		headApp.run(args, System.in, outContent);
		assertEquals(
				"// Copy paste this Java Template and save it as \"PatientNames.java\"" + System.lineSeparator()
						+ "import java.lang.String;" + System.lineSeparator(),
				outContent.toString());
	}

	@Test
	public void testHeadAppWithMultiplesOption() throws HeadException {
		String[] args = { "head", "-n2", "-n5", "-n", "8", "muttest.txt" };
		headApp.run(args, System.in, outContent);
		assertEquals(
				"// Copy paste this Java Template and save it as \"PatientNames.java\"" + System.lineSeparator()
						+ "import java.lang.String;" + System.lineSeparator()
						+ "import java.lang.System;" + System.lineSeparator() 
						+ "import java.util.*;" + System.lineSeparator()
						+ "import java.io.*;" + System.lineSeparator()
						+ "import java.util.ArrayList;" + System.lineSeparator()
						+ "import java.util.TreeSet;" + System.lineSeparator() + System.lineSeparator(),
				outContent.toString());
	}

	@Test
	public void testHeadAppWithoutOptionInStdin() throws HeadException {
		ByteArrayInputStream stdin = new ByteArrayInputStream(
				("They may be found on menus in restaurants that serve seafood. UNDER THE SEA" + System.lineSeparator())
						.getBytes());
		String[] args = { "head" };
		headApp.run(args, stdin, outContent);
		assertEquals(
				"They may be found on menus in restaurants that serve seafood. UNDER THE SEA" + System.lineSeparator(),
				outContent.toString());
	}

	@Test
	public void testHeadAppWithOptionInStdin() throws HeadException {
		ByteArrayInputStream stdin = new ByteArrayInputStream(
				("They may be found on menus in restaurants that serve seafood. UNDER THE SEA" + System.lineSeparator())
						.getBytes());
		String[] args = { "head", "-n", "1" };
		headApp.run(args, stdin, outContent);
		assertEquals(
				"They may be found on menus in restaurants that serve seafood. UNDER THE SEA" + System.lineSeparator(),
				outContent.toString());
	}

	@Test
	public void testHeadAppWithMultiplesOptionInStdin() throws HeadException {
		ByteArrayInputStream stdin = new ByteArrayInputStream(
				("They may be found on menus in restaurants that serve seafood. UNDER THE SEA" + System.lineSeparator())
						.getBytes());
		String[] args = { "head", "-n2", "-n5", "-n", "999" };
		headApp.run(args, stdin, outContent);
		assertEquals(
				"They may be found on menus in restaurants that serve seafood. UNDER THE SEA" + System.lineSeparator(),
				outContent.toString());
	}

	//Instead of default 10 lines from stdin, all lines are printed.
	@Test
	public void testPrintFromStdinWith1Arg() throws HeadException {
		String[] arg = { "head" };
		String testStdinInput = TEST_STRING_11_LINES;
		String expectedOutput = TEST_STRING_10_LINES;
		ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
		System.setIn(in);
		headApp.run(arg, System.in, System.out);
		assertEquals(expectedOutput, outContent.toString());
	}

	@Test(expected=HeadException.class)
	public void testEmptyStdoutWithNullStdoutException() throws HeadException {
		String[] arg = {"head"};
		headApp.run(arg, System.in, null);
	}
}
