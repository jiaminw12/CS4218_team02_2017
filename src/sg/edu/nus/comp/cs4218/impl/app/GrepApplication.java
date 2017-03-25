package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.app.Grep;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.GrepException;

/*
 * Assumptions: 
 * 1) run function will call the correct functions with the correct inputs in the 
 * correct order separated by a space
 * 2) Run function will take inputs directly from shell ordered or unordered
 * 3) Files only contain ASCII characters and are not folder or directory
 * 4) Path of files and the file name must not contain any spaces
 * 5) Valid file must be provided. Otherwise, it will be treated as a Regex Pattern.
 * 6) Does not support providing directory and filename in 2 separate arguments
 * 7) Args for run: grep with ordered or unordered consisting of pattern and files
 * 8) Args for grepFromOneFile: pattern, file
 * 9) Args for grepFromMultipleFiles: pattern, file, file, ...
 * 10) Args for grepFromStdin: pattern (tdin will be parsed from run)
 * 11) Args for grepInvalidPatternInStdin: grep, pattern (Stdin will be parsed from run)
 * 12) Arg for grepInvalidPatternInFile: grep, pattern, file
 * 
 */

/**
 * 
 * The grep command searches for lines containing a match to a specified
 * pattern. The output of the command is the list of the lines. Each line is
 * printed followed by a newline
 * 
 * <p>
 * <b>Command format:</b> <code>grep PATTERN [FILE]...</code>
 * <dl>
 * <dt>PATTERN</dt>
 * <dd>specifies a regular expression in JAVA format.</dd>
 * <dt>FILE</dt>
 * <dd>the name of the file(s). If not specified, use stdin.</dd>
 * </dl>
 * </p>
 */
public class GrepApplication implements Application, Grep {
	String line;

	/**
	 * Runs the grep application with the specified arguments.
	 * 
	 * @param args
	 *            Array of arguments for the application.
	 * @param stdin
	 *            An InputStream, not used.
	 * @param stdout
	 *            An OutputStream. Elements of args will be output to stdout,
	 *            separated by a space character.
	 * 
	 * @throws GrepException
	 *             If an I/O exception occurs.
	 */
	public void run(String[] args, InputStream stdin, OutputStream stdout)
			throws GrepException {
		
		if (stdin == null) {
			throw new GrepException("Input Stream should not be null");
		}

		if (args.length == 1 && args[0].equalsIgnoreCase("grep")) {
			throw new GrepException("Null arguments");
		}
		if (stdout == null) {
			throw new GrepException("OutputStream not provided");
		}

		int numberOfTxtFiles = 0;
		int numberOfRegex = 0;
		StringBuilder builder = new StringBuilder();
		for (int s = 1; s < args.length; s++) {
			try {
				File file = new File(args[s]);
				if (checkValidFile(file) && isFileValid(file)) {
					numberOfTxtFiles++;
				}
			} catch (GrepException e) {
				try {
					Pattern.compile((args[s]));
					numberOfRegex++;
				} catch (PatternSyntaxException e1) {

				}
			}
			builder.append(args[s]);
			builder.append(" ");
		}

		if (numberOfRegex == 0) {
			throw new GrepException("No pattern detected");
		} else if (numberOfRegex > 1) {
			throw new GrepException("Too many patterns found");
		}

		String argsString = builder.toString();
		if (numberOfTxtFiles == 0) {

			if (args.length == 2) {
				line = grepFromStdin(args[1], stdin);
			} else {
				line = grepFromStdin(args[0], stdin);
			}
			try {
				stdout.write(line.getBytes());
				stdout.write(System.lineSeparator().getBytes());
			} catch (IOException e) {
			}
		}

		else if (numberOfTxtFiles == 1) {
			line = grepFromOneFile(argsString);
			try {
				stdout.write(line.getBytes());
				stdout.write(System.lineSeparator().getBytes());
			} catch (IOException e) {
			}
		}

		else if (numberOfTxtFiles > 1) {
			line = grepFromMultipleFiles(argsString);
			try {
				stdout.write(line.getBytes());
				stdout.write(System.lineSeparator().getBytes());
			} catch (IOException e) {
			}
		}
	}

	public boolean checkValidFile(File file) throws GrepException {

		Path filePath = file.toPath();

		if (!Files.exists(filePath)) {
			throw new GrepException("File does not exist.");
		} else if (!Files.isReadable(filePath)) {
			throw new GrepException("Unable to read file.");
		} else if (Files.isDirectory(filePath)) {
			throw new GrepException("This is a directory");
		} else {
			return true;
		}
	}

