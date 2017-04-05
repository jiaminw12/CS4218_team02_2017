package sg.edu.nus.comp.cs4218.integrationtest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class ChainOfInteractionCommandSub {

	java.util.Date now = new java.util.Date();
	String dateNow = "";
	String expected = "";
	static ShellImpl shellImpl;
	private String grepSortFilePath = "folder" + File.separatorChar
			+ "GrepAndSortFiles" + File.separatorChar;
	private String sedWcFilePath = "folder" + File.separatorChar
			+ "SedAndWCFiles" + File.separatorChar;

	private String readFile(String path) throws IOException {
		byte[] byteArr = Files.readAllBytes(Paths.get(
				System.getProperty("user.dir") + File.separatorChar + path));
		return new String(byteArr);
	}

	@BeforeClass
	public static void setUpOnce() {
		// one-time initialization code
	}

	@Before
	public void setUp() {
		shellImpl = new ShellImpl();
	}

	@AfterClass
	public static void tearDownOnce() {
		File file = new File("test1.txt");
		file.setWritable(true);
		file.delete();
	}

	@Test
	public void testInvalidCommand()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo `cal -m 1 2017 2018; echo head.txt`";
		expected = shellImpl.performCommandSubstitutionWithException(cmdLine);
		assertEquals("cal: Too many arguments", expected);
	}

	@Test
	public void testInvalidApplication()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo `find head.txt; cal -m 1 2017`";
		expected = shellImpl.performCommandSubstitutionWithException(cmdLine);
		assertEquals("shell: find: Invalid app.", expected);
	}

	@Test
	public void testInvalidFile()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "sort `cat " + grepSortFilePath
				+ "Taxsa.txt | tail -n 1`";
		expected = shellImpl.performCommandSubstitutionWithException(cmdLine);
		assertEquals("cat: No such file exists", expected);
	}

	@Test
	public void testSortCatTail()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo `cat " + grepSortFilePath
				+ "TestSortMethods.txt | tail -n 3`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("+  5 " + System.lineSeparator(), expected);
	}

	@Test
	public void testSortCatHead()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo " + grepSortFilePath + "TestSortNumeric.txt "
				+ "`cat " + grepSortFilePath
				+ "TestSortMethods.txt | head -n 3`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("folder/GrepAndSortFiles/TestSortNumeric.txt 1 a A "
				+ System.lineSeparator(), expected);
	}

	@Test
	public void testEchoTailHeadIORedirection()
			throws ShellException, AbstractApplicationException, IOException {
		String cmdLine = "echo `tail -n 1 slicing.txt | head -n 1 | tail -n 1` > test1.txt ";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals(
				"Program slicing can be used in debugging to locate source of errors more easily. "
						+ System.lineSeparator(),
				readFile("test1.txt"));
	}

	@Test
	public void testCalWithMonDate()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo `cal -m; echo date`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("      April 2017 Mo Tu We Th Fr Sa Su                "
				+ "1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 "
				+ "18 19 20 21 22 23 24 25 26 27 28 29 30 date "
				+ System.lineSeparator(), expected);
	}

	@Test
	public void testSortGrepWcSedIORedirection()
			throws ShellException, AbstractApplicationException, IOException {
		String cmdLine = "echo `grep *ort testdoc.txt | wc -lmw helloworld.txt | "
				+ "sed s/a/bbbb/ slicing.txt > test1.txt`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals(readFile("test1.txt"),
				"Progrbbbbm slicing can be used in debugging to locate source of errors more easily."
						+ System.lineSeparator());
		assertEquals("" + System.lineSeparator(), expected);
	}

	@Test
	public void testSortCatGlobbingIORedirection()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "sort `cat folder/*; cat < test1.txt`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("cat: Could not read file", expected.trim());
	}

	@Test
	public void testCatGrep()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "cat `cat *.txt | grep ed` | grep sd";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("cat: No such file exists", expected.trim());
	}

	@Test
	public void testDateCatGrepHeadWc()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo `date; cat slicing.txt | grep \"se\" | head -n 1 | wc -l`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		dateNow = now.toString();
		assertEquals(dateNow + "        0  " + System.lineSeparator(),
				expected);
	}

	@Test
	public void testSortPwdGrep()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo `sort slicing.txt | pwd | grep \"abc\"`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("Pattern Not Found In Stdin! " + System.lineSeparator(),
				expected);
	}

	@Test
	public void testPwd() throws ShellException, AbstractApplicationException {
		String cmdLine = "echo `pwd; head " + grepSortFilePath + "test.txt`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals(Environment.currentDirectory + " Just To Test "
				+ System.lineSeparator(), expected);
	}

	@Test
	public void testEchoCatIOdirectionSortCd()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo `cat < folder/* > test1.txt | sort | cd testLvl01`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("shell: folder/* (No such file or directory)",
				expected.trim());
	}

	@Test
	public void testEchoCatDate()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo `date | cat | cat`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		String actualResult = now.toString();
		assertEquals(actualResult + " " + System.lineSeparator(), expected);
	}

	@Test
	public void testEchoTailIODirection()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo `tail -n 2  < " + grepSortFilePath
				+ "TestSortMethods.txt" + "`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("5", expected.trim());
	}

	@Test
	public void testEchoCd()
			throws ShellException, AbstractApplicationException {
		String currentPath = Environment.currentDirectory;
		String cmdLine = "echo `cd folder; cat farfaraway.txt`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals(currentPath + File.separatorChar + "folder",
				Environment.currentDirectory);
		assertEquals(
				"Far far away, behind the word mountains, far from the countries Vokalia"
						+ " and Consonantia, there live the blind texts. Separated they live in "
						+ "Bookmarksgrove right at the coast of the Semantics, a large language "
						+ "ocean. A small river named Duden flows by their place and supplies it "
						+ "with the necessary regelialia. It is a paradisematic country, in which"
						+ " roasted parts of sentences fly into your mouth. At vero eos et accusamus"
						+ " et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum"
						+ " deleniti atque corrupti quos dolores et quas molestias excepturi sint. "
						+ System.lineSeparator(),
				expected);
		Environment.currentDirectory = currentPath;
	}

	@Test
	public void testTwoCmdSub01()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo \"this is a test `date`\"; echo \"this is another test `cat "
				+ sedWcFilePath + "helloworld.txt`\"";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		dateNow = now.toString();
		assertEquals("this is a test " + dateNow + " " + System.lineSeparator()
				+ "this is another test hello world " + System.lineSeparator(),
				expected);
	}

	@Test
	public void testTwoCmdSub02()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo \"this is a test `grep F " + grepSortFilePath
				+ "greptestdoc2.txt`\"; echo \"this is another test `sort "
				+ grepSortFilePath + "TestSortMethods.txt`\"";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("this is a test DEF FGH " + System.lineSeparator()
				+ "this is another test  + 1 2 5 A B a b "
				+ System.lineSeparator(), expected);

	}

	@Test
	public void testTwoCmdSub03()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo `cat " + grepSortFilePath
				+ "greptestdoc.txt` | echo \"this is another test `wc -l "
				+ sedWcFilePath + "wcTestFiles" + File.separatorChar
				+ "singleWord.txt`\" | grep e*";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals(
				"this is another test        0 folder/SedAndWCFiles/wcTestFiles/singleWord.txt"
						+ System.lineSeparator(),
				expected);
	}

	@Test
	public void testTwoCmdSub04()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo `sort " + grepSortFilePath
				+ "greptestdoc.txt`; grep e* | echo \"this is `echo \"this is test\"`\"";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("ABC Hello ABCDEFGHI Heli Hello Hello Hi "
				+ System.lineSeparator() + "this is this is test "
				+ System.lineSeparator(), expected);
	}

	@Test
	public void testMoreThanTwoCmdSub01()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo \"this is a test `sed s&e&a& " + sedWcFilePath
				+ "helloworld.txt`\"; echo \"this is another test `wc -l "
				+ sedWcFilePath + "wcTestFiles" + File.separatorChar
				+ "singleWord.txt`\"; echo `cat " + grepSortFilePath
				+ "greptestdoc.txt`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("this is a test hallo world " + System.lineSeparator()
				+ "this is another test        0 folder/SedAndWCFiles/wcTestFiles/singleWord.txt "
				+ System.lineSeparator()
				+ "Hi Hello Hello ABC Hello ABCDEFGHI Heli "
				+ System.lineSeparator(), expected);
	}

	@Test
	public void testMoreThanTwoCmdSub02()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "wc -lmw `sort " + grepSortFilePath
				+ "greptestdoc.txt`; echo \"this is another test `wc -l "
				+ sedWcFilePath + "wcTestFiles" + File.separatorChar
				+ "singleWord.txt`\" ; head -n 1 `echo farfaraway.txt`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("ABC Hello ABCDEFGHI Heli Hello Hello Hi  is not a file"
				+ System.lineSeparator()
				+ "this is another test        0 folder/SedAndWCFiles/wcTestFiles/singleWord.txt "
				+ System.lineSeparator()
				+ "Far far away, behind the word mountains, far from the countries "
				+ "Vokalia and Consonantia, there live the blind texts."
				+ System.lineSeparator(), expected);
	}

	@Test
	public void testMoreThanTwoCmdSub03()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo `cat " + grepSortFilePath
				+ "greptestdoc.txt | grep hello`; echo \"this is another test `wc -l "
				+ sedWcFilePath + "singleWord.txt`\" "
				+ "| head -n 5 `echo slicing.txt`; echo `echo \"flower\"`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("Pattern Not Found In Stdin! " + System.lineSeparator()
				+ "Program slicing can be used in debugging to locate source of errors more easily."
				+ System.lineSeparator() + "flower " + System.lineSeparator(),
				expected);
	}

	@Test
	public void testMoreThanTwoCmdSub04()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo `cat " + grepSortFilePath
				+ "greptestdoc.txt | grep Hello`; echo \"this is another test `wc -l "
				+ sedWcFilePath + "wcTestFiles" + File.separatorChar
				+ "singleWord.txt`\"; echo `echo \"hippotamus  \"`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("Hello Hello ABC Hello " + System.lineSeparator()
				+ "this is another test        0 folder/SedAndWCFiles/wcTestFiles/singleWord.txt "
				+ System.lineSeparator() + "hippotamus   "
				+ System.lineSeparator(), expected);
	}

}
