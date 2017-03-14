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
import java.util.ArrayList;
import java.util.stream.Collectors;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.app.Wc;
import sg.edu.nus.comp.cs4218.exception.WcException;
import sg.edu.nus.comp.cs4218.impl.cmd.PipeCommand;

/**
 * The wc command prints the number of bytes, words, and lines in given files
 * (followed by a newline).
 * 
 * <p>
 * <b>Command format:</b> <code>wc [OPTIONS] [FILE]</code>
 * <dl>
 * <dt>OPTIONS</dt>
 * <dd>“-m” Print only the character counts</dd>
 * <dd>“-w” Print only the word counts</dd>
 * <dd>“-l” Print only the newline counts</dd>
 * <dt>FILES</dt>
 * <dd>the file(s), when no file is present, use stdin.</dd>
 * </dl>
 * </p>
 */
public class WcApplication implements Application, Wc {

	private int lineCount = 0;
	private int wordCount = 0;
	private int charCount = 0;
	private int totalLineCount = 0;
	private int totalWordCount = 0;
	private int totalCharCount = 0;
	private boolean lineFlag = false;
	private boolean wordFlag = false;
	private boolean charFlag = false;
	private boolean totalLineFlag = false;
	private boolean totalWordFlag = false;
	private boolean totalCharFlag = false;
	private boolean fileFlag = false;
	private boolean fileErrorFlag = false;
	private ArrayList<String> fileNameList = new ArrayList<String>();

