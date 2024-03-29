package sg.edu.nus.comp.cs4218.impl.ef1;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.GrepException;
import sg.edu.nus.comp.cs4218.impl.app.GrepApplication;

public class GrepApplicationTest {

	static GrepApplication grepApp;

	@Before
	public void setUp() throws GrepException, IOException {
		grepApp = new GrepApplication();
	}

	@Test(expected = GrepException.class)
	public void testGrepAppWithNullArgument() throws GrepException {
		String[] args = {"grep"};
		grepApp.run(args, null, System.out);
	}

	@Test(expected = GrepException.class)
	public void testGrepAppWithEmptyArgsWithEmptyInput() throws GrepException {
		String[] args = {};
		grepApp.run(args, System.in, System.out);
	}

	@Test(expected = GrepException.class)
	public void testGrepAppWithNullInputOutput() throws GrepException {
		String[] args = { "grep", "12" };
		grepApp.run(args, null, null);
	}

	@Test(expected = GrepException.class)
	public void testIllegalOption() throws GrepException {
		String[] args = { "cal", "12", "2017" };
		grepApp.run(args, null, null);
	}

	@Test
	public void testGrepFromStdin() {
		ByteArrayInputStream in = new ByteArrayInputStream(
				"134Lorem 678ipsum 8900dolor sit amet, consectetuer adi 97543piscing elit.\n"
						.getBytes());
		String actualResult = grepApp.grepFromStdin("134", in);
		String expectedResult = "134Lorem 678ipsum 8900dolor sit amet, consectetuer adi 97543piscing elit.";
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testGrepNoMatchFoundInStdin() {
		ByteArrayInputStream in = new ByteArrayInputStream(
				"134Lorem 678ipsum 8900dolor sit amet, consectetuer adi 97543piscing elit.\n"
						.getBytes());
		String actualResult = grepApp.grepFromStdin("hello", in);
		String expectedResult = "Pattern Not Found In Stdin!";
		assertEquals(expectedResult, actualResult);
	}
	
	@Test(expected = Exception.class)
	public void testGrepInvalidRegexInStdin() {
		ByteArrayInputStream in = new ByteArrayInputStream(
				"134Lorem 678ipsum 8900dolor sit amet, consectetuer adi 97543piscing elit.\n"
						.getBytes());
		String actualResult = grepApp.grepFromStdin("[0-9*)", in);
		String expectedResult = "Pattern Not Found In Stdin!";
		assertEquals(expectedResult, actualResult);
	}
	
	@Test(expected = Exception.class)
	public void testGrepInvalidRegexInFile() {
		String actualResult = grepApp
				.grepFromOneFile("[0-9*) 02.txt");
		String expectedResult = "Pattern Not Found In File!";
		assertEquals(expectedResult, actualResult);
	}
	
	@Test(expected = Exception.class)
	public void testGrepInvalidRegexInMultipleFile() {
		String actualResult = grepApp
				.grepFromOneFile("[0-9*) 01.txt 02.txt");
		String expectedResult = "Pattern Not Found In File!";
		assertEquals(expectedResult, actualResult);
	}
	
	@Test(expected = GrepException.class)
	public void testRunNullStdin() throws GrepException, IOException {
		String pattern = "some";
		String[] args = { "grep", pattern };
		grepApp.run(args, null, System.out);
	}
}
