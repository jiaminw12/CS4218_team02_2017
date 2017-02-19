package sg.edu.nus.comp.cs4218.impl.cmd;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.stream.Collectors;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

/**
 * A Pipe Command is a left-associative operator that can be used to bind 
 * a set of call commands into a chain. Each pipe operator binds the output of 
 * the left part to the input of the right part, then evaluates these parts 
 * concurrently. If an exception occurred in any of these parts, the execution 
 * of the other part must be terminated.
 * 
 * <p>
 * <b>Command format:</b> <code>&lt;call&gt; “|” &lt;call&gt; | &lt;pipe&gt; “|” <call></code>
 * </p>
 */

public class PipeCommand implements Command {

	private String cmdline;

	public PipeCommand(String cmdline) {
		this.cmdline = cmdline.trim();
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

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String[] args = cmdline.split("\\|");	//TODO: Update this split method

		if(cmdline.length() > 0 && (cmdline.charAt(0) == '|' 
				|| cmdline.charAt(cmdline.length() - 1) == '|')
				|| cmdline.contains("||")) {
			throw new ShellException("Invalid pipe operators");
		}
		
		if(args.length == 1) {
			String[] words = args[0].trim().split(" ");  	//TODO: Update this split method
			ShellImpl.runApp(words[0], words, stdin, stdout);
		} else {
			for (int i = 0; i < args.length; i++) {
				String[] words = args[i].trim().split(" "); 	//TODO: Update this split method
				String result = new String();

				if(i > 0) {
					result = new BufferedReader(new InputStreamReader(stdin)).lines()
							.parallel().collect(Collectors.joining(System.getProperty("line.separator")));
				}

				ByteArrayInputStream inputStream = new ByteArrayInputStream(result.getBytes());
				ShellImpl.runApp(words[0], words, inputStream, outputStream);

				if(i != args.length - 1) {
					stdin = ShellImpl.outputStreamToInputStream(outputStream);
					outputStream = new ByteArrayOutputStream();
				} else {
					// Final command
					ShellImpl.closeOutputStream(outputStream);
				} 
			}
		}
		
		ShellImpl.writeToStdout(outputStream, stdout);
	}

	/**
	 * Terminates current execution of the command (unused for now)
	 */
	@Override
	public void terminate() {
		// TODO Auto-generated method stub

	}

}
