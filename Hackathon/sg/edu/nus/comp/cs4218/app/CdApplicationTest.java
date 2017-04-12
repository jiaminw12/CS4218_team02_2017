package sg.edu.nus.comp.cs4218.app;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.DirectoryNotFoundException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.app.CdApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CdApplicationTest {

	private InputStream input;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private CdApplication cdApp;
	private static String revertDir = Environment.currentDirectory;
	private final static String PATH_SEPARATOR = File.separator;

	@Before
	public void setUpBeforeTest() throws Exception {
		cdApp = new CdApplication();
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void tearDownAfterTest() {
		Environment.currentDirectory = revertDir;
	}

	@Test
	public void testCdDirectoryUnspecifiedException()
			throws DirectoryNotFoundException, AbstractApplicationException {
		String[] args = { "cd" };
		cdApp.run(args, input, System.out);
		assertEquals("Insufficient Argument: Input directory required.",
				outContent.toString().trim());
	}

	@Test
	public void testCdWithPreviousDirectorySymbolThenCurrentDirectorySymbol()
			throws AbstractApplicationException, ShellException {
		String command = "cd .. ; cd . ; pwd";
		ShellImpl shell = new ShellImpl();
		shell.parseAndEvaluate(command, System.out);
		assertEquals(outContent.toString().trim(),
				Environment.currentDirectory);
	}

	@Test
	public void testCdToADirectoryThenCdToPreviousDirectory()
			throws AbstractApplicationException, ShellException {
		ShellImpl shell = new ShellImpl();
		String command = "cd src" + PATH_SEPARATOR + "sg ; cd .. ; pwd";
		shell.parseAndEvaluate(command, System.out);
		assertEquals(outContent.toString().trim(),
				Environment.currentDirectory);
	}

	@Test
	public void testCdWithHead()
			throws AbstractApplicationException, ShellException {
		String command = "cd folder" + PATH_SEPARATOR + "SedAndWCFiles"
				+ PATH_SEPARATOR + " | head -n 2 titles.txt";
		String expected = "The Shawshank Redemption" + System.lineSeparator()
				+ "The Godfather";
		ShellImpl shell = new ShellImpl();
		shell.parseAndEvaluate(command, System.out);
		assertEquals(expected, outContent.toString().trim());
	}

}