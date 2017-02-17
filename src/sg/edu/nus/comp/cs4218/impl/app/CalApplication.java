package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.app.Cal;
import sg.edu.nus.comp.cs4218.exception.CalException;

/**
 * The cal command prints the calendar of the current month. If neither month nor year are specified, print calendar for the current
 * month.
 * 
 * <p>
 * <b>Command format:</b> <code>cal [-m][[month] year]</code>
 * <dl>
 * <dt>-m</dt>
 * <dd>Make Monday the first day of the week. By default, first day of the week
 * is Sunday.</dd>
 * <dt>month</dt>
 * <dd>If month and year are specified, print the calendar for the
 * specified.</dd>
 * <dt>year</dt>
 * <dd>If only year is specified, print the calendar for each month in the
 * specified year in a grid 3 wide and 4 down.</dd>
 * </dl>
 * </p>
 */
public class CalApplication implements Application, Cal {

	/**
	 * Runs the cal application with the specified arguments.
	 * 
	 * @param args
	 *            Array of arguments for the application.
	 * @param stdin
	 *            An InputStream, not used.
	 * @param stdout
	 *            An OutputStream. Elements of args will be output to stdout,
	 *            separated by a space character.
	 * 
	 * @throws CalException
	 *             If an I/O exception occurs.
	 */
	public void run(String[] args, InputStream stdin, OutputStream stdout)
			throws CalException {
		if (args == null) {
			throw new CalException("Null arguments");
		}
		if (stdout == null) {
			throw new CalException("OutputStream not provided");
		}
	}

	@Override
	public String printCal(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String printCalWithMondayFirst(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String printCalForMonthYear(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String printCalForYear(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String printCalForMonthYearMondayFirst(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String printCalForYearMondayFirst(String args) {
		// TODO Auto-generated method stub
		return null;
	}

}
