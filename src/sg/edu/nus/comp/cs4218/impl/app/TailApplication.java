package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.HeadException;
import sg.edu.nus.comp.cs4218.exception.TailException;
import sg.edu.nus.comp.cs4218.impl.cmd.PipeCommand;

/*
 * Assumptions:
 * 1) run function will call the correct functions with the correct inputs in the 
 * correct order separated by a space
 * 2) Run function will take inputs directly from shell ordered
 * 3) Files only contain ASCII characters and are not folder or directory
 * 4) Path of files and the file name must not contain any spaces
 * 5) -n 2 is equivalent to -n2
 * 6) When -n 0, print nothing
 * 7) When the option is more than the total number of lines of file, 
 * will print all the lines of the file
 * 8) Allow multiple options, but overwrites when there is a new option
 * 			For example, tail -n4 -n 3 -n2 prints first two lines
 */

/**
 * The cat command concatenates the content of given files and prints on the
 * standard output.
 * 
 * <p>
 * <b>Command format:</b> <code>Tail [OPTIONS] [FILE]</code>
 * <dl>
 * <dt>OPTIONS</dt>
 * <dd>“-n 15” means printing 15 lines. Print first 10 lines if not
 * specified.</dd>
 * <dt>FILES</dt>
 * <dd>the name of the file. If not specified, use stdin.</dd>
 * </dl>
 * </p>
 *  
 */
