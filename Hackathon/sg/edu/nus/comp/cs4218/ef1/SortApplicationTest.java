package sg.edu.nus.comp.cs4218.ef1;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.app.SortApplication;

public class SortApplicationTest {
	final static String PATH_SEPARATOR = File.separator;
	final static String LINE_SEPARATOR = System.getProperty("line.separator");
	final static String RELATIVE_TEST_DIRECTORY = "folder" + PATH_SEPARATOR
			+ "SortFiles" + PATH_SEPARATOR;
	private ByteArrayOutputStream output = new ByteArrayOutputStream();
	String expectedOutput = "";
	SortApplication sort;

	@Before
	public void setupBeforeTest() {
		sort = new SortApplication();
		System.setOut(new PrintStream(output));
	}

	@Test
	public void testRunSortStartLineNumberWithOpt()
			throws AbstractApplicationException, IOException {

		expectedOutput = new String(
				Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY
						+ "expected" + PATH_SEPARATOR + "testRunOption")));
		String file = RELATIVE_TEST_DIRECTORY + "testRunOption";
		String[] args = { "sort", "-n", file };
		sort.run(args, System.in, System.out);
		assertEquals(expectedOutput + LINE_SEPARATOR, output.toString());
	}

	@Test
	public void testRunSortStartLineNumberWithOptSpace()
			throws AbstractApplicationException, IOException {
		expectedOutput = new String(
				Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY
						+ "expected" + PATH_SEPARATOR + "testRunOptionSpace")));
		String file = RELATIVE_TEST_DIRECTORY + "testRunOptionSpace";
		String[] args = { "sort", "-n", file };
		sort.run(args, System.in, System.out);
		assertEquals(expectedOutput + LINE_SEPARATOR, output.toString());
	}
	
	@Test
	public void testRunSortNumbersSpecialChars()
			throws AbstractApplicationException, IOException {
		expectedOutput = new String(Files
				.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY + "expected"
						+ PATH_SEPARATOR + "testSortNumbersSpecialChars")));
		String file = RELATIVE_TEST_DIRECTORY + "testSortNumbersSpecialChars";
		String[] args = { "sort", file };
		sort.run(args, System.in, System.out);
		assertEquals(expectedOutput + LINE_SEPARATOR, output.toString());
	}

	@Test
	public void testRunSortSimpleCapitalSpecialChars()
			throws AbstractApplicationException, IOException {
		expectedOutput = new String(Files.readAllBytes(
				Paths.get(RELATIVE_TEST_DIRECTORY + "expected" + PATH_SEPARATOR
						+ "testSortSimpleCapitalSpecialChars")));
		String file = RELATIVE_TEST_DIRECTORY
				+ "testSortSimpleCapitalSpecialChars";
		String[] args = { "sort", file };
		sort.run(args, System.in, System.out);
		assertEquals(expectedOutput + LINE_SEPARATOR, output.toString());
	}

}
