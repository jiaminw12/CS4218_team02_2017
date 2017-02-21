package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.EchoException;

public class EchoApplicationTest {
	
	static EchoApplication echoApp;
	private ByteArrayOutputStream outContent;

	@BeforeClass
	public static void setUpOnce() {

	}

	@Before
	public void setUp() {
		echoApp = new EchoApplication();
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void tearDown() {
		System.setOut(null);
	}

	@AfterClass
	public static void tearDownOnce() {
		echoApp = null;
	}
	
	@Test(expected = EchoException.class)
	public void testEchoAppWithNullArgument() throws EchoException {
		echoApp.run(null, null, outContent);
	}
	
	@Test(expected = EchoException.class)
	public void testEchoAppWithNullOutputStream() throws EchoException  {
		String[] args = {"echo", "Travel time Singapore -> Paris is 13h and 15`"};
		echoApp.run(args, null, null);
	}
	
	@Test
	public void testEchoAppWithSingleQuote() throws EchoException {
		String[] args = {"echo", "'Walallalal'"};
		echoApp.run(args, null, outContent);
		String actualResult = outContent.toString();
		assertEquals("'Walallalal'", actualResult.trim());
	}
	
	@Test
	public void testEchoAppWithNewline() throws EchoException {
		String[] args = {"echo", ""};
		echoApp.run(args, null, outContent);
		String actualResult = outContent.toString();
		assertEquals("", actualResult.trim());
	}
	
	@Test
	public void testEchoAppWithDoubleQuote() throws EchoException {
		String[] args = {"echo", "This is space: `echo \" \"`."};
		echoApp.run(args, null, outContent);
		String actualResult = outContent.toString();
		assertEquals("This is space: `echo \" \"`.", actualResult.trim());
	}
	
	@Test
	public void testEchoAppWithoutQuote() throws EchoException {
		String[] args = {"echo", "hello"};
		echoApp.run(args, null, outContent);
		String actualResult = outContent.toString();
		assertEquals("hello", actualResult.trim());
	}
	
	@Test
	public void testEchoAppWithoutQuoteMultipleArgs() throws EchoException {
		String[] args = {"echo", "hello", "world"};
		echoApp.run(args, null, outContent);
		String actualResult = outContent.toString();
		assertEquals("hello world", actualResult.trim());
	}
	
	@Test
	public void testEchoAppWithABackqQuote() throws EchoException {
		String[] args = {"echo", "Travel time Singapore -> Paris is 13h and 15`"};
		echoApp.run(args, null, outContent);
		String actualResult = outContent.toString();
		assertEquals("Travel time Singapore -> Paris is 13h and 15`", actualResult.trim());
	}
	
	@Test
	public void testEchoAppWithMultipleArgs() throws EchoException {
		String[] args = {"echo", "This", "is space: . "};
		echoApp.run(args, null, outContent);
		String actualResult = outContent.toString();
		assertEquals("This is space: .", actualResult.trim());
	}
	
	@Test
	public void testEchoAppWithDoubleBackBackQuote() throws EchoException {
		String[] args = {"echo", "This is space:`echo \" \"`."};
		echoApp.run(args, null, outContent);
		String actualResult = outContent.toString();
		assertEquals("This is space:`echo \" \"`.", actualResult.trim());
	}
	
	@Test
	public void testEchoAppWithGlobbing() throws EchoException {
		String[] args = {"echo", "This is space:" ,"*."};
		echoApp.run(args, null, outContent);
		String actualResult = outContent.toString();
		assertEquals("This is space: *.", actualResult.trim());
	}
	
	@Test
	public void testEchoAppWithSpecialCharacters() throws EchoException {
		String[] args = {"echo", "*&^<>$ +|~`"};
		echoApp.run(args, null, outContent);
		String actualResult = outContent.toString();
		assertEquals("*&^<>$ +|~`", actualResult.trim());
	}
	
	

}
