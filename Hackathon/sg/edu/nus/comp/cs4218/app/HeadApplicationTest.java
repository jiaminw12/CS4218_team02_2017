package sg.edu.nus.comp.cs4218.app;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sg.edu.nus.comp.cs4218.exception.HeadException;
import sg.edu.nus.comp.cs4218.exception.TailException;
import sg.edu.nus.comp.cs4218.impl.app.HeadApplication;

public class HeadApplicationTest {
	private static HeadApplication head;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final static String PATH_SEPARATOR = File.separator;
	private final static String LINE_SEPARATOR = System
			.getProperty("line.separator");
	private final static String RELATIVE_TEST_DIRECTORY = "folder"
			+ PATH_SEPARATOR;
	private final static String TEST_STRING_11_LINES = "1" + LINE_SEPARATOR
			+ "2" + LINE_SEPARATOR + "3" + LINE_SEPARATOR + "4" + LINE_SEPARATOR
			+ "5" + LINE_SEPARATOR + "6" + LINE_SEPARATOR + "7" + LINE_SEPARATOR
			+ "8" + LINE_SEPARATOR + "9" + LINE_SEPARATOR + "10"
			+ LINE_SEPARATOR + "11";

	private final static String TEST_STRING_10_LINES = "1" + LINE_SEPARATOR
			+ "2" + LINE_SEPARATOR + "3" + LINE_SEPARATOR + "4" + LINE_SEPARATOR
			+ "5" + LINE_SEPARATOR + "6" + LINE_SEPARATOR + "7" + LINE_SEPARATOR
			+ "8" + LINE_SEPARATOR + "9" + LINE_SEPARATOR + "10";

	@Before
	public void setUp() throws Exception {
		head = new HeadApplication();
		System.setOut(new PrintStream(outContent));
	}
	
	@Test
	public void testInvalidFileException() {
		String file = RELATIVE_TEST_DIRECTORY + "input"
				+ PATH_SEPARATOR + "testHead11Linesa";
		String[] arg = { "head", "-n", "10", file };
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(new byte[1]);
			System.setIn(in);
			head.run(arg, System.in, System.out);
		} catch (HeadException e) {
			assertEquals("head: File does not found", e.getMessage());
		}
	}

	@Test
	public void testPrintFromStdinWithEmptyArg() throws HeadException {
		String[] arg = { "head" };
		String testStdinInput = TEST_STRING_11_LINES;
		String expectedOutput = TEST_STRING_10_LINES;
		ByteArrayInputStream in = new ByteArrayInputStream(
				testStdinInput.getBytes());
		System.setIn(in);
		head.run(arg, System.in, System.out);
		assertEquals(expectedOutput, outContent.toString().trim());
	}

	@Test(expected = HeadException.class)
	public void testEmptyStdoutWithNullStdoutException() throws HeadException {
		String[] arg = { "head" };
		head.run(arg, System.in, null);
	}

}
