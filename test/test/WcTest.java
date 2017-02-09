package test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.*;

import sg.edu.nus.comp.cs4218.exception.WcException;
import sg.edu.nus.comp.cs4218.impl.app.WcApplication;

public class WcTest {

	static WcApplication wcApp;

	String tempFileInput = "temp-file-input";
	String tempFileOutput = "temp-file-output";
	String tmpExt = ".txt";
	String charString = "-m";
	String wordString = "-w";
	String lineString = "-l";
	String command;
	String tabString = "\t";
	String nextLineString = "\n";

	@BeforeClass
	public static void setUpOnce() throws WcException {
		// one-time initialization code
	}

	@Before
	public void setUp() throws WcException, IOException {
		wcApp = new WcApplication();
	}

	@Test(expected = WcException.class)
	public void testWcAppWithNullArgument() throws WcException {
		wcApp.run(null, null, System.out);
	}
	
	@Test(expected = WcException.class)
	public void testWcAppWithEmptyArgsWithEmptyInput() throws WcException {
		String[] args = {};
		wcApp.run(args, null, System.out);
	}

	@Test(expected = WcException.class)
	public void testWcAppWithNullInputOutput() throws WcException {
		String[] args = { "wc", "-m", "cybody40.txt" };
		wcApp.run(args, null, null);
	}

	@Test(expected = WcException.class)
	public void testIllegalOption() throws WcException {
		String[] args = { "wc", "-z", "cybody40.txt" };
		wcApp.run(args, System.in, System.out);
	}

	@Test(expected = WcException.class)
	public void testIllegalFile() throws WcException {
		String[] args = { "wc", "-l", "cybody40" };
		wcApp.run(args, System.in, System.out);
	}
	
	@Test(expected = WcException.class)
	public void testIllegalFiles() throws WcException {
		String[] args = { "wc", "-l", "cybody40.txt", "invalidFileName#@#$$%" };
		wcApp.run(args, System.in, System.out);
	}

	@Test
	public void testPrintCharacterCountInFile() throws WcException {
		// wc -m cybody40.txt
		String[] args = { "wc", "-m", "cybody40.txt" };
		wcApp.run(args, System.in, System.out);

		String expectedResult = wcApp
				.printCharacterCountInFile("wc -m cybody40.txt");
		assertEquals("304" + tabString, expectedResult);
	}

	@Test
	public void testPrintWordCountInFile() throws WcException {
		// wc -w cybody40.txt
		String[] args = { "wc", "-w", "cybody40.txt" };
		wcApp.run(args, System.in, System.out);

		String expectedResult = wcApp
				.printWordCountInFile("wc -w cybody40.txt");
		assertEquals("54" + tabString, expectedResult);
	}

	@Test
	public void testPrintLineCountInFile() throws WcException {
		// wc -l cybody40.txt
		String[] args = { "wc", "-l", "cybody40.txt" };
		wcApp.run(args, System.in, System.out);

		String expectedResult = wcApp
				.printNewlineCountInFile("wc -l cybody40.txt");
		assertEquals("4" + tabString, expectedResult);
	}

	@Test
	public void testPrintAllCountsInFile() throws WcException {
		// wc -m -w -l cybody40.txt
		String[] args = { "wc", "-m", "-w", "-l", "cybody40.txt" };
		wcApp.run(args, System.in, System.out);

		String expectedResult = wcApp
				.printAllCountsInFile("wc -m -w -l cybody40.txt");
		assertEquals("304" + tabString + "54" + tabString + "4" + tabString,
				expectedResult);
	}

	@Test
	public void testPrintAllCharactersInTwoFiles() throws WcException {
		// wc -m cxintro02.txt cybody40.txt
		String[] args = { "wc", "-m", "cxintro02.txt", "cybody40.txt", };
		wcApp.run(args, System.in, System.out);

		String expectedResult = wcApp
				.printAllCountsInFile("wc -m cxintro02.txt cybody40.txt");
		assertEquals("47" + tabString + "304" + tabString + "351" + tabString,
				expectedResult);
	}

	@Test
	public void testPrintAllCountsInTwoFiles() throws WcException {
		// wc -m -w -l cxintro02.txt cybody40.txt
		String[] args = { "wc", "-m", "-w", "-l", "cxintro02.txt",
				"cybody40.txt" };
		wcApp.run(args, System.in, System.out);

		String expectedResult = wcApp
				.printAllCountsInFile("wc -m -w -l cxintro02.txt cybody40.txt");
		assertEquals(
				"47" + tabString + "8" + tabString + "1" + tabString + "304"
						+ tabString + "54" + tabString + "4" + tabString + "351"
						+ tabString + "62" + tabString + "5" + tabString,
				expectedResult);
	}

	@Test
	public void testPrintCharacterCountInStdin() throws WcException {
		// wc -m
		String[] args = { "wc", "-m" };
		InputStream in = new ByteArrayInputStream("a\nb\nc\n".getBytes());
		wcApp.run(args, in, System.out);

		String expectedResult = wcApp.printCharacterCountInStdin("wc -m");
		assertEquals("3" + tabString, expectedResult);
	}

	@Test
	public void testPrintWordCountInStdin() throws WcException {
		// wc -w
		String[] args = { "wc", "-w" };
		InputStream in = new ByteArrayInputStream("a\nb\nc\n".getBytes());
		wcApp.run(args, in, System.out);

		String expectedResult = wcApp.printWordCountInStdin("wc -w");
		assertEquals("3" + tabString, expectedResult);
	}

	@Test
	public void testPrintNewlineCountInStdin() throws WcException {
		// wc -l
		String[] args = { "wc", "-l" };
		InputStream in = new ByteArrayInputStream("a\nb\nc\n".getBytes());
		wcApp.run(args, in, System.out);

		String expectedResult = wcApp.printNewlineCountInStdin("wc -l");
		assertEquals("3" + tabString, expectedResult);
	}

	@Test
	public void testPrintAllCountsInStdin() throws WcException {
		// wc -m -w -l
		String[] args = { "wc", "-m", "-w", "-l" };
		InputStream in = new ByteArrayInputStream("a\nb\nc\n".getBytes());
		wcApp.run(args, in, System.out);

		String expectedResult = wcApp.printNewlineCountInFile("wc -m -w -l");
		assertEquals("3" + tabString + "3" + tabString + "3" + tabString,
				expectedResult);
	}

	@After
	public void tearDown() {
	}

	@AfterClass
	public static void tearDownOnce() {
		// one-time cleanup code
		wcApp = null;
	}

}
