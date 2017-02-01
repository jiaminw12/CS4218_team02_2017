package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Calendar;

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

	PrintWriter writer;
	
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
	 *             If application has error.
	 */
	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout)
			throws DateException {
		
		if (args.length > 1){
			throw new DateException("Exception Caught");
		}
		
		writer = new PrintWriter(
				new BufferedWriter(new OutputStreamWriter(stdout)));
		writer.println((getCurrentDate()));
		writer.flush();
	}

	@Override
	public String printCurrentDate(String args) {
		// args - date
		return getCurrentDate();
	}
	
	public String getCurrentDate(){
		Calendar now = Calendar.getInstance();
		return now.getTime().toString();
	}

}
