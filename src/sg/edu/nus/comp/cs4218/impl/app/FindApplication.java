package sg.edu.nus.comp.cs4218.impl.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.FindException;

/**
 * The find command finds all files in current directory.
 * 
 * <p>
 * <b>Command format:</b> <code>find [OPTIONS] [FILE EXTENSION]</code>
 * <dl>
 * <dt>OPTIONS</dt>
 * <dd>“-name” Find the name of file(s)</dd>
 * <dt>FILE EXTENSION</dt>
 * <dd>the extension of file(s).</dd>
 * </dl>
 * </p>
 */
public class FindApplication implements Application {

	private static ArrayList<String> fileNameList = new ArrayList<String>();
	private String currentPath = Environment.currentDirectory;
	private String nextLineString = "\n";
	private String tabString = "\t";
	boolean optionFlag = false;
	boolean folderFlag = false;

	File tempInput = null;
	File tempOutput = null;
	OutputStream fileOutStream = null;

	/**
	 * Runs the find application with the specified arguments.
	 * 
	 * @param args
	 *            Array of arguments for the application. Each array element is
	 *            the path to a file. If no files are specified stdin is used.
	 * @param stdin
	 *            An InputStream. The input for the command is read from this
	 *            InputStream if no files are specified.
	 * @param stdout
	 *            An OutputStream. The output of the command is written to this
	 *            OutputStream.
	 * 
	 * @throws WcException
	 *             If the file(s) specified do not exist or are unreadable.
	 */
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout)
			throws FindException {

		if (args == null || args.length == 0) {
			throw new FindException("Null Pointer Exception");
		}

		if (stdout == null) {
			throw new FindException("Null Pointer Exception");
		}

		if (args.length > 2) {
			for (int i = 1; i < args.length; i++) {
				if (args[i].startsWith("-")) {
					if (!(args[i].equals("-name"))) {
						throw new FindException("Invalid options");
					} else {
						optionFlag = true;
					}
				} else {
					if (optionFlag) {
						String queryString = args[i].replaceAll("\\*", "\\.+");
						Path path = Paths.get(currentPath);
						getFileNames(path, queryString);
						printList(fileNameList, stdout);
						fileNameList = new ArrayList<String>();
						optionFlag = false;
					} else {
						throw new FindException("Null Pointer Exception");
					}
				}
			}
		} else {
			throw new FindException("Invalid Length");
		}

	}

	/**
	 * Processes and print counts of the array list of filename
	 *
	 * @param dir
	 *            Path of current directory.
	 * @param queryStr
	 *            Query String to find all files.
	 * @throws IOException
	 * 
	 */
	private void printList(ArrayList<String> fileNameList, OutputStream stdout)
			throws FindException {
		if (!(fileNameList.isEmpty())) {
			try {
				for (int i = 0; i < fileNameList.size(); i++) {
					stdout.write(fileNameList.get(i).getBytes());
					stdout.write(nextLineString.getBytes());
				}
			} catch (IOException e) {
				throw new FindException("IO Exception");
			}
		}
	}

	/**
	 * Find the files with query string
	 *
	 * @param dir
	 *            Path of current directory.
	 * @param queryStr
	 *            Query String to find all files.
	 * @throws IOException
	 * 
	 */
	private void getFileNames(Path dir, String queryStr) {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (Path path : stream) {
				if (path.toFile().isDirectory()) {
					getFileNames(path, queryStr);
				} else {

					String parentPath = path.getParent().toString();
					String xxx = parentPath + File.separator + queryStr;
					if ((path.toFile().toString()).matches(xxx)) {

						/*System.out.println(
								checkFileIsDirectory(new File("test")));
						System.out.println("testing : "
								+ path.toAbsolutePath().toString());*/
					}

					if ((path.getFileName().toString()).matches(queryStr)) {
						fileNameList.add(path.toAbsolutePath().toString());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the absolute path of given filePath
	 *
	 * @param filePath
	 *            A string. Use this to generate absolute path
	 */
	public String getAbsolutePath(String filePath) {
		if (filePath.startsWith(Environment.currentDirectory)) {
			return filePath;
		}
		return Environment.currentDirectory + File.separator + filePath;
	}

	/**
	 * Check whether file is directory
	 *
	 * @param file
	 *            A file. To check whether is directory.
	 */
	private boolean checkFileIsDirectory(File file) {
		if (file.isDirectory()) {
			return true;
		}
		return false;
	}

	/**
	 * Check whether file exists
	 *
	 * @param file
	 *            A file. To check whether exists.
	 */
	private boolean checkFileExist(File file) {
		if (file.exists()) {
			return true;
		}
		return false;
	}

}
