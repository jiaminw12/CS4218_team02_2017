package sg.edu.nus.comp.cs4218.impl.cmd;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Vector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.HeadException;
import sg.edu.nus.comp.cs4218.exception.ShellException;

public class CallCommandTest {

	static CallCommand callCmd;
	private Vector<String> cmdVector;
	String inputLine;
	public InputStream stdin;
	public OutputStream stdout;

	@BeforeClass
	public static void setUpOnce() {

	}

	@Before
	public void setUp() {
		callCmd = new CallCommand();
		cmdVector = new Vector<String>();
	}

	@After
	public void tearDown() {
		System.setOut(null);
	}

	@AfterClass
	public static void tearDownOnce() {
		callCmd = null;
	}
	
	@Test(expected = ShellException.class)
	public void testExtractArgsInvalidSingleQuoted() throws ShellException, AbstractApplicationException {
		inputLine = "text1 \'\'text2\' text3";
		callCmd.extractArgs(" " + inputLine + " ", cmdVector);
	}
	
	@Test(expected = ShellException.class)
	public void testExtractArgsInvalidDoubleQuoted() throws ShellException, AbstractApplicationException {
		inputLine = "text1 \"\"text2\" text3";
		callCmd.extractArgs(" " + inputLine + " ", cmdVector);
	}
	
	@Test(expected = ShellException.class)
	public void testExtractArgsInvalidBackQuoted() throws ShellException, AbstractApplicationException {
		inputLine = "text1 \"\"text2\" text3";
		callCmd.extractArgs(" " + inputLine + " ", cmdVector);
	}
	
	@Test(expected = ShellException.class)
	public void testExtractArgsUnqouted() throws HeadException, ShellException {
		inputLine = "cat text1.txt | cat text2.txt";
		callCmd.extractArgs(" " + inputLine + " ", cmdVector);
	}
	
	@Test(expected = ShellException.class)
	public void testExtractArgsInvalidSemicolon() throws ShellException, AbstractApplicationException {
		inputLine = "text1; text2; text3";
		callCmd.extractArgs(" " + inputLine + " ", cmdVector);
	}
	
	@Test(expected = ShellException.class)
	public void testExtractArgsInvalidQuoted() throws ShellException, AbstractApplicationException {
		inputLine = "text1 \"text2' text3";
		callCmd.extractArgs(" " + inputLine + " ", cmdVector);
	}

	@Test
	public void testExtractArgsDash() throws HeadException, ShellException {
		inputLine = "-text1 -text2 -text3";
		callCmd.extractArgs(" " + inputLine + " ", cmdVector);
		String[] resultStrArr = { "-text1", "-text2", "-text3" };
		Vector<String> resultStrVect = new Vector<String>(Arrays.asList(resultStrArr));
		assertEquals(cmdVector, resultStrVect);
	}
	
	@Test
	public void testExtractArgsSingleQuoted() throws ShellException {
		inputLine = "'text1' 'text2' 'text3'";
		callCmd.extractArgs(" " + inputLine + " ", cmdVector);
		String[] resultStrArr = { "text1", "text2", "text3"};
		Vector<String> resultStrVect = new Vector<String>(Arrays.asList(resultStrArr));
		assertEquals(cmdVector, resultStrVect);
	}
	
	@Test
	public void testExtractArgsDoubleQuoted() throws ShellException {
		inputLine = "\"text1\" \"text2\" \"text3\"";
		callCmd.extractArgs(" " + inputLine + " ", cmdVector);
		String[] resultStrArr = { "text1", "text2", "text3"};
		Vector<String> resultStrVect = new Vector<String>(Arrays.asList(resultStrArr));
		assertEquals(cmdVector, resultStrVect);
	}
	
	@Test
	public void testExtractArgsBackQuoted() throws ShellException {
		inputLine = "`text1` text2 `text3`";
		callCmd.extractArgs(" " + inputLine + " ", cmdVector);
		String[] resultStrArr = { "`text1`", "text2", "`text3`"};
		Vector<String> resultStrVect = new Vector<String>(Arrays.asList(resultStrArr));
		assertEquals(cmdVector, resultStrVect);
	}
	
	@Test
	public void testExtractArgsBQinDQ() throws HeadException, ShellException {
		inputLine = "echo \"`hello world`\"";
		callCmd.extractArgs(" " + inputLine + " ", cmdVector);
		String[] resultStrArr = { "echo", "`hello world`"};
		Vector<String> resultStrVect = new Vector<String>(Arrays.asList(resultStrArr));
		assertEquals(cmdVector, resultStrVect);
	}
	
	@Test
	public void testExtractArgsMultipleTypesTest() throws ShellException {
		inputLine = "text1 \'text2\' \"text3 `text3.2`\" \"dir/text-4.txt\" -text5";
		callCmd.extractArgs(" " + inputLine + " ", cmdVector);
		String[] resultStrArr = { "text1", "text2", "text3 `text3.2`", "dir/text-4.txt", "-text5" };
		Vector<String> resultStrVect = new Vector<String>(Arrays.asList(resultStrArr));
		assertEquals(cmdVector, resultStrVect);
	}
	
	@Test (expected = ShellException.class)
	public void testGlobbing() throws AbstractApplicationException, ShellException{
		inputLine = "cat article/*";
		stdin = new ByteArrayInputStream(inputLine.getBytes());
		stdout = System.out;
		callCmd.evaluate(stdin, stdout);
	}
	
}
