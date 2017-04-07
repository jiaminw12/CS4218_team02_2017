package sg.edu.nus.comp.cs4218.impl;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class ShellImplTest {

	private ShellImpl shell;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setUpBeforeTest() {
		shell = new ShellImpl();
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void tearDownAfterTest() {
		System.setOut(System.out);
	}

	@Test(expected = ShellException.class)
	public void testNullArg()
			throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate(null, System.out);
		String expectedOut = null;
		assertEquals(expectedOut, outContent.toString());
	}

	@Test(expected = ShellException.class)
	public void testNoAppName()
			throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate(" ''''odd number of single quote' ", System.out);
	}

}
