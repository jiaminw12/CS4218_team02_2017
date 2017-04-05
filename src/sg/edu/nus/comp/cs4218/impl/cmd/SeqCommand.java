package sg.edu.nus.comp.cs4218.impl.cmd;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;

/*
 * Assumptions: 
 * 1) Allow multiple semi-colons as long as they are within single or double quotes
 * 		For example,
 * 			echo 'hello;;;;;'
 * 			echo "hello;;;;;"
 */

/**
 * A Sequence Command is a sub-command consisting of at least one non-keyword
 * and quoted (if any).
 * 
 * <p>
 * <b>Command format:</b> <code>(&lt;non-Keyword&gt; | &lt;quoted&gt;)*</code>
 * </p>
 */

public class SeqCommand implements Command {

	private List<String> argsArray;
	private String cmdline;
	private boolean error;

	public SeqCommand(String cmdline) {
		this.cmdline = cmdline;
		this.error = false;
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

		for (int i = 0; i < argsArray.size(); i++) {
			if (argsArray.get(i).contains("|")) {
				PipeCommand pipeCommand = new PipeCommand(argsArray.get(i));
				pipeCommand.parse();
				pipeCommand.evaluate(System.in, stdout);
			} else {
				CallCommand callCmd = new CallCommand(argsArray.get(i));
				callCmd.parse();
				callCmd.evaluate(stdin, stdout);
			}
		}
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

		try {
			cmdline = cmdline.replace("\r", "").replace("\n", " ");
			String tempCmdLine = cmdline.trim();

			int countSQ = 0;
			int countDQ = 0;
			int countBQ = 0;
			int index = 0;

			if (tempCmdLine.length() > 0 && (tempCmdLine.charAt(0) == ';'
					|| tempCmdLine.charAt(tempCmdLine.length() - 1) == ';')) {
				throw new ShellException("Invalid sequence operators.");
			}
			
			cmdline = cmdline + ";";
			for (int i = 0; i < cmdline.length(); i++) {
				if (cmdline.charAt(i) == '`') {
					countBQ += 1;
				} else if (cmdline.charAt(i) == '"') {
					countDQ += 1;
				} else if (cmdline.charAt(i) == '\'') {
					countSQ += 1;
				} else if (cmdline.charAt(i) == ';') {
					if (countSQ == 2 && countSQ % 2 == 0) {
						argsArray.add(cmdline.substring(index, i).trim());
						index = i + 1;
					} else if (countDQ % 2 == 0 ){
						if (countBQ % 2 == 0 && countSQ % 2 == 0){
							argsArray.add(cmdline.substring(index, i).trim());
							index = i + 1;
						}
					}
				}
			}

			if (argsArray.size() == 0) {
				throw new ShellException("Invalid quoting.");
			}

		} catch (NullPointerException e) {
			throw new ShellException("Invalid quoting.");
		}
	}

	/**
	 * For testing purposes only
	 */
	public int getArgsArrayLength() {
		return argsArray.size();
	}

	public List<String> getArgsArray() {
		return argsArray;
	}

	/**
	 * Terminates current execution of the command (unused for now)
	 */
	@Override
	public void terminate() {
		// TODO Auto-generated method stub
	}

}
