package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.app.Grep;
import sg.edu.nus.comp.cs4218.exception.GrepException;
import sg.edu.nus.comp.cs4218.exception.HeadException;

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
		if (args == null) {
			throw new GrepException("Null arguments");
		}
		if (stdout == null) {
			throw new GrepException("OutputStream not provided");
		}
	}

	@Override
	public String grepFromStdin(String args, InputStream stdin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String grepFromOneFile(String args) {
		// TODO Auto-generated method stub
		String fileContent = "";
		String [] stringArray= args.split(" ");
		Pattern pattern = Pattern.compile(stringArray[0]);
		BufferedReader reader;
		int lineCount = 0;
		try {
			File file = new File(stringArray[1]);
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			Matcher matcher;
			while (line!=null) {
				matcher = pattern.matcher(line);
				if (matcher.find()) {
					if (lineCount > 0) {
						fileContent = fileContent + System.lineSeparator() + line;
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
		// TODO Auto-generated method stub
		ArrayList <File> listOfTextFiles = new ArrayList <File>();
		String fileContent = "";
		String [] stringArray= args.split(" ");
		Pattern pattern = Pattern.compile(stringArray[0]);
		File file;
		BufferedReader reader;
		int lineCount = 0;
		
		for (int i=1; i<stringArray.length; i++) {
			try {
				if (checkValidFile(new File(stringArray[i]))) {
					file = new File(stringArray[i]);
					listOfTextFiles.add(file);
				}
			} catch (GrepException e) {
				
			}
//			System.out.println(stringArray[i]);
		}
		
		for (int j=0; j<listOfTextFiles.size(); j++) {
			try {
				reader = new BufferedReader(new FileReader(listOfTextFiles.get(j)));
				String line = reader.readLine();
				Matcher matcher;
				while (line!=null) {
					matcher = pattern.matcher(line);
					if (matcher.find()) {
						if (lineCount > 0) {
							fileContent = fileContent + System.lineSeparator() + line;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String grepInvalidPatternInFile(String args) {
		// TODO Auto-generated method stub
		return null;
	}

}
