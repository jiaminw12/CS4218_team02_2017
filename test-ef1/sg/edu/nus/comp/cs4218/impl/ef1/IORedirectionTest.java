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

public class IORedirectionTest {

	private static ShellImpl shell;
	private static ByteArrayOutputStream stdout;
	String[] args;

	private String readFile(String path) throws IOException {
		byte[] byteArr = Files
				.readAllBytes(Paths.get(System.getProperty("user.dir") + path));
		return new String(byteArr);
	}

	@Before
	public void setUp() {
		shell = new ShellImpl();
		stdout = new ByteArrayOutputStream();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		File file = new File("a.txt");
		file.setWritable(true);
		file.delete();

		file = new File("b.txt");
		file.setWritable(true);
		file.delete();
	}

	@Test(expected = ShellException.class)
	public void testIllegalIORedirectionOneLine()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo TEST > a.txt > b.txt";
		shell.parseAndEvaluate(cmdLine, stdout);
	}
	
	@Test(expected = ShellException.class)
	public void testIllegalIORedirectionMultipleLines() throws Exception {
		String cmdLine = "echo TEST > a.txt";
		shell.parseAndEvaluate(cmdLine, stdout);

		cmdLine = "echo TEST > b.txt";
		shell.parseAndEvaluate(cmdLine, stdout);

		cmdLine = "grep TEST < a.txt < b.txt";
		shell.parseAndEvaluate(cmdLine, stdout);
	}
	
	@Test(expected = ShellException.class)
	public void testIORedirectionWithEmtpyFile() throws Exception {
		String cmdLine = "grep TEST < ";
		shell.parseAndEvaluate(cmdLine, stdout);
	}

	@Test(expected = ShellException.class)
	public void testIORedirectionWithIllegalFile() throws Exception {
		String cmdLine = "grep TEST < nofile.txt";
		shell.parseAndEvaluate(cmdLine, stdout);
	}
	
	@Test(expected = ShellException.class)
	public void testIORedirectionWithIllegalFormat() throws Exception {
		String cmdLine = "grep TEST < > nofile.txt";
		shell.parseAndEvaluate(cmdLine, stdout);
	}

	@Test
	public void testIORedirectionWithEcho() throws Exception {
		String cmdLine = "echo TEST > a.txt";
		shell.parseAndEvaluate(cmdLine, stdout);
		String expected = "TEST" + System.lineSeparator();
		assertEquals(expected, readFile("a.txt"));
	}

	@Test
	public void testIORedirectionWithEchoGrep() throws Exception {
		String cmdLine1 = "echo TEST > a.txt";
		shell.parseAndEvaluate(cmdLine1, stdout);

		String cmdLine2 = "grep TEST < a.txt";
		stdout = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdLine2, stdout);

		String expected = "TEST" + System.lineSeparator();
		assertEquals(expected, stdout.toString());
	}

	@Test
	public void testIORedirectionWithLeftAndRight() throws Exception {
		String cmdLine1 = "echo TEST > a.txt";
		shell.parseAndEvaluate(cmdLine1, stdout);

		String cmdLine2 = "grep TEST < a.txt > b.txt";
		stdout = new ByteArrayOutputStream();
		shell.parseAndEvaluate(cmdLine2, stdout);

		String expected = "TEST" + System.lineSeparator();

		assertEquals(expected, readFile("b.txt"));
	}

}
