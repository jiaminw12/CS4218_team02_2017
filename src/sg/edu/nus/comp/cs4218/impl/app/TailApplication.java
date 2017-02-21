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
 */
public class TailApplication implements Application {

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout)
			throws TailException {

		processArguments(args, stdin, stdout);
	}

	private void processArguments(String[] args, InputStream stdin,
			OutputStream stdout) throws TailException {

		File file;

		if (args.length == 0) {
			throw new TailException("Insufficient arguments.");
		} else {
			args = parseTail(args);

			try {
				if(args.length == 1 && args[0].toLowerCase().equals("Tail")) {
					String input = readFromStdin(args, stdin);
					stdout.write(input.getBytes());
					stdout.write(System.lineSeparator().getBytes());
				} else if(args.length == 2) {
					file = new File(args[1]);
					if(isFileValid(file)) {
						printLine(10, file, stdin, stdout);
					} else {
						String[] input = readFromStdin(args, stdin).split("\n");
						int numOfLines = (Integer.parseInt(args[1]) < input.length) ? Integer.parseInt(args[1]) : input.length;

						for(int i = input.length - numOfLines; i < input.length; i++) {
							stdout.write(input[i].getBytes());
							stdout.write(System.lineSeparator().getBytes());
						}
					}
				} else if(args.length == 3) {
					file = new File(args[2]);
					if (checkValidFile(file)) {
						printLine(Integer.parseInt(args[1]), file, stdin, stdout);
					}
				}
			} catch (IOException e) {
				throw new TailException("Cannot Read From Stdin");
			} catch (NumberFormatException e) {
				throw new TailException("Invalid arguments");
			}
		}
	}

	private String[] parseTail(String[] args) throws TailException {

		if(args.length == 1 || (args.length == 2 && isFileValid(new File(args[1])))) {
			return args;
		}

		String cmdline = getOptionArguments(args);
		String options = new String();

		Matcher matcher = Pattern.compile("(\\s*-n\\s*[0-9]+\\s*)+").matcher(cmdline);
		if(matcher.matches()) {
			matcher = Pattern.compile("-n\\s*(?<number>[0-9]+)").matcher(cmdline);
			while(matcher.find()){
				options = matcher.group("number");
			}
		} else {
			throw new TailException("Invalid arguments");
		}

		if(isLastArgumentFile(args)) {
			return new String[] { args[0], options, args[args.length - 1] };
		}

		return new String[] { args[0], options };
	}

	private String getOptionArguments(String[] args) {

		String cmdline = new String();

		int length = isLastArgumentFile(args) ? args.length - 2 : args.length - 1;
		for(int i = 0; i < length; i++) {
			cmdline += args[i + 1] + " ";
		}

		return cmdline;
	}

	private boolean isLastArgumentFile(String[] args) {

		boolean isLastArgFile = false;

		try {
			if(checkValidFile(new File(args[args.length - 1].trim()))) {
				isLastArgFile = true;
			}
		} catch(TailException e) {
			isLastArgFile = false;
		}

		return isLastArgFile;
	}

	private void printLine(int numberOfLines, File file, InputStream stdin, OutputStream stdout) 
			throws TailException {

		if (stdin == null || stdout == null) {
			throw new TailException("Null Pointer Exception");
		}

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			int tempNumberOfLines = numberOfLines;
			String[] linesArray = new String[numberOfLines];
			int count = 0;

			while (tempNumberOfLines > 0) {
				String line = reader.readLine();
				if (line != null) {
					linesArray[count%numberOfLines] = line;
					count++;
				} else if (line==null) {
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
					stdout.write(linesArray[i%numberOfLines].getBytes());
					stdout.write(System.lineSeparator().getBytes());
				}
			}

			reader.close();
		} catch (IOException e) {
			throw new TailException("IO Exception");
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

		String input = new String();

		if(PipeCommand.isPipe) {
			input = new BufferedReader(new InputStreamReader(stdin)).lines()
					.parallel().collect(Collectors.joining(System.getProperty("line.separator")));
		} else {
			try {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stdin));
				input = bufferedReader.readLine();
			} catch (IOException e) {
				throw new TailException("Cannot Read From Stdin");
			} 
		}

		// try to replace substrings
		if(input == null || input.isEmpty()) {
			throw new TailException("Invalid Input");
		} 

		return input;
	}
}
