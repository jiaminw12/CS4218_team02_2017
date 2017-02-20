package sg.edu.nus.comp.cs4218.impl.ef1;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.*;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class GlobbingTest {

	private static ShellImpl shell;
	private static OutputStream stdout;
	private static String TEST_FOLDER_NAME = "test_globbing";
	private static final String[] FILE_NAMES = { "testGlobe.txt",
			"testGlobe.py", "testGlobe.cpp", "testGlobe.html", "testGlobe.css",
			"testGlobe.js", "testGlobe.xml" };

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		stdout = System.out;
		
		new File(TEST_FOLDER_NAME).mkdir();
		for (int i = 0; i < FILE_NAMES.length; i++) {
			Files.write(Paths.get(TEST_FOLDER_NAME + "/" + FILE_NAMES[i]), "Test".getBytes());
		}
		
		new File(TEST_FOLDER_NAME+"/testSubSubDir").mkdir();
		Files.write(Paths.get(TEST_FOLDER_NAME +"/testSubSubDir/testGlobe.txt"), "Test".getBytes());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		File file = new File(TEST_FOLDER_NAME +"/testSubSubDir/testGlobe.txt");
		file.setWritable(true);
		file.delete();
		
		file = new File(TEST_FOLDER_NAME+"/testSubSubDir");
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
	}

	@After
	public void tearDown() throws Exception {

	}
	
	@Test(expected = ShellException.class)
	public void testInvalidPath()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "cat " + TEST_FOLDER_NAME + "/*/invalid";
		shell.parseAndEvaluate(cmdLine, stdout);
	}
	
	@Test(expected = ShellException.class)
	public void testInvalidSlash()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "cat " + TEST_FOLDER_NAME + "//*";
		shell.parseAndEvaluate(cmdLine, stdout);
	}

	@Test
	public void testGlobbingWithOneLevel()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "cat " + TEST_FOLDER_NAME + "/*";
		shell.parseAndEvaluate(cmdLine, stdout);
		assertEquals("Test Test Test Test Test Test Test Test", stdout.toString());
	}

	@Test
	public void testGlobbingMultiLevel() throws AbstractApplicationException, ShellException {
		String cmdLine = "cat " + TEST_FOLDER_NAME + "/testSubSubDir/*";
		shell.parseAndEvaluate(cmdLine, stdout);
		assertEquals("Test", stdout.toString());
	}

	@Test
	public void testArgWhenEchoHasGlobbing()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo what*here";
		shell.parseAndEvaluate(cmdLine, stdout);
		String expected = "what*here" + System.lineSeparator();
		Assert.assertEquals(expected, stdout.toString());
	}

	@Test
	public void testArgWhenEchoHasGlobbingMultipleLevels()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo crayfish1886/*/dolphin8976";
		shell.parseAndEvaluate(cmdLine, stdout);
		String expected = "crayfish1886/*/dolphin8976" + System.lineSeparator();
		Assert.assertEquals(expected, stdout.toString());
	}

	@Test
	public void testArgWhenEchoOnlyHasGlobbing()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo *";
		shell.parseAndEvaluate(cmdLine, stdout);
		String expected = "*" + System.lineSeparator();
		Assert.assertEquals(expected, stdout.toString());
	}
	
	@Test
	public void testArgWhenWcGlobbing()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "wc -m *.txt";
		shell.parseAndEvaluate(cmdLine, stdout);
		String expected = "47\t" + System.lineSeparator() + "304\t"
				+ System.lineSeparator() + "351";
		Assert.assertEquals(expected, stdout.toString());
	}

}
