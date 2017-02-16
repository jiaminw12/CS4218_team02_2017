package sg.edu.nus.comp.cs4218.impl.ef1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;

import org.junit.*;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class GlobbingTest {

	private static ShellImpl shell;
	private static ByteArrayOutputStream stdout;
	String[] args;
	private String TEST_FOLDER_NAME = "test-ef1";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	
	}

	@Before
	public void setUp() throws Exception {
		shell = new ShellImpl();
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testInvalidGlob() {
		String readLine = "cat *";
		try {
			shell.parseAndEvaluate(readLine, stdout);
		} catch (Exception e) {
			String exceptionMsg = "shell: Invalid globbing scenario";
			assertEquals(exceptionMsg, e.getMessage());
		}
	}
	
	@Test
	public void testInvalidGlob2() {
		String readLine = "cat /*";
		try {
			shell.parseAndEvaluate(readLine, stdout);
			//fail(MISSING_EXP);
		} catch (Exception e) {
			String exceptionMsg = "shell: Invalid globbing scenario";
			assertEquals(exceptionMsg, e.getMessage());
		}
	}
	
	@Test
	public void testInvalidGlob3() {
		String readLine = "cat " + TEST_FOLDER_NAME + "/*/invalid";
		try {
			shell.parseAndEvaluate(readLine, stdout);
			//fail(MISSING_EXP);
		} catch (Exception e) {
			String exceptionMsg = "shell: Invalid globbing scenario";
			assertEquals(exceptionMsg, e.getMessage());
		}
	}
	
	@Test
	public void testInvalidPath() {
		String readLine = "cat ///*";
		try {
			shell.parseAndEvaluate(readLine, stdout);
			//fail(MISSING_EXP);
		} catch (Exception e) {
			String exceptionMsg = "shell: Invalid globbing scenario";
			assertEquals(exceptionMsg, e.getMessage());
		}
	}
	
	@Test
	public void testGlobNoPaths() {
		String readLine = "cat srctest/*";
		try {
			shell.globWithException(readLine);
			//fail(MISSING_EXP);
		} catch (Exception e) {
			String exceptionMsg = "cat: No such file exists";
			assertEquals(exceptionMsg, e.getMessage());
		}
	}

	@Test
	public void testGlobOneFile() {
		String readLine = "cat " + TEST_FOLDER_NAME + "/SingleFileFolder/*";
		try {
			shell.globOneFile(readLine);
		} catch (Exception e) {
			//fail(VALID_CMD_NO_EXP);
		}
	}

	@Test
	public void testGlobFilesDirectories() {
		String readLine = "cat " + TEST_FOLDER_NAME + "/MultiFileFolder/*";
		try {
			shell.globFilesDirectories(readLine);
		} catch (Exception e) {
			//fail(VALID_CMD_NO_EXP);
		}
	}

	@Test
	public void testGlobMultiLevel() {
		String readLine = "cat " + TEST_FOLDER_NAME + "/*";
		try {
			shell.parseAndEvaluate(readLine, stdout);
		} catch (Exception e) {
			//fail(VALID_CMD_NO_EXP);
		}
	}
	
	@Test
	public void displayTheContentOfFilesInFolder()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "cat articles/*";
		stdout = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdLine, stdout);

		String expected = "no*matches" + System.lineSeparator();
		Assert.assertEquals(expected, stdout.toString());
	}

	@Test
	public void returnOriginalArgWhenGlobbingHasNoFileMatch()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo no*matches";
		stdout = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdLine, stdout);

		String expected = "no*matches" + System.lineSeparator();
		Assert.assertEquals(expected, stdout.toString());
	}

	@Test
	public void returnOriginalArgWhenFileNameIsBetweenDirectories()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo oyster1337/*/mussel7715";
		stdout = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdLine, stdout);

		String expected = "oyster1337/*/mussel7715" + System.lineSeparator();
		Assert.assertEquals(expected, stdout.toString());
	}

	@Test
	public void returnExpandedArgsWhenGlobbingHasMatches()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo *.txt13*";
		stdout = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdLine, stdout);

		String expected = "5callop.txt133 5callop.txt139"
				+ System.lineSeparator();

		Assert.assertEquals(expected, stdout.toString());
	}

	@Test
	public void returnBothFilesAndDirectoriesAlphabeticallyWhenMatched()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo *";

		stdout = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdLine, stdout);

		String expected = "5callop.txt133 5callop.txt139 clam1533 oyster1337 sca110p.txt339"
				+ System.lineSeparator();

		Assert.assertEquals(expected, stdout.toString());
	}

}
