package sg.edu.nus.comp.cs4218.app;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class QuotingTest {

	ShellImpl shell;
	ByteArrayOutputStream out;
	String input = "";

	@Before
	public void setupBeforeTest() {
		shell = new ShellImpl();
		out = new ByteArrayOutputStream();
	}

	@Test(expected = ShellException.class)
	public void testInputNull()
			throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate(null, out);
	}

	@Test
	public void testEmptyQuotesAppendedToAppName()
			throws AbstractApplicationException, ShellException {
		input = "echo'' hi there";
		shell.parseAndEvaluate(input, out);
		assertEquals("hi there" + System.lineSeparator(), out.toString());
	}

	@Test(expected = ShellException.class)
	public void testUnclosedDoubleQuoteWithinBackQuotes()
			throws AbstractApplicationException, ShellException {
		input = "echo `echo hi \" there`";
		shell.parseAndEvaluate(input, out);
	}

	@Test(expected = ShellException.class)
	public void testUnclosedSingleQuoteWithinDoubleQuotes()
			throws AbstractApplicationException, ShellException {
		input = "echo \"hi ' there\"";
		shell.parseAndEvaluate(input, out);
	}

	@Test
	public void testBackQuotesWithinSingleQuotesWithinDoubleQuotes()
			throws AbstractApplicationException, ShellException {
		input = "echo \" '`echo hi`' \"";
		shell.parseAndEvaluate(input, out);
		assertEquals(" `echo hi` " + System.lineSeparator(), out.toString());
	}

	@Test
	public void testDoubleQuotesWithinSingleQuotesWithinDoubleQuotes()
			throws AbstractApplicationException, ShellException {
		input = "echo \" '\"hi\"' \"";
		shell.parseAndEvaluate(input, out);
		assertEquals(" \"hi\" " + System.lineSeparator(), out.toString());
	}
	
	@Test(expected = ShellException.class)
	public void testTailWithSortNegativeUnclosedBackQuotes()
			throws AbstractApplicationException, ShellException {
		input = "echo hi|tail `echo -m 1``|sort";
		shell.parseAndEvaluate(input, out);
	}
	
	@Test
	public void testInvalidSubCmdSyntax() throws AbstractApplicationException, ShellException {
		String output = shell.performCommandSubstitutionWithException("echo `hi");
		String expectedOut = "shell: Invalid quoting.";
		assertEquals(expectedOut, output);
	}

	@Test
	public void testNewLineReplacedBySpace()
			throws AbstractApplicationException, ShellException {
		input = "echo `cat farfaraway.txt`";
		shell.parseAndEvaluate(input, out);
		assertEquals(
				"Far far away, behind the word mountains, far from the countries Vokalia"
						+ " and Consonantia, there live the blind texts. Separated they live in "
						+ "Bookmarksgrove right at the coast of the Semantics, a large language ocean. "
						+ "A small river named Duden flows by their place and supplies it with the "
						+ "necessary regelialia. It is a paradisematic country, in which roasted "
						+ "parts of sentences fly into your mouth. At vero eos et accusamus et iusto "
						+ "odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti "
						+ "atque corrupti quos dolores et quas molestias excepturi sint.",
				out.toString().trim());
	}

	@Test
	public void testBackquoteAsOutput()
			throws AbstractApplicationException, ShellException {
		input = "cal `echo 4 2017`";
		shell.parseAndEvaluate(input, out);
		String expected = "      April 2017    " + System.lineSeparator()
				+ "Su Mo Tu We Th Fr Sa" + System.lineSeparator()
				+ "                  1 " + System.lineSeparator()
				+ "2  3  4  5  6  7  8 " + System.lineSeparator()
				+ "9  10 11 12 13 14 15" + System.lineSeparator()
				+ "16 17 18 19 20 21 22" + System.lineSeparator()
				+ "23 24 25 26 27 28 29" + System.lineSeparator()
				+ "30                  " + System.lineSeparator();
		assertEquals(expected, out.toString());
	}

}
