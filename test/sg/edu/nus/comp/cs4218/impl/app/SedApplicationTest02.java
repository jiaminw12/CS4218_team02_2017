package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.SedException;

/**
 * Unit Test For Sed Application
 *
 */
public class SedApplicationTest02 {
	private static final String FS = File.separator;
	private static final String NEWLINE = System.lineSeparator();
	private static final String TWO_LINE_FILE_PATH = "files" + FS + "sedTestFiles" + FS + "two-lines.txt";
	private static final String EMPTY_FILE_PATH = "files" + FS + "sedTestFiles" + FS + "empty.txt";
	private static final String NUMBER_FILE_PATH = "files" + FS + "sedTestFiles" + FS + "number.txt";
	private static final String HELLO_WORLD_FILE_PATH = "files" + FS + "sedTestFiles" + FS + "hello world.txt";

	private static SedApplication sed;
	private OutputStream stdout;
	private InputStream stdin;
	private static InputStream twoLineFileInputStream;
	private static InputStream emptyFileInputStream;
	private static InputStream numberFileInputStream;
	private static InputStream hellowWorldFileInputStream;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
//		Environment.resetCurrentDirectory();
		sed = new SedApplication();

	}

	@AfterClass
	public static void reset() {
//		Environment.resetCurrentDirectory();
	}

	@Before
	public void setUp() throws Exception {
		String complex = "Hey, good to know <you>!\n"
				+ "This is a small file consists of {1+1+0} lines.\n"
				+ "/* Hope this helps */ # no new line here";
		stdout = new ByteArrayOutputStream();
		twoLineFileInputStream = new ByteArrayInputStream(complex.getBytes());
		emptyFileInputStream = new ByteArrayInputStream("".getBytes());
		numberFileInputStream =  new ByteArrayInputStream("0123456789".getBytes());
		hellowWorldFileInputStream = new ByteArrayInputStream("hello world".getBytes());
	}

	@Test(expected = SedException.class)
	public void testSedWithNullArgument() throws SedException {
		String args[] = null;
		stdin = hellowWorldFileInputStream;
		sed.run(args, stdin, stdout);
		// "error on sed command - fails to throw exception with null args";
	}

	@Test(expected = SedException.class)
	public void testSedWithEmptyArgument() throws SedException {
		String args[] = {};
		stdin = new ByteArrayInputStream("".getBytes());
		sed.run(args, stdin, stdout);
		// "error on sed command - fails to throw exception with empty args";
	}

	@Test(expected = SedException.class)
	public void testSedWithSingleArgument() throws SedException {
		String args[] = { "sed", "arg1" };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - fails to throw exception with insuffcient
		// args";
	}

	@Test(expected = SedException.class)
	public void testSedWithNullStdout() throws SedException {
		String args[] = { "sed", "s-c-a-g", EMPTY_FILE_PATH };
		stdin = new ByteArrayInputStream("".getBytes());
		stdout = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - fails to throw exception with empty stdout";
	}

	@Test(expected = SedException.class)
	public void testSedWithNullStdinAndNonFileArg() throws SedException {
		String args[] = { "sed", "s|a|b|" };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - fails to throw exception with null stdin and
		// non file arg";
	}

	@Test(expected = SedException.class)
	public void testSedWithNullStdinAndNonExistentFile() throws SedException {
		String args[] = { "sed", "s|a|b|", "non-existent.txt" };
		stdin = null;
		sed.run(args, stdin, stdout);
		// String msg =
		// "error on sed command - fails to throw exception with null stdin and
		// non-existent file ";
	}

	@Test(expected = SedException.class)
	public void testSedWithEmptyFile() throws SedException {
		String args[] = { "sed", "s|a|b|", EMPTY_FILE_PATH };
		stdin = null;
		String expected = NEWLINE;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with empty file";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test(expected = SedException.class)
	public void testSedWithTwoLineFile() throws SedException {
		String args[] = { "sed", "s|a|b|", TWO_LINE_FILE_PATH };
		stdin = null;
		String expected = "Hey, good to know <you>!" + NEWLINE + "This is b small file consists of {1+1+0} lines."
				+ NEWLINE + "/* Hope this helps */ # no new line here" + NEWLINE;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with two line file";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithFileThatNameIncludesSpace() throws SedException {
		String args[] = { "sed", "s|1|2|", HELLO_WORLD_FILE_PATH };
		stdin = emptyFileInputStream;
		sed.run(args, stdin, stdout);
	}

	@Test
	public void testSedWithEmptyFileInputStream() throws SedException {
		String args[] = { "sed", "s|a|b|" };
		stdin = emptyFileInputStream;
		sed.run(args, stdin, stdout);
	}

	@Test
	public void testSedWithNumberFileInputStream() throws SedException {
		String args[] = { "sed", "s*3*76*" };
		stdin = numberFileInputStream;
		String expected = "01276456789";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with two line file input stream";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithTwoLineFileInputStream() throws SedException {
		// mock current directory to a fake non-root one
		String args[] = { "sed", "s|a|b|" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, good to know <you>!" + NEWLINE + "This is b small file consists of {1+1+0} lines."
				+ NEWLINE + "/* Hope this helps */ # no new line here";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with two line file input stream";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithTwoLineFileInputStreamAndEmptyFile() throws SedException {
		String args[] = { "sed", "s|a|b|", EMPTY_FILE_PATH };
		stdin = twoLineFileInputStream;
		sed.run(args, stdin, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedWithExtraArgs1() throws SedException {
		String args[] = { "sed", "s|a|b|", EMPTY_FILE_PATH, "-l" };
		stdin = null;
		String expected = NEWLINE;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with extra redundant arg";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test(expected = SedException.class)
	public void testSedWithExtraArgs2() throws SedException {
		String args[] = { "sed", "s|0|1|", NUMBER_FILE_PATH, TWO_LINE_FILE_PATH, "-l" };
		stdin = null;
		String expected = "1123456789" + NEWLINE;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with extra redundant arg";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test(expected = SedException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat1() throws SedException {
		String args[] = { "sed", "|0|1|", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = SedException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat2() throws SedException {
		// mock current directory to a fake non-root one
		String args[] = { "sed", "s|0|1|gg", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = SedException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat3() throws SedException {
		String args[] = { "sed", "s-0|1|g", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = SedException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat4() throws SedException {
		String args[] = { "sed", "s-0|1g", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = SedException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat5() throws SedException {
		String args[] = { "sed", "s|0| m| |g", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = SedException.class)
	public void testSedWithInvalidReplacementOnIllegalRegex1() throws SedException {
		String args[] = { "sed", "s|0| m| |g", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal regular expression";
	}

	@Test(expected = SedException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat6() throws SedException {
		String args[] = { "sed", "s||0||1||", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = SedException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat7() throws SedException {
		// mock current directory to a fake non-root one
		String args[] = { "sed", "s|", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = SedException.class)
	public void testSedWithInvalidReplacementOnIllegalFormat8() throws SedException {
		String args[] = { "sed", "m|1|2|", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal format";
	}

	@Test(expected = SedException.class)
	public void testSedWithInvalidReplacementOnIllegalRegrex() throws SedException {
		String args[] = { "sed", "s|[|1|", NUMBER_FILE_PATH };
		stdin = null;
		sed.run(args, stdin, stdout);
		// "error on sed command - incorrect output with invalid replacement
		// that has illegal regrex";
	}

	@Test(expected = SedException.class)
	public void testSedWithDifferentSeparator1() throws SedException {
		String args[] = { "sed", "ssls*sg" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, good to know <you>!" + NEWLINE + "This is a sma** fi*e consists of {1+1+0} *ines."
				+ NEWLINE + "/* Hope this he*ps */ # no new *ine here" + NEWLINE;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with different separator";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test(expected = SedException.class)
	public void testSedWithEmptyReplacment() throws SedException {
		String args[] = { "sed", "s|l||" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, good to know <you>!" + NEWLINE + "This is a smal file consists of {1+1+0} lines."
				+ NEWLINE + "/* Hope this heps */ # no new line here" + NEWLINE;
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with empty replacement";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithComplexReplacement1() throws SedException {
		String args[] = { "sed", "s|no| *&/s\\$|" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, good to k *&/s$w <you>!" + NEWLINE + "This is a small file consists of {1+1+0} lines."
				+ NEWLINE + "/* Hope this helps */ #  *&/s$ new line here";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with complex replacement";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithComplexReplacement2() throws SedException {
		String args[] = { "sed", "s|o|[^]|" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, g[^]od to know <you>!" + NEWLINE + "This is a small file c[^]nsists of {1+1+0} lines."
				+ NEWLINE + "/* H[^]pe this helps */ # no new line here";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with complex replacement";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithComplexReplacement3() throws SedException {
		String args[] = { "sed", "s|o   |% #$%^&|" };
		stdin = twoLineFileInputStream;
		sed.run(args, stdin, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedWithEmtpyRegexpAndEmptyReplacement() throws SedException {
		String args[] = { "sed", "s|||" };
		stdin = twoLineFileInputStream;
		sed.run(args, stdin, stdout);
		// String msg =
		// "error on sed command - fail to throw exception with empty regular
		// expression and empty replacement";
	}

	@Test
	public void testSedWithComplexRegexp2() throws SedException {
		String args[] = { "sed", "s|o{2,3}d*|r|" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, gr to know <you>!" + NEWLINE + "This is a small file consists of {1+1+0} lines."
				+ NEWLINE + "/* Hope this helps */ # no new line here";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with complex regular expression";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithComplexRegexp4() throws SedException {
		String args[] = { "sed", "s-good|know-r-" };
		stdin = twoLineFileInputStream;
		String expected = "Hey, r to know <you>!" + NEWLINE + "This is a small file consists of {1+1+0} lines."
				+ NEWLINE + "/* Hope this helps */ # no new line here";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with complex regular expression";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testReplaceFirstSubStringInFile() throws SedException {
		String cmd = "sed \"s|o||\"  " + TWO_LINE_FILE_PATH;
		sed.replaceFirstSubStringInFile(cmd);
	}

	@Test
	public void testReplaceAllSubstringsInFile() throws SedException {
		String cmd = "sed \"s|o||g\"  " + TWO_LINE_FILE_PATH;
		sed.replaceAllSubstringsInFile(cmd);
	}

	public void testReplaceSubstringWithInvalidReplacement() throws SedException {
		String cmd = "cat " + TWO_LINE_FILE_PATH + " | sed \"s|||g\" ";
		String expected = "";
		String msg = "error on sed command - incorrect output with method replaceSubstringWithInvalidReplacement";
		assertEquals(msg, expected, sed.replaceSubstringWithInvalidReplacement(cmd));
	}

	@Test
	public void testReplaceSubstringWithInvalidRegex() throws SedException {
		String cmd = "cat " + TWO_LINE_FILE_PATH + " | sed \"s|[||g\" ";
		stdin = twoLineFileInputStream;
		sed.replaceSubstringWithInvalidRegex(cmd);
	}
}
