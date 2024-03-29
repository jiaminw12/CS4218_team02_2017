package sg.edu.nus.comp.cs4218.impl.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.DirectoryNotFoundException;

/*
 * Assumptions:
 * 1) run function will call the correct functions with the correct inputs in the 
 * correct order separated by a space
 * 2) Run function will take inputs directly from shell ordered or unordered
 * 3) Path of files and the file name must not contain any spaces
 * 4) Args for run: ordered consisting of cd and a valid file path
 * 
 */

/**
 * The cd command changes the current working directory.
 * 
 * <p>
 * <b>Command format:</b> <code>cd PATH</code>
 * <dl>
 * <dt>PATH</dt>
 * <dd>relative directory path</dd>
 * </dl>
 * </p>
 * 
 */
public class CdApplication implements Application {
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout)
			throws AbstractApplicationException {
		try {
			setDirectory(args);
		} catch (DirectoryNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 
	 * @param args
	 *            Array of elements for the application. 2nd argument of the
	 *            array contains the file path that the would like to go to.
	 * @throws DirectoryNotFoundException
	 *             If application has errors
	 */
	public void setDirectory(String[] args) throws DirectoryNotFoundException {
		if (args == null) {
			throw new DirectoryNotFoundException("Invalid Arguments.");
		} else if (args.length == 1) {
			throw new DirectoryNotFoundException(
					"Insufficient Argument: Input directory required.");
		} else if (args.length > 2) {
			throw new DirectoryNotFoundException("Too many Arguments.");
		} else {
			String filePath = args[1];
			File file = new File(filePath);

			if (!file.exists()) {
				throw new DirectoryNotFoundException("File does not exists.");
			} else if (!file.isDirectory()) {
				throw new DirectoryNotFoundException("Invalid Arguments.");
			}

			try {
				if (!(filePath.equals("."))) {
					if (filePath.equals("..")) {
						String path = Environment.currentDirectory;
						Environment.currentDirectory = path.substring(0,
								path.lastIndexOf(File.separator));
					} else {
						Environment.currentDirectory = file.getCanonicalPath();
					}
				}
			} catch (IOException e) {
				new IOException("Unable to change Directory.");
			}
		}
	}

}
