package sg.edu.nus.comp.cs4218.impl.ef1;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
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
	static File testFolder = new File("testGrepDir");

//	@BeforeClass
//	public static void setUpOnce() throws IOException {
//		testFolder.mkdir();
//		File testFile = new File(testFolder, "01.txt");
//		testFile.createNewFile();
//		BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
//		writer.write("123");
//		writer.write(System.lineSeparator());
//		writer.write("4567");
//		writer.write(System.lineSeparator());
//		writer.write("89001 )(*&^%$#!@*9745543");
//		writer.write(System.lineSeparator());
//		writer.write("Fox nymphs grab quick-jived waltz.");
//		writer.close();
//
//		testFile = new File(testFolder, "02.txt");
//		testFile.createNewFile();
//		writer = new BufferedWriter(new FileWriter(testFile));
//		writer.write(
//				"Li nov 346lingua franca va esser 236plu simplic e regulari quam 457li existent Europan lingues.");
//		writer.write(System.lineSeparator());
//		writer.close();
//	}

//	@AfterClass
//	public static void tearDown() {
//		for (File file : testFolder.listFiles()) {
//			file.delete();
//		}
//		testFolder.delete();
//	}

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
	public void testGrepFromOneFile() {
		String actualResult = grepApp.grepFromOneFile("Fox test/sg/edu/nus/comp/cs4218/impl/app/01.txt");
		String expectedResult = "Fox nymphs grab quick-jived waltz.";
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testGrepFromMultipleFiles() {
		String actualResult = grepApp
				.grepFromMultipleFiles("grab test/sg/edu/nus/comp/cs4218/impl/app/01.txt test/sg/edu/nus/comp/cs4218/impl/app/02.txt");
		String expectedResult = "grab 4567" + System.lineSeparator() + "Fox nymphs grab quick-jived waltz."
				+ System.lineSeparator()
				+ "grab Li nov 346lingua franca va esser 236plu simplic e regulari quam 457li existent Europan lingues.";
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testGrepInvalidPatternInStdin() {
		String actualResult = grepApp.grepInvalidPatternInFile("grep [0-9*)");
		String expectedResult = "grep: Invalid pattern";
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testGrepInvalidPatternInFile() {
		String actualResult = grepApp
				.grepFromOneFile("grep [0-9*) 02.txt");
		String expectedResult = "Pattern Not Found In File!";
		assertEquals(expectedResult, actualResult);
	}

}
