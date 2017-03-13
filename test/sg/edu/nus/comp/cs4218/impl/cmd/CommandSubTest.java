package sg.edu.nus.comp.cs4218.impl.cmd;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.junit.*;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class CommandSubTest {

	static ShellImpl shellImpl;
	ByteArrayOutputStream outputStream = null;

	@BeforeClass
	public static void setUpOnce() {
		// one-time initialization code
	}

	@Before
	public void setUp() {
		shellImpl = new ShellImpl();
	}

	@Test
	public void testIllegalApplicationName()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo `echa token1 `";
		String result = shellImpl.performCommandSubstitutionWithException(cmdLine);
		assertEquals("shell: echa: Invalid app.", result.trim());
	}

	@Test
	public void testIllegalNumOfBackQuoteAtBack()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo ` token1 ```";
		String result = shellImpl.performCommandSubstitutionWithException(cmdLine);
		assertEquals("shell: Invalid syntax encountered.", result.trim());
	}

	@Test
	public void testIllegalNumOfBackQuoteAtStart()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo ``` token1 `";
		String result = shellImpl.performCommandSubstitutionWithException(cmdLine);
		assertEquals("shell: Invalid syntax encountered.", result.trim());
	}

	@Test
	public void testIllegalCommand()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo `cat farfarway.txt |`";
		String result = shellImpl.performCommandSubstitutionWithException(cmdLine);
		assertEquals("shell: Invalid pipe operators", result.trim());
	}

	@Test
	public void testInvalidArg()
			throws ShellException, AbstractApplicationException, IOException {
		String cmdLine = "echo `hahaha.... echo` echo";
		String result = shellImpl.performCommandSubstitutionWithException(cmdLine);
		assertEquals("shell: hahaha....: Invalid app.", result.trim());
	}

	@Test
	public void testBQContainBQ()
			throws ShellException, AbstractApplicationException, IOException {
		String cmdLine = "echo `echo `echo testing ing ....``";
		String result = shellImpl.performCommandSubstitutionWithException(cmdLine);
		assertEquals("shell: Invalid syntax encountered.", result.trim());
	}

	@Test
	public void testEchoWithSemicolon() throws Exception {
		String cmdLine = "echo `echo ABC; echo DEF`";
		String result = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("ABCDEF", result.trim());
	}

	@Test
	public void testCommandSub() throws Exception {
		String cmdLine = "echo `cat farfaraway.txt`";
		String result = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals(
				"Far far away, behind the word mountains, far from the countries Vokalia "
						+ "and Consonantia, there live the blind texts.Separated they live in "
						+ "Bookmarksgrove right at the coast of the Semantics, a large language "
						+ "ocean.A small river named Duden flows by their place and supplies it "
						+ "with the necessary regelialia.It is a paradisematic country, in which "
						+ "roasted parts of sentences fly into your mouth.At vero eos et accusamus "
						+ "et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum "
						+ "deleniti atque corrupti quos dolores et quas molestias excepturi sint.",
				result.trim());
	}

	@Test
	public void testInputSingleQuote()
			throws IOException, AbstractApplicationException, ShellException {
		String cmdLine = "echo '`cat farfaraway.txt`' ";
		String result = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("`cat farfaraway.txt`", result.trim());
	}

	@Test
	public void testOneCommandSubWithNewLine()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo `cat " + System.lineSeparator() + "slicing.txt`";
		String result = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals(
				"Program slicing can be used in debugging to locate source of "
						+ "errors more easily.",
				result.trim());
	}

	@Test
	public void testOneCommandSubWithDoubleQuote()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo \"`echo popeye here`\"";
		String result = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("popeye here", result.trim());
	}

	@Test
	public void testOneCommandSub()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo `wc -l slicing.txt`";
		String result = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("0 slicing.txt", result.trim());
	}

	@Test
	public void testCommandSubWithPipeHeadWithNoOptions() throws Exception {
		String cmdLine = "echo `cat farfaraway.txt | head`";
		String result = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals(
				"Far far away, behind the word mountains, far from the countries "
						+ "Vokalia and Consonantia, there live the blind texts.Separated "
						+ "they live in Bookmarksgrove right at the coast of the Semantics, "
						+ "a large language ocean.A small river named Duden flows by their place "
						+ "and supplies it with the necessary regelialia.It is a paradisematic "
						+ "country, in which roasted parts of sentences fly into your mouth.At vero"
						+ " eos et accusamus et iusto odio dignissimos ducimus qui blanditiis "
						+ "praesentium voluptatum deleniti atque corrupti quos dolores et quas "
						+ "molestias excepturi sint.",
				result.trim());
	}

	@Test
	public void testOneCommandWithPipeHead()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo `cat farfaraway.txt | head -n 1`";
		String result = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals(
				"Far far away, behind the word mountains, far from the countries "
				+ "Vokalia and Consonantia, there live the blind texts.",
				result.trim());
	}

	@Test
	public void testOneCommandWithPipeTail()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo `cat farfaraway.txt | tail -n 5`";
		String result = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("Far far away, behind the word mountains, far from the countries "
				+ "Vokalia and Consonantia, there live the blind texts.Separated "
				+ "they live in Bookmarksgrove right at the coast of the Semantics, "
				+ "a large language ocean.A small river named Duden flows by their place "
				+ "and supplies it with the necessary regelialia.It is a paradisematic "
				+ "country, in which roasted parts of sentences fly into your mouth.At vero"
				+ " eos et accusamus et iusto odio dignissimos ducimus qui blanditiis "
				+ "praesentium voluptatum deleniti atque corrupti quos dolores et quas "
				+ "molestias excepturi sint.", result.trim());
	}

	@Test
	public void testOneCommandWithSemicolonCdCat()
			throws AbstractApplicationException, ShellException {
		String originalPath = Environment.currentDirectory;
		String cmdLine = "echo `cat slicing.txt; cd test`";
		String result = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals(
				"Program slicing can be used in debugging to locate source of "
						+ "errors more easily.",
				result.trim());
		assertEquals(originalPath + File.separatorChar + "test",
				Environment.currentDirectory);
		Environment.currentDirectory = originalPath;
	}

	@Test
	public void testOneCommandWithSemicolonCatPipe()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo `cat slicing.txt` ; echo `cat slicing.txt` | echo";
		String result = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals(
				"Program slicing can be used in debugging to locate source of errors more easily.",
				result.trim());
	}

	@Test
	public void testMultipleCommandSub()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo `cat slicing.txt` ; echo `wc -l farfaraway.txt`";
		String result = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals(
				"Program slicing can be used in debugging to locate source of errors more easily."
						+ System.lineSeparator() + "       4 farfaraway.txt",
				result.trim());
	}

	@Test
	public void testMultiplePipeSub()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo `cat slicing.txt | tail -n 1` | cat";
		String result = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals(
				"Program slicing can be used in debugging to locate source of errors more easily.",
				result.trim());
	}

	@Test
	public void testMultipleSemicolonSub()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo `echo Showing contents of text1.txt cat text1.txt` ; echo yea";
		String result = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("Showing contents of text1.txt cat text1.txt"
				+ System.lineSeparator() + "yea", result.trim());
	}

	@After
	public void tearDown() {
	}

	@AfterClass
	public static void tearDownOnce() {
		// one-time cleanup code
		shellImpl = null;
	}

}
