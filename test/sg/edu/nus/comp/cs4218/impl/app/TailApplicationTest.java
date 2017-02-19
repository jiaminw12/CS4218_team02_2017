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
		assertTrue(tailApp.checkValidFile(new File("cybody40.txt")));
	}

	@Test(expected = TailException.class)
	public void testOtherFileType() throws TailException {
		assertFalse(tailApp.checkValidFile(new File("cybody40.md")));
	}

	@Test(expected = TailException.class)
	public void testHeadAppWithNullArgument() throws TailException {
		String[] args = {};
		tailApp.run(args, System.in, System.out);
	}
	
	@Test(expected = TailException.class)
	public void testHeadAppWithIllegalOption() throws TailException {
		String[] args = { "tail", "-a", "15", "cybody40.txt"};
		tailApp.run(args, System.in, System.out);
	}

	@Test(expected = TailException.class)
	public void testHeadAppWith5Arguments() throws TailException {
		String[] args = { "tail", "-n", "15", "cybody40.txt", "text2.txt" };
		tailApp.run(args, System.in, System.out);
	}
	
	@Test(expected = TailException.class)
	public void testHeadAppWithIllegalNumOfLinesUseNegativeDigits() throws TailException {
		String[] args = { "tail", "-n", "-2", "cybody40.txt"};
		tailApp.run(args, System.in, System.out);
	}
	
	@Test
	public void testHeadAppWithIllegalNumOfLinesUseZero() throws TailException {
		String[] args = { "tail", "-n", "0", "cybody40.txt" };
		tailApp.run(args, System.in, System.out);
	}

	@Test(expected = TailException.class)
	public void testHeadAppWithIllegalNumOfLinesUseChar() throws TailException {
		String[] args = { "tail", "-n", "aaa", "cybody40.txt" };
		tailApp.run(args, System.in, System.out);
	}
	
	@Test(expected = TailException.class)
	public void testHeadAppWithIllegalFile() throws TailException {
		String[] args = { "tail", "-n", "15", "text2.txt" };
		tailApp.run(args, System.in, System.out);
	}

	@Test(expected = TailException.class)
	public void testHeadAppWith3Arguments() throws TailException {
		String[] args = { "tail", "-n", "cybody40.txt" };
		tailApp.run(args, System.in, System.out);
	}

	@Test
	public void testHeadAppWithoutOption() throws TailException {
		String[] args = { "tail", "cybody40.txt" };
		tailApp.run(args, System.in, outContent);
		assertEquals(
				"They may be found on menus in restaurants that serve seafood.\n"
						+ "Clams are a fairly common form of bivalve, therefore making it part of the phylum mollusca.\n"
						+ "They may be found on menus in restaurants that serve seafood.\n"
						+ "Clams are a fairly common form of bivalve, therefore making it part of the phylum mollusca.\n",
				outContent.toString());
	}

	@Test
	public void testHeadAppWithOption() throws TailException {
		String[] args = { "tail", "-n", "1", "cybody40.txt" };
		tailApp.run(args, System.in, outContent);
		assertEquals(
				"Clams are a fairly common form of bivalve, therefore making it part of the phylum mollusca.\n",
				outContent.toString());
	}

	@Test
	public void testHeadAppWithoutOptionInStdin() throws TailException {
		ByteArrayInputStream stdin = new ByteArrayInputStream(
				("They may be found on menus in restaurants that serve seafood.\n")
						.getBytes());
		String[] args = { "tail" };
		tailApp.run(args, stdin, outContent);
		assertEquals(
				"They may be found on menus in restaurants that serve seafood.\n",
				outContent.toString());
	}

	@Test
	public void testHeadAppWithOptionInStdin() throws TailException {
		ByteArrayInputStream stdin = new ByteArrayInputStream(
				("They may be found on menus in restaurants that serve seafood.\n"
						+ "Clams are a fairly common form of bivalve, therefore making it part of the phylum mollusca.\n"
						+ "They may be found on menus in restaurants that serve seafood.\n"
						+ "Clams are a fairly common form of bivalve, therefore making it part of the phylum mollusca.\n")
								.getBytes());
		String[] args = { "tail", "-n", "1" };
		tailApp.run(args, stdin, outContent);
		assertEquals(
				"Clams are a fairly common form of bivalve, therefore making it part of the phylum mollusca.\n",
				outContent.toString());
	}

}
