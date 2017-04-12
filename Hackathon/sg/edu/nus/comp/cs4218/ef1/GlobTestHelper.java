package sg.edu.nus.comp.cs4218.ef1;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import sg.edu.nus.comp.cs4218.Environment;

public final class GlobTestHelper {
	
	private static final String PATH_SEPARATOR = File.separator;

	public static void setupGlobFiles(String absTestDirPath) {

		String[] dirsToCreate = getDirectoriesToCreate(absTestDirPath);
		String[] filesToCreate = getFilesToCreate(absTestDirPath);

		for (String filePath : filesToCreate) {
			deleteFile(filePath);
		}
		for (int i = dirsToCreate.length - 1; i >= 0; --i) {
			deleteFile(dirsToCreate[i]);
		}

		for (String dirPath : dirsToCreate) {
			File dir = createNewDirectory(dirPath);
			assertTrue(dir != null);
			dir.deleteOnExit();
		}
		for (String filePath : filesToCreate) {
			File file = createNewDirectory(filePath);
			assertTrue(file != null);
			file.deleteOnExit();
		}
	}

	private static File createNewDirectory(String absPath) {

		if (absPath == null) {
			return null;
		}

		boolean isCreated = false;
		File file = new File(absPath);

		if (file.exists()) {
			return file;
		}

		try {
			isCreated = file.mkdir();
		} catch (SecurityException e) {
		}

		return isCreated ? file : null;
	}

	public static File createNewFile(String absPath) {

		if (absPath == null) {
			return null;
		}

		boolean isCreated = false;
		File file = new File(absPath);

		if (file.exists()) {
			return file;
		}

		try {
			isCreated = file.createNewFile();
		} catch (IOException | SecurityException e) {
		}

		return isCreated ? file : null;
	}
	
	private static boolean isExists(String absPath) {

		if (absPath == null) {
			return false;
		}
		File file = new File(absPath);
		return file.exists();
	}

	private static boolean deleteFile(String absPath) {

		if (absPath == null) {
			return false;
		}

		if (!isExists(absPath)) {
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

	private static String[] getDirectoriesToCreate(String absTestDirPath) {

		if (absTestDirPath == null) {
			return new String[0];
		}
		String[] directories = { absTestDirPath + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "file with spaces",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "-carr",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "2712",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "cab",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "cat",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "2712" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab" + PATH_SEPARATOR
						+ "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR
						+ "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ ".cab.car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712" + PATH_SEPARATOR
						+ "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "file with spaces", absTestDirPath + PATH_SEPARATOR
						+ "file with spaces" + PATH_SEPARATOR + "New folder" };
		return directories;
	}

	private static String[] getFilesToCreate(String absTestDirPath) {

		if (absTestDirPath == null) {
			return new String[0];
		}
		String[] files = {
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "carrier",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "file with spaces",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + ".cab.car"
						+ PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-.-" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr" + PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr"
						+ PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "271.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712 2712 2712",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + ".cab.car" + PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "-.-"
						+ PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cab"
						+ PATH_SEPARATOR + "file with spaces",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "car"
						+ PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "2712" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "2712"
						+ PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "file with spaces",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cab" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "car" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-.-",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-.-.txt",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-carr",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "-carr.txt",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + ".cab.car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + ".cab.car.txt",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "2712",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "2712.txt",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cab",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cab.txt",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "car",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "car.txt",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cat",
				absTestDirPath + PATH_SEPARATOR + "cat" + PATH_SEPARATOR + "cat"
						+ PATH_SEPARATOR + "cat.txt",
				absTestDirPath + PATH_SEPARATOR + "file with spaces" + PATH_SEPARATOR
						+ "file with spaces",
				absTestDirPath + PATH_SEPARATOR + "file with spaces" + PATH_SEPARATOR
						+ "file with spaces.txt" };
		return files;
	}

	private GlobTestHelper() {
	}
}
