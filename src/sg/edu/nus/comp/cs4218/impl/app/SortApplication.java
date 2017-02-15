package sg.edu.nus.comp.cs4218.impl.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.app.Sort;
import sg.edu.nus.comp.cs4218.exception.SortException;

/**
 * 
 * The sort command orders the lines of the specified file or input and prints
 * the same lines but in sorted order. It compares each line character by
 * character. A special character (e.g., +) comes before numbers. A number comes
 * before capital letters. A capital letter comes before small letters, etc.
 * Within each character class, the characters are sorted according to their
 * ASCII value.
 * 
 * <p>
 * <b>Command format:</b> <code>sort[-n][FILE]</code>
 * <dl>
 * <dt>-n</dt>
 * <dd>If specified, treat the first word of a line as a number.</dd>
 * <dt>FILE</dt>
 * <dd>The name of the file(s). If not specified, use stdin.</dd>
 * </dl>
 * </p>
 */
public class SortApplication implements Application, Sort {

	/**
	 * Runs the sort application with the specified arguments.
	 * 
	 * @param args
	 *            Array of arguments for the application.
	 * @param stdin
	 *            An InputStream, not used.
	 * @param stdout
	 *            An OutputStream. Elements of args will be output to stdout,
	 *            separated by a space character.
	 * 
	 * @throws SortException
	 *             If an I/O exception occurs.
	 */
	public void run(String[] args, InputStream stdin, OutputStream stdout)
			throws SortException {
		if (args == null) {
			throw new SortException("Null arguments");
		}
		if (stdout == null) {
			throw new SortException("OutputStream not provided");
		}
	}

	@Override
	public String sortStringsSimple(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortStringsCapital(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortNumbers(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortSimpleCapital(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortSimpleNumbers(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortSimpleSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortCapitalNumbers(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortCapitalSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortNumbersSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortSimpleCapitalNumber(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortSimpleCapitalSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortSimpleNumbersSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortCapitalNumbersSpecialChars(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortAll(String toSort) {
		// TODO Auto-generated method stub
		return null;
	}

}
