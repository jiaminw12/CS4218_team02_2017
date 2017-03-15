package sg.edu.nus.comp.cs4218.impl.app;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.app.Date;
import sg.edu.nus.comp.cs4218.exception.DateException;

/**
 * The date command print the current date and time.
 * 
 * <p>
 * <b>Command format:</b> <code>date</code>
 * </p>
 */
public class DateApplication implements Application, Date {

	private java.util.Date currentDate;

	/**
	 * Runs the cat application with the specified arguments.
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
	 * @throws DateException
	 *             If exception occurs.
	 */
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout)
			throws DateException {

		if (args == null || args.length > 1) {
			throw new DateException("Exception Caught");
		}

		if (stdout == null) {
			throw new DateException("Null Pointer Exception");
		}

		currentDate = new java.util.Date();

		try {
			stdout.write(currentDate.toString().getBytes());
			stdout.write(System.lineSeparator().getBytes());
		} catch (IOException e) {
			throw new DateException("IOException Caught");
		}
	}

	/**
	 * For testing purpose. Split the args.
	 * 
	 * @param args
	 *            A string, User input
	 * 
	 */
	@Override
	public String printCurrentDate(String args) {
		String[] splitArgs = args.split("\\s{2,}");
		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				new byte[1]);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			run(splitArgs, inputStream, outputStream);
		} catch (DateException e) {
			e.printStackTrace();
		}
		return new String(outputStream.toByteArray()).trim();
	}

}
