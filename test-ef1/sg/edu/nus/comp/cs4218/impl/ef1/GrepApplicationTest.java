package sg.edu.nus.comp.cs4218.impl.ef1;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.GrepException;
import sg.edu.nus.comp.cs4218.impl.app.GrepApplication;

public class GrepApplicationTest {

	static GrepApplication grepApp;
	static File testDir = new File("testGrepDir");

	@BeforeClass
	public static void setUpOnce() throws IOException {
		testDir.mkdir();
		File testFile = new File(testDir, "1.txt");
		testFile.createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
		writer.write("134 678 8900 #@#^&!&((*97543)");
		writer.write(System.lineSeparator());
		writer.write("The quick brown fox jumps over the lazy dog");
		writer.close();

		testFile = new File(testDir, "2.txt");
		testFile.createNewFile();
		writer = new BufferedWriter(new FileWriter(testFile));
		writer.write("The 234quick brown fox 456 jumps over the lazy dog");
		writer.write(System.lineSeparator());
		writer.close();
	}

	@AfterClass
	public static void tearDown() {
		for (File file : testDir.listFiles()) {
			file.delete();
		}
		testDir.delete();
	}

	@Before
	public void setUp() throws GrepException, IOException {
		grepApp = new GrepApplication();
	}

	@Test(expected = GrepException.class)
	public void testGrepaPPWithNullArgument() throws GrepException {
		grepApp.run(null, null, System.out);
	}

	@Test(expected = GrepException.class)
	public void testGrepaPPWithEmptyArgsWithEmptyInput() throws GrepException {
		String[] args = {};
		grepApp.run(args, System.in, System.out);
	}

	@Test(expected = GrepException.class)
	public void testGrepaPPWithNullInputOutput() throws GrepException {
		String[] args = { "cal", "12", "2017" };
		grepApp.run(args, null, null);
	}

	@Test(expected = GrepException.class)
	public void testIllegalOption() throws GrepException {
		String[] args = { "cal", "12", "2017" };
		grepApp.run(args, null, null);
	}

	@Test
	public void testGrepFromStdin() {
		// grepApp.setInputStream("a\n");
		String actualResult = grepApp.grepFromStdin("grep [0-9]");
		String expectedResult = "134" + System.lineSeparator() + "678"
				+ System.lineSeparator() + "8900" + System.lineSeparator()
				+ "97543";
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testGrepFromOneFile() {
		String actualResult = grepApp.grepFromOneFile("grep [0-9] 1.txt");
		String expectedResult = "134" + System.lineSeparator() + "678"
				+ System.lineSeparator() + "8900" + System.lineSeparator()
				+ "97543";
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testGrepFromMultipleFiles() {
		String actualResult = grepApp
				.grepFromMultipleFiles("grep [0-9] 1.txt 2.txt");
		String expectedResult = "134" + System.lineSeparator() + "678"
				+ System.lineSeparator() + "8900" + System.lineSeparator()
				+ "97543" + System.lineSeparator() + "234"
				+ System.lineSeparator() + "456";
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	public void testGrepInvalidPatternInStdin() {
		String actualResult = grepApp
				.grepInvalidPatternInFile("grep [0-9)");
		String expectedResult = "grep: Invalid pattern";
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	public void testGrepInvalidPatternInFile() {
		String actualResult = grepApp
				.grepInvalidPatternInFile("grep [0-9) 2.txt");
		String expectedResult = "grep: Invalid pattern";
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
