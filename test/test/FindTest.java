package test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.junit.*;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.FindException;
import sg.edu.nus.comp.cs4218.impl.app.FindApplication;

public class FindTest {

	static FindApplication findApp = new FindApplication();
	OutputStream stdout;

	String tmpExt = ".txt";
	String charString = "-m";
	String wordString = "-w";
	String lineString = "-l";
	String command;
	String tabString = "\t";
	String nextLineString = "\n";

	@BeforeClass
	public static void setUpOnce() throws FindException {
		// one-time initialization code
	}

	@Before
	public void setUp() throws FindException, IOException {
		findApp = new FindApplication();
		stdout = new java.io.ByteArrayOutputStream();
	}

	@Test(expected = FindException.class)
	public void testFindAppWithNullArgument() throws FindException {
		findApp.run(null, null, System.out);
	}

	@Test(expected = FindException.class)
	public void testFindAppWithNullInputOutput() throws FindException {
		String[] args = { "find", "-name", "cybody40.txt" };
		findApp.run(args, null, null);
	}

	@Test(expected = FindException.class)
	public void testFindAppWithEmptyArgsWithEmptyInput() throws FindException {
		String[] args = {};
		findApp.run(args, null, System.out);
	}

	@Test(expected = FindException.class)
	public void testIllegalOption() throws FindException {
		String[] args = { "find", "-na", "cybody40.txt" };
		findApp.run(args, null, System.out);
	}

	@Test
	public void testFindExactFileName() throws FindException {
		// find -name cybody40.txt
		String[] args = { "find", "-name", "cybody40.txt" };
		findApp.run(args, null, stdout);
		String expectedResult = Environment.currentDirectory + File.separator
				+ "cybody40.txt" + nextLineString + Environment.currentDirectory
				+ File.separator + "test" + File.separator + "cybody40.txt"
				+ nextLineString;
		assertEquals(expectedResult, stdout.toString());
	}

	@Test
	public void testFindExactNotExistFile() throws FindException {
		// find -name cybody40
		String[] args = { "find", "-name", "cybody40" };
		findApp.run(args, null, stdout);
		String expectedResult = "";
		assertEquals(expectedResult, stdout.toString());
	}

	@Test
	public void testFindAllTxt() throws FindException {
		// find -name *.txt
		String[] args = { "find", "-name", "*.txt" };
		findApp.run(args, null, stdout);
		String expectedResult = Environment.currentDirectory + File.separator
				+ "cxintro02.txt" + nextLineString
				+ Environment.currentDirectory + File.separator + "cybody40.txt"
				+ nextLineString + Environment.currentDirectory + File.separator
				+ "test" + File.separator + "cxintro02.txt" + nextLineString
				+ Environment.currentDirectory + File.separator + "test"
				+ File.separator + "cybody40.txt" + nextLineString;
		assertEquals(expectedResult, stdout.toString());
	}

	@Test
	public void testFindFileWithStarAtMiddle() throws FindException {
		// find -name cy*.txt
		String[] args = { "find", "-name", "cy*.txt" };
		findApp.run(args, null, stdout);
		String expectedResult = Environment.currentDirectory + File.separator
				+ "cybody40.txt" + nextLineString + Environment.currentDirectory
				+ File.separator + "test" + File.separator + "cybody40.txt"
				+ nextLineString;
		assertEquals(expectedResult, stdout.toString());
	}
	
	@Test
	public void testFindFileWithStarAtEnd() throws FindException {
		// find -name cy*
		String[] args = { "find", "-name", "cy*" };
		findApp.run(args, null, stdout);
		String expectedResult = Environment.currentDirectory + File.separator
				+ "cybody40.txt" + nextLineString + Environment.currentDirectory
				+ File.separator + "test" + File.separator + "cybody40.txt"
				+ nextLineString;
		assertEquals(expectedResult, stdout.toString());
	}
	

	// FIND FOLDER
	// find -name test
	// find -name /test
	// find -name test-hackathon-files/wr-test/short.txt
	
	// FIND FILES
	// find -name test-files-ef1/clam1533/*.txt
	
	// find -name cybody40.txt
	// find -name *.txt
	// find -name cy*.txt
	// find -name cy*
	
	// FIND FOLDER
	// find -name test
	// find -name test/*.txt
	
	@Test
	public void testFindFileWithProvidedPath() throws FindException {
		// find -name cy*
		String[] args = { "find", "-name", "cy*" };
		findApp.run(args, null, stdout);
		String expectedResult = Environment.currentDirectory + File.separator
				+ "cybody40.txt" + nextLineString + Environment.currentDirectory
				+ File.separator + "test" + File.separator + "cybody40.txt"
				+ nextLineString;
		assertEquals(expectedResult, stdout.toString());
	}

	@After
	public void tearDown() {
	}

	@AfterClass
	public static void tearDownOnce() {
		// one-time cleanup code
		findApp = null;
	}

}
