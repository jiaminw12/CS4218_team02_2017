package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.HeadException;

public class HeadApplicationTest {

	static HeadApplication headApp;
	private ByteArrayOutputStream outContent;

	@BeforeClass
	public static void setUpOnce() {

	}

	@Before
	public void setUp() {
		headApp = new HeadApplication();
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void tearDown() {
		System.setOut(null);
	}

	@AfterClass
	public static void tearDownOnce() {
		headApp = null;
	}

	@Test
	public void testCheckTxtFile() throws HeadException {
		assertTrue(headApp.checkValidFile(new File("slicing.txt")));
	}

	@Test
	public void testOtherFileType() throws HeadException {
		assertTrue(headApp.checkValidFile(new File("README.md")));
	}

	@Test(expected = HeadException.class)
	public void testHeadAppWithNullArgument() throws HeadException {
		String[] args = {};
		headApp.run(args, System.in, outContent);
	}

	@Test(expected = HeadException.class)
	public void testHeadAppWithIllegalOption() throws HeadException {
		String[] args = { "head", "-a", "15", "muttest.txt" };
		headApp.run(args, System.in, outContent);
	}

	@Test(expected = HeadException.class)
	public void testHeadAppWith5Arguments() throws HeadException {
		String[] args = { "head", "-n", "15", "slicing.txt", "muttest.txt" };
		headApp.run(args, System.in, outContent);
	}

	@Test(expected = HeadException.class)
	public void testHeadAppWithIllegalNumOfLinesUseNegativeDigits()
			throws HeadException {
		String[] args = { "head", "-n", "-2", "muttest.txt" };
		headApp.run(args, System.in, outContent);
	}

	@Test
	public void testHeadAppWithIllegalNumOfLinesUseZero() throws HeadException {
		String[] args = { "head", "-n", "0", "muttest.txt" };
		headApp.run(args, System.in, outContent);
	}

	@Test(expected = HeadException.class)
	public void testHeadAppWithIllegalNumOfLinesUseChar() throws HeadException {
		String[] args = { "head", "-n", "aaa", "muttest.txt" };
		headApp.run(args, System.in, outContent);
	}

	@Test(expected = HeadException.class)
	public void testHeadAppWithIllegalFile() throws HeadException {
		String[] args = { "head", "-n", "15", "text2.txt" };
		headApp.run(args, System.in, outContent);
	}

	@Test(expected = HeadException.class)
	public void testHeadAppWith3Arguments() throws HeadException {
		String[] args = { "head", "-n", "muttest.txt" };
		headApp.run(args, System.in, outContent);
	}

	@Test
	public void testHeadAppWithoutOption() throws HeadException {
		String[] args = { "head", "slicing.txt" };
		headApp.run(args, System.in, outContent);
		assertEquals(
				"Program slicing can be used in debugging to locate source of errors more easily.\n",
				outContent.toString());
	}

	@Test
	public void testHeadAppWithOption() throws HeadException {
		String[] args = { "head", "-n", "2", "muttest.txt" };
		headApp.run(args, System.in, outContent);
		assertEquals(
				"// Copy paste this Java Template and save it as \"PatientNames.java\"\n"
						+ "import java.lang.String;\n",
				outContent.toString());
	}

	@Test
	public void testHeadAppWithMultiplesOption() throws HeadException {
		String[] args = { "head", "-n2", "-n5", "-n", "8", "muttest.txt" };
		headApp.run(args, System.in, outContent);
		assertEquals(
				"// Copy paste this Java Template and save it as \"PatientNames.java\"\n"
						+ "import java.lang.String;\n"
						+ "import java.lang.System;\n" 
						+ "import java.util.*;\n"
						+ "import java.io.*;\n"
						+ "import java.util.ArrayList;\n"
						+ "import java.util.TreeSet;\n" + "\n",
				outContent.toString());
	}

	@Test
	public void testHeadAppWithoutOptionInStdin() throws HeadException {
		ByteArrayInputStream stdin = new ByteArrayInputStream(
				("They may be found on menus in restaurants that serve seafood. UNDER THE SEA\n")
						.getBytes());
		String[] args = { "head" };
		headApp.run(args, stdin, outContent);
		assertEquals(
				"They may be found on menus in restaurants that serve seafood. UNDER THE SEA\n",
				outContent.toString());
	}

	@Test
	public void testHeadAppWithOptionInStdin() throws HeadException {
		ByteArrayInputStream stdin = new ByteArrayInputStream(
				("They may be found on menus in restaurants that serve seafood. UNDER THE SEA\n")
						.getBytes());
		String[] args = { "head", "-n", "1" };
		headApp.run(args, stdin, outContent);
		assertEquals(
				"They may be found on menus in restaurants that serve seafood. UNDER THE SEA\n",
				outContent.toString());
	}

	@Test
	public void testHeadAppWithMultiplesOptionInStdin() throws HeadException {
		ByteArrayInputStream stdin = new ByteArrayInputStream(
				("They may be found on menus in restaurants that serve seafood. UNDER THE SEA\n")
						.getBytes());
		String[] args = { "head", "-n2", "-n5", "-n", "999" };
		headApp.run(args, stdin, outContent);
		assertEquals(
				"They may be found on menus in restaurants that serve seafood. UNDER THE SEA\n",
				outContent.toString());
	}

}
