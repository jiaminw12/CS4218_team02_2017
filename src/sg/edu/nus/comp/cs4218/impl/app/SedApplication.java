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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.app.Sed;
import sg.edu.nus.comp.cs4218.exception.HeadException;
import sg.edu.nus.comp.cs4218.exception.SedException;
import sg.edu.nus.comp.cs4218.impl.cmd.PipeCommand;

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
	
	private static final int TYPE_STDIN = 2;
	private static final int TYPE_FILE = 3;
	private static final int REPLACE_FIRST_SUBSTRING = 3;
	private static final int REPLACE_ALL_SUBSTRING = 4;

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
			String text;
			text = "";
			for(int i = 0; i < args.length; i++) {
				text += args[i] + " ";
			}

			try {
				String[] replacementRule = getReplacementRule(args);
				int replacementType = replacementRule.length;

				if(args.length == 2) {
					// Input type stdin

					if(replacementType == REPLACE_FIRST_SUBSTRING) {
						text = replaceFirstSubStringFromStdin(text, stdin);
					} else if(replacementType == REPLACE_ALL_SUBSTRING) { 
						text = replaceAllSubstringsInStdin(text, stdin);
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
	private String readFromStdin(String[] args, InputStream stdin) throws SedException {

		StringBuffer text = new StringBuffer();
		String str = "";

		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stdin, "UTF-8"));
			
			while((str = bufferedReader.readLine()) != null) {
				text.append(str);
				text.append(System.getProperty("line.separator"));
			}
			
		} catch (IOException e) {
			throw new SedException("Could not read input");
		}
		
		// try to replace substrings
		if(text == null || text.toString().isEmpty()) {
			throw new SedException("Invalid Input");
		} 

		return text.toString().trim();
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
	String[] getReplacementRule(String[] args) throws SedException {
		String cmdline = "";
		for(int i = 0; i < args.length; i++) {
			cmdline += args[i] + " ";
		}

		String replacementRule = args[1];
		String separator;
		
		try {
			separator = replacementRule.substring(1, 2);
		} catch(NullPointerException e) {
			throw new SedException("Error in replacement rule");
		}
		
		String[] splitReplacementRule = null;
		
		try {
			splitReplacementRule = replacementRule.split("\\" + separator);
		} catch (PatternSyntaxException e) {
			throw new SedException(replaceSubstringWithInvalidRule(cmdline));
		}
		
		int length = splitReplacementRule.length;

		if(replacementRule.contains(separator + separator) || length < 3 || length > 4 
				|| !splitReplacementRule[0].equals("s")
				|| (length == 3 && !replacementRule.substring(replacementRule.length() - 1, 
						replacementRule.length()).equals(separator))
				|| (length == 4 && (!splitReplacementRule[3].equals("g") 
						|| replacementRule.substring(replacementRule.length() - 1, 
								replacementRule.length()).equals(separator)))) {
			throw new SedException(replaceSubstringWithInvalidRule(cmdline));
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
	String replaceFirstSubString(String text, String[] args) throws SedException {

		String finalText = "";
		
		try {
			String[] splitLine = text.split(System.getProperty("line.separator"));
			String[] replacementRule = getReplacementRule(args);
			Pattern pattern;
	
			if(replacementRule != null) {
				String regexp = replacementRule[1].replaceAll("\"", "").replaceAll("'", ""); 
				String replacement = replacementRule[2].replaceAll("\"", "").replaceAll("'", ""); 
	
				
				try {
					pattern = Pattern.compile(regexp);
				} catch (PatternSyntaxException e) {
					throw new SedException(replaceSubstringWithInvalidRegex(text));
				}
				
				for(int i = 0; i < splitLine.length; i++) {
					try {
						Matcher matcher = pattern.matcher(splitLine[i]);
						finalText += matcher.replaceFirst(replacement);
					} catch (IllegalArgumentException e) {
						throw new SedException(replaceSubstringWithInvalidReplacement(text));
					}
					
					if(i < splitLine.length - 1) {
						finalText += System.getProperty("line.separator");
					}
				}
		}
		} catch(NullPointerException e) {
			throw new SedException("Null Pointer Exception");
		}

		return finalText;
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
	String replaceAllSubStrings(String text, String[] args) throws SedException {

		String[] replacementRule = getReplacementRule(args);
		Pattern pattern;
		String finalText = "";

		if(replacementRule != null) {
			String regexp = replacementRule[1].replaceAll("\"", "").replaceAll("'", ""); 
			String replacement = replacementRule[2].replaceAll("\"", "").replaceAll("'", ""); 

			try {
				pattern = Pattern.compile(regexp);
			} catch (PatternSyntaxException e) {
				throw new SedException(replaceSubstringWithInvalidRegex(text));
			}
			
			try {
				Matcher matcher = pattern.matcher(text);
				finalText = matcher.replaceAll(replacement);
			} catch (PatternSyntaxException e) {
				throw new SedException(replaceSubstringWithInvalidReplacement(text));
			}
		} 

		return finalText;
	}
	
	/**
	 * Splits the arguments according to the replacement rules.
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
	String[] splitArguments(String args, int inputType) {
		
		String separator = args.split(" ")[1].substring(1, 2);
		String[] splitArgs = new String[inputType];
		int index = args.indexOf("s" + separator);
		
		if(inputType == TYPE_STDIN) {
			if(index > 0) {
				splitArgs[0] = args.substring(0, index).trim();	
				splitArgs[1] = args.substring(index, args.length()).trim();
			}
		} else if(inputType == TYPE_FILE) {
			if(index > 0) {
				splitArgs[0] = args.substring(0, index).trim();
			}
			
			if(args.contains(separator + "g ")) {
				if(index > 0) {
					splitArgs[1] = args.substring(index, args.indexOf(separator + "g ") + 2).trim();
				}
				
				index = args.indexOf(separator + "g ") + 2;
				if(index < args.length()) {
					splitArgs[2] = args.substring(index, args.length()).trim();
				}
			} else {
				String reverseString = new StringBuilder(args).reverse().toString();
				index = reverseString.indexOf(" " + separator);
				int separatorIndex = reverseString.indexOf(separator + "s") + 2;
				
				if(index > 0 && index < reverseString.length() && separatorIndex < reverseString.length()) {
					splitArgs[2] = reverseString.substring(0, index).trim();
					splitArgs[2] = new StringBuilder(splitArgs[2]).reverse().toString();
					splitArgs[1] = reverseString.substring(index, separatorIndex).trim();
					splitArgs[1] = new StringBuilder(splitArgs[1]).reverse().toString();
				}
			}
		}
		
		return splitArgs;
	}

	@Override
	public String replaceFirstSubStringInFile(String args) {
		
		String[] splitArgs = splitArguments(args.replaceAll("\\s{2,}", " ").trim(), TYPE_FILE);	//TODO: Replace
		String text;

		try {
			text = readFromFile(splitArgs);
			text = replaceFirstSubString(text, splitArgs);
		} catch(SedException e) {
			return e.getMessage();
		}

		return text;
	}

	@Override
	public String replaceAllSubstringsInFile(String args) {

		String[] splitArgs = splitArguments(args.replaceAll("\\s{2,}", " ").trim(), TYPE_FILE);	//TODO: Replace
		String text = "";

		try {
			text = readFromFile(splitArgs);
			text = replaceAllSubStrings(text, splitArgs);
		} catch(SedException e) {
			return e.getMessage();
		}

		return text;
	}

	@Override
	public String replaceFirstSubStringFromStdin(String args, InputStream stdin) {
		
		String[] splitArgs = splitArguments(args.replaceAll("\\s{2,}", " ").trim(), TYPE_STDIN);	//TODO: Replace
		String text;
		try {
			text = readFromStdin(splitArgs, stdin);
			text = replaceFirstSubString(text, splitArgs);
		} catch(SedException e) {
			return e.getMessage();
		}

		return text;
	}

	@Override
	public String replaceAllSubstringsInStdin(String args, InputStream stdin) {
		
		String[] splitArgs = splitArguments(args.replaceAll("\\s{2,}", " ").trim(), TYPE_STDIN);	//TODO: Replace
		String text;

		try {
			text = readFromStdin(splitArgs, stdin);
			text = replaceAllSubStrings(text, splitArgs);
		} catch(SedException e) {
			return e.getMessage();
		}

		return text;
	}

	@Override
	public String replaceSubstringWithInvalidRule(String args) {
		
		String[] splitArgs = args.split(" ");
		String errorMessage = "Invalid Replacement Rule: ";
		String replacementRule = splitArgs[1];
		String separator = replacementRule.substring(1, 2);
		
		if(replacementRule.contains(separator + separator)) {
			return errorMessage + "Empty arguments";
		} 
		
		String[] splitReplacementRule = null;
		
		try {
			splitReplacementRule = replacementRule.split("\\" + separator);
		} catch (PatternSyntaxException e) {
			return errorMessage + "Invalid separator";
		}
		
		int length = splitReplacementRule.length;
		
		if(length < 3) {
			errorMessage += "Insufficient arguments";
		} else if(length > 4) {
			errorMessage += "Extra arguments";
		} else if(!splitReplacementRule[0].equals("s")) {
			errorMessage += "Missing \"s\" in front";
		} else if(length == 3) {
			if(!replacementRule.substring(replacementRule.length() - 1, 
					replacementRule.length()).equals(separator)) {
				errorMessage += "Missing separator at the end";
			}
		} else if(length == 4) {
			if(!splitReplacementRule[3].equals("g")) {
				errorMessage += "Missing \"g\" at the end";
			}

			if(replacementRule.substring(replacementRule.length() - 1, 
					replacementRule.length()).equals(separator)) {
				errorMessage += "Extra separator at the end";
			}
		} 

		return errorMessage;
	}

	@Override
	public String replaceSubstringWithInvalidReplacement(String args) {
		return "Invalid Replacement";
	}

	@Override
	public String replaceSubstringWithInvalidRegex(String args) {
		return "Invalid Regex";
	}
}