	public boolean isFileValid(File file) {

		Path filePath = file.toPath();
		boolean isValid = true;

		if (!Files.exists(filePath)) {
			isValid = false;
		} else if (!Files.isReadable(filePath)) {
			isValid = false;
		} else if (Files.isDirectory(filePath)) {
			isValid = false;
		}

		return isValid;
	}

	@Override
	public String grepFromStdin(String args, InputStream stdin) {
		String fileContent = "";
		Pattern pattern = Pattern.compile(args);
		String input = readFromInputStream(stdin);
		int lineCount = 0;
		String[] inputArray = input.split("\\r?\\n");

		Matcher matcher;

		for (int i = 0; i < inputArray.length; i++) {
			matcher = pattern.matcher(inputArray[i]);
			if (matcher.find()) {
				if (lineCount > 0) {
					fileContent += System.lineSeparator() + inputArray[i];
				} else {
					fileContent += inputArray[i];
				}
				lineCount++;
			}
		}

		if (lineCount == 0) {
			fileContent = "Pattern Not Found In Stdin!";
		}

		return fileContent;
	}

	@Override
	public String grepFromOneFile(String args) {
		String fileContent = "";
		String[] stringArray = args.split(" ");
		Pattern pattern = Pattern.compile(stringArray[0]);
		BufferedReader reader;
		int lineCount = 0;
		try {
			File file = new File(stringArray[1]);
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			Matcher matcher;
			while (line != null) {
				matcher = pattern.matcher(line);
				if (matcher.find()) {
					if (lineCount > 0) {
						fileContent = fileContent + System.lineSeparator()
								+ line;
					} else {
						fileContent = fileContent + line;
					}
					lineCount++;
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
		}
		if (lineCount == 0) {
			fileContent = "Pattern Not Found In File!";
		}

		return fileContent;
	}

	@Override
	public String grepFromMultipleFiles(String args) {
		ArrayList<File> listOfTextFiles = new ArrayList<File>();
		String fileContent = "";
		String[] stringArray = args.split(" ");
		Pattern pattern = null;
		File file;
		BufferedReader reader;
		int lineCount = 0;

		for (int i = 0; i < stringArray.length; i++) {
			try {
				file = new File(stringArray[i]);
				if (checkValidFile(file) && isFileValid(file)) {
					listOfTextFiles.add(file);
				}
			} catch (GrepException e) {
				if (pattern == null) {
					try {
						pattern = Pattern.compile(stringArray[i]);
					} catch (PatternSyntaxException e1) {

					}
				}
			}
		}

		for (int j = 0; j < listOfTextFiles.size(); j++) {
			try {
				reader = new BufferedReader(
						new FileReader(listOfTextFiles.get(j)));
				String line = reader.readLine();
				Matcher matcher;
				while (line != null) {
					matcher = pattern.matcher(line);
					if (matcher.find()) {
						if (lineCount > 0) {
							fileContent = fileContent + System.lineSeparator()
									+ line;
						} else {
							fileContent = fileContent + line;
						}
						lineCount++;
					}
					line = reader.readLine();
				}
				reader.close();
			} catch (IOException e) {
			}
		}
		if (lineCount == 0) {
			fileContent = "Pattern Not Found In File!";
		}

		return fileContent;
	}

	@Override
	public String grepInvalidPatternInStdin(String args, InputStream stdin) {
		String[] splitArgs = args.split("\\s+");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		InputStream inputStream = null;

		if (stdin != null) {
			inputStream = stdin;
		} else {
			inputStream = new ByteArrayInputStream(new byte[1]);
		}

		try {
			run(splitArgs, inputStream, outputStream);
		} catch (GrepException e) {
			e.printStackTrace();
		}
		return new String(outputStream.toByteArray());
	}

	@Override
	public String grepInvalidPatternInFile(String args) {
		String[] splitArgs = args.split("\\s+");
		OutputStream outputStream = new ByteArrayOutputStream();
		InputStream inputStream = new ByteArrayInputStream(new byte[1]);
		String result = "";

		try {
			run(splitArgs, inputStream, outputStream);
			ByteArrayOutputStream outByte = (ByteArrayOutputStream) outputStream;
			byte[] byteArray = outByte.toByteArray();
			result = new String(byteArray);
		} catch (AbstractApplicationException e) {
			return e.getMessage();
		}

		return result;
	}

	/**
	 * Read from input stream
	 *
	 * @param stdin
	 *            Input Stream. Read the input stream
	 *            
	 */
	public String readFromInputStream(InputStream stdin) {
		StringBuffer text = new StringBuffer();
		String str = "";

		try {
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(stdin, "UTF-8"));

			while ((str = bufferedReader.readLine()) != null) {
				text.append(str);
				text.append(System.getProperty("line.separator"));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return text.toString().trim();
	}

}
