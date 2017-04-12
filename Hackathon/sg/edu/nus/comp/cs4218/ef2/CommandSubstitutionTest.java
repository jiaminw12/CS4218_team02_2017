package sg.edu.nus.comp.cs4218.ef2;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class CommandSubstitutionTest {

	ShellImpl shell;
	ByteArrayOutputStream out;
	String input = "";
	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");
	private static final String PATH_SEPARATOR = File.separator;
	private static final String RELATIVE_STRANGER1 = "folder" + PATH_SEPARATOR
			+ "stranger1";
	private static final String RELATIVE_RICK2 = "folder" + PATH_SEPARATOR + "rickroll2";

	@Before
	public void setupBeforeTest() {
		shell = new ShellImpl();
		out = new ByteArrayOutputStream();
	}

	@Test
	public void testIntegrationGrepCat()
			throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate("grep that `cat slicing.txt`", out);
		assertEquals("  ", out.toString());
	}
	
	@Test
	public void testIntegrateGrepHead() throws AbstractApplicationException, ShellException {
		input = "grep rt `head -n 3 " + RELATIVE_RICK2 + "`";
		String expected = "Never gonna run around and desert you" + LINE_SEPARATOR;
		shell.parseAndEvaluate(input, out);
		assertEquals(expected, out.toString());
	}
	
	@Test
	public void testIntegrateGrepTail() throws AbstractApplicationException, ShellException {
		input = "grep gonna `tail -n 2 " + RELATIVE_RICK2 + "`";
		String expected = "Never gonna tell a lie and hurt you";
		shell.parseAndEvaluate(input, out);
		assertEquals(expected, out.toString());
	}

	@Test
	public void testCmdSubIntegrateCat()
			throws AbstractApplicationException, ShellException {
		input = "echo abc`cat slicing.txt`";
		shell.parseAndEvaluate(input, out);
		assertEquals(
				"abcProgram slicing can be used in debugging to locate source "
						+ "of errors more easily.",
				out.toString().trim());
	}

	@Test
	public void testCmdSubIntegrateHead()
			throws AbstractApplicationException, ShellException {
		input = "echo 123`head -n 2 " + RELATIVE_STRANGER1 + "`456";
		String expected = "123Darlin\' you got to let me know "
				+ "Should I stay or should I go?456" + LINE_SEPARATOR;
		shell.parseAndEvaluate(input, out);
		assertEquals(expected, out.toString());
	}

	@Test
	public void testCmdSubIntegrateTail()
			throws AbstractApplicationException, ShellException {
		input = "echo gg`tail -n 3 " + RELATIVE_STRANGER1 + "`gg";
		String expected = "ggI'll be here 'til the end of time "
				+ "So you got to let me know "
				+ "Should I stay or should I go?gg" + LINE_SEPARATOR;
		shell.parseAndEvaluate(input, out);
		assertEquals(expected, out.toString());
	}

}
