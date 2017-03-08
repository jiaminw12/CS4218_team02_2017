package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.PwdException;

/**
 * The pwd command reports the current working directory followed by a newline.
 * 
 * <p>
 * <b>Command format:</b> <code>pwd</code>
 * <dl>
 * </dl>
 * </p>
 */
public class PwdApplication implements Application {

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout)
			throws PwdException {
		if (args == null) {
			throw new PwdException("Argument cannot be null.");
		} else if (stdout == null){
			throw new PwdException("OutputStream not provided");
		} else if (args.length != 1) {
			throw new PwdException(
					"Too many arguments. Pwd do not accept any arguments.");
		} else {
			String workingDir = Environment.currentDirectory;
			try {
				stdout.write(workingDir.getBytes());
				stdout.write(System.lineSeparator().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
