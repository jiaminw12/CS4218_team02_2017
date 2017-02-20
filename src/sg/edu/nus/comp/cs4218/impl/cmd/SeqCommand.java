package sg.edu.nus.comp.cs4218.impl.cmd;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.comp.cs4218.Command;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;

/**
 * A Sequence Command is a sub-command consisting of at least one non-keyword and
 * quoted (if any).
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
		this.cmdline = cmdline.trim();
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
			if(argsArray.get(i).contains("|")) {
				PipeCommand pipeCommand = new PipeCommand(argsArray.get(i));
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
		
		if(cmdline.length() > 0 && (cmdline.charAt(0) == ';' 
				|| cmdline.charAt(cmdline.length() - 1) == ';')) {
			error = true;
			throw new ShellException("Invalid sequence operators");
		}
		
        String[] tokens = cmdline.split(";(?=(?:[^\"\']*\"\'[^\"\']*\"\')*[^\"\']*$)", -1);
        for(String command : tokens) {
//        	System.out.println("COMMAND: " + command.trim());
        	argsArray.add(command.trim());
        }
	}
	
	/**
	 * For testing purposes only
	 */
	public int getArgsLength(){
		return argsArray.size(); 
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
