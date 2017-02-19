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
		assertTrue(headApp.checkValidFile(new File("cybody40.txt")));
	}

	@Test(expected = HeadException.class)
	public void testOtherFileType() throws HeadException {
		assertFalse(headApp.checkValidFile(new File("cybody40.md")));
	}

	@Test(expected = HeadException.class)
	public void testHeadAppWithNullArgument() throws HeadException {
		String[] args = {};
		headApp.run(args, System.in, System.out);
	}
	
	@Test(expected = HeadException.class)
	public void testHeadAppWithIllegalOption() throws HeadException {
		String[] args = { "head", "-a", "15", "cybody40.txt"};
		headApp.run(args, System.in, System.out);
	}

	@Test(expected = HeadException.class)
	public void testHeadAppWith5Arguments() throws HeadException {
		String[] args = { "head", "-n", "15", "cybody40.txt", "text2.txt" };
		headApp.run(args, System.in, System.out);
	}
	
	@Test(expected = HeadException.class)
	public void testHeadAppWithIllegalNumOfLinesUseNegativeDigits() throws HeadException {
		String[] args = { "head", "-n", "-2", "cybody40.txt"};
		headApp.run(args, System.in, System.out);
	}
	
	@Test
	public void testHeadAppWithIllegalNumOfLinesUseZero() throws HeadException {
		String[] args = { "head", "-n", "0", "cybody40.txt" };
		headApp.run(args, System.in, System.out);
	}

	@Test(expected = HeadException.class)
	public void testHeadAppWithIllegalNumOfLinesUseChar() throws HeadException {
		String[] args = { "head", "-n", "aaa", "cybody40.txt" };
		headApp.run(args, System.in, System.out);
	}
	
	@Test(expected = HeadException.class)
	public void testHeadAppWithIllegalFile() throws HeadException {
		String[] args = { "head", "-n", "15", "text2.txt" };
		headApp.run(args, System.in, System.out);
	}

	@Test(expected = HeadException.class)
	public void testHeadAppWith3Arguments() throws HeadException {
		String[] args = { "head", "-n", "cybody40.txt" };
		headApp.run(args, System.in, System.out);
	}

	@Test
	public void testHeadAppWithoutOption() throws HeadException {
		String[] args = { "head", "cybody40.txt" };
		headApp.run(args, System.in, outContent);
		assertEquals(
				"They may be found on menus in restaurants that serve seafood.\n"
						+ "Clams are a fairly common form of bivalve, therefore making it part of the phylum mollusca.\n"
						+ "They may be found on menus in restaurants that serve seafood.\n"
						+ "Clams are a fairly common form of bivalve, therefore making it part of the phylum mollusca.\n",
				outContent.toString());
	}

	@Test
	public void testHeadAppWithOption() throws HeadException {
		String[] args = { "head", "-n", "2", "cybody40.txt" };
		headApp.run(args, System.in, outContent);
		assertEquals(
				"They may be found on menus in restaurants that serve seafood.\n"
						+ "Clams are a fairly common form of bivalve, therefore making it part of the phylum mollusca.\n",
				outContent.toString());
	}

	@Test
	public void testHeadAppWithoutOptionInStdin() throws HeadException {
		ByteArrayInputStream stdin = new ByteArrayInputStream(
				("They may be found on menus in restaurants that serve seafood.\n")
						.getBytes());
		String[] args = { "head" };
		headApp.run(args, stdin, outContent);
		assertEquals(
				"They may be found on menus in restaurants that serve seafood.\n",
				outContent.toString());
	}

	@Test
	public void testHeadAppWithOptionInStdin() throws HeadException {
		ByteArrayInputStream stdin = new ByteArrayInputStream(
				("They may be found on menus in restaurants that serve seafood.\n"
						+ "Clams are a fairly common form of bivalve, therefore making it part of the phylum mollusca.\n"
						+ "They may be found on menus in restaurants that serve seafood.\n"
						+ "Clams are a fairly common form of bivalve, therefore making it part of the phylum mollusca.\n")
								.getBytes());
		String[] args = { "head", "-n", "1" };
		headApp.run(args, stdin, outContent);
		assertEquals(
				"They may be found on menus in restaurants that serve seafood.\n",
				outContent.toString());
	}

}
