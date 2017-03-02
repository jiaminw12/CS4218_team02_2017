package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.DirectoryNotFoundException;

public class CdApplicationTest {

	private static CdApplication cdApp;
	private static String originalPath;
	private static File testDir;
	private static File folderDepth1;
	private static File folderDepth2;
	private static File folderDepth3;

	@BeforeClass
	public static void setUpOnce() {
		originalPath = Environment.currentDirectory;

		testDir = new File("test_resources");
		testDir.mkdir();

		folderDepth1 = new File(testDir, "depth1");
		folderDepth1.mkdir();

		folderDepth2 = new File(folderDepth1, "depth2");
		folderDepth2.mkdir();

		folderDepth3 = new File(folderDepth2, "depth3");
		folderDepth3.mkdir();

	}

	@Before
	public void setUp() throws IOException {
		cdApp = new CdApplication();
	}

	@After
	public void tearDown() {
	}

	@AfterClass
	public static void tearDownOnce() {
		folderDepth3.delete();
		folderDepth2.delete();
		folderDepth1.delete();
		testDir.delete();

		cdApp = null;
		Environment.currentDirectory = originalPath;
	}

	@Test(expected = DirectoryNotFoundException.class)
	public void testCdAppWithNullArgument() throws DirectoryNotFoundException {
		cdApp.setDirectory(null);
	}

	@Test(expected = DirectoryNotFoundException.class)
	public void testCdAppWithIllegallLengthArgument()
			throws DirectoryNotFoundException {
		String[] args = { "cd", File.separatorChar + "testFolder" + File.separatorChar + "here" };
		cdApp.setDirectory(args);
	}

	@Test(expected = DirectoryNotFoundException.class)
	public void testCdAppWithIllegalDirectory()
			throws DirectoryNotFoundException {
		String[] args = { "cd", File.separatorChar + "testFolder" };
		cdApp.setDirectory(args);
	}

	@Test(expected = DirectoryNotFoundException.class)
	public void testCdAppWithIllegalFilePath()
			throws DirectoryNotFoundException {
		String[] args = { "cd", File.separatorChar + "testFolder"
				+ File.separatorChar + "test01.txt" };
		cdApp.setDirectory(args);
	}

	@Test(expected = DirectoryNotFoundException.class)
	public void testCdAppWithSymbol01() throws DirectoryNotFoundException {
		String[] args = { "cd", "~/test_resources" };
		cdApp.setDirectory(args);
	}

	@Test
	public void testCdAppWithValidDirectoryPath()
			throws DirectoryNotFoundException {
		String[] args = { "cd", "test_resources" };
		cdApp.setDirectory(args);
		assertEquals(originalPath + File.separatorChar + "test_resources",
				Environment.currentDirectory);
	}

	@Test
	public void testCdAppWithValidDirectoryPathInNestedLevel()
			throws DirectoryNotFoundException {
		String[] args = { "cd", "test_resources" + File.separatorChar
						+ "depth1" + File.separatorChar + "depth2"
						+ File.separatorChar + "depth3" };
		cdApp.setDirectory(args);
		assertEquals(
				originalPath + File.separatorChar + "test_resources"
						+ File.separatorChar + "depth1" + File.separatorChar
						+ "depth2" + File.separatorChar + "depth3",
				Environment.currentDirectory);
	}

	@Test
	public void testCdAppWithValidDirectoryPathFromRun()
			throws AbstractApplicationException {
		String[] args = { "cd", "test_resources" };
		cdApp.run(args, null, null);
		assertEquals(originalPath + File.separatorChar + "test_resources",
				Environment.currentDirectory);
	}

	@Test
	public void testCdAppWithValidDirectoryPathInNestedLevelFromRun()
			throws AbstractApplicationException {
		String[] args = { "cd", "test_resources" + File.separatorChar
				+ "depth1" + File.separatorChar + "depth2"
				+ File.separatorChar + "depth3" };
		cdApp.run(args, null, null);
		assertEquals(originalPath + File.separatorChar + "test_resources"
				+ File.separatorChar + "depth1" + File.separatorChar
				+ "depth2" + File.separatorChar + "depth3",
				Environment.currentDirectory);
	}

}
