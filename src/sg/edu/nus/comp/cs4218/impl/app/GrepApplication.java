package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.app.Grep;
import sg.edu.nus.comp.cs4218.exception.GrepException;

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
		return null;
	}

	@Override
	public String grepFromMultipleFiles(String args) {
		// TODO Auto-generated method stub
		return null;
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
