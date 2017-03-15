package sg.edu.nus.comp.cs4218.impl.app;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.WcException;

public class WcApplicationTest02 {
	private static final char FILE_SEPARATOR = File.separatorChar;
	private static final String TEST_FILE_EMPTY = String.format(
			"folder%sSedAndWCFiles%swcTestFiles%sempty.txt", FILE_SEPARATOR,
			FILE_SEPARATOR, FILE_SEPARATOR);
	private static final String TEST_FILE_SINGLE_WORD = String.format(
			"folder%sSedAndWCFiles%swcTestFiles%ssingleWord.txt",
			FILE_SEPARATOR, FILE_SEPARATOR, FILE_SEPARATOR);
	private static final String TEST_FILE_TITLES = String.format(
			"folder%sSedAndWCFiles%stitles.txt", FILE_SEPARATOR,
			FILE_SEPARATOR);
	private static final File TITLE_FILES = new File(TEST_FILE_TITLES);
	private static final long TITLE_FILES_TOTAL_BYTES = TITLE_FILES.length();
	private static final String TEST_FILE_NAME_HAS_SPACE = String.format(
			"folder%sSedAndWCFiles%snamehasspace.txt", FILE_SEPARATOR,
			FILE_SEPARATOR);
	private static final String LINE_SEPARATOR = System.lineSeparator();

	WcApplication app;
	ByteArrayOutputStream stdout;
	PrintStream print;

	@Before
	public void setUp() throws Exception {
		app = new WcApplication();
		stdout = new ByteArrayOutputStream();
		print = new PrintStream(stdout);
	}

	@Test(expected = WcException.class)
	public void testWcWithNullStdout() throws WcException {
		String[] args = null;
		app.run(args, System.in, null);
	}

	@Test(expected = WcException.class)
	public void testWcWithNullArgsAndNullStdin() throws WcException {
		String[] args = null;
		app.run(args, null, stdout);
	}

	@Test(expected = WcException.class)
	public void testWcWithEmptyArgsAndNullStdin() throws WcException {
		String[] args = new String[0];
		app.run(args, null, stdout);
	}

	@Test
	public void testWcWithEmptyFile() throws WcException {
		String filePath = TEST_FILE_EMPTY;
		String[] args = { "wc", "-lmw", filePath };
		app.run(args, System.in, stdout);
		assertEquals(String.format("       0       0       0 %s%s", filePath,
				LINE_SEPARATOR), stdout.toString());
	}

