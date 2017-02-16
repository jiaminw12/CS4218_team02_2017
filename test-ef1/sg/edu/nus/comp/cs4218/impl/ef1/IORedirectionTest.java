package sg.edu.nus.comp.cs4218.impl.ef1;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class IORedirectionTest {

	private static ShellImpl shell;
	private static ByteArrayOutputStream stdout;
	String[] args;

	static String readFile(String path) throws IOException {
		byte[] byteArr = Files.readAllBytes(Paths.get(System
				.getProperty("user.dir") + "/test-files-ef2/" + path));
		return new String(byteArr);
	}

	@Before
	public void setUp() {
		Environment.currentDirectory = System.getProperty("user.dir")
				+ "/test-files-ef2/";
		shell = new ShellImpl();
	}

	@Test
	public void testIORedirectionPositve1() throws Exception {
		String cmdLine = "echo ABC > a.txt";
		stdout = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdLine, stdout);

		String expected = "ABC" + System.lineSeparator();

		assertEquals(expected, readFile("a.txt"));
	}

	@Test
	public void testIORedirectionPositve2() throws Exception {
		String cmdLine1 = "echo ABC > a.txt";
		stdout = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdLine1, stdout);

		String cmdLine2 = "grep ABC < a.txt";
		stdout = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdLine2, stdout);

		String expected = "ABC" + System.lineSeparator();

		assertEquals(expected, stdout.toString());
	}

	@Test
	public void testIORedirectionPositve3() throws Exception {
		String cmdLine1 = "echo ABC > a.txt";
		stdout = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdLine1, stdout);

		String cmdLine2 = "grep ABC < a.txt > b.txt";
		stdout = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdLine2, stdout);

		String expected = "ABC"+System.lineSeparator();

		assertEquals(expected, readFile("b.txt"));
	}

	// The following test check the following requirement:
	// "If several files are specified for input redirection or output
	// redirection, throw an exception."
	@Test(expected = ShellException.class)
	public void testIORedirectionNegative1()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo ABC > a.txt > b.txt";
		stdout = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdLine, stdout);
	}

	// The following test check the following requirement:
	// "If several files are specified for input redirection or output
	// redirection, throw an exception."
	@Test(expected = ShellException.class)
	public void testIORedirectionNegative2() throws Exception {
		String cmdLine1 = "echo ABC > a.txt";
		stdout = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdLine1, stdout);

		String cmdLine2 = "echo ABC > b.txt";
		stdout = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdLine2, stdout);

		String cmdLine3 = "grep ABC < a.txt < b.txt";
		stdout = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdLine3, stdout);
	}

	// The following test check the following requirement:
	// "If the file specified for input redirection does not exist, throw an
	// exception."
	@Test(expected = ShellException.class)
	public void testIORedirectionNegative3() throws Exception {
		String cmdLine = "grep ABC < nofile.txt";
		stdout = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdLine, stdout);
	}
}
