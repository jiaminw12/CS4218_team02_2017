package sg.edu.nus.comp.cs4218.ef2;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.WcException;
import sg.edu.nus.comp.cs4218.impl.app.WcApplication;

public class WcApplicationTest {

	WcApplication wcApp;
	final static String PATH_SEPARATOR = File.separator;
	final static String LINE_SEPARATOR = System.getProperty("line.separator");
	final static String RELATIVE_TEST_DIRECTORY = String
			.format("folder%sSedAndWCFiles%s", PATH_SEPARATOR, PATH_SEPARATOR);

	@Before
	public void setUp() throws WcException, IOException {
		wcApp = new WcApplication();
	}

	@AfterClass
	public static void tearDownAfterTest() {
		System.setOut(System.out);
	}

	@Test
	public void testPrintAllCountsInFile() {
		assertEquals(
				"6617123 1095695  128457 " + RELATIVE_TEST_DIRECTORY
						+ "big.txt",
				wcApp.printAllCountsInFile(
						"wc -mwl " + RELATIVE_TEST_DIRECTORY + "big.txt")
						.trim());
	}

	@Test
	public void testPrintAllCountsInStdin() {
		InputStream stdin;
		try {
			stdin = new ByteArrayInputStream(Files.readAllBytes(
					Paths.get(RELATIVE_TEST_DIRECTORY +  "text1")));
			assertEquals("    3647     592      71 " + System.lineSeparator(),
					wcApp.printAllCountsInStdin("wc -lmw", stdin));
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
	}

	@Test
	public void testRunPrintCharacterCountInStdin() {
		InputStream stdin;
		try {
			stdin = new ByteArrayInputStream(Files.readAllBytes(
					Paths.get(RELATIVE_TEST_DIRECTORY + "textblank")));
			assertEquals("     198 " + LINE_SEPARATOR,
					wcApp.printCharacterCountInStdin("wc -m", stdin));
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
	}
}
