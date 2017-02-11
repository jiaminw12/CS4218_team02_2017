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
import java.util.Arrays;
import java.util.regex.Pattern;

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
	
	private static final int REPLACE_FIRST_SUBSTRING = 3;
	private static final int REPLACE_ALL_SUBSTRING = 4;
	
	private InputStream stdin;
	
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
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws SedException {
		
		if (args == null || stdin == null || stdout == null) {
			throw new SedException("Null Pointer Exception");
		} else if (args.length < 2) {
			throw new SedException("No Replacement Rule Specified");
		} else if(args.length > 3) {
			throw new SedException("Invalid Arguments");
		} else {
			String text = new String();
			for(int i = 0; i < args.length; i++) {
				text += args[i] + " ";
			}
			
			try {
				String[] replacementRule = getReplacementRule(args);
				if(replacementRule != null) {
					int replacementType = replacementRule.length;
					
					if(args.length == 2) {
						// Input type stdin
						this.stdin = stdin;
						
						if(replacementType == REPLACE_FIRST_SUBSTRING) {
							text = replaceFirstSubStringFromStdin(text);
						} else if(replacementType == REPLACE_ALL_SUBSTRING) { 
							text = replaceAllSubstringsInStdin(text);
						} 
						
						byte[] byteString = (text + System.getProperty("line.separator")).getBytes();
						stdout.write(byteString);
					} else if(args.length == 3) {
						// Input type file
						
						if(replacementType == REPLACE_FIRST_SUBSTRING) {
							text = replaceFirstSubStringInFile(text);
						} else if(replacementType == REPLACE_ALL_SUBSTRING) {
							text = replaceAllSubstringsInFile(text);
						}
						
						byte[] byteString = (text + System.getProperty("line.separator")).getBytes();
						stdout.write(byteString);
					}
				} else {
					throw new SedException("Invalid Replacement Rule");
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
	 * @param stdin
	 *            An InputStream. The input for the command is read from this
	 *            InputStream if no files are specified.
	 * 
	 * @throws SedException
	 *             If the file(s) specified do not exist or are unreadable.
	 */
	String readFromStdin(String[] args, InputStream stdin) throws SedException {

		String text = null;
		String input = null;
		
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stdin));
			input = bufferedReader.readLine();
		} catch (IOException e) {
			throw new SedException("Cannot Read From Stdin");
		} 

		// try to replace substrings
		if(input != null && !input.isEmpty()) {
			text = input;
		} else {
			throw new SedException("Invalid Input");
		}

		return text;
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

		String text = null;
		String file = args[2];

		if (file != null && !file.isEmpty()) {
			Path filePath;
			Path currentDir = Paths.get(Environment.currentDirectory);
			boolean isFileReadable = false;

			filePath = currentDir.resolve(file);
			isFileReadable = checkIfFileIsReadable(filePath);

			if(isFileReadable) {
				// file could be read. perform sed command
				try {
					byte[] byteFileArray = Files.readAllBytes(filePath);
					String currText = new String(byteFileArray).replaceAll("\\s{2,}", " ").trim();
					
					if(currText != null && !currText.isEmpty()) {
						text = currText;
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

		return text;
	}
	
	/**
	 * Checks if a file is readable.
	 * 
	 * @param filePath
	 *            File path of the file provided by the user.
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
	 * 
	 * @throws SedException
	 *             If the file(s) specified do not exist or are unreadable.
	 */
	String[] getReplacementRule(String[] args) {
		
		String[] splitReplacementRule = null;
		String replacementRule = args[1];
		String separator = replacementRule.substring(1, 2);
		
		if(replacementRule.contains(separator + separator)) {
			splitReplacementRule = null;
		} else {
			splitReplacementRule = replacementRule.split(separator);
			int length = splitReplacementRule.length;

			if(length < 3 || length > 4) {
				splitReplacementRule = null;
			} else {
				if(!splitReplacementRule[0].equals("s")) {
					splitReplacementRule = null;
				}
				
				if(length == 3) {
					if(!replacementRule.substring(replacementRule.length() - 1, 
							replacementRule.length()).equals(separator)) {
						splitReplacementRule = null;
					}
				}

				if(length == 4) {
					if(!splitReplacementRule[3].equals("g")) {
						splitReplacementRule = null;
					}
				
					if(replacementRule.substring(replacementRule.length() - 1, 
							replacementRule.length()).equals(separator)) {
						splitReplacementRule = null;
					}
				} 
			}
		}
		
		return splitReplacementRule;
	}
	
	/**
	 * Replaces first regexp of each line from input string with replacement string.
	 * 
	 * @param text
	 *            Input string to be replaced by substring.
	 * @param args
	 *            Array of arguments for the application. Each array element is
	 *            the path to a file. If no files are specified stdin is used.
	 * 
	 * @throws SedException
	 *             If the file(s) specified do not exist or are unreadable.
	 */
	String replaceFirstSubString(String text, String[] args) {

		String[] splitLine = text.split(System.getProperty("line.separator"));
		String[] replacementRule = getReplacementRule(args);
		
		if(replacementRule != null) {
			String regexp = replacementRule[1]; 
			String replacement = replacementRule[2];
			text = new String();
			
			for(int i = 0; i < splitLine.length; i++) {
				text += splitLine[i].replaceFirst(Pattern.quote(regexp), replacement);
				if(i < splitLine.length - 1) {
					text += System.getProperty("line.separator");
				}
			}
		}
		
		return text;
	}
	
	/**
	 * Replaces all regexp from input string with replacement string.
	 * 
	 * @param text
	 *            Input string to be replaced by substring.
	 * @param args
	 *            Array of arguments for the application. Each array element is
	 *            the path to a file. If no files are specified stdin is used.
	 *            
	 * @throws SedException
	 *             If the file(s) specified do not exist or are unreadable.
	 */
	String replaceAllSubStrings(String text, String[] args) {
		
		String[] replacementRule = getReplacementRule(args);
		
		if(replacementRule != null) {
			String regexp = replacementRule[1];	
			String replacement = replacementRule[2];
			
			text = text.replaceAll(Pattern.quote(regexp), replacement);
		} 
		
		return text;
	}

	/**
	 * Returns string containing lines with the first matched substring replaced
	 * in file
	 * 
	 * @param args String containing command and arguments
	 */
	@Override
	public String replaceFirstSubStringInFile(String args) {

		String[] splitArgs = args.replaceAll("\\s{2,}", " ").trim().split(" ");
		String text = new String();
		
		try {
			text = readFromFile(splitArgs);
			text = replaceFirstSubString(text, splitArgs);
		} catch(SedException e) {
			e.printStackTrace();
		}
		
		return text;
	}

	/**
	 * Returns string containing lines with all matched substring replaced in
	 * file
	 * 
	 * @param args String containing command and arguments
	 */
	@Override
	public String replaceAllSubstringsInFile(String args) {
		
		String[] splitArgs = args.replaceAll("\\s{2,}", " ").trim().split(" ");
		String text = new String();
		
		try {
			text = readFromFile(splitArgs);
			text = replaceAllSubStrings(text, splitArgs);
		} catch(SedException e) {
			e.printStackTrace();
		}
		
		return text;
	}

	/**
	 * Returns string containing lines with first matched substring replaced in
	 * Stdin
	 * 
	 * @param args String containing command and arguments
	 */
	@Override
	public String replaceFirstSubStringFromStdin(String args) {
		String[] splitArgs = args.replaceAll("\\s{2,}", " ").trim().split(" ");
		String text = new String();
		
		if(stdin == null) {
			stdin = new ByteArrayInputStream(SedTest.input.getBytes());
		}
		
		try {
			text = readFromStdin(splitArgs, stdin);
			text = replaceFirstSubString(text, splitArgs);
		} catch(SedException e) {
			e.printStackTrace();
		}
		
		return text;
	}

	/**
	 * Returns string containing lines with all matched substring replaced in
	 * Stdin
	 * 
	 * @param args String containing command and arguments
	 */
	@Override
	public String replaceAllSubstringsInStdin(String args) {
		String[] splitArgs = args.replaceAll("\\s{2,}", " ").trim().split(" ");
		String text = new String();
		
		if(stdin == null) {
			stdin = new ByteArrayInputStream(SedTest.input.getBytes());
		}
		
		try {
			text = readFromStdin(splitArgs, stdin);
			text = replaceAllSubStrings(text, splitArgs);
		} catch(SedException e) {
			e.printStackTrace();
		}
		
		return text;
	}
	
	/**
	 * Returns string containing lines when invalid replacement rule is
	 * provided
	 * 
	 * @param args String containing command and arguments 
	 */
	@Override
	public String replaceSubstringWithInvalidRule(String args) {
		String[] splitArgs = args.replaceAll("\\s{2,}", " ").trim().split(" ");
		String text = SedTest.input;
		
		return replaceFirstSubString(text, splitArgs);
	}

	/**
	 * Returns string containing lines when invalid replacement string is
	 * provided
	 * 
	 * @param args String containing command and arguments
	 */
	@Override
	public String replaceSubstringWithInvalidReplacement(String args) {
		String[] splitArgs = args.replaceAll("\\s{2,}", " ").trim().split(" ");
		String text = SedTest.input;
		
		return replaceFirstSubString(text, splitArgs);
	}

	/**
	 * Returns string containing lines when invalid regex is provided
	 * 
	 * @param args String containing command and arguments
	 */
	@Override
	public String replaceSubstringWithInvalidRegex(String args) {
		String[] splitArgs = args.replaceAll("\\s{2,}", " ").trim().split(" ");
		String text = SedTest.input;
		
		return replaceFirstSubString(text, splitArgs);
	}

}
