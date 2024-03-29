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

import sg.edu.nus.comp.cs4218.exception.TailException;

public class TailApplicationTest {

	static TailApplication tailApp;
	private ByteArrayOutputStream outContent;
	private final static String LINE_SEPARATOR = System.getProperty("line.separator");
	private final static String TESTSTRING11LINES = "1" + LINE_SEPARATOR + "2" + LINE_SEPARATOR + "3" + LINE_SEPARATOR
			+ "4" + LINE_SEPARATOR + "5" + LINE_SEPARATOR + "6" + LINE_SEPARATOR + "7" + LINE_SEPARATOR + "8"
			+ LINE_SEPARATOR + "9" + LINE_SEPARATOR + "10" + LINE_SEPARATOR + "11" + LINE_SEPARATOR;

	private final static String TESTSTRING10LINES = "2" + LINE_SEPARATOR + "3" + LINE_SEPARATOR + "4" + LINE_SEPARATOR
			+ "5" + LINE_SEPARATOR + "6" + LINE_SEPARATOR + "7" + LINE_SEPARATOR + "8" + LINE_SEPARATOR + "9"
			+ LINE_SEPARATOR + "10" + LINE_SEPARATOR + "11" + LINE_SEPARATOR;
	@BeforeClass
	public static void setUpOnce() {

	}

	@Before
	public void setUp() {
		tailApp = new TailApplication();
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void tearDown() {
		System.setOut(null);
	}

	@AfterClass
	public static void tearDownOnce() {
		tailApp = null;
	}

	@Test
	public void testCheckTxtFile() throws TailException {
		assertTrue(tailApp.checkValidFile(new File("muttest.txt").toPath()));
	}

	@Test
	public void testOtherFileType() throws TailException {
		assertTrue(tailApp.checkValidFile(new File("README.md").toPath()));
	}

	@Test(expected = TailException.class)
	public void testTailAppWithNullArgument() throws TailException {
		String[] args = {};
		tailApp.run(args, System.in, outContent);
	}

	@Test(expected = TailException.class)
	public void testTailAppWithIllegalOption() throws TailException {
		String[] args = { "tail", "-a", "15", "muttest.txt" };
		tailApp.run(args, System.in, outContent);
	}

	@Test(expected = TailException.class)
	public void testTailAppWith5Arguments() throws TailException {
		String[] args = { "tail", "-n", "15", "muttest.txt", "slicing.txt" };
		tailApp.run(args, System.in, outContent);
	}

	@Test(expected = TailException.class)
	public void testTailAppWithIllegalNumOfLinesUseNegativeDigits()
			throws TailException {
		String[] args = { "tail", "-n", "-2", "muttest.txt" };
		tailApp.run(args, System.in, outContent);
	}

	@Test(expected = TailException.class)
	public void testTailAppWithIllegalNumOfLinesUseChar() throws TailException {
		String[] args = { "tail", "-n", "aaa", "muttest.txt" };
		tailApp.run(args, System.in, outContent);
	}

	@Test(expected = TailException.class)
	public void testTailAppWithIllegalFile() throws TailException {
		String[] args = { "tail", "-n", "15", "muttestS.txt" };
		tailApp.run(args, System.in, outContent);
	}

	@Test(expected = TailException.class)
	public void testTailAppWith3Arguments() throws TailException {
		String[] args = { "tail", "-n", "muttest.txt" };
		tailApp.run(args, System.in, outContent);
	}

	@Test
	public void testTailAppWithIllegalNumOfLinesUseZero() throws TailException {
		String[] args = { "tail", "-n", "0", "muttest.txt" };
		tailApp.run(args, System.in, outContent);
	}

	@Test
	public void testTailAppWithoutOption() throws TailException {
		String[] args = { "tail", "slicing.txt" };
		tailApp.run(args, System.in, outContent);
		assertEquals(
				"Program slicing can be used in debugging to locate source of errors more easily."
						+ System.lineSeparator(),
				outContent.toString());
	}

	@Test
	public void testTailAppWithOption() throws TailException {
		String[] args = { "tail", "-n", "1", "muttest.txt" };
		tailApp.run(args, System.in, outContent);
		assertEquals("}" + System.lineSeparator(), outContent.toString());
	}

	@Test
	public void testTailAppWithMultiplesOption() throws TailException {
		String[] args = { "tail", "-n2", "-n5", "-n", "3", "muttest.txt" };
		tailApp.run(args, System.in, outContent);
		assertEquals(
				"    ps2.run();" + System.lineSeparator() + "  }"
						+ System.lineSeparator() + "}" + System.lineSeparator(),
				outContent.toString());
	}

	@Test
	public void testTailAppWithoutOptionInStdin() throws TailException {
		ByteArrayInputStream stdin = new ByteArrayInputStream(
				("They may be found on menus in restaurants that serve seafood. SEA SEA SEA"
						+ System.lineSeparator()).getBytes());
		String[] args = { "tail" };
		tailApp.run(args, stdin, outContent);
		assertEquals(
				"They may be found on menus in restaurants that serve seafood. SEA SEA SEA"
						+ System.lineSeparator(),
				outContent.toString());
	}

	@Test
	public void testTailAppWithOptionInStdin() throws TailException {
		ByteArrayInputStream stdin = new ByteArrayInputStream(
				("They may be found on menus in restaurants that serve seafood. SEA SEA SEA"
						+ System.lineSeparator()).getBytes());
		String[] args = { "tail", "-n", "1" };
		tailApp.run(args, stdin, outContent);
		assertEquals(
				"They may be found on menus in restaurants that serve seafood. SEA SEA SEA"
						+ System.lineSeparator(),
				outContent.toString());
	}

	@Test
	public void testTailAppWithMultiplesOptionInStdin() throws TailException {
		ByteArrayInputStream stdin = new ByteArrayInputStream(
				("They may be found on menus in restaurants that serve seafood. SEA SEA SEA"
						+ System.lineSeparator()).getBytes());
		String[] args = { "tail", "-n2", "-n5", "-n", "999" };
		tailApp.run(args, stdin, outContent);
		assertEquals(
				"They may be found on menus in restaurants that serve seafood. SEA SEA SEA"
						+ System.lineSeparator(),
				outContent.toString());
	}
	
	// Test application print from stdin behaviour
	
	//Should print default 10 lines from stdin instead of everything if no options specified, refer to project specification
	@Test
	public void testPrintFromStdinWithEmptyArg() throws TailException {
		String[] arg = {"tail"};
		String testStdinInput = TESTSTRING11LINES;
		String expectedOutput = TESTSTRING10LINES;
		ByteArrayInputStream in = new ByteArrayInputStream(testStdinInput.getBytes());
		System.setIn(in);
		tailApp.run(arg, System.in, System.out);
		assertEquals(expectedOutput, outContent.toString());
	}
	
	@Test(expected=TailException.class)
	public void testEmptyStdoutWith1ArgException() throws TailException {
		String[] arg = {"tail"};
		tailApp.run(arg, System.in, null);
	}
}
