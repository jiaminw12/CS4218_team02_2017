package sg.edu.nus.comp.cs4218.impl.cmd;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

/**
 * A Sequence Command is a sub-command consisting of at least one non-keyword and
 * quoted (if any).
 * 
 * <p>
 * <b>Command format:</b> <code>(&lt;non-Keyword&gt; | &lt;quoted&gt;)*</code>
 * </p>
 */

public class SeqCommand implements Command {

	private String cmdline;
	String inputStreamS, outputStreamS;
	String app;
	String[] argsArray;
	Boolean error;
	String errorMsg;
	
	public SeqCommand(String cmdline) {
		this.cmdline = cmdline.trim();
		app = inputStreamS = outputStreamS = "";
		error = false;
		errorMsg = "";
		argsArray = new String[0];
	}

	public SeqCommand() {
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
		
		if (error) {
			throw new ShellException(errorMsg);
		}
		
		argsArray = cmdline.split(";");
		
		if(cmdline.length() > 0 && (cmdline.charAt(0) == ';' 
				|| cmdline.charAt(cmdline.length() - 1) == ';')
				|| cmdline.contains(";;")) {
			throw new ShellException("Invalid sequence operators");
		}
		
		System.out.println(argsArray);

		InputStream inputStream;
		OutputStream outputStream;
		
		if (("").equals(inputStreamS)) {  // empty
			inputStream = stdin;
		} else { // not empty
			inputStream = ShellImpl.openInputRedir(inputStreamS);
		}
		if (("").equals(outputStreamS)) { // empty
			outputStream = stdout;
		} else {
			outputStream = ShellImpl.openOutputRedir(outputStreamS);
		}
	
		
		/*
		if(args.length == 1) {
			String[] words = args[0].trim().split(" ");  	
			ShellImpl.runApp(app, argsArray, inputStream, outputStream);
		} else {
			for (int i = 0; i < args.length; i++) {
				String[] command = args[i].trim().split(" "); 
				
				app = command[0];
				argsArray = command;
				ShellImpl.runApp(app, argsArray, inputStream, outputStream);
				
			}
		}
		*/
		
	//	ShellImpl.closeInputStream(inputStream);
	//	ShellImpl.closeOutputStream(outputStream);
		
	}
	
	/**
	 * For testing purposes only
	 */
	
	public int getArgsLength(){
		return argsArray.length; 
	}
	
	public void setErrorTrue(Boolean setError){
		error = setError;
	}

	/**
	 * Terminates current execution of the command (unused for now)
	 */
	@Override
	public void terminate() {
		// TODO Auto-generated method stub

	}

}
