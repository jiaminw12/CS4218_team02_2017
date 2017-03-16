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
		String cmdLine = "sort `cat " + grepSortFilePath + "Taxsa.txt | tail -n 1`";
		expected = shellImpl.performCommandSubstitutionWithException(cmdLine);
		assertEquals("cat: No such file exists", expected);
	}

	@Test
	public void testSortCatTail()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "sort `cat " + grepSortFilePath + "TestSortMethods.txt | tail`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("cat: No such file exists", expected);
	}

	@Test
	public void testSortCatHead()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "sort " + grepSortFilePath + "TestSortNumeric.txt"
				+ "`cat " + grepSortFilePath + "TestSortMethods.txt | head -n 3`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("cat: No such file exists", expected);
	}

	@Test
	public void testEchoTailHeadIORedirection()
			throws ShellException, AbstractApplicationException, IOException {
		String cmdLine = "echo `tail -n 1 slicing.txt | head -n 1 | tail -n 1` > test1.txt ";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals(
				"Program slicing can be used in debugging to locate source of errors more easily."
						+ System.lineSeparator(),
				readFile("test1.txt"));
	}

	@Test
	public void testCalWithMonDate()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo `cal -m; echo date`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals(
				"      March 2017Mo Tu We Th Fr Sa Su      "
						+ "1  2  3  4  5 6  7  8  9  10 11 12"
						+ "13 14 15 16 17 18 1920 21 22 23 24 25 26"
						+ "27 28 29 30 31 date" + System.lineSeparator(),
				expected);
	}

	@Test
	public void testSortGrepWcSedIORedirection()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "sort `grep *ort testdoc.txt | wc -lmw helloworld.txt | "
				+ "sed s/a/bbbb/ slicing.txt > test1.txt`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("cal: Too many patterns found", expected);
	}

	@Test
	public void testSortCatGlobbingIORedirection()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "sort `cat folder/*; cat < test1.txt`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("cat: No such file exists", expected);
	}

	@Test
	public void testCatGrep()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "cat `cat *.txt | grep ed` | grep sd";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("cat: No such file exists", expected);
	}

	@Test
	public void testDateCatGrepHeadWc()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo `date; cat slicing.txt | grep \"se\" | head -n 1 | wc -l`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		dateNow = now.toString();
		assertEquals(dateNow + "       0 " + System.lineSeparator(), expected);
	}

	@Test
	public void testSortPwdGrep()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo `sort slicing.txt | pwd | grep \"abc\"`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("Pattern Not Found In Stdin!" + System.lineSeparator(),
				expected);
	}

	@Test
	public void testEchoCatIOdirectionSortCd()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo `cat < folder/* > test1.txt | sort | cd testLvl01`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("shell: folder/* (No such file or directory)", expected);
	}

	@Test
	public void testEchoCatDate()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo `date | cat | cat`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		String actualResult = now.toString();
		assertEquals(actualResult + System.lineSeparator(), expected);
	}

	@Test
	public void testTwoCmdSub01()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo \"this is a test `date`\"; echo \"this is another test `cat "
				+ sedWcFilePath + "helloworld.txt`\"";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		dateNow = now.toString();
		assertEquals("this is a test " + dateNow + System.lineSeparator()
				+ "this is another test hello world" + System.lineSeparator(),
				expected);
	}

	@Test
	public void testTwoCmdSub02()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo \"this is a test `grep *F* " + grepSortFilePath
				+ "greptestdoc2.txt`\"; echo \"this is another test `sort "
				+ grepSortFilePath + "TestSortMethods.txt`\"";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		System.out.println(cmdLine);
		assertEquals("this is a test Pattern Not Found In File!"
				+ System.lineSeparator() + "this is another test +125ABab"
				+ System.lineSeparator(), expected);

	}

	@Test
	public void testTwoCmdSub03()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo `cat " + grepSortFilePath
				+ "greptestdoc.txt` | echo \"this is another test `wc -l "
				+ sedWcFilePath + "singleWord.txt`\" | grep e*";
		// echo `cat folder/GrepAndSortFiles/greptestdoc.txt` | echo "this is another test `wc -l folder/SedAndWCFiles/singleWord.txt`" | grep e*
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("shell: Invalid globbing scenario", expected);
	}

	@Test
	public void testTwoCmdSub04()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "cat `sort " + grepSortFilePath
				+ "greptestdoc.txt` | grep e* | echo \"this is `echo \"this is test\"`\"; ``; \"\"; '' ";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		// cat `sort folder/GrepAndSortFiles/greptestdoc.txt` | grep e* | echo "this is `echo "this is test"`"; ``; ""; '' 
		assertEquals("cat: No such file exists", expected);
	}

	@Test
	public void testMoreThanTwoCmdSub01()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "echo \"this is a test `sed s/e/a/ " + sedWcFilePath
				+ "helloworld.txt`\"; echo \"this is another test `wc -l "
				+ sedWcFilePath + "wcTestFiles" + File.separatorChar
				+ "singleWord.txt`\"; sort `cat " + grepSortFilePath
				+ "greptestdoc.txt`";
		// echo "this is a test `sed s/e/a/ folder/SedAndWCFiles/helloworld.txt`"; echo "this is another test `wc -l folder/SedAndWCFiles/wcTestFiles/singleWord.txt`"; sort `cat folder/GrepAndSortFiles/greptestdoc.txt`

		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals(
				"sed: Invalid Replacement Rule: Missing separator at the end: Extra arguments",
				expected);
	}

	@Test
	public void testMoreThanTwoCmdSub02()
			throws ShellException, AbstractApplicationException {
		String cmdLine = "wc -lmw `sort " + grepSortFilePath
				+ "greptestdoc.txt`; echo \"this is another test `wc -l "
				+ sedWcFilePath + "wcTestFiles" + File.separatorChar
				+ "singleWord.txt`\" ; head -n 1 `echo farfaraway.txt`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("ABC HelloABCDEFGHIHeliHello HelloHi is not a file"
				+ System.lineSeparator()
				+ "this is another test        0 folder/SedAndWCFiles/wcTestFiles/singleWord.txt"
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
				+ sedWcFilePath
				+ "singleWord.txt`\" | head -n 5 `echo slicing.txt`; echo `echo \"flower\"`";
		expected = shellImpl.performCommandSubstitution(cmdLine);
		assertEquals("Pattern Not Found In Stdin!" + System.lineSeparator()
				+ "Program slicing can be used in debugging to locate source of errors more easily."
				+ System.lineSeparator() + "flower" + System.lineSeparator(),
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
		assertEquals("Hello HelloABC Hello" + System.lineSeparator()
				+ "this is another test        0 folder/SedAndWCFiles/wcTestFiles/singleWord.txt"
				+ System.lineSeparator() + "hippotamus  "
				+ System.lineSeparator(), expected);
	}

}
