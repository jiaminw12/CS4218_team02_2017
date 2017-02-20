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

import sg.edu.nus.comp.cs4218.exception.TailException;

public class TailApplicationTest {

	static TailApplication tailApp;
	private ByteArrayOutputStream outContent;

	@BeforeClass
	public static void setUpOnce() {

	}

	@Before
	public void setUp() {
		tailApp = new TailApplication();
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void tearDown() {
		System.setOut(null);
	}

	@AfterClass
	public static void tearDownOnce() {
		tailApp = null;
	}

	@Test
	public void testCheckTxtFile() throws TailException {
		assertTrue(tailApp.checkValidFile(new File("muttest.txt")));
	}

	@Test
	public void testOtherFileType() throws TailException {
		assertTrue(tailApp.checkValidFile(new File("README.md")));
	}

	@Test(expected = TailException.class)
	public void testHeadAppWithNullArgument() throws TailException {
		String[] args = {};
		tailApp.run(args, System.in, outContent);
	}

	@Test(expected = TailException.class)
	public void testHeadAppWithIllegalOption() throws TailException {
		String[] args = { "tail", "-a", "15", "muttest.txt" };
		tailApp.run(args, System.in, outContent);
	}

	@Test(expected = TailException.class)
	public void testHeadAppWith5Arguments() throws TailException {
		String[] args = { "tail", "-n", "15", "muttest.txt", "slicing.txt" };
		tailApp.run(args, System.in, outContent);
	}

	@Test(expected = TailException.class)
	public void testHeadAppWithIllegalNumOfLinesUseNegativeDigits()
			throws TailException {
		String[] args = { "tail", "-n", "-2", "muttest.txt" };
		tailApp.run(args, System.in, outContent);
	}

	@Test(expected = TailException.class)
	public void testHeadAppWithIllegalNumOfLinesUseChar() throws TailException {
		String[] args = { "tail", "-n", "aaa", "muttest.txt" };
		tailApp.run(args, System.in, outContent);
	}

	@Test(expected = TailException.class)
	public void testHeadAppWithIllegalFile() throws TailException {
		String[] args = { "tail", "-n", "15", "muttestS.txt" };
		tailApp.run(args, System.in, outContent);
	}

	@Test(expected = TailException.class)
	public void testHeadAppWith3Arguments() throws TailException {
		String[] args = { "tail", "-n", "muttest.txt" };
		tailApp.run(args, System.in, outContent);
	}

	@Test
	public void testHeadAppWithIllegalNumOfLinesUseZero() throws TailException {
		String[] args = { "tail", "-n", "0", "muttest.txt" };
		tailApp.run(args, System.in, outContent);
	}

	@Test
	public void testHeadAppWithoutOption() throws TailException {
		String[] args = { "tail", "slicing.txt" };
		tailApp.run(args, System.in, outContent);
		assertEquals(
				"Program slicing can be used in debugging to locate source of errors more easily.\n",
				outContent.toString());
	}

	@Test
	public void testHeadAppWithOption() throws TailException {
		String[] args = { "tail", "-n", "1", "muttest.txt" };
		tailApp.run(args, System.in, outContent);
		assertEquals("}\n", outContent.toString());
	}

	@Test
	public void testHeadAppWithMultiplesOption() throws TailException {
		String[] args = { "tail", "-n2", "-n5", "-n", "3", "muttest.txt" };
		tailApp.run(args, System.in, outContent);
		assertEquals("    ps2.run();\n" + "  }\n" + "}\n", outContent.toString());
	}

	@Test
	public void testHeadAppWithoutOptionInStdin() throws TailException {
		ByteArrayInputStream stdin = new ByteArrayInputStream(
				("They may be found on menus in restaurants that serve seafood. SEA SEA SEA\n")
						.getBytes());
		String[] args = { "tail" };
		tailApp.run(args, stdin, outContent);
		assertEquals(
				"They may be found on menus in restaurants that serve seafood. SEA SEA SEA\n",
				outContent.toString());
	}

	@Test
	public void testHeadAppWithOptionInStdin() throws TailException {
		ByteArrayInputStream stdin = new ByteArrayInputStream(
				("They may be found on menus in restaurants that serve seafood. SEA SEA SEA\n")
						.getBytes());
		String[] args = { "tail", "-n", "1" };
		tailApp.run(args, stdin, outContent);
		assertEquals(
				"They may be found on menus in restaurants that serve seafood. SEA SEA SEA\n",
				outContent.toString());
	}

	@Test
	public void testHeadAppWithMultiplesOptionInStdin() throws TailException {
		ByteArrayInputStream stdin = new ByteArrayInputStream(
				("They may be found on menus in restaurants that serve seafood. SEA SEA SEA\n")
						.getBytes());
		String[] args = { "tail", "-n2", "-n5", "-n", "999" };
		tailApp.run(args, stdin, outContent);
		assertEquals(
				"They may be found on menus in restaurants that serve seafood. SEA SEA SEA\n",
				outContent.toString());
	}

}
