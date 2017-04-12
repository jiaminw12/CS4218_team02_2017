package sg.edu.nus.comp.cs4218.ef2;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.SedException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.app.SedApplication;

public class SedApplicationTest {

	private static SedApplication sed;

	public static final String PATH_SEPARATOR = File.separator;
	public static final String NEW_LINE_S = System.lineSeparator();
	public static final String RELATIVE_TEST_DIRECTORY = "src" + PATH_SEPARATOR
			+ "test_hackathon" + PATH_SEPARATOR + "ef2" + PATH_SEPARATOR + "sed"
			+ PATH_SEPARATOR + "sed";
	public static final String ABSOLUTE_TEST_DIRECTORY = Environment.currentDirectory
			+ PATH_SEPARATOR + "src" + PATH_SEPARATOR + "test_hackathon"
			+ PATH_SEPARATOR + "ef2" + PATH_SEPARATOR + "sed" + PATH_SEPARATOR
			+ "sed";
	final static String RELATIVE_TEST_DIRECTORY_SEDWCFOLDER = String.format(
			"folder%sSedAndWCFiles%ssedTestFiles%s", PATH_SEPARATOR,
			PATH_SEPARATOR, PATH_SEPARATOR);

	@BeforeClass
	public static void setup() {
		sed = new SedApplication();
	}