	/**
	 * Runs the wc application with the specified arguments.
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
			throws WcException {

		if (args == null || args.length == 0) {
			throw new WcException("Null Pointer Exception");
		}

		if (stdin == null || stdout == null) {
			throw new WcException("Null Pointer Exception");
		}

		if (args.length == 1) {
			throw new WcException("Input stream cannot be null");
		}

		for (int i = 1; i < args.length; i++) {
			if (args[i].charAt(0) == '-') {

				String[] splitOptions = args[i].trim().split("");
				for (int j = 1; j < splitOptions.length; j++) {
					if (splitOptions[j].equals("m")) { // char counts
						charFlag = true;
						totalCharFlag = true;
					} else if (splitOptions[j].equals("w")) { // word counts
						wordFlag = true;
						totalWordFlag = true;
					} else if (splitOptions[j].equals("l")) { // newline counts
						lineFlag = true;
						totalLineFlag = true;
					} else {
						throw new WcException(args[j] + " is a illegal option");
					}
				}
			} else {
				String fullFileName = "";
				File file = null;
				for (int z = i; z < args.length; z++) {
					if (args[z].contains(".")) {
						fullFileName += args[z];
						file = new File(fullFileName);
						if (checkFileExist(file)) {
							i = z;
							break;
						}
					} else {
						fullFileName += args[z] + " ";
					}
				}

				if (file == null) {
					file = new File(fullFileName);
				}

				if (checkFileExist(file)) {
					fileNameList.add(getAbsolutePath(fullFileName));
					fileFlag = true;
					processCountFromFile(fullFileName, stdout);
				} else {
					try {
						fileErrorFlag = true;
						stdout.write(
								(fullFileName + " is not a file").getBytes());
						stdout.write(System.lineSeparator().getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		if (!fileFlag && !fileErrorFlag) {
			processCountFromInputStream(stdin, stdout);
		}

		if (fileNameList.size() > 1) {
			printTotalResult(stdout);
		}

		resetAllVariables();
	}

	/**
	 * Processes and print counts of the array list of filename
	 *
	 * @param fileNameList
	 *            Array of arguments for the application. Each array element is
	 *            the filename.
	 * @param stdout
	 *            An OutputStream. The output of the command is written to this
	 *            OutputStream.
	 * 
	 */
	private void processCountFromFile(String fileName, OutputStream stdout)
			throws WcException {

		if (fileName == "") {
			throw new WcException("Null Pointer Exception");
		}

		if (stdout == null) {
			throw new WcException("Null Pointer Exception");
		}

		File file = new File(fileName);
		String line = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			StringBuilder stringBuilder = new StringBuilder();
			int currentChar = br.read();
			while (currentChar != -1) {
				stringBuilder.append((char) currentChar);
				currentChar = br.read();
			}
			lineCount = processNewLineCount(stringBuilder.toString());
			
			br = new BufferedReader(new FileReader(file));
			if (file.length() == 0) {
				lineCount = 0;
				charCount = 0;
				wordCount = 0;
			} else {
				while ((line = br.readLine()) != null) {
					charCount += line.length();
					wordCount += line.trim().split("\\s+").length;
				}
				br.close();
			}

			charCount += lineCount;
			printResult(fileName, stdout);
			totalLineCount += lineCount;
			totalWordCount += wordCount;
			totalCharCount += charCount;
			lineCount = 0;
			wordCount = 0;
			charCount = 0;
		} catch (Exception exIO) {
			throw new WcException("Null Pointer Exception");
		}
	}

	/**
	 * Count the number of new line
	 *
	 * @param fileContent
	 *            A String. Content of file.
	 * 
	 */
	private int processNewLineCount(String fileContent) throws WcException {
		int totalLineCount = 0;
		if (fileContent == null) {
			throw new WcException("File content cannot be null");
		}

		for (int i = 0; i < fileContent.length(); i++) {
			if (fileContent.charAt(i) == '\n') {
				totalLineCount++;
			}
		}
		return totalLineCount;
	}

	/**
	 * Processes and print counts when stdin is used
	 *
	 * @param stdin
	 *            An InputStream. The input for the command is read from this
	 *            InputStream if no files are specified.
	 * @param stdout
	 *            An OutputStream. The output of the command is written to this
	 *            OutputStream.
	 * 
	 */
	private void processCountFromInputStream(InputStream stdin,
			OutputStream stdout) throws WcException {

		if (stdin == null || stdout == null) {
			throw new WcException("Null Pointer Exception");
		}

		try {
			String userInput = "";
			if (PipeCommand.isPipe) {
				userInput = new BufferedReader(new InputStreamReader(stdin))
						.lines().parallel().collect(Collectors
								.joining(System.getProperty("line.separator")));
			} else {
				try {
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(stdin));
					userInput = bufferedReader.readLine();
				} catch (IOException e) {
					throw new WcException("Cannot Read From Stdin");
				}
			}

			charCount += userInput.length() + lineCount;
			wordCount += userInput.trim().split("\\s+").length;

			printResult("", stdout);
			lineCount = 0;
			wordCount = 0;
			charCount = 0;
			totalLineCount += lineCount;
			totalWordCount += wordCount;
			totalCharCount += charCount;
		} catch (Exception exIO) {
			throw new WcException("Null Pointer Exception");
		}
	}

	/**
	 * Print the results
	 *
	 * @param fileName
	 *            FileName. The specific file which is used to perform counting.
	 * @param stdout
	 *            An OutputStream. The output of the command is written to this
	 *            OutputStream.
	 * @throws WcException
	 * 
	 */
	private void printResult(String fileName, OutputStream stdout)
			throws WcException {

		if (stdout == null) {
			throw new WcException("Null Pointer Exception");
		}

		try {
			if (charFlag) {
				stdout.write(String.format("%8d", charCount).getBytes());
			}

			if (wordFlag) {
				stdout.write(String.format("%8d", wordCount).getBytes());
			}

			if (lineFlag) {
				stdout.write(String.format("%8d", lineCount).getBytes());
			}

			stdout.write((" " + fileName).getBytes());
			stdout.write(System.lineSeparator().getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print the total results
	 *
	 * @param stdout
	 *            An OutputStream. The output of the command is written to this
	 *            OutputStream.
	 * @throws WcException
	 * 
	 */
	private void printTotalResult(OutputStream stdout) throws WcException {

		if (stdout == null) {
			throw new WcException("Null Pointer Exception");
		}

		try {
			if (totalCharFlag) {
				stdout.write(String.format("%8d", totalCharCount).getBytes());
			}

			if (totalWordFlag) {
				stdout.write(String.format("%8d", totalWordCount).getBytes());
			}

			if (totalLineFlag) {
				stdout.write(String.format("%8d", totalLineCount).getBytes());
			}

			stdout.write((" total" + System.lineSeparator()).getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reset all variables
	 */
	private void resetAllVariables() {
		lineCount = 0;
		wordCount = 0;
		charCount = 0;
		totalLineCount = 0;
		totalWordCount = 0;
		totalCharCount = 0;
		lineFlag = false;
		wordFlag = false;
		charFlag = false;
		totalLineFlag = false;
		totalWordFlag = false;
		totalCharFlag = false;
		fileNameList = new ArrayList<String>();
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

	/**
	 * Gets the absolute path of given filePath
	 *
	 * @param filePath
	 *            A string. Use this to generate absolute path
	 */
	private String getAbsolutePath(String filePath) {
		if (filePath.startsWith(Environment.currentDirectory)) {
			return filePath;
		}
		return Environment.currentDirectory + File.separator + filePath;
	}

	/**
	 * Gets the result from user input
	 *
	 * @param args
	 *            A string. User's command.
	 * @param readConsole
	 *            A checker. To check whether read from stdin or ignore
	 */
	private String printOutputForTest(String args, InputStream stdin) {
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
		} catch (WcException e) {
			e.printStackTrace();
		}
		return new String(outputStream.toByteArray());
	}

	@Override
	public String printCharacterCountInFile(String args) {
		return printOutputForTest(args, null);
	}

	@Override
	public String printWordCountInFile(String args) {
		return printOutputForTest(args, null);
	}

	@Override
	public String printNewlineCountInFile(String args) {
		return printOutputForTest(args, null);
	}

	@Override
	public String printAllCountsInFile(String args) {
		return printOutputForTest(args, null);
	}

	@Override
	public String printCharacterCountInStdin(String args, InputStream stdin) {
		return printOutputForTest(args, stdin);
	}

	@Override
	public String printWordCountInStdin(String args, InputStream stdin) {
		return printOutputForTest(args, stdin);
	}

	@Override
	public String printNewlineCountInStdin(String args, InputStream stdin) {
		return printOutputForTest(args, stdin);
	}

	@Override
	public String printAllCountsInStdin(String args, InputStream stdin) {
		return printOutputForTest(args, stdin);
	}

}
