package sg.edu.nus.comp.cs4218.impl.ef1;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.*;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class GlobbingTest {

	private static ShellImpl shell;
	private static final String FILE_SEPARATOR = String.valueOf(File.separatorChar);
	private static final String TEST_FOLDER_NAME = String
			.format("folder%sSedAndWCFiles%s", FILE_SEPARATOR, FILE_SEPARATOR);
	private static final String TEST_FOLDER_NAME_2 = String.format(
			"folder%sSedAndWCFiles%ssedTestFiles%s", FILE_SEPARATOR,
			FILE_SEPARATOR, FILE_SEPARATOR);
	private static final String TEST_FILE_NAME = String.format("wcTestFiles%s", FILE_SEPARATOR);
	private static final String[] FILE_NAMES = { "testGlobe.txt",
			"testGlobe.py", "testGlobe.cpp", "testGlobe.html", "testGlobe.css",
			"testGlobe.js", "testGlobe.xml" };
	private String expected;
	ByteArrayOutputStream stdout;
	PrintStream print;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		new File(TEST_FOLDER_NAME).mkdir();
		for (int i = 0; i < FILE_NAMES.length; i++) {
			Files.write(Paths.get(TEST_FOLDER_NAME + "/" + FILE_NAMES[i]),
					"Test".getBytes());
		}

		new File(TEST_FOLDER_NAME + "/testSubSubDir").mkdir();
		Files.write(
				Paths.get(TEST_FOLDER_NAME + "/testSubSubDir/testGlobe.txt"),
				"Test".getBytes());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		File file = new File(TEST_FOLDER_NAME + "/testSubSubDir/testGlobe.txt");
		file.setWritable(true);
		file.delete();

		file = new File(TEST_FOLDER_NAME + "/testSubSubDir");
		file.delete();

		for (int i = 0; i < FILE_NAMES.length; i++) {
			file = new File(TEST_FOLDER_NAME + "/" + FILE_NAMES[i]);
			file.setWritable(true);
			file.delete();
		}

		file = new File(TEST_FOLDER_NAME);
		file.delete();
	}

	@Before
	public void setUp() throws Exception {
		shell = new ShellImpl();
		stdout = new ByteArrayOutputStream();
		print = new PrintStream(stdout);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test(expected = Exception.class)
	public void testInvalidPath()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "cat " + TEST_FOLDER_NAME + "/*/invalid";
		shell.globWithException(cmdLine);
	}

	@Test(expected = Exception.class)
	public void testInvalidSlash()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "cat " + TEST_FOLDER_NAME + "*/";
		shell.globWithException(cmdLine);
	}

	@Test
	public void testGlobbingWithOneLevel()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "cat " + TEST_FOLDER_NAME_2 + "*";
		shell.parseAndEvaluate(cmdLine, stdout);

		String expected = System.lineSeparator() + "0123456789"
				+ System.lineSeparator() + "Hey, good to know <you>!"
				+ System.lineSeparator()
				+ "This is a small file consists of {1+1+0} lines."
				+ System.lineSeparator()
				+ "/* Hope this helps */ # no new line here"
				+ System.lineSeparator();

		assertEquals(expected, stdout.toString());
	}

	@Test
	public void testGlobbingMultiLevel()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "cat " + TEST_FOLDER_NAME + TEST_FILE_NAME + "*";
		System.out.println(cmdLine);
		shell.parseAndEvaluate(cmdLine, stdout);
		String expected = System.lineSeparator() + "Hello"
				+ System.lineSeparator() + "";
		assertEquals(expected, stdout.toString());
	}

	@Test
	public void testArgWhenEchoHasGlobbing()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo what*here";
		shell.parseAndEvaluate(cmdLine, stdout);
		expected = "what*here" + System.lineSeparator();
		Assert.assertEquals(expected, stdout.toString());
	}

	@Test
	public void testArgWhenEchoHasGlobbingMultipleLevels()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo crayfish1886/*/dolphin8976";
		shell.parseAndEvaluate(cmdLine, stdout);
		expected = "crayfish1886/*/dolphin8976" + System.lineSeparator();
		Assert.assertEquals(expected, stdout.toString());
	}

	@Test
	public void testArgWhenEchoOnlyHasGlobbing()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo *";
		shell.parseAndEvaluate(cmdLine, stdout);
		expected = "*" + System.lineSeparator();
		Assert.assertEquals(expected, stdout.toString());
	}

	@Test
	public void testGlobNoPaths() {
		String[] args = { "" };
		expected = shell.globNoPaths(args);
		assertEquals("", expected);
	}

	@Test
	public void testGlobOneFile() {
		String args = "";
		expected = shell.globOneFile(args);
		assertEquals("", expected);
	}

	@Test
	public void testGlobFilesDirectories() {
		String args = "" ;
		expected = shell.globFilesDirectories(args);
		assertEquals("", expected);
	}
	
	@Test
	public void testGlobAllPaths() throws AbstractApplicationException, ShellException {
		String cmdLine = "echo folder/GrepAndSortFiles/*";
		expected = "folder/GrepAndSortFiles/emptydoc.txt folder/GrepAndSortFiles/greptestdoc.txt folder/GrepAndSortFiles/greptestdoc2.txt folder/GrepAndSortFiles/MoreFiles folder/GrepAndSortFiles/MoreFiles/test.txt folder/GrepAndSortFiles/MoreFiles/test2.txt folder/GrepAndSortFiles/MoreFiles/testdoc.txt folder/GrepAndSortFiles/MoreFiles/TestSortMethods.txt folder/GrepAndSortFiles/MoreFiles/TestSortNumeric.txt ";
		String actual = shell.processGlob(cmdLine);
		assertEquals(expected, actual);
	}
}
