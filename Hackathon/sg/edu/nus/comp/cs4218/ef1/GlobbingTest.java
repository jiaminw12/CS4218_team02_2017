package sg.edu.nus.comp.cs4218.ef1;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.*;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class GlobbingTest {

	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");
	private static final String PATH_SEPARATOR = File.separator;
	private static final String RELATIVE_DIR = "folder" + PATH_SEPARATOR
			+ "SedAndWCFiles" + PATH_SEPARATOR + "sedTestFiles"
			+ PATH_SEPARATOR;
	private final ByteArrayOutputStream output = new ByteArrayOutputStream();
	private String expected = "";
	private static ShellImpl shell;

	@Before
	public void setupBeforeTest() {
		shell = new ShellImpl();
		System.setOut(new PrintStream(output));
	}

	@Test
	public void testGlobAllPaths()
			throws AbstractApplicationException, ShellException {
		String cmdLine = "echo folder/GrepAndSortFiles/*";
		expected = "folder/GrepAndSortFiles/emptydoc.txt "
				+ "folder/GrepAndSortFiles/greptestdoc.txt "
				+ "folder/GrepAndSortFiles/greptestdoc2.txt "
				+ "folder/GrepAndSortFiles/MoreFiles "
				+ "folder/GrepAndSortFiles/MoreFiles/test.txt "
				+ "folder/GrepAndSortFiles/MoreFiles/test2.txt "
				+ "folder/GrepAndSortFiles/MoreFiles/testdoc.txt "
				+ "folder/GrepAndSortFiles/MoreFiles/TestSortMethods.txt "
				+ "folder/GrepAndSortFiles/MoreFiles/TestSortNumeric.txt ";
		String actual = shell.processGlob(cmdLine);
		assertEquals(expected, actual);
	}

	@Test
	public void testIntegrateGlobbingWithSedCat()
			throws AbstractApplicationException, ShellException {
		shell.parseAndEvaluate(
				"sed s/sub/many/ sedIntegration > sedIntegration2.txt; "
						+ "cat sedIntegration2.txt; cat " + RELATIVE_DIR + "*",
				System.out);
		String expected = "many sub string replacement substring substitution"
				+ LINE_SEPARATOR + LINE_SEPARATOR + "0123456789"
				+ LINE_SEPARATOR + "Hey, good to know <you>!" + LINE_SEPARATOR
				+ "This is a small file consists of {1+1+0} lines."
				+ LINE_SEPARATOR + "/* Hope this helps */ # no new line here"
				+ LINE_SEPARATOR;

		File file = new File("sedIntegration2.txt");
		if (file.exists()) {
			file.delete();
		}

		assertEquals(expected, output.toString());
	}

}
