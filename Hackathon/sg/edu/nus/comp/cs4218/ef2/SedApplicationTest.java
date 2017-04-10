package sg.edu.nus.comp.cs4218.ef2;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.SedException;
import sg.edu.nus.comp.cs4218.impl.app.SedApplication;

public class SedApplicationTest {

	private static SedApplication sed;

	public static final String PATH_SEPARATOR = File.separator;
	public static final String RELATIVE_TEST_DIRECTORY = "src" + PATH_SEPARATOR + "test_hackathon" + PATH_SEPARATOR + "ef2"
			+ PATH_SEPARATOR + "sed" + PATH_SEPARATOR + "sed";
	public static final String ABSOLUTE_TEST_DIRECTORY = Environment.currentDirectory + PATH_SEPARATOR + "src"
			+ PATH_SEPARATOR + "test_hackathon" + PATH_SEPARATOR + "ef2" + PATH_SEPARATOR + "sed" + PATH_SEPARATOR + "sed";
	public static final String NEW_LINE_S = System.lineSeparator();
	
	@BeforeClass
	public static void setup() {
		sed = new SedApplication();
	}

	@Before
	public void setupBeforeTest() {
		assertTrue(deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in"));
		assertTrue(deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in2"));
		assertTrue(deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "out"));
	}

	@After
	public void tearDownAfterTest() {
		assertTrue(deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in"));
		assertTrue(deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in2"));
		assertTrue(deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "out"));
	}
	
	/**
	 * Space as regex is not replaced with replacement
	 * Ref. page 13, section 7.2.11, s/regex/replacement/g is supposed to replace all substrings matched by regex, all spaces is supposed to be replaced with tail, however, it is not.
	 */
	@Test
	public void testSedWithSpaceRegex() throws SedException {

		String[] arg = {"sed", "s/ /tail/g" };
		String content = NEW_LINE_S + NEW_LINE_S + "  head heat of the moment hi" + NEW_LINE_S
				+ "piehihahead head" + NEW_LINE_S + "head who are u?" + NEW_LINE_S;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEW_LINE_S + NEW_LINE_S + "tailtailheadtailheattailoftailthetailmomenttailhi"
				+ NEW_LINE_S + "piehihaheadtailhead" + NEW_LINE_S + "headtailwhotailaretailu?"
				+ NEW_LINE_S;

		assertEquals(expected, out);
	}
	/**
	 * Regex is supposed to be replaced with empty space but did not do so
	 * Ref. page 13, section 7.2.11, s/regex/ /g is supposed to replace all substring with matching regex to ' ' but is not replaced.
	 */
	@Test
	public void testSedWithSpaceReplacement() throws SedException {

		String[] arg = {"sed", "s/head/ /g" };
		String content = NEW_LINE_S + NEW_LINE_S + "  head heat of the moment hi" + NEW_LINE_S
				+ "piehihahead head" + NEW_LINE_S + "head who are u?" + NEW_LINE_S;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEW_LINE_S + NEW_LINE_S + "    heat of the moment hi" + NEW_LINE_S
				+ "piehiha   " + NEW_LINE_S + "  who are u?" + NEW_LINE_S;

		assertEquals(expected, out);
	}
	
	/**
	 * Empty replacement is thrown as an exception
	 */
	@Test
	public void testSedWithEmptyReplacement() throws SedException {

		String[] arg = {"sed", "s/head//g" };
		String content = NEW_LINE_S + NEW_LINE_S + "  head heat of the moment hi" + NEW_LINE_S
				+ "piehihahead head" + NEW_LINE_S + "head who are u?" + NEW_LINE_S;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEW_LINE_S + NEW_LINE_S + "   heat of the moment hi" + NEW_LINE_S
				+ "piehiha " + NEW_LINE_S + " who are u?" + NEW_LINE_S;

		assertEquals(out, expected);
	}
	
	/**
	 * Original space in input from stdin is not supposed to be replaced
	 *  Ref. specs. page 7.2.11, s/regex/replacement/ is supposed to replace all substrings matching regexp
	 */
	@Test
	public void testSedWithBackSlashAsSeparator() throws SedException {

		String[] arg = {"sed", "s\\hihihi\\a\\g" };
		String content = NEW_LINE_S + NEW_LINE_S + "  hihihi heat of the moment hi" + NEW_LINE_S
				+ "piehihahihihi hihihi" + NEW_LINE_S + "hihihi who are u?" + NEW_LINE_S;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEW_LINE_S + NEW_LINE_S + "  a heat of the moment hi" + NEW_LINE_S
				+ "piehihaa a" + NEW_LINE_S + "a who are u?" + NEW_LINE_S;

		assertEquals(expected, out);
	}
	
	/**
	 * New line in original file is replaced (unintended behaviour)
	 * Ref. specs. page 7.2.11, s/regex/replacement/ is supposed to replace first substring in each line, however, new line is not part of the regex to be preplaced
	 */
	@Test
	public void testSedReplaceFirstWithOverlappingMatchedSubString() throws SedException {

		String[] arg = {"sed", "s\\hihihi\\%\\" };
		String content = NEW_LINE_S + NEW_LINE_S + "  hihihihihi heat of the moment hi"
				+ NEW_LINE_S + "piehihahihihi hihihihihi" + NEW_LINE_S + "hihihihhi who are u?"
				+ NEW_LINE_S;

		ByteArrayInputStream stdin = new ByteArrayInputStream(content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());

		String expected = NEW_LINE_S + NEW_LINE_S + "  %hihi heat of the moment hi" + NEW_LINE_S
				+ "piehiha% hihihihihi" + NEW_LINE_S + "%hhi who are u?";

		assertEquals(out, expected);
	}

	private static boolean createFile(String file, String content) {
		String fileName = file;
		
		if (fileName == null || content == null) {
			return false;
		}

		fileName = Environment.getAbsPath(fileName);

		if (!Environment.isExists(fileName)) {
			if (!Environment.createNewFile(fileName)) {
				return false;
			}
		} else if (Environment.isDirectory(fileName)) {
			return false;
		}

		try {
			FileOutputStream os = new FileOutputStream(fileName);
			os.write(content.getBytes());
			os.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	private static boolean deleteFile(String absPath) {

		if (absPath == null) {
			return false;
		}

		if (!Environment.isExists(absPath)) {
			return true;
		}

		boolean isDeleted = false;
		File file = new File(absPath);
		try {
			isDeleted = file.delete();
		} catch (SecurityException e) {
		}

		return isDeleted;
	}
}
