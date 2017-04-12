package sg.edu.nus.comp.cs4218.ef1;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.*;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class GlobbingTest {

	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");
	private static final String PATH_SEPARATOR = File.separator;
	private static final String SED_RELATIVE_DIR = "folder" + PATH_SEPARATOR
			+ "SedAndWCFiles" + PATH_SEPARATOR + "sedTestFiles"
			+ PATH_SEPARATOR;
	private static final String WC_RELATIVE_DIR = "folder" + PATH_SEPARATOR
			+ "SedAndWCFiles" + PATH_SEPARATOR + "wcTestFiles" + PATH_SEPARATOR;
	private static final String HEAD_RELATIVE_DIR = "folder" + PATH_SEPARATOR
			+ "HeadFiles" + PATH_SEPARATOR;
	private final ByteArrayOutputStream output = new ByteArrayOutputStream();
	private String expected = "";
	private static ShellImpl shell;

	@Before
	public void setupBeforeTest() {
		shell = new ShellImpl();
		System.setOut(new PrintStream(output));
	}

	@Test
	public void testGlobAllPaths()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo folder/GrepAndSortFiles/*";
		expected = "folder/GrepAndSortFiles/emptydoc.txt "
				+ "folder/GrepAndSortFiles/greptestdoc.txt "
				+ "folder/GrepAndSortFiles/greptestdoc2.txt "
				+ "folder/GrepAndSortFiles/MoreFiles "
				+ "folder/GrepAndSortFiles/MoreFiles/test.txt "
				+ "folder/GrepAndSortFiles/MoreFiles/test2.txt "
				+ "folder/GrepAndSortFiles/MoreFiles/testdoc.txt "
				+ "folder/GrepAndSortFiles/MoreFiles/TestSortMethods.txt "
				+ "folder/GrepAndSortFiles/MoreFiles/TestSortNumeric.txt ";
		String actual = shell.processGlob(cmdLine);
		assertEquals(expected, actual);
	}

	@Test
	public void testIntegrateGlobbingWithSedCat()
			throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate(
				"sed s/sub/many/ sedIntegration > sedIntegration2.txt; "
						+ "cat sedIntegration2.txt; cat " + SED_RELATIVE_DIR
						+ "*",
				System.out);
		String expected = "many sub string replacement substring substitution"
				+ LINE_SEPARATOR + LINE_SEPARATOR + "0123456789"
				+ LINE_SEPARATOR + "Hey, good to know <you>!" + LINE_SEPARATOR
				+ "This is a small file consists of {1+1+0} lines."
				+ LINE_SEPARATOR + "/* Hope this helps */ # no new line here"
				+ LINE_SEPARATOR;

		File file = new File("sedIntegration2.txt");
		if (file.exists()) {
			file.delete();
		}
		assertEquals(expected, output.toString());
	}

	@Test
	public void testIntegrateWCSortCat()
			throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate("wc -lmw " + WC_RELATIVE_DIR + "wcIntegrate > "
				+ WC_RELATIVE_DIR + "wcResults ; cat " + WC_RELATIVE_DIR
				+ "* | sort", System.out);
		String expected = "many wc" + "      29       6       2"
				+ LINE_SEPARATOR + "wc wc" + LINE_SEPARATOR + "wc:word count"
				+ LINE_SEPARATOR;

		File file = new File(WC_RELATIVE_DIR + "wcResults");
		if (file.exists()) {
			file.delete();
		}
		assertEquals(expected, output.toString());
	}

	@Test
	public void testWithSingleGlobCharacter()
			throws AbstractApplicationException, ShellException {
		GlobTestHelper.setupGlobFiles("folder" + PATH_SEPARATOR + "glob");
		String path = Environment.currentDirectory;
		String input = "cd " + "folder" + PATH_SEPARATOR + "glob"
				+ PATH_SEPARATOR + "-.-; echo *";
		String expected = "2712 " + "cab " + "car " + "-carr " + "cat"
				+ LINE_SEPARATOR;
		shell.parseAndEvaluate(input, System.out);
		Environment.currentDirectory = path;
		assertEquals(expected, output.toString());
	}

	@Test
	public void testWithPreviousDirectorySymbolInPath()
			throws AbstractApplicationException, ShellException {
		String input = "echo " + Environment.currentDirectory + PATH_SEPARATOR
				+ "src" + PATH_SEPARATOR + "sg" + PATH_SEPARATOR + "edu"
				+ PATH_SEPARATOR + ".." + PATH_SEPARATOR + "*";
		String expected = Environment.currentDirectory + PATH_SEPARATOR + "src"
				+ PATH_SEPARATOR + "sg" + PATH_SEPARATOR + "edu";
		shell.parseAndEvaluate(input, System.out);
		assertEquals(expected, output.toString());
	}

	@Test
	public void testWithCurrentDirectorySymbolInPath()
			throws AbstractApplicationException, ShellException {
		String input = "echo " + Environment.currentDirectory + PATH_SEPARATOR
				+ "src" + PATH_SEPARATOR + "sg" + PATH_SEPARATOR + "."
				+ PATH_SEPARATOR + "*";
		String expected = Environment.currentDirectory + PATH_SEPARATOR + "src"
				+ PATH_SEPARATOR + "sg" + PATH_SEPARATOR + "edu";
		shell.parseAndEvaluate(input, System.out);
		assertEquals(expected, output.toString());
	}

	@Test
	public void testFilesInMultipleDirectories()
			throws ShellException, AbstractApplicationException {
		String absTestDirPath = "folder" + PATH_SEPARATOR + "glob";
		GlobTestHelper.setupGlobFiles(absTestDirPath);
		String input = "echo " + absTestDirPath + PATH_SEPARATOR + "ca*"
				+ PATH_SEPARATOR + "cat";

		String expected = absTestDirPath + PATH_SEPARATOR + "cab"
				+ PATH_SEPARATOR + "cat " + absTestDirPath + PATH_SEPARATOR
				+ "car" + PATH_SEPARATOR + "cat " + absTestDirPath
				+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cat"
				+ LINE_SEPARATOR;
		shell.parseAndEvaluate(input, System.out);
		assertEquals(expected, output.toString());
	}

	@Test
	public void testWithEscapeCharacters()
			throws ShellException, AbstractApplicationException {
		String absTestDirPath = "folder" + PATH_SEPARATOR + "glob";
		GlobTestHelper.setupGlobFiles(absTestDirPath);
		String input = "echo " + absTestDirPath + PATH_SEPARATOR + ".cab.car"
				+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "*'\\E2712\\Q'*";

		String expected = absTestDirPath + PATH_SEPARATOR + ".cab.car"
				+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "*\\E2712\\Q*"
				+ LINE_SEPARATOR;
		shell.parseAndEvaluate(input, System.out);
		assertEquals(expected, output.toString());
	}

	@Test
	public void testIntegrateSed()
			throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate(
				"head " + HEAD_RELATIVE_DIR + "10headlines* | sed \"s/`cat "
						+ HEAD_RELATIVE_DIR + "sedRegex" + " `/`echo 'tail'`/\"",
				System.out);

		String expectedOut = "tail1" + LINE_SEPARATOR + "tail2" + LINE_SEPARATOR
				+ "tail3" + LINE_SEPARATOR + "tail4" + LINE_SEPARATOR + "tail5"
				+ LINE_SEPARATOR + "tail6" + LINE_SEPARATOR + "tail7"
				+ LINE_SEPARATOR + "tail8" + LINE_SEPARATOR + "tail9"
				+ LINE_SEPARATOR + "tail10" + LINE_SEPARATOR;
		assertEquals(expectedOut, output.toString());
	}

}