	@Test
	public void testWcWithNoFlag() throws WcException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "wc", filePath };
		app.run(args, System.in, stdout);
		assertEquals(
				"       5       1       0 folder/SedAndWCFiles/wcTestFiles/singleWord.txt"
						+ System.lineSeparator(),
				stdout.toString());
	}

	@Test(expected = WcException.class)
	public void testWcWithInvalidFlag() throws WcException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "wc", "-x", filePath };
		app.run(args, System.in, stdout);
	}

	@Test(expected = WcException.class)
	public void testWcWithMixValidAndInvalidFlags() throws WcException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "wc", "-m", "-x", "-l", filePath };
		app.run(args, System.in, stdout);
	}

	@Test(expected = WcException.class)
	public void testWcWithInvalidComplexFlags() throws WcException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "wc", "-mxl", filePath };
		app.run(args, System.in, stdout);
	}

	@Test
	public void testIllegalOption02() throws WcException {
		String[] args = { "wc", "m", "farfaraway.txt" };
		app.run(args, System.in, stdout);
		assertEquals("m is not a file" + System.lineSeparator()
				+ "     565      90       4 farfaraway.txt"
				+ System.lineSeparator(), stdout.toString());
	}

	@Test
	public void testWcWithOnlyCharFlag() throws WcException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "wc", "-m", filePath };
		app.run(args, System.in, stdout);
		assertEquals(String.format("       5 %s%s", filePath, LINE_SEPARATOR),
				stdout.toString());
	}

	@Test
	public void testWcWithOnlyWordFlag() throws WcException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "wc", "-w", filePath };
		app.run(args, System.in, stdout);
		assertEquals(String.format("       1 %s%s", filePath, LINE_SEPARATOR),
				stdout.toString());
	}

	@Test
	public void testWcWithOnlyNewlineFlag() throws WcException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "wc", "-l", filePath };
		app.run(args, System.in, stdout);
		assertEquals(String.format("       0 %s%s", filePath, LINE_SEPARATOR),
				stdout.toString());
	}

	@Test
	public void testWcWithComplexFlags() throws WcException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "wc", "-mlw", filePath };
		app.run(args, System.in, stdout);
		assertEquals(String.format("       5       1       0 %s%s", filePath,
				LINE_SEPARATOR), stdout.toString());
	}

	@Test
	public void testWcWithMultipleSingleFlags() throws WcException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "wc", "-w", "-m", "-l", filePath };
		app.run(args, System.in, stdout);
		assertEquals(String.format("       5       1       0 %s%s", filePath,
				LINE_SEPARATOR), stdout.toString());
	}

	@Test
	public void testWcWithMixFlags() throws WcException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "wc", "-w", "-ml", "-mlw", filePath };
		app.run(args, System.in, stdout);
		assertEquals(String.format("       5       1       0 %s%s", filePath,
				LINE_SEPARATOR), stdout.toString());
	}

	@Test
	public void testWcWithRepeativeSingleFlags() throws WcException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "wc", "-w", "-w", "-w", filePath };
		app.run(args, System.in, stdout);
		assertEquals(String.format("       1 %s%s", filePath, LINE_SEPARATOR),
				stdout.toString());
	}

	@Test
	public void testWcWithDuplicateFlags() throws WcException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "wc", "-wwwww", filePath };
		app.run(args, System.in, stdout);
		assertEquals(String.format("       1 %s%s", filePath, LINE_SEPARATOR),
				stdout.toString());
	}

	@Test
	public void testWcWithDifferentFlagOrders() throws WcException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args1 = { "wc", "-wlm", filePath };
		app.run(args1, System.in, stdout);
		String[] args2 = { "wc", "-lmw", filePath };
		app.run(args2, System.in, stdout);
		assertEquals(
				String.format(
						"       5       1       0 %s%s       5       1       0 %s%s",
						filePath, LINE_SEPARATOR, filePath, LINE_SEPARATOR),
				stdout.toString());
	}

	@Test
	public void testWcWithOutOfOrderFlag() throws WcException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "wc", filePath, "-w" };
		try {
			app.run(args, System.in, stdout);
		} catch (WcException e) {
			assertEquals(String.format("      5      1      0 %s%s", filePath,
					LINE_SEPARATOR), stdout.toString());
			assertEquals("wc: Could not read file", e.getMessage());
		}
	}

	@Test
	public void testWcWithInOrderFlagAndOutOfOrderFlag() throws WcException {
		String filePath = TEST_FILE_SINGLE_WORD;
		String[] args = { "wc", "-w", filePath, "-w" };
		try {
			app.run(args, System.in, stdout);
		} catch (WcException e) {
			assertEquals(
					String.format("      1 %s%s", filePath, LINE_SEPARATOR),
					stdout.toString());
			assertEquals("wc: Could not read file", e.getMessage());
		}
	}

	@Test
	public void testWcWithMultipleFiles() throws WcException {
		String[] args = { "wc", "-lmw", TEST_FILE_EMPTY, TEST_FILE_SINGLE_WORD,
				TEST_FILE_TITLES };
		app.run(args, System.in, stdout);
		String emptyFileExpected = String.format(
				"       0       0       0 %s%s", TEST_FILE_EMPTY,
				LINE_SEPARATOR);
		String singleWordFileExpected = String.format(
				"       5       1       0 %s%s", TEST_FILE_SINGLE_WORD,
				LINE_SEPARATOR);
		String titlesFileExpected = String.format("%8d     717     250 %s%s",
				TITLE_FILES_TOTAL_BYTES, TEST_FILE_TITLES, LINE_SEPARATOR);
		long totalBytes = TITLE_FILES_TOTAL_BYTES
				+ new File(TEST_FILE_SINGLE_WORD).length() + 0;
		String totalExpected = String.format("%8d     718     250 total%s",
				totalBytes, LINE_SEPARATOR);
		String expectedResult = String.format("%s%s%s%s", emptyFileExpected,
				singleWordFileExpected, titlesFileExpected, totalExpected);
		assertEquals(expectedResult, stdout.toString());
	}

	@Test
	public void testWcWithInvalidFiles() throws WcException {
		String[] args = { "wc", "-lmw", TEST_FILE_EMPTY, "-w",
				TEST_FILE_SINGLE_WORD };
		app.run(args, System.in, stdout);
		String emptyFileExpected = String.format(
				"       0       0       0 %s%s", TEST_FILE_EMPTY,
				LINE_SEPARATOR);
		String singleWordFileExpected = String.format(
				"       5       1       0 %s%s", TEST_FILE_SINGLE_WORD,
				LINE_SEPARATOR);
		String totalExpected = String.format("       5       1       0 total%s",
				LINE_SEPARATOR);
		String expectedResult = String.format("%s%s%s", emptyFileExpected,
				singleWordFileExpected, totalExpected);
		assertEquals(expectedResult, stdout.toString());
	}

	@Test
	public void testWcReadFromStdin() throws WcException {
		String[] args = { "wc", "-lmw" };
		ByteArrayInputStream in = new ByteArrayInputStream(
				"hello world".getBytes());
		app.run(args, in, stdout);
		String expected = String.format("      11       2       0 %s",
				LINE_SEPARATOR);
		assertEquals(expected, stdout.toString());
	}

	@Test
	public void testWcReadFromStdinWithFlag() throws WcException {
		String[] args = { "wc", "-m" };
		ByteArrayInputStream in = new ByteArrayInputStream(
				"hello world".getBytes());
		app.run(args, in, stdout);
		String expected = String.format("      11 %s", LINE_SEPARATOR);
		assertEquals(expected, stdout.toString());
	}

	@Test(expected = WcException.class)
	public void testWcReadFromStdinWithInvalidFlag() throws WcException {
		String[] args = { "wc", "-x" };
		ByteArrayInputStream in = new ByteArrayInputStream(
				"hello world".getBytes());
		app.run(args, in, stdout);
	}

	@Test
	public void testWcWithBothStdinAndFile() throws WcException {
		String[] args = { "wc", "-lmw", TEST_FILE_SINGLE_WORD };
		ByteArrayInputStream in = new ByteArrayInputStream(
				"not single word".getBytes());
		String singleWordFileExpected = String.format(
				"       5       1       0 %s%s", TEST_FILE_SINGLE_WORD,
				LINE_SEPARATOR);
		app.run(args, in, stdout);
		assertEquals(singleWordFileExpected, stdout.toString());
	}

	@Test
	public void testWcWithFileNameContainSpace() throws WcException {
		String[] args = { "wc", "-lmw", TEST_FILE_NAME_HAS_SPACE };
		File testFile = new File(TEST_FILE_NAME_HAS_SPACE);
		long byteCount = testFile.length();
		String singleWordFileExpected = String.format(
				"%8d       6       5 %s%s", byteCount, TEST_FILE_NAME_HAS_SPACE,
				LINE_SEPARATOR);
		app.run(args, System.in, stdout);
		assertEquals(singleWordFileExpected, stdout.toString());
	}

	@Test
	public void testPrintCharacterCountInFile() {
		String expected = String.format("%8d %s%s", TITLE_FILES_TOTAL_BYTES,
				TEST_FILE_TITLES, LINE_SEPARATOR);
		String result = app.printCharacterCountInFile(
				String.format("wc -m %s", TEST_FILE_TITLES));
		assertEquals(expected, result);
	}

	@Test
	public void testPrintCharacterCountInInvalidFile() {
		String result = app.printCharacterCountInFile("wc -m invalid.txt");
		assertEquals("invalid.txt is not a file", result.trim());
	}

	@Test
	public void testPrintWordCountInFile() {
		String expected = String.format("     717 %s%s", TEST_FILE_TITLES,
				LINE_SEPARATOR);
		String result = app.printWordCountInFile(
				String.format("wc -w %s", TEST_FILE_TITLES));
		assertEquals(expected, result);
	}

	@Test
	public void testPrintWordCountInInvalidFile() {
		String result = app.printWordCountInFile("wc -m invalid.txt");
		assertEquals("invalid.txt is not a file", result.trim());
	}

	@Test
	public void testPrintNewlineCountInFile() {
		String expected = String.format("     250 %s%s", TEST_FILE_TITLES,
				LINE_SEPARATOR);
		String result = app.printNewlineCountInFile(
				String.format("wc -l %s", TEST_FILE_TITLES));
		assertEquals(expected, result);
	}

	@Test
	public void testPrintNewlineCountInInvalidFile() {
		String expected = String.format("invalid.txt is not a file%s",
				LINE_SEPARATOR);
		String result = app.printNewlineCountInFile("wc -l invalid.txt");
		assertEquals(expected, result);
	}

	@Test
	public void printAllCountsInFile() {
		String expected = String.format("%8d     717     250 %s%s",
				TITLE_FILES_TOTAL_BYTES, TEST_FILE_TITLES, LINE_SEPARATOR);
		String result = app.printAllCountsInFile(
				String.format("wc -lmw %s", TEST_FILE_TITLES));
		assertEquals(expected, result);
	}

	@Test
	public void printAllCountsInInvalidFile() {
		String result = app.printAllCountsInFile("wc -m invalid.txt");
		assertEquals("invalid.txt is not a file", result.trim());
	}

}
