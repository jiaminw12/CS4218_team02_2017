package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.app.Wc;
import sg.edu.nus.comp.cs4218.exception.WcException;

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
	private boolean totalLineCountFlag = false;
	private boolean totalWordCountFlag = false;
	private boolean totalCharCountFlag = false;
	private boolean fileFlag = false;
	private ArrayList<String> fileNameList = new ArrayList<String>();
	private String nextLineString = "\n";
	private String tabString = "\t";

	String tempFileOutput = "temp-file-output";
	String tmpExt = ".txt";

	File tempInput = null;
	File tempOutput = null;
	BufferedWriter writer = null;
	OutputStream fileOutStream = null;

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
			if (stdin == null || stdout == null) {
				throw new WcException("Null Pointer Exception");
			}
		}

		for (int i = 1; i < args.length; i++) {
			if (args[i].equals("-lmw")) { // character counts
				charFlag = true;
				wordFlag = true;
				lineFlag = true;
				
				totalCharCountFlag = true;
				totalWordCountFlag = true;
				totalLineCountFlag = true;
			} else if (args[i].equals("-m")) { // char counts
				charFlag = true;
				totalCharCountFlag = true;
			} else if (args[i].equals("-w")) { // word counts
				wordFlag = true;
				totalWordCountFlag = true;
			} else if (args[i].equals("-l")) { // newline counts
				lineFlag = true;
				totalLineCountFlag = true;
			} else {
				if (args[i].charAt(0) == '-') {
					throw new WcException(args[i] + " is a illegal option");
				}

				// contain file extension
				File file = new File(args[i]);
				if (checkFileIsDirectory(file)) {
					throw new WcException(args[i] + " is not a directory");
				} else if (checkFileExist(file)) {
					fileNameList.add(getAbsolutePath(args[i]));
					fileFlag = true;
				} else {
					throw new WcException(
							args[i] + " is not a directory or a file");
				}
			}
		}

		try {
			tempOutput = File.createTempFile(tempFileOutput, tmpExt);
			fileOutStream = new FileOutputStream(tempOutput);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (fileFlag) {
			processCountFromFile(fileNameList, stdout);
		} else {
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
	private void processCountFromFile(ArrayList<String> fileNameList,
			OutputStream stdout) throws WcException {

		if (fileNameList.isEmpty()) {
			throw new WcException("fileNameList is empty");
		}

		if (stdout == null) {
			throw new WcException("Null Pointer Exception");
		}

		for (int i = 0; i < fileNameList.size(); i++) {
			File file = new File(fileNameList.get(i));
			String line = null;
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				while ((line = br.readLine()) != null) {
					lineCount++;
					charCount += line.length();
					wordCount += line.trim().split("\\s+").length;
				}
				br.close();

				printResult(fileNameList.get(i), stdout);
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
			BufferedReader br = new BufferedReader(
					new InputStreamReader(stdin));

			String line;
			while ((line = br.readLine()) != null && line.length() != 0) {
				lineCount++;
				charCount += line.length();
				wordCount += line.trim().split("\\s+").length;
			}

			printResult("", stdout);
			lineCount = 0;
			wordCount = 0;
			charCount = 0;
			totalLineCount += lineCount;
			totalWordCount += wordCount;
			totalCharCount += charCount;
		} catch (Exception exIO) {
			exIO.printStackTrace();
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

		writer = new BufferedWriter(new OutputStreamWriter(fileOutStream));

		if (!(lineFlag || charFlag || wordFlag)) {
			lineFlag = true;
			charFlag = true;
			wordFlag = true;
		}

		try {
			if (charFlag) {
				stdout.write(String.valueOf(charCount).getBytes());
				stdout.write(tabString.getBytes());

				writer.write(String.valueOf(charCount) + tabString);
			}

			if (wordFlag) {
				stdout.write(String.valueOf(wordCount).getBytes());
				stdout.write(tabString.getBytes());

				writer.write(String.valueOf(wordCount) + tabString);
			}

			if (lineFlag) {
				stdout.write(String.valueOf(lineCount).getBytes());
				stdout.write(tabString.getBytes());

				writer.write(String.valueOf(lineCount) + tabString);
			}

			//stdout.write(fileName.getBytes());
			stdout.write(String.format("%n").getBytes());

			writer.flush();
			fileOutStream.flush();

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

		writer = new BufferedWriter(new OutputStreamWriter(fileOutStream));

		if (!(totalLineCountFlag || totalCharCountFlag || totalWordCountFlag)) {
			totalLineCountFlag = true;
			totalCharCountFlag = true;
			totalWordCountFlag = true;
		}

		try {
			if (totalCharCountFlag) {
				stdout.write(String.valueOf(totalCharCount).getBytes());
				stdout.write(tabString.getBytes());

				writer.write(String.valueOf(totalCharCount) + tabString);
			}

			if (totalWordCountFlag) {
				stdout.write(String.valueOf(totalWordCount).getBytes());
				stdout.write(tabString.getBytes());

				writer.write(String.valueOf(totalWordCount) + tabString);
			}

			if (totalLineCountFlag) {
				stdout.write(String.valueOf(totalLineCount).getBytes());
				stdout.write(tabString.getBytes());

				writer.write(String.valueOf(totalLineCount) + tabString);
			}

			// stdout.write("total".getBytes());
			stdout.write(String.format("%n").getBytes());

			writer.flush();
			fileOutStream.flush();

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
		totalLineCountFlag = false;
		totalWordCountFlag = false;
		totalCharCountFlag = false;
		fileNameList = new ArrayList<String>();
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
	 * Read file
	 *
	 * @param fileOutput
	 *            A string. Use this to read file
	 */
	private String readFromFile(File fileOutput) {
		String result = null;
		try {
			BufferedReader bufferedReader = new BufferedReader(
					new FileReader(fileOutput));
			result = bufferedReader.readLine();
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String printCharacterCountInFile(String args) {
		return readFromFile(tempOutput);
	}

	@Override
	public String printWordCountInFile(String args) {
		return readFromFile(tempOutput);
	}

	@Override
	public String printNewlineCountInFile(String args) {
		return readFromFile(tempOutput);
	}

	@Override
	public String printAllCountsInFile(String args) {
		return readFromFile(tempOutput);
	}

	@Override
	public String printCharacterCountInStdin(String args) {
		return readFromFile(tempOutput);
	}

	@Override
	public String printWordCountInStdin(String args) {
		return readFromFile(tempOutput);
	}

	@Override
	public String printNewlineCountInStdin(String args) {
		return readFromFile(tempOutput);
	}

	@Override
	public String printAllCountsInStdin(String args) {
		return readFromFile(tempOutput);
	}

}
