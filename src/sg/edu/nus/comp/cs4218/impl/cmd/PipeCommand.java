package sg.edu.nus.comp.cs4218.impl.cmd;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

/*
 * Assumptions:
 * 1) run function will call the correct functions with the correct
 * inputs in the correct order separated by a space 
 * 2) Folders and Files's name contain no space 
 * 3) Run function will take inputs directly from shell ordered
 * 4) Files only contain ASCII characters and are not folder or directory
 * 5) Path of files and the file name must not contain any spaces
 * 6) Allow multiple pipe operators as long as they are within single or double quotes
 * 		For example,
 * 			echo ‘hello ||||’
 * 			echo “hello ||||”
 * 7) If pipe operator is the first or last character, it is invalid
 * 		For example,
 * 			Invalid: | cat test.txt | sed s/one/two
 * 			Invalid: cat test.txt | sed s/one/two |
 * 8) If it is empty (or just spaces) between at least two pipe operators, it is invalid
 * 		For example,
 * 			Invalid: cat test.txt | | sed s/one/two 
 */

/**
 * A Pipe Command is a left-associative operator that can be used to bind 
 * a set of call commands into a chain. Each pipe operator binds the output of 
 * the left part to the input of the right part, then evaluates these parts 
 * concurrently. If an exception occurred in any of these parts, the execution 
 * of the other part must be terminated.
 * 
 * 
 * <p>
 * <b>Command format:</b> <code>&lt;call&gt; “|” &lt;call&gt; | &lt;pipe&gt; “|” <call></code>
 * </p>
 */

public class PipeCommand implements Command {

	private List<String> argsArray;
	private String cmdline;

	public PipeCommand(String cmdline) {
		this.cmdline = cmdline.trim();
		this.argsArray = new ArrayList<String>();
	}

	/**
	 * Evaluates sub-command using data provided through stdin stream. Writes
	 * result to stdout stream.
	 * 
	 * @param stdin
	 *            InputStream to get data from.
	 * @param stdout
	 *            OutputStream to write resultant data to.
	 * 
	 * @throws AbstractApplicationException
	 *             If an exception happens while evaluating the sub-command.
	 * @throws ShellException
	 *             If an exception happens while evaluating the sub-command.
	 */
	@Override
	public void evaluate(InputStream stdin, OutputStream stdout)
			throws AbstractApplicationException, ShellException {

		InputStream input = stdin;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		if(argsArray.size() == 1) {
			CallCommand callCmd = new CallCommand(argsArray.get(0));
			callCmd.parse();
			callCmd.evaluate(input, stdout);
		} else {
			for (int i = 0; i < argsArray.size(); i++) {
				String result = "";

				if(i > 0) {
					result = new BufferedReader(new InputStreamReader(input)).lines()
							.parallel().collect(Collectors.joining(System.getProperty("line.separator")));
				}

				ByteArrayInputStream inputStream = new ByteArrayInputStream(result.getBytes());
				CallCommand callCmd = new CallCommand(argsArray.get(i));
				callCmd.parse();
				callCmd.evaluate(inputStream, outputStream);

				if(i != argsArray.size() - 1) {
					input = ShellImpl.outputStreamToInputStream(outputStream);
					outputStream = new ByteArrayOutputStream();
				} else {
					// Final command
					ShellImpl.closeOutputStream(outputStream);
				} 
			}
		}

		ShellImpl.writeToStdout(outputStream, stdout);
		ShellImpl.closeOutputStream(outputStream);
	}

	/**
	 * Parses command to tokenise commands using semicolons into Call Commands,
	 * if semicolons is not within the backquote
	 * 
	 * @throws ShellException
	 *             If an exception happens while parsing the sub-command and
	 *             semicolon is the 1st and last index of the cmdline
	 */
	public void parse() throws ShellException {

		int countSQ = 0;
		int countDQ = 0;
		int countBQ = 0;
		int index = 0;

		if(cmdline.length() > 0 && (cmdline.charAt(0) == '|' 
				|| cmdline.charAt(cmdline.length() - 1) == '|')) {
			throw new ShellException("Invalid pipe operators");
		}

		cmdline = cmdline + "|";
		for (int i = 0 ; i < cmdline.length(); i++) {
			if(cmdline.charAt(i) == '`'){
				countBQ += 1;
			} else if (cmdline.charAt(i) == '"'){
				countDQ += 1;
			} else if (cmdline.charAt(i) =='\'') {
				countSQ += 1;
			} else if (cmdline.charAt(i) == '|'){
				if (countSQ > 1){
					if (countSQ %2 == 0){
						argsArray.add(cmdline.substring(index, i).trim());
						index = i+1;
					}
				} else {
					if (countBQ % 2 == 0  && countDQ % 2 == 0){
						argsArray.add(cmdline.substring(index, i).trim());
						index = i+1;
					}
				}
			}
		}
	}

	/**
	 * Terminates current execution of the command (unused for now)
	 */
	@Override
	public void terminate() {
		// TODO Auto-generated method stub

	}

}
