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
import sg.edu.nus.comp.cs4218.impl.cmd.PipeCommand;

/**
 * The cat command concatenates the content of given files and prints on the
 * standard output.
 * 
 * <p>
 * <b>Command format:</b> <code>head [OPTIONS] [FILE]</code>
 * <dl>
 * <dt>OPTIONS</dt>
 * <dd>“-n 15” means printing 15 lines. Print first 10 lines if not
 * specified.</dd>
 * <dt>FILES</dt>
 * <dd>the name of the file. If not specified, use stdin.</dd>
 * </dl>
 * </p>
 */
public class HeadApplication implements Application {

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout)
			throws HeadException {

		File file;

		if (args.length == 0) {
			throw new HeadException("Insufficient arguments.");
		} else {
			String[] afterParseHead = parseHead(args);

			try {
				if (afterParseHead.length == 1
						&& afterParseHead[0].toLowerCase().equals("head")) {
					String input = readFromStdin(afterParseHead, stdin);
					stdout.write(input.getBytes());
					stdout.write(System.lineSeparator().getBytes());
				} else if (afterParseHead.length == 2) {
					file = new File(afterParseHead[1]);
					if (isFileValid(file)) {
						printLine(10, file, stdin, stdout);
					} else {
						String[] input = readFromStdin(afterParseHead, stdin)
								.split("\n");
						int numOfLines = (Integer
								.parseInt(afterParseHead[1]) < input.length)
										? Integer.parseInt(afterParseHead[1])
										: input.length;

						for (int i = 0; i < numOfLines; i++) {
							stdout.write(input[i].getBytes());
							stdout.write(System.lineSeparator().getBytes());
						}
					}
				} else if (afterParseHead.length == 3) {
					file = new File(afterParseHead[2]);
					if (checkValidFile(file)) {
						printLine(Integer.parseInt(afterParseHead[1]), file,
								stdin, stdout);
					}
				}
			} catch (IOException e) {
				throw new HeadException("Cannot Read From Stdin");
			} catch (NumberFormatException e) {
				throw new HeadException("Invalid arguments");
			}
		}
	}

	private String[] parseHead(String[] args) throws HeadException {

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
		} else {
			throw new HeadException("Invalid arguments");
		}

		if (isLastArgumentFile(args)) {
			return new String[] { args[0], options, args[args.length - 1] };
		}

		return new String[] { args[0], options };
	}

	private String getOptionArguments(String[] args) {

		String cmdline = "";

		int length = isLastArgumentFile(args) ? args.length - 2
				: args.length - 1;
		for (int i = 0; i < length; i++) {
			cmdline += args[i + 1] + " ";
		}

		return cmdline;
	}

	private boolean isLastArgumentFile(String[] args) {

		boolean isLastArgFile = false;

		try {
			if (checkValidFile(new File(args[args.length - 1].trim()))) {
				isLastArgFile = true;
			}
		} catch (HeadException e) {
			isLastArgFile = false;
		}

		return isLastArgFile;
	}

	private void printLine(int numberOfLines, File file, InputStream stdin,
			OutputStream stdout) throws HeadException {

		if (stdin == null || stdout == null) {
			throw new HeadException("Null Pointer Exception");
		}
		try {
			int linesOfNumber = numberOfLines;
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while (linesOfNumber > 0) {
				String line = reader.readLine();
				if (line != null) {
					stdout.write(line.getBytes());
					stdout.write(System.lineSeparator().getBytes());
				}
				linesOfNumber--;
			}
			reader.close();
		} catch (IOException e) {
			throw new HeadException("IO Exception");
		}
	}

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

	public boolean checkValidFile(File file) throws HeadException {

		Path filePath = file.toPath();

		if (!Files.exists(filePath)) {
			throw new HeadException("File does not exist.");
		} else if (!Files.isReadable(filePath)) {
			throw new HeadException("Unable to read file.");
		} else if (Files.isDirectory(filePath)) {
			throw new HeadException("This is a directory");
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
	private String readFromStdin(String[] args, InputStream stdin) throws HeadException {

		StringBuffer text = new StringBuffer();
		String str = "";

		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stdin, "UTF-8"));
			
			while((str = bufferedReader.readLine()) != null) {
				text.append(str);
				text.append(System.getProperty("line.separator"));
			}
			
		} catch (IOException e) {
			throw new HeadException("Could not read input");
		}
		
		// try to replace substrings
		if(text == null || text.toString().isEmpty()) {
			throw new HeadException("Invalid Input");
		} 

		return text.toString().trim();
	}
}