public class TailApplication implements Application {

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout)
			throws TailException {
		
		if (stdin == null || stdout == null) {
			throw new TailException("Null Pointer Exception");
		}
		
		File file;

		if (args.length == 0) {
			throw new TailException("Insufficient arguments.");
		} else {
			String[] afterParseTail = parseTail(args);

			try {
				if (afterParseTail.length == 1
						&& afterParseTail[0].toLowerCase().equals("tail")) {
					String[] input = readFromStdin(afterParseTail, stdin)
							.split("\n");
					if (input.length < 10) {
						for (int i = 0; i < input.length; i++) {
							stdout.write(input[i].getBytes());
							stdout.write(System.lineSeparator().getBytes());
						}
					} else {
						for (int i = input.length-10; i < input.length; i++) {
							stdout.write(input[i].getBytes());
							stdout.write(System.lineSeparator().getBytes());
						}
					}

					
				} else if (afterParseTail.length == 2) {
					file = new File(afterParseTail[1]);
					if (isFileValid(file)) {
						printLine(10, file, stdin, stdout);
					} else {
						String[] input = readFromStdin(afterParseTail, stdin)
								.split("\n");
						int numOfLines = (Integer
								.parseInt(afterParseTail[1]) < input.length)
										? Integer.parseInt(afterParseTail[1])
										: input.length;

						for (int i = input.length
								- numOfLines; i < input.length; i++) {
							stdout.write(input[i].getBytes());
							stdout.write(System.lineSeparator().getBytes());
						}
					}
				} else if (afterParseTail.length == 3) {
					file = new File(afterParseTail[2]);
					if (checkValidFile(file)) {
						printLine(Integer.parseInt(afterParseTail[1]), file,
								stdin, stdout);
					}
				}
			} catch (IOException e) {
				throw new TailException("Cannot Read From Stdin");
			} catch (NumberFormatException e) {
				throw new TailException("Invalid arguments");
			}
		}
	}

	/**
	 * Parse args.
	 *
	 * @param args
	 *            Array of arguments for the application. Each array element is
	 *            the path to a file. If no files are specified stdin is used.
	 *            
	 */
	private String[] parseTail(String[] args) throws TailException {

		if (args.length == 1
				|| (args.length == 2 && isFileValid(new File(args[1])))) {
			return args;
		}

		String cmdline = getOptionArguments(args);
		String options = "";

		Matcher matcher = Pattern.compile("(\\s*-n\\s*[0-9]+\\s*)+")
				.matcher(cmdline);
		if (matcher.matches()) {
			matcher = Pattern.compile("-n\\s*(?<number>[0-9]+)")
					.matcher(cmdline);
			while (matcher.find()) {
				options = matcher.group("number");
			}
		}  else if (isLastArgumentFile(args) == false) {
			throw new TailException("File does not found");
		} else {
			throw new TailException("Invalid arguments");
		}

		if (isLastArgumentFile(args)) {
			return new String[] { args[0], options, args[args.length - 1] };
		}

		return new String[] { args[0], options };
	}

	/**
	 * Get Option of Arguments
	 *
	 * @param args
	 *            Array of arguments for the application. Each array element is
	 *            the path to a file. If no files are specified stdin is used.
	 *            
	 */
	private String getOptionArguments(String[] args) {

		String cmdline = "";

		int length = isLastArgumentFile(args) ? args.length - 2
				: args.length - 1;
		for (int i = 0; i < length; i++) {
			cmdline += args[i + 1] + " ";
		}

		return cmdline;
	}

	/**
	 * Check whether last argument is file
	 *
	 * @param args
	 *            Array of arguments for the application. Each array element is
	 *            the path to a file. If no files are specified stdin is used.
	 *            
	 */
	private boolean isLastArgumentFile(String[] args) {

		boolean isLastArgFile = false;

		try {
			if (checkValidFile(new File(args[args.length - 1].trim()))) {
				isLastArgFile = true;
			}
		} catch (TailException e) {
			isLastArgFile = false;
		}

		return isLastArgFile;
	}

	/**
	 * Print the line
	 *
	 * @param numberOfLines
	 *            Integer. The number of lines
	 * @param file
	 * 			  File.
	 * @param stdin
	 * 			  An InputStream. The input for the command is read from this
	 *            InputStream if no files are specified.           
	 * @param stdout
	 * 			  An OutputStream. Elements of args will be output to stdout,
	 *            separated by a space character.           
	 *            
	 */
	private void printLine(int numberOfLines, File file, InputStream stdin,
			OutputStream stdout) throws TailException {

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			int tempNumberOfLines = numberOfLines;
			String[] linesArray = new String[numberOfLines];
			int count = 0;

			while (tempNumberOfLines > 0) {
				String line = reader.readLine();
				if (line != null) {
					linesArray[count % numberOfLines] = line;
					count++;
				} else if (line == null) {
					tempNumberOfLines = 0;
				}
			}

			if (numberOfLines >= count) {
				for (int i = 0; i < count; i++) {
					stdout.write(linesArray[i].getBytes());
					stdout.write(System.lineSeparator().getBytes());
				}
			} else if (numberOfLines < count) {
				int start = count - numberOfLines;
				for (int i = start; i < count; i++) {
					stdout.write(linesArray[i % numberOfLines].getBytes());
					stdout.write(System.lineSeparator().getBytes());
				}
			}

			reader.close();
		} catch (IOException e) {
			throw new TailException("IO Exception");
		}

	}

	/**
	 * Check validity the file
	 *
	 * @param file
	 * 			  File.           
	 *            
	 */
	private boolean isFileValid(File file) {

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

	/**
	 * Check validity the file by throwing exception
	 *
	 * @param file
	 * 			  File.           
	 *            
	 */
	public boolean checkValidFile(File file) throws TailException {

		Path filePath = file.toPath();

		if (!Files.exists(filePath)) {
			throw new TailException("File does not exist.");
		} else if (!Files.isReadable(filePath)) {
			throw new TailException("Unable to read file.");
		} else if (Files.isDirectory(filePath)) {
			throw new TailException("This is a directory");
		} else {
			return true;
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
	private String readFromStdin(String[] args, InputStream stdin) throws TailException {

		StringBuffer text = new StringBuffer();
		String str = "";

		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stdin, "UTF-8"));
			
			while((str = bufferedReader.readLine()) != null) {
				text.append(str);
				text.append(System.getProperty("line.separator"));
			}
			
		} catch (IOException e) {
			throw new TailException("Could not read input");
		}
		
		// try to replace substrings
		if(text == null || text.toString().isEmpty()) {
			throw new TailException("Invalid Input");
		} 

		return text.toString().trim();
	}
}
