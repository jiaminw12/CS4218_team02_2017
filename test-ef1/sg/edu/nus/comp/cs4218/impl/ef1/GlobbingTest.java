package sg.edu.nus.comp.cs4218.impl.ef1;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.*;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class GlobbingTest {

	private static ShellImpl shell;
	private static String test_folder_name = "folder/SedAndWCFiles";
	private static String test_folder_name_2 = "folder/SedAndWCFiles/sedTestFiles"; 
	private static String test_file_name = "/wcTestFiles";
	private static final String[] FILE_NAMES = { "testGlobe.txt",
			"testGlobe.py", "testGlobe.cpp", "testGlobe.html", "testGlobe.css",
			"testGlobe.js", "testGlobe.xml" };
	private String expected;
	ByteArrayOutputStream stdout;
	PrintStream print;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		new File(test_folder_name).mkdir();
		for (int i = 0; i < FILE_NAMES.length; i++) {
			Files.write(Paths.get(test_folder_name + "/" + FILE_NAMES[i]), "Test".getBytes());
		}
		
		new File(test_folder_name+"/testSubSubDir").mkdir();
		Files.write(Paths.get(test_folder_name +"/testSubSubDir/testGlobe.txt"), "Test".getBytes());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		File file = new File(test_folder_name +"/testSubSubDir/testGlobe.txt");
		file.setWritable(true);
		file.delete();
		
		file = new File(test_folder_name+"/testSubSubDir");
		file.delete();
		
		for (int i = 0; i < FILE_NAMES.length; i++) {
			file = new File(test_folder_name + "/" + FILE_NAMES[i]);
			file.setWritable(true);
			file.delete();
		}

		file = new File(test_folder_name);
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
		String cmdLine = "cat " + test_folder_name + "/*/invalid";
		shell.globWithException(cmdLine);
	}
	
	@Test(expected = Exception.class)
	public void testInvalidSlash()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "cat " + test_folder_name + "//*";
		shell.globWithException(cmdLine);
	}

	@Test
	public void testGlobbingWithOneLevel()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "cat " + test_folder_name_2 + "/*";
		shell.parseAndEvaluate(cmdLine, stdout);
		
		String expected = System.lineSeparator() +"0123456789"+ System.lineSeparator() + "Hey, good to know <you>!" + 
				System.lineSeparator() + "This is a small file consists of {1+1+0} lines."+ 
				System.lineSeparator() + "/* Hope this helps */ # no new line here" + System.lineSeparator();
		
		assertEquals(expected, stdout.toString());
	}

	@Test
	public void testGlobbingMultiLevel() throws AbstractApplicationException, ShellException {
		String cmdLine = "cat " + test_folder_name + test_file_name + "/*";
		shell.parseAndEvaluate(cmdLine, stdout);
		String expected = System.lineSeparator() +"Hello"+ System.lineSeparator() + "";
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
	public void testGlobNoPaths(){
		String[] args = {""};
		expected = shell.globNoPaths(args);
		assertEquals("", expected);
	}
	
	@Test
	public void testGlobOneFile(){
		String args = "";
		expected = shell.globOneFile(args);
		assertEquals("", expected);
	}
	
	@Test
	public void testGlobFilesDirectories(){
		String[] args = {""};
		expected = shell.globFilesDirectories(args);
		assertEquals("", expected);
	}
}
