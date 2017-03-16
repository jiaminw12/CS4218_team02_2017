package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.EchoException;

/*
 * Assumptions:
 * 1) run function will call the correct functions with the correct inputs in the 
 * correct order separated by a space
 * 2) At the end of the output, there should be a newline
 * 
 */

/**
 * The echo command writes its arguments separated by spaces and terminates by a
 * newline on the standard output.
 * 
 * <p>
 * <b>Command format:</b> <code>echo [ARG]...</code>
 * </p>
 */
public class EchoApplication implements Application {

	/**
	 * Runs the echo application with the specified arguments.
	 * 
	 * @param args
	 *            Array of arguments for the application.
	 * @param stdin
	 *            An InputStream, not used.
	 * @param stdout
	 *            An OutputStream. Elements of args will be output to stdout,
	 *            separated by a space character.
	 * 
	 * @throws EchoException
	 *             If an I/O exception occurs.
	 */
	public void run(String[] args, InputStream stdin, OutputStream stdout)
			throws EchoException {
		if (args == null) {
			throw new EchoException("Null arguments");
		}
		if (stdout == null) {
			throw new EchoException("OutputStream not provided");
		}

		try {
			if (args.length == 0) {
				stdout.write("\n\n".getBytes());
			} else {
				for (int i = 1; i < args.length; i++) {
					stdout.write(args[i].getBytes("UTF-8"));

					if (i <= args.length - 2) {
						stdout.write(" ".getBytes("UTF-8"));
					}
				}
				stdout.write("\n".getBytes("UTF-8"));
			}
		} catch (IOException e) {
			throw new EchoException("IOException");
		}
	}

}
