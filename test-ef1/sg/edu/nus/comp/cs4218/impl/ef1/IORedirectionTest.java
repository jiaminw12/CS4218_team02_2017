package sg.edu.nus.comp.cs4218.impl.ef1;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

/* Assumption: 
 */
public class IORedirectionTest {

	private static ShellImpl shell;
	private static ByteArrayOutputStream stdout;
	String[] args;

	private String readFile(String path) throws IOException {
		byte[] byteArr = Files
				.readAllBytes(Paths.get(System.getProperty("user.dir") + File.separatorChar + path));
		return new String(byteArr);
	}

	@Before
	public void setUp() {
		shell = new ShellImpl();
		stdout = new ByteArrayOutputStream();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		File file = new File("test1.txt");
		file.setWritable(true);
		file.delete();

		file = new File("test2.txt");
		file.setWritable(true);
		file.delete();
	}	
	
	@Test(expected = ShellException.class)
	public void testInputRedirectionWithEmtpyFileFromShell() throws Exception {
		String cmdLine = "cat < ";
		shell.parseAndEvaluate(cmdLine, stdout);
	}
	
	@Test
	public void testInputRedirectionWithEmtpyFile() throws Exception {
		String cmdLine = "cat < ";
		String result = shell.redirectInputWithNoFile(cmdLine);
		assertEquals("shell: Invalid syntax encountered.", result);
	}
	
	@Test(expected = ShellException.class)
	public void testOutputRedirectionWithEmtpyFileFromShell() throws Exception {
		String cmdLine = "cat > ";
		shell.parseAndEvaluate(cmdLine, stdout);
	}
	
	@Test
	public void testOutputRedirectionWithEmtpyFile() throws Exception {
		String cmdLine = "cat > ";
		String result = shell.redirectOutputWithNoFile(cmdLine);
		assertEquals("shell: Invalid syntax encountered.", result);
	}
	
	@Test(expected = ShellException.class)
	public void testInputOutputRedirectionWithMultipleFiles()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo TEST > test1.txt test2.txt > test3.txt";
		shell.parseAndEvaluate(cmdLine, stdout);
	}

	@Test(expected = ShellException.class)
	public void testIllegalMultipleInputRedirectionFromShell()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo TEST > test1.txt > test2.txt";
		shell.parseAndEvaluate(cmdLine, stdout);
	}
	
	@Test
	public void testIllegalMultipleInputRedirection()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo TEST > test1.txt > test2.txt";
		String result = shell.redirectInputWithException(cmdLine);
		assertEquals("shell: Invalid syntax encountered.", result);
	}
	
	@Test(expected = ShellException.class)
	public void testIllegalMultipleOutputRedirectionFromShell()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "cat < test1.txt < test2.txt";
		shell.parseAndEvaluate(cmdLine, stdout);
	}
	
	@Test
	public void testIllegalMultipleOutputRedirection()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "cat < test1.txt < test2.txt";
		String result = shell.redirectOutputWithException(cmdLine);
		assertEquals("shell: Invalid syntax encountered.", result);
	}
	

	@Test(expected = ShellException.class)
	public void testIORedirectionWithIllegalFile() throws Exception {
		String cmdLine = "cat TEST < noFile.txt";
		shell.parseAndEvaluate(cmdLine, stdout);
	}
	
	@Test(expected = ShellException.class)
	public void testIORedirectionWithIllegalFormat() throws Exception {
		String cmdLine = "cat TEST < > noFile.txt";
		shell.parseAndEvaluate(cmdLine, stdout);
	}
	
	@Test
	public void testInputRedirectionWithCatFromShell() throws Exception {
		String cmdLine = "cat < slicing.txt";
		shell.parseAndEvaluate(cmdLine, stdout);
		String expected = "Program slicing can be used in debugging to locate source of errors more easily.";
		assertEquals(expected, readFile("slicing.txt"));
	}
	
	@Test
	public void testInputRedirectionWithCat() throws Exception {
		String cmdLine = "cat < slicing.txt";
		shell.redirectInput(cmdLine);
		String expected = "Program slicing can be used in debugging to locate source of errors more easily.";
		assertEquals(expected, readFile("slicing.txt"));
	}
	
	@Test
	public void testOutputRedirectionWithEchoFromShell() throws Exception {
		String cmdLine = "echo TEST > test1.txt";
		shell.parseAndEvaluate(cmdLine, stdout);
		String expected = "TEST" + System.lineSeparator();
		assertEquals(expected, readFile("test1.txt"));
	}
	
	@Test
	public void testOutputRedirectionWithEcho() throws Exception {
		String cmdLine = "echo TEST > test1.txt";
		shell.redirectOutput(cmdLine);
		String expected = "TEST" + System.lineSeparator();
		assertEquals(expected, readFile("test1.txt"));
	}
	
	@Test(expected = ShellException.class)
	public void testIllegalIORedirectionMultipleLinesFromShell() throws Exception {
		String cmdLine = "echo TEST > test1.txt";
		shell.parseAndEvaluate(cmdLine, stdout);

		cmdLine = "echo TEST > test2.txt";
		shell.parseAndEvaluate(cmdLine, stdout);

		cmdLine = "cat TEST < test1.txt < test2.txt";
		shell.parseAndEvaluate(cmdLine, stdout);
	}

	@Test
	public void testIORedirectionWithEchoCatFromShell() throws Exception {
		String cmdLine1 = "echo TEST > test1.txt";
		shell.parseAndEvaluate(cmdLine1, stdout);

		String cmdLine2 = "cat < test1.txt";
		stdout = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdLine2, stdout);

		String expected = "TEST" + System.lineSeparator();
		assertEquals(expected, stdout.toString());
	}

	@Test
	public void testIORedirectionWithLeftAndRightFromShell() throws Exception {
		String cmdLine1 = "echo TEST > test1.txt";
		shell.parseAndEvaluate(cmdLine1, stdout);

		String cmdLine2 = "cat < test1.txt > test2.txt";
		stdout = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdLine2, stdout);

		String expected = "TEST" + System.lineSeparator();

		assertEquals(expected, readFile("test2.txt"));
	}

}
