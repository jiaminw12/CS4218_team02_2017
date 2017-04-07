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

	private static ShellImpl shell;
	private String expected = "";;

	@Before
	public void setupBeforeTest() {
		shell = new ShellImpl();
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
}
