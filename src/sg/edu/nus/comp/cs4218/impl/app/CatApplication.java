package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.CatException;
import sg.edu.nus.comp.cs4218.exception.HeadException;

/**
 * The cat command concatenates the content of given files and prints on the
 * standard output.
 * 
 * <p>
 * <b>Command format:</b> <code>cat [FILE]...</code>
 * <dl>
 * <dt>FILE</dt>
 * <dd>the name of the file(s). If no files are specified, use stdin.</dd>
 * </dl>
 * </p>
 */
public class CatApplication implements Application {

	/**
	 * Runs the cat application with the specified arguments.
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
	 * @throws CatException
	 *             If the file(s) specified do not exist or are unreadable.
	 */
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout)
			throws CatException {

		if (args == null || args.length == 0) {
			throw new CatException("Null Pointer Exception");
		}

		if (stdin == null || stdout == null) {
			throw new CatException("Null Pointer Exception");
		}

		try {

			if (args.length == 1) {
				String input = readFromStdin(args, stdin);
				stdout.write(input.getBytes());
				stdout.write(System.lineSeparator().getBytes());
			} else {
				int numOfFiles = args.length;

				if (numOfFiles > 0) {
					Path filePath;
					Path[] filePathArray = new Path[numOfFiles];
					Path currentDir = Paths.get(Environment.currentDirectory);
					boolean isFileReadable = false;
//					System.out.println("currentdir: "+currentDir);
					
					for (int i = 1; i < numOfFiles; i++) {
						filePath = currentDir.resolve(args[i]);
//						System.out.println("filePath: "+filePath);
						isFileReadable = checkIfFileIsReadable(filePath);
						if (isFileReadable) {
							filePathArray[i] = filePath;
						}
					}

					// file could be read. perform cat command
					if (filePathArray.length != 0) {
						String testStr = "";
						for (int j = 1; j < filePathArray.length; j++) {
							try {

								byte[] byteFileArray = Files
										.readAllBytes(filePathArray[j]);
								String byteString = new String(byteFileArray,
										"UTF-8");
								testStr = testStr + byteString;
								stdout.write(byteFileArray);
								stdout.write(System.lineSeparator().getBytes());
							} catch (IOException e) {
								throw new CatException(
										"Could not write to output stream");
							}
						}
					}
				}
			}
		} catch (IOException exIO) {
			exIO.printStackTrace();
			//throw new CatException("Exception Caught");
		}

	}
	
	/**
	 * Reads from stdin.
	 * 
	 * @param args
	 *            Array of arguments for the application. Each array element is
	 *            the path to a file. If no files are specified stdin is used.
	 * @param stdin
	 *            An InputStream. The input for the command is read from this
	 *            InputStream if no files are specified.
	 */
	private String readFromStdin(String[] args, InputStream stdin) throws CatException {

		StringBuffer text = new StringBuffer();
		String str = "";

		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stdin, "UTF-8"));
			
			while((str = bufferedReader.readLine()) != null) {
				text.append(str);
				text.append(System.getProperty("line.separator"));
			}
			
		} catch (IOException e) {
			throw new CatException("Could not read input");
		}
		
		// try to replace substrings
		if(text == null || text.toString().isEmpty()) {
			throw new CatException("Invalid Input");
		} 

		return text.toString().trim();
	}

	/**
	 * Checks if a file is readable.
	 * 
	 * @param filePath
	 *            The path to the file
	 * @return True if the file is readable.
	 * @throws CatException
	 *             If the file is not readable
	 */
	boolean checkIfFileIsReadable(Path filePath) throws CatException {

		if (Files.isDirectory(filePath)) {
			throw new CatException("This is a directory");
		}
		if (Files.exists(filePath) && Files.isReadable(filePath)) {
			return true;
		} else if (Files.notExists(filePath)) {
//			System.out.println(filePath);
			throw new CatException("No such file exists");
		} else {
			throw new CatException("Could not read file");
		}
	}
}
