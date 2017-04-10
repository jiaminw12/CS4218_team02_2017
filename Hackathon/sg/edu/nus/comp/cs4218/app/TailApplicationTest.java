package sg.edu.nus.comp.cs4218.app;

import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import sg.edu.nus.comp.cs4218.exception.HeadException;
import sg.edu.nus.comp.cs4218.exception.TailException;
import sg.edu.nus.comp.cs4218.impl.app.TailApplication;

public class TailApplicationTest {
	private static TailApplication tail;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final static String PATH_SEPARATOR = File.separator;
	private final static String LINE_SEPARATOR = System
			.getProperty("line.separator");
	private final static String RELATIVE_TEST_DIRECTORY = "folder"
			+ PATH_SEPARATOR;
	private final static String ABSOLUTE_TEST_DIRECTORY = new File(
			RELATIVE_TEST_DIRECTORY).getAbsolutePath() + PATH_SEPARATOR;
	private final static String TESTSTRING11LINES = "1" + LINE_SEPARATOR + "2"
			+ LINE_SEPARATOR + "3" + LINE_SEPARATOR + "4" + LINE_SEPARATOR + "5"
			+ LINE_SEPARATOR + "6" + LINE_SEPARATOR + "7" + LINE_SEPARATOR + "8"
			+ LINE_SEPARATOR + "9" + LINE_SEPARATOR + "10" + LINE_SEPARATOR
			+ "11";
	private final static String TESTSTRING10LINES = "2" + LINE_SEPARATOR + "3"
			+ LINE_SEPARATOR + "4" + LINE_SEPARATOR + "5" + LINE_SEPARATOR + "6"
			+ LINE_SEPARATOR + "7" + LINE_SEPARATOR + "8" + LINE_SEPARATOR + "9"
			+ LINE_SEPARATOR + "10" + LINE_SEPARATOR + "11";

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10);

	@Before
	public void setUp() throws Exception {
		tail = new TailApplication();
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void tearDown() throws Exception {
		System.setOut(null);
		tail = null;
	}

	@Test(expected = TailException.class)
	public void testEmptyStdoutWithNullStdoutException() throws TailException {
		String[] arg = { "tail" };
		tail.run(arg, System.in, null);
	}

	@Test
	public void testValidRelativeFilePathNonExistentFileDefaultException()
			throws TailException {
		String file = RELATIVE_TEST_DIRECTORY + "input" + PATH_SEPARATOR
				+ "invalidfile";
		String[] arg = { "tail", file };
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(new byte[1]);
			System.setIn(in);
			tail.run(arg, System.in, System.out);
		} catch (TailException e) {
			assertEquals("tail: File does not found", e.getMessage());
		}
	}
	
	@Test
	public void testValidAbsoluteFilePathNonExistentFileDefaultException()
			throws TailException {
		String file = ABSOLUTE_TEST_DIRECTORY + "input" + PATH_SEPARATOR
				+ "invalidfile";
		String[] arg = { "tail", file };
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(new byte[1]);
			System.setIn(in);
			tail.run(arg, System.in, System.out);
		} catch (TailException e) {
			assertEquals("tail: File does not found", e.getMessage());
		}
	}

	@Test
	public void testPrintFromStdinWithEmptyArg() throws TailException {
		String[] arg = { "tail" };
		String testStdinInput = TESTSTRING11LINES;
		String expectedOutput = TESTSTRING10LINES;
		ByteArrayInputStream in = new ByteArrayInputStream(
				testStdinInput.getBytes());
		System.setIn(in);
		tail.run(arg, System.in, System.out);
		assertEquals(expectedOutput, outContent.toString().trim());
	}

}
