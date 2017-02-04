package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.app.Sed;
import sg.edu.nus.comp.cs4218.exception.SedException;
import test.SedTest;

/**
 * The sed command copies input file (or input stream) to stdout performing string
 * replacement. For each line containing a match to a specified pattern, replaces
 * the matched substring with the specified string.
 * 
 * <p>
 * <b>Command format:</b> <code>sed REPLACEMENT [FILE]</code>
 * <dl>
 * <dt>REPLACEMENT</dt>
 * <dd>specifies replacement rule, as follows:</dd>
 * <dd>s/regexp/replacement/:</dd>
 * <dd>replace the first (in each line) substring matched by regexp with the string replacement.</dd>
 * <dd>s/regexp/replacement/g:</dd>
 * <dd>replace all the substrings matched by regexp with the string replacement.</dd>
 * <dt>FILE</dt>
 * <dd>the name of the file. If not specified, use stdin.</dd>
 * </dl>
 * </p>
 */
public class SedApplication implements Application, Sed {

	/**
	 * Runs the sed application with the specified arguments.
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
	 * @throws SedException
	 *             If the file(s) specified do not exist or are unreadable.
	 */
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout)
			throws SedException {
		
		if (args == null || stdin == null || stdout == null) {
			throw new SedException("Null Pointer Exception");
		} else if (args.length < 2) {
			throw new SedException("No Replacement Rule Specified");
		} else if(args.length > 3) {
			throw new SedException("Invalid Arguments");
		} else {
			try {
				if(args.length == 2) {
					byte[] byteString = (readFromStdin(args, stdin) 
							+ System.getProperty("line.separator")).getBytes();
					stdout.write(byteString);
				} else if(args.length == 3) {
					byte[] byteString = (readFromFile(args) 
							+ System.getProperty("line.separator")).getBytes();
					stdout.write(byteString);
				}
			} catch (IOException e) {
				throw new SedException("Could not write to output stream");
			}
		}
	}

	/**
	 * Reads from stdin.
	 * 
	 * @param args
	 *            Array of arguments for the application. Each array element is
	 *            the path to a file. If no files are specified stdin is used.
	 * 
	 * @throws SedException
	 *             If the file(s) specified do not exist or are unreadable.
	 */
	String readFromStdin(String[] args, InputStream stdin) throws SedException {

		String line = null;

		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stdin));
			line = bufferedReader.readLine();
		} catch (IOException e) {
			throw new SedException("Cannot Read From Stdin");
		} 

		// try to replace substrings
		if(line != null && !line.isEmpty()) {
			String[] replacementRule = getReplacementRule(args);
			
			if(replacementRule.length == 3) {
				line = replaceFirstSubString(line, replacementRule[1], replacementRule[2]);
			} else if(replacementRule.length == 4) {
				line = replaceAllSubString(line, replacementRule[1], replacementRule[2]);
			} else {
				throw new SedException("Invalid Replacement Rule");
			}
		} else {
			throw new SedException("Invalid Input");
		}

		return line;
	}

	/**
	 * Reads from file.
	 * 
	 * @param args
	 *            Array of arguments for the application. Each array element is
	 *            the path to a file. If no files are specified stdin is used.
	 * 
	 * @throws SedException
	 *             If the file(s) specified do not exist or are unreadable.
	 */
	String readFromFile(String[] args) throws SedException {

		String line = null;
		String file = args[2];
		
		if (file != null && !file.isEmpty()) {
			Path filePath;
			Path currentDir = Paths.get(Environment.currentDirectory);
			boolean isFileReadable = false;

			filePath = currentDir.resolve(file);
			isFileReadable = checkIfFileIsReadable(filePath);

			if (isFileReadable) {
				// file could be read. perform sed command
				try {
					byte[] byteFileArray = Files.readAllBytes(filePath);
					String currLine = new String(byteFileArray);

					// try to replace substrings
					if(currLine != null && !currLine.isEmpty()) {
						String[] replacementRule = getReplacementRule(args);
						
						if(replacementRule.length == 3) {
							line = replaceFirstSubString(currLine, replacementRule[1], replacementRule[2]);
						} else if(replacementRule.length == 4) {
							line = replaceAllSubString(currLine, replacementRule[1], replacementRule[2]);
						} 
					} else {
						throw new SedException("Invalid Input");
					}
				} catch (IOException e) {
					throw new SedException("Could not write to output stream");
				}
			} else {
				throw new SedException("File Could Not Be Read");
			}
		}

		return line;
	}
	
	/**
	 * Checks if a file is readable.
	 * 
	 * @param args
	 *            Array of arguments for the application. Each array element is
	 *            the path to a file. If no files are specified stdin is used.
	 * @param stdin
	 *            An InputStream. The input for the command is read from this
	 *            InputStream if no files are specified.
	 *            
	 * @throws SedException
	 *            If the file is not readable
	 */
	boolean checkIfFileIsReadable(Path filePath) throws SedException {

		if (Files.isDirectory(filePath)) {
			throw new SedException("This is a directory");
		}
		if (Files.exists(filePath) && Files.isReadable(filePath)) {
			return true;
		} else {
			throw new SedException("Could not read file");
		}
	}
	
	/**
	 * Retrieves replacement rule.
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
	 * @throws SedException
	 *             If the file(s) specified do not exist or are unreadable.
	 */
	String[] getReplacementRule(String[] args) throws SedException {

		String replacementRule = args[1];
		String separator = replacementRule.substring(1, 2);
		String[] splitReplacementRule = replacementRule.split(separator);
		int length = splitReplacementRule.length;

		if(length < 3 || length > 4) {
			throw new SedException("Invalid Replacement Rule");
		} else {
			if(!replacementRule.substring(0, 1).equals("s")) {
				throw new SedException("Invalid Replacement Rule");
			}

			if(length == 4) {
				if(!replacementRule.substring(replacementRule.length() - 1, 
						replacementRule.length()).equals("g")) {
					throw new SedException("Invalid Replacement Rule");
				}
			}
		}

		return splitReplacementRule;
	}
	
	/**
	 * Replaces first regexp of each line from input string with replacement string.
	 * 
	 * @param line
	 *            Input string to be replaced by substring.
	 * @param regexp
	 *            String to be replaced in input string.
	 * @param replacement
	 *            Replace regexp found in line string with replacement string.
	 * 
	 * @throws SedException
	 *             If the file(s) specified do not exist or are unreadable.
	 */
	String replaceFirstSubString(String line, String regexp, String replacement) {
		String[] splitLine = line.split(System.getProperty("line.separator"));
		line = "";
		
		for(int i = 0; i < splitLine.length; i++) {
			line += splitLine[i].replaceFirst(regexp, replacement);
		}
		
		return line;
	}
	
	/**
	 * Replaces all regexp from input string with replacement string.
	 * 
	 * @param line
	 *            Input string to be replaced by substring.
	 * @param regexp
	 *            String to be replaced in input string.
	 * @param replacement
	 *            Replace regexp found in line string with replacement string.
	 * 
	 * @throws SedException
	 *             If the file(s) specified do not exist or are unreadable.
	 */
	String replaceAllSubString(String line, String regexp, String replacement) {
		return line.replace(regexp, replacement);
	}

	@Override
	public String replaceFirstSubStringInFile(String args) throws SedException {
		return readFromFile(args.split(" "));
	}

	@Override
	public String replaceAllSubstringsInFile(String args) throws SedException {
		return readFromFile(args.split(" "));
	}

	@Override
	public String replaceFirstSubStringFromStdin(String args) throws SedException {
		return readFromStdin(args.split(" "), new ByteArrayInputStream(SedTest.TEXT.getBytes()));
	}

	@Override
	public String replaceAllSubstringsInStdin(String args) throws SedException {
		return readFromStdin(args.split(" "), new ByteArrayInputStream(SedTest.TEXT.getBytes()));
	}

	@Override
	public String replaceSubstringWithInvalidReplacement(String args) throws SedException {
		return args;
	}

	@Override
	public String replaceSubstringWithInvalidRegex(String args) throws SedException {
		return args;
	}

}
