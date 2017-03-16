package sg.edu.nus.comp.cs4218.integrationtest;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class ChainOfInteractionPiping {

	private static final char FILE_SEPARATOR = File.separatorChar;
	private static final String TEST_FILE_GREP_SORT = String.format(
			"folder%sGrepAndSortFiles%stest.txt", FILE_SEPARATOR,
			FILE_SEPARATOR, FILE_SEPARATOR);
	private static final String TEST_FILE_SINGLE_WORD = String.format(
			"folder%sSedAndWCFiles%swcTestFiles%ssingleWord.txt",
			FILE_SEPARATOR, FILE_SEPARATOR, FILE_SEPARATOR);
	private static final String TEST_FILE_TITLES = String.format(
			"folder%sSedAndWCFiles%stitles.txt", FILE_SEPARATOR,
			FILE_SEPARATOR);
	private static final File TITLE_FILES = new File(TEST_FILE_TITLES);
	private static final long TITLE_FILES_TOTAL_BYTES = TITLE_FILES.length();
	private static final String TEST_FILE_SLICING = "slicing.txt";
	private static final String LINE_SEPARATOR = System.lineSeparator();
	String expected;
	ShellImpl shellImpl;
	ByteArrayOutputStream stdout;
	PrintStream print;

	@Before
	public void setUp() throws Exception {
		shellImpl = new ShellImpl();
		stdout = new ByteArrayOutputStream();
		print = new PrintStream(stdout);
	}

	@Test
	public void testPipeWithEchoSort()
			throws AbstractApplicationException, ShellException {
		expected = shellImpl
				.pipeTwoCommands("echo hello apple pineapple pen | sort "
						+ TEST_FILE_SLICING);
		assertEquals("Program slicing can be used in debugging to locate source of errors more easily."
				+ LINE_SEPARATOR, expected);
	}

	@Test
	public void testPipeWithEchoHead()
			throws AbstractApplicationException, ShellException {
		expected = shellImpl.pipeTwoCommands("echo Lord/lalala/h&&&H | head -n5");
		assertEquals("Lord/lalala/h&&&H" + LINE_SEPARATOR, expected);
	}

	@Test
	public void testPipeWithCatTail()
			throws AbstractApplicationException, ShellException {
		expected = shellImpl.pipeTwoCommands(
				"cat " + TEST_FILE_SINGLE_WORD + "| tail -n 100 ");
		assertEquals("Hello\n", expected);
	}

	@Test
	public void testPipeWithGrepCat()
			throws AbstractApplicationException, ShellException {
		expected = shellImpl.pipeTwoCommands("grep Lord " + TEST_FILE_TITLES
				+ " | cat < " + TEST_FILE_SLICING);
		assertEquals("Program slicing can be used in debugging to locate source of errors more easily."
				+ System.getProperty("line.separator"), expected);
	}

	@Test
	public void testPipeWithCalGrep()
			throws AbstractApplicationException, ShellException {
		expected = shellImpl.pipeTwoCommands("cal | grep 12");
		assertEquals("12 13 14 15 16 17 18" + System.getProperty("line.separator"), expected);
	}

	@Test
	public void testPipeWithCdCat()
			throws AbstractApplicationException, ShellException {
		String currentPath = Environment.currentDirectory;
		expected = shellImpl.pipeTwoCommands("cd folder | cat farfaraway.txt");
		assertEquals("Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts." + System.getProperty("line.separator")
			+ "Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean." + System.getProperty("line.separator")
			+ "A small river named Duden flows by their place and supplies it with the necessary regelialia." + System.getProperty("line.separator")
			+ "It is a paradisematic country, in which roasted parts of sentences fly into your mouth." + System.getProperty("line.separator")
			+ "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti "
			+ "atque corrupti quos dolores et quas molestias excepturi sint."  + System.getProperty("line.separator"), expected);

		Environment.currentDirectory = currentPath;
	}

	@Test
	public void testPipeWithCatSed()
			throws AbstractApplicationException, ShellException {
		expected = shellImpl.pipeTwoCommands(
				"sed s/e/fff/ " + TEST_FILE_SLICING + " | cat");
		assertEquals("Program slicing can bfff used in debugging to locate source of errors more easily."
				+ System.getProperty("line.separator"), expected);
	}

	@Test
	public void testPipeWithEchoDate()
			throws AbstractApplicationException, ShellException {
		expected = shellImpl
				.pipeTwoCommands("echo 'I---m going to Paris!!!@@## ' | date");
		java.util.Date now = new java.util.Date();
		String actualResult = now.toString() + System.getProperty("line.separator");
		assertEquals(actualResult, expected);
	}

	@Test
	public void testPipeWithSortCat()
			throws AbstractApplicationException, ShellException {
		expected = shellImpl.pipeTwoCommands(
				"sort folder/GrepAndSortFiles/greptestdoc2.txt | cat");
		assertEquals("123" + System.getProperty("line.separator")
			+ "CBA" + System.getProperty("line.separator")
			+ "DEF" + System.getProperty("line.separator")
			+ "FGH" + System.getProperty("line.separator")
			+ "His" + System.getProperty("line.separator")
			+ "ello milo" + System.getProperty("line.separator"), expected);
	}

	@Test
	public void testPipeWithGrepSort()
			throws AbstractApplicationException, ShellException {
		expected = shellImpl.pipeTwoCommands(
				"grep h* " + TEST_FILE_GREP_SORT + " folder/GrepAndSortFiles/greptestdoc2.txt | sort");
		assertEquals("123" + System.getProperty("line.separator")
				+ "CBA" + System.getProperty("line.separator")
				+ "DEF" + System.getProperty("line.separator")
				+ "FGH" + System.getProperty("line.separator")
				+ "His" + System.getProperty("line.separator")
				+ "Just To Test" + System.getProperty("line.separator")
				+ "ello milo" + System.getProperty("line.separator"), expected);
	}

	@Test
	public void testPipeWithCatWc()
			throws AbstractApplicationException, ShellException {
		expected = shellImpl
				.pipeTwoCommands("cat " + TEST_FILE_TITLES + " | wc -lmw");
		assertEquals("    4128     717       0 " + LINE_SEPARATOR, expected);
	}

	@Test
	public void testPipeWithCatSortWc()
			throws AbstractApplicationException, ShellException {
		expected = shellImpl.pipeMultipleCommands(
				"cat " + TEST_FILE_TITLES + " | sort | wc -lmw");
		assertEquals("    4128     717       0 " + LINE_SEPARATOR, expected);
	}
	
	@Test
	public void testPipeWithGrepTailSed()
			throws AbstractApplicationException, ShellException {
		expected = shellImpl.pipeMultipleCommands(
				"grep *lia farfaraway.txt | tail | sed s/lia/ail");
		assertEquals("", expected);
	}
	
	@Test
	public void testPipeWithCatHeadTail()
			throws AbstractApplicationException, ShellException {
		expected = shellImpl.pipeMultipleCommands(
				"cat farfaraway.txt | head -n 1 | tail -n 1");
		assertEquals("Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts."
				+ System.getProperty("line.separator"), expected);
	}
	
	@Test
	public void testPipeWithHeadWcGrep()
			throws AbstractApplicationException, ShellException {
		expected = shellImpl.pipeMultipleCommands(
				"head -n 3 farfaraway.txt | grep lia | wc -w");
		assertEquals("      35 " + LINE_SEPARATOR , expected);
	}
	
	@Test
	public void testPipeWithHeadWcGrepWithInvalidFile()
			throws AbstractApplicationException, ShellException {
		expected = shellImpl.pipeWithException(
				"head -n 3 nosuchfile.txt | wc -w | grep lia*");
		assertEquals("head: Invalid arguments", expected);
	}
	
	@Test
	public void testPipeWithException01()
			throws AbstractApplicationException, ShellException {
		expected = shellImpl.pipeWithException(
				"head -n 3 farfaraway.txt | wc -w | | grep lia*");
		assertEquals("shell: : Invalid app.", expected);
	}
	
	

}