	@Before
	public void setupBeforeTest() {
		deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in");
		deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in2");
		deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "out");
	}

	@After
	public void tearDownAfterTest() {
		deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in");
		deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "in2");
		deleteFile(ABSOLUTE_TEST_DIRECTORY + PATH_SEPARATOR + "out");
	}

	@AfterClass
	public static void teardown() {
		deleteFile("out.txt");
	}

	@Test
	public void testSedWithSpaceRegex() throws SedException {

		String[] arg = { "sed", "s/ /tail/g" };
		String content = NEW_LINE_S + NEW_LINE_S
				+ "  head heat of the moment hi" + NEW_LINE_S
				+ "piehihahead head" + NEW_LINE_S + "head who are u?"
				+ NEW_LINE_S;

		ByteArrayInputStream stdin = new ByteArrayInputStream(
				content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());
		String expected = NEW_LINE_S + NEW_LINE_S
				+ "tailtailheadtailheattailoftailthetailmomenttailhi"
				+ NEW_LINE_S + "piehihaheadtailhead" + NEW_LINE_S
				+ "headtailwhotailaretailu?" + NEW_LINE_S;
		assertEquals(expected, out);
	}

	@Test
	public void testSedWithSpaceReplacement() throws SedException {

		String[] arg = { "sed", "s/head/ /g" };
		String content = NEW_LINE_S + NEW_LINE_S
				+ "  head heat of the moment hi" + NEW_LINE_S
				+ "piehihahead head" + NEW_LINE_S + "head who are u?"
				+ NEW_LINE_S;

		ByteArrayInputStream stdin = new ByteArrayInputStream(
				content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());
		String expected = NEW_LINE_S + NEW_LINE_S + "    heat of the moment hi"
				+ NEW_LINE_S + "piehiha   " + NEW_LINE_S + "  who are u?"
				+ NEW_LINE_S;
		assertEquals(expected, out);
	}

	@Test
	public void testSedWithEmptyReplacement() throws SedException {

		String[] arg = { "sed", "s/head//g" };
		String content = NEW_LINE_S + NEW_LINE_S
				+ "  head heat of the moment hi" + NEW_LINE_S
				+ "piehihahead head" + NEW_LINE_S + "head who are u?"
				+ NEW_LINE_S;

		ByteArrayInputStream stdin = new ByteArrayInputStream(
				content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());
		String expected = NEW_LINE_S + NEW_LINE_S + "   heat of the moment hi"
				+ NEW_LINE_S + "piehiha " + NEW_LINE_S + " who are u?"
				+ NEW_LINE_S;
		assertEquals(out, expected);
	}

	@Test
	public void testSedWithBackSlashAsSeparator() throws SedException {

		String[] arg = { "sed", "s\\hihihi\\a\\g" };
		String content = NEW_LINE_S + NEW_LINE_S
				+ "  hihihi heat of the moment hi" + NEW_LINE_S
				+ "piehihahihihi hihihi" + NEW_LINE_S + "hihihi who are u?"
				+ NEW_LINE_S;

		ByteArrayInputStream stdin = new ByteArrayInputStream(
				content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());
		String expected = NEW_LINE_S + NEW_LINE_S + "  a heat of the moment hi"
				+ NEW_LINE_S + "piehihaa a" + NEW_LINE_S + "a who are u?"
				+ NEW_LINE_S;
		assertEquals(expected, out);
	}

	@Test
	public void testSedReplaceFirstWithOverlappingMatchedSubString()
			throws SedException {

		String[] arg = { "sed", "s\\hihihi\\%\\" };
		String content = NEW_LINE_S + NEW_LINE_S
				+ "  hihihihihi heat of the moment hi" + NEW_LINE_S
				+ "piehihahihihi hihihihihi" + NEW_LINE_S
				+ "hihihihhi who are u?" + NEW_LINE_S;

		ByteArrayInputStream stdin = new ByteArrayInputStream(
				content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());
		String expected = NEW_LINE_S + NEW_LINE_S
				+ "  %hihi heat of the moment hi" + NEW_LINE_S
				+ "piehiha% hihihihihi" + NEW_LINE_S + "%hhi who are u?";
		assertEquals(out, expected);
	}

	@Test
	public void testSedReplaceAllWithGSeparator() throws SedException {

		String[] arg = { "sed", "sghihihigagg" };
		String content = NEW_LINE_S + NEW_LINE_S
				+ "  hihihi heat of the moment hi" + NEW_LINE_S
				+ "piehihahihihi hihihi" + NEW_LINE_S + "hihihi who are u?"
				+ NEW_LINE_S;

		ByteArrayInputStream stdin = new ByteArrayInputStream(
				content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		sed.run(arg, stdin, stdout);

		String out = new String(stdout.toByteArray());
		String expected = NEW_LINE_S + NEW_LINE_S + "  a heat of the moment hi"
				+ NEW_LINE_S + "piehihaa a" + NEW_LINE_S + "a who are u?"
				+ NEW_LINE_S;
		assertEquals(out, expected);
	}

	@Test
	public void testSedWithComplexReplacement()
			throws SedException, IOException {
		String args[] = { "sed", "s|o   |% #$%^&|" };
		ByteArrayInputStream stdin = new ByteArrayInputStream(
				Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY_SEDWCFOLDER
						+ "two-lines.txt")));
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		sed.run(args, stdin, stdout);
		String expected = "Hey, good to know <you>!" + NEW_LINE_S
				+ "This is a small file consists of {1+1+0} lines." + NEW_LINE_S
				+ "/* Hope this helps */ # no new line here";
		String msg = "error on sed command - incorrect output with complex replacement";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithComplexReplacement2()
			throws SedException, IOException {
		String args[] = { "sed", "s|no| *&/s\\$|" };
		ByteArrayInputStream stdin = new ByteArrayInputStream(
				Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY_SEDWCFOLDER
						+ "two-lines.txt")));
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		sed.run(args, stdin, stdout);
		String expected = "Hey, good to k *&/s\\$w <you>!" + NEW_LINE_S
				+ "This is a small file consists of {1+1+0} lines." + NEW_LINE_S
				+ "/* Hope this helps */ #  *&/s\\$ new line here";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with complex replacement";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithComplexRegexp() throws SedException, IOException {
		String args[] = { "sed", "s|^This|r|g" };
		ByteArrayInputStream stdin = new ByteArrayInputStream(
				Files.readAllBytes(Paths.get(RELATIVE_TEST_DIRECTORY_SEDWCFOLDER
						+ "two-lines.txt")));
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		sed.run(args, stdin, stdout);
		String expected = "Hey, good to know <you>!" + NEW_LINE_S
				+ "r is a small file consists of {1+1+0} lines." + NEW_LINE_S
				+ "/* Hope this helps */ # no new line here";
		String msg = "error on sed command - incorrect output with complex regular expression";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithTwoLineFile() throws SedException {
		String args[] = { "sed", "s|a|b|",
				RELATIVE_TEST_DIRECTORY_SEDWCFOLDER + "two-lines.txt" };
		ByteArrayInputStream stdin = null;
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		String expected = "Hey, good to know <you>!" + NEW_LINE_S
				+ "This is b small file consists of {1+1+0} lines." + NEW_LINE_S
				+ "/* Hope this helps */ # no new line here";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with two line file";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test
	public void testSedWithEmptyFileInputStream()
			throws SedException, IOException {
		String args[] = { "sed", "s|a|b|" };
		ByteArrayInputStream stdin = new ByteArrayInputStream(
				Files.readAllBytes(Paths.get(
						RELATIVE_TEST_DIRECTORY_SEDWCFOLDER + "empty.txt")));
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		String expected = "";
		sed.run(args, stdin, stdout);
		String msg = "error on sed command - incorrect output with empty file input stream";
		assertEquals(msg, expected, stdout.toString());
	}

	@Test(expected = SedException.class)
	public void testSedWithClosedInput() throws SedException, IOException {

		String content = NEW_LINE_S + NEW_LINE_S
				+ "  hihihi heat of the moment hi" + NEW_LINE_S
				+ "piehihahihihi hihihi" + NEW_LINE_S + "hihihi who are u?"
				+ NEW_LINE_S;
		String[] arg = { "sed", "s\\hihihi\\a\\g" };
		ByteArrayInputStream stdin = new ByteArrayInputStream(
				content.getBytes());
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		stdin.close();
		sed.run(arg, stdin, stdout);
	}

	@Test
	public void testIntegratePwd()
			throws AbstractApplicationException, ShellException {
		ShellImpl shell = new ShellImpl();
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		shell.parseAndEvaluate("sed s/sub/' '/g sedIntegration; pwd", stdout);
		String expected = "    string" + NEW_LINE_S + "replacement  string"
				+ NEW_LINE_S + " stitution";
		assertEquals(expected + NEW_LINE_S + Environment.currentDirectory
				+ NEW_LINE_S, stdout.toString());
	}

	@Test
	public void testIntegrateEcho()
			throws AbstractApplicationException, ShellException {
		ShellImpl shell = new ShellImpl();
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		shell.parseAndEvaluate(
				"sed s/sub/`echo many`/ sedIntegration | head -n 1",
				System.out);
		String expected = "many sub string" + NEW_LINE_S;
		assertEquals(expected, stdout.toString());
	}
	
	@Test
	public void testIntegrateHead() throws AbstractApplicationException, ShellException {
		ShellImpl shell = new ShellImpl();
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		shell.parseAndEvaluate("sed s/sub/`echo many`/ sedIntegration | head `cat parameters`", System.out);
		String expected = "many sub string" + NEW_LINE_S;
		assertEquals(expected, stdout.toString());
	}

	private static void deleteFile(String absPath) {
		File file = new File(absPath);
		file.delete();
	}
}
