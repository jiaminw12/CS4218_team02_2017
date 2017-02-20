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
		String[] args = {"echo", "'Travel time Singapore -> Paris is 13h and 15`'"};
		echoApp.run(args, null, null);
	}
	
	@Test(expected = EchoException.class)
	public void testEchoAppWithDoubleQuoteWithNewline() throws EchoException {
		String[] args = {"echo", "\"\n\""};
		echoApp.run(args, null, outContent);
	}
	
	@Test(expected = EchoException.class)
	public void testEchoAppWithDoubleQuoteWithDoubleQuote() throws EchoException {
		String[] args = {"echo", "\"\"\"\""};
		echoApp.run(args, null, outContent);
	}
	
	@Test(expected = EchoException.class)
	public void testEchoAppWithDoubleQuoteWithBackQuote() throws EchoException {
		String[] args = {"echo", "``"};
		echoApp.run(args, null, outContent);
	}
	
	@Test
	public void testEchoAppWithoutQuoteSingle() throws EchoException {
		String[] args = {"echo", "hello"};
		echoApp.run(args, null, outContent);
		assertEquals("hello", outContent.toString());
	}
	
	@Test
	public void testEchoAppWithoutQuoteDouble() throws EchoException {
		String[] args = {"echo", "hello", "world"};
		echoApp.run(args, null, outContent);
		assertEquals("hello world", outContent.toString());
	}
	
	@Test
	public void testEchoAppWithSingleBackQuote() throws EchoException {
		String[] args = {"echo", "'Travel time Singapore -> Paris is 13h and 15`'"};
		echoApp.run(args, null, outContent);
		assertEquals("Travel time Singapore -> Paris is 13h and 15`", outContent.toString());
	}
	
	@Test
	public void testEchoAppWithDoubleBackQuote() throws EchoException {
		String[] args = {"echo", "\"This is space:`echo \" \"`.\""};
		echoApp.run(args, null, outContent);
		assertEquals("This is space: .", outContent.toString());
	}
	
	@Test
	public void testEchoAppWithSingleDoubleBackQuote() throws EchoException {
		String[] args = {"echo", "'This is space:`echo \" \"`.'"};
		echoApp.run(args, null, outContent);
		assertEquals("This is space:`echo \" \"`.", outContent.toString());
	}
	
	@Test
	public void testEchoAppWithDoubleBackQuoteWithGlobbibg() throws EchoException {
		String[] args = {"echo", "\"This is space:`echo \" \"`*.\""};
		echoApp.run(args, null, outContent);
		assertEquals("This is space: *.", outContent.toString());
	}
	
	@Test
	public void testEchoAppWithSpecialCharacters() throws EchoException {
		String[] args = {"echo", "\t*&^<>$ +|~`"};
		echoApp.run(args, null, outContent);
		assertEquals("\t*&^<>$ +|~`", outContent.toString());
	}
	
	

}
