package sg.edu.nus.comp.cs4218.impl.cmd;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.*;

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
		outputStream = new ByteArrayOutputStream();
	}
	
	@Test
	public void testEmptyCallCommand() throws ShellException,
		AbstractApplicationException {
			CallCommand cmd = new CallCommand("");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			cmd.evaluate(null, out);
			assertEquals(out.toString(), "");
	}
	
	@Test(expected = ShellException.class)
	public void testIllegalApplicationName()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo `echa token1 `";
		shellImpl.parseAndEvaluate(cmdLine, outputStream);
	}

	@Test(expected = ShellException.class)
	public void testIllegalNumOfBackQuoteAtBack()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo ` token1 ```";
		shellImpl.parseAndEvaluate(cmdLine, outputStream);
	}

	@Test(expected = ShellException.class)
	public void testIllegalNumOfBackQuoteAtStart()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo ``` token1 `";
		shellImpl.parseAndEvaluate(cmdLine, outputStream);
	}

	@Test(expected = ShellException.class)
	public void testIllegalCommand()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo `cat cybody40.txt |`";
		shellImpl.parseAndEvaluate(cmdLine, outputStream);
	}

	@Test(expected = ShellException.class)
	public void testInvalidArg()
			throws ShellException, AbstractApplicationException, IOException {
		String cmdLine = "echo `hahaha.... echo` echo";
		shellImpl.parseAndEvaluate(cmdLine, outputStream);
	}

	@Test(expected = ShellException.class)
	public void testBQContainBQ()
			throws ShellException, AbstractApplicationException, IOException {
		String cmdLine = "echo `echo `echo testing ing ....``";
		shellImpl.parseAndEvaluate(cmdLine, outputStream);
	}

	// echo 'Travel time Singapore -> Paris is 13h and 15`'
	// Travel time Singapore -> Paris is 13h and 15`
	// echo "This is space:`echo " "`."
	// This is space: .
	// echo 'This is space:`echo " "`.'
	// This is space:`echo " "`.

	@Test
	public void testEchoWithSemicolon() throws Exception {
		String cmdLine = "echo `echo ABC; echo DEF`";
		shellImpl.parseAndEvaluate(cmdLine, outputStream);
		String actualResult = new String(outputStream.toByteArray()).trim();
		String expectedResult = "ABC DEF" + System.lineSeparator();
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	public void testCommandSub() throws Exception {
		String cmdLine = "echo `cat cybody40.txt`";
		shellImpl.parseAndEvaluate(cmdLine, outputStream);
		String actualResult = new String(outputStream.toByteArray()).trim();
		assertEquals(
				"They may be found on menus in restaurants that serve seafood. Clams are a fairly common form of bivalve, therefore making it part of the phylum mollusca. They may be found on menus in restaurants that serve seafood. Clams are a fairly common form of bivalve, therefore making it part of the phylum mollusca.",
				actualResult);
	}

	@Test
	public void testInputSingleQuote()
			throws IOException, AbstractApplicationException, ShellException {
		String cmdLine = "echo '`cat cxintro20.txt`' ";
		shellImpl.parseAndEvaluate(cmdLine, outputStream);
		String actualResult = new String(outputStream.toByteArray()).trim();
		assertEquals("cat cxintro20.txt", actualResult);
	}

	@Test
	public void testOneCommandSubWithNewLine()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo `cat " + "\n" + "cxintro02.txt`";
		shellImpl.parseAndEvaluate(cmdLine, outputStream);
		String actualResult = new String(outputStream.toByteArray()).trim();
		assertEquals(
				"Clams are a fairly common form of bivalve, therefore making it part of the phylum mollusca.",
				actualResult);
	}

	@Test
	public void testOneCommandSubWithDoubleQuote()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo \"`echo i'm here`\"";
		shellImpl.parseAndEvaluate(cmdLine, outputStream);
		String actualResult = new String(outputStream.toByteArray()).trim();
		assertEquals("i'm here", actualResult);
	}

	@Test
	public void testOneCommandSub()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo `wc -l cxintro02.txt`";
		shellImpl.parseAndEvaluate(cmdLine, outputStream);
		String actualResult = new String(outputStream.toByteArray()).trim();
		assertEquals("1", actualResult);
	}

	@Test
	public void testCommandSubWithPipeHeadWithNoOptions() throws Exception {
		String cmdLine = "echo `cat cybody40.txt | head`";
		shellImpl.parseAndEvaluate(cmdLine, outputStream);
		String actualResult = new String(outputStream.toByteArray()).trim();
		assertEquals(
				"They may be found on menus in restaurants that serve seafood. Clams are a fairly common form of bivalve, therefore making it part of the phylum mollusca. They may be found on menus in restaurants that serve seafood. Clams are a fairly common form of bivalve, therefore making it part of the phylum mollusca.",
				actualResult);
	}

	@Test
	public void testOneCommandWithPipeHead()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo `cat cybody40.txt | head -n 1`";
		shellImpl.parseAndEvaluate(cmdLine, outputStream);
		String actualResult = new String(outputStream.toByteArray()).trim();
		assertEquals(
				"They may be found on menus in restaurants that serve seafood.",
				actualResult);
	}

	@Test
	public void testOneCommandWithPipeTail()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo `cat cybody40.txt | tail -n 5`";
		shellImpl.parseAndEvaluate(cmdLine, outputStream);
		String actualResult = new String(outputStream.toByteArray()).trim();
		assertEquals(
				"Clams are a fairly common form of bivalve, therefore making it part of the phylum mollusca.",
				actualResult);
	}

	@Test
	public void testOneCommandWithSemicolonCdEcho()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo `cd test; echo 'Travel time Singapore -> Los Angels is 24h and 15`'`";
		shellImpl.parseAndEvaluate(cmdLine, outputStream);
		String actualResult = new String(outputStream.toByteArray()).trim();
		assertEquals("Travel time Singapore -> Los Angels is 24h and 15`",
				actualResult);
	}

	@Test
	public void testOneCommandWithSemicolonCdCat()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "wc -l `cd test; cat cxintro02.txt`";
		shellImpl.parseAndEvaluate(cmdLine, outputStream);
		String actualResult = new String(outputStream.toByteArray()).trim();
		assertEquals(
				"Clams are a fairly common form of bivalve, therefore making it part of the phylum mollusca.",
				actualResult);
	}

	@Test
	public void testOneCommandWithSemicolonCatPipe()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo `cat cxintro02.txt` ; echo `cat cxintro02.txt` | echo";
		shellImpl.parseAndEvaluate(cmdLine, outputStream);
		String actualResult = new String(outputStream.toByteArray()).trim();
		assertEquals(
				"Clams are a fairly common form of bivalve, therefore making it part of the phylum mollusca.",
				actualResult);
	}

	@Test
	public void testMultipleCommandSub()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo `cat cxintro02.txt` ; echo `wc -l cybody40.txt`";
		shellImpl.parseAndEvaluate(cmdLine, outputStream);
		String actualResult = new String(outputStream.toByteArray()).trim();
		assertEquals("Clams can be found in saltwater and freshwater. 4",
				actualResult);
	}

	@Test
	public void testMultiplePipeSub()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo `cat cxintro02.txt | tail -n 1` | cat";
		shellImpl.parseAndEvaluate(cmdLine, outputStream);
		String actualResult = new String(outputStream.toByteArray()).trim();
		assertEquals(
				"Clams can be found in saltwater and freshwater. Clams can be found in saltwater and freshwater.",
				actualResult);
	}

	@Test
	public void testMultipleSemicolonSub()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo `echo Showing contents of text1.txt cat text1.txt` ; echo yea";
		shellImpl.parseAndEvaluate(cmdLine, outputStream);
		String actualResult = new String(outputStream.toByteArray()).trim();
		assertEquals("Showing contents of text1.txt cat text1.txt yea",
				actualResult);
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
