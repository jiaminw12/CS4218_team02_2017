package sg.edu.nus.comp.cs4218.impl.app;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.app.Cal;
import sg.edu.nus.comp.cs4218.exception.CalException;

/**
 * The cal command prints the calendar of the current month. If neither month
 * nor year are specified, print calendar for the current month.
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

	public static final String EXP_SYNTAX = "Invalid syntax encountered.";
	public static final String EXP_ARG = "Invalid argument encountered.";

	String[] months = { "", "January", "February", "March", "April", "May",
			"June", "July", "August", "September", "October", "November",
			"December" };

	String[] shortFormatMonths = { "", "Jan", "Feb", "Mar", "Apr", "May", "Jun",
			"Jul", "Aug", "Sept", "Oct", "Nov", "Dec" };

	int[] days = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	private boolean mondayFirst = false;
	private boolean yearOnly = false;
	private boolean monthOnly = false;
	private int month = 0;
	private int year = 0;

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
		if (args == null || args.length == 0) {
			throw new CalException("Null arguments");
		}

		if (stdout == null) {
			throw new CalException("OutputStream not provided");
		}

		if (args.length > 4) {
			throw new CalException("Too many arguments");
		}

		if (args[0].equals("cal")) {
			Calendar cal = Calendar.getInstance();
			month = cal.get(Calendar.MONTH) + 1;
			year = cal.get(Calendar.YEAR);

			for (int i = 1; i < args.length; i++) {
				if (args[i].equals("-m")) {
					if (!mondayFirst) {
						mondayFirst = true;
					} else {
						throw new CalException(EXP_ARG);
					}
				} else if (checkInteger(args[i])) {
					if (args[i].length() == 4 && checkValidYear(args[i])) {
						if (!yearOnly) {
							year = Integer.parseInt(args[i]);
							yearOnly = true;
						} else {
							throw new CalException(EXP_ARG);
						}
					} else if ((args[i].length() == 1 || args[i].length() == 2)
							&& checkValidMonth(args[i])) {
						if (!monthOnly) {
							month = Integer.parseInt(args[i]);
							monthOnly = true;
						} else {
							throw new CalException(EXP_ARG);
						}
					} else {
						throw new CalException(EXP_ARG);
					}
				} else {
					if (checkValidMonthString(args[i]) != 1
							|| checkValidMonthString(args[i]) == 0) {
						if (!monthOnly) {
							month = checkValidMonthString(args[i]);
							monthOnly = true;
						} else {
							throw new CalException(EXP_ARG);
						}
					} else {
						throw new CalException(EXP_ARG);
					}
				}
			}
		} else {
			throw new CalException(EXP_SYNTAX);
		}

		try {
			String result = null;
			if (args.length == 1 && args[0].equals("cal")) {
				result = printCalendar(month, year);
			} else if (args.length == 2) {
				if (mondayFirst) {
					result = printCalendar(month, year);
				} else if (yearOnly) {
					result = printFullCalendar(year);
				}
			} else if (args.length == 3) {
				if (monthOnly && yearOnly) {
					result = printCalendar(month, year);
				} else if (mondayFirst && yearOnly) {
					result = printFullCalendar(year);
				}
			} else if (args.length == 4 && mondayFirst && monthOnly
					&& yearOnly) {
				result = printCalendar(month, year);
			}
			stdout.write(result.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print the given month of given year
	 *
	 * @param month
	 *            An integer. Given month.
	 * @param year
	 *            An integer. Given year.
	 * 
	 */
	private String printCalendar(int month, int year) throws IOException {
		int d = 0;
		String header = "", dates = "";

		if (month == 2 && checkLeapYear(year))
			days[month] = 29;

		String title = "      " + months[month] + " " + year
				+ System.lineSeparator();

		if (mondayFirst) {
			header = "Mo Tu We Th Fr Sa Su" + System.lineSeparator();
			d = day(month, year, 7);
		} else {
			header = "Su Mo Tu We Th Fr Sa" + System.lineSeparator();
			d = day(month, year, 1);
		}

		// print the calendar
		for (int i = 0; i < d; i++)
			dates += "   ";

		for (int i = 1; i <= days[month]; i++) {
			if (((i + d) % 7 != 0)) {
				if (i < 10) {
					dates += String.valueOf(i) + "  ";
				} else {
					dates += String.valueOf(i) + " ";
				}
			} else if (((i + d) % 7 == 0) || (i == days[month])) {
				if (i < 10) {
					dates += String.valueOf(i) + " ";
				} else {
					dates += String.valueOf(i);
				}
				dates += System.lineSeparator();
			}
		}
		dates += System.lineSeparator();

		return title + header + dates;
	}

	/**
	 * Print the calendar for the given year
	 * 
	 * @param year
	 *            An integer. Given year.
	 * 
	 */
	private String printFullCalendar(int year) throws IOException {
		String[] jan = null, feb = null, mar = null, apr = null, may = null,
				jun = null, jul = null, aug = null, sep = null, oct = null,
				nov = null, dec = null;

		for (int i = 0; i < 12; i++) {
			switch (i) {
			case 0:
				jan = printCalendar(1, year).split("\n");
				break;
			case 1:
				feb = printCalendar(2, year).split("\n");
				break;
			case 2:
				mar = printCalendar(3, year).split("\n");
				break;
			case 3:
				apr = printCalendar(4, year).split("\n");
				break;
			case 4:
				may = printCalendar(5, year).split("\n");
				break;
			case 5:
				jun = printCalendar(6, year).split("\n");
				break;
			case 6:
				jul = printCalendar(7, year).split("\n");
				break;
			case 7:
				aug = printCalendar(8, year).split("\n");
				break;
			case 8:
				sep = printCalendar(9, year).split("\n");
				break;
			case 9:
				oct = printCalendar(10, year).split("\n");
				break;
			case 10:
				nov = printCalendar(11, year).split("\n");
				break;
			case 11:
				dec = printCalendar(12, year).split("\n");
				break;
			}
		}

		String lines = "                              " + year
				+ System.lineSeparator();
		lines += "      January               February               March          "
				+ System.lineSeparator();
		lines += datesLine(jan, feb, mar);
		lines += "        April                 May                   June    		 "
				+ System.lineSeparator();
		lines += datesLine(apr, may, jun);
		lines += "        July                 August              September        "
				+ System.lineSeparator();
		lines += datesLine(jul, aug, sep);
		lines += "      October               November              December        "
				+ System.lineSeparator();
		lines += datesLine(oct, nov, dec);
		lines += System.lineSeparator();
		return lines;
	}

	/**
	 * Append all lines for the given first, second and third month
	 * 
	 * @param first
	 *            An array. Given first month.
	 * @param second
	 *            An array. Given second month.
	 * @param third
	 *            An array. Given third month.
	 * 
	 */
	private String datesLine(String[] first, String[] second, String[] third) {
		String lines = "";
		lines += first[1] + "  " + second[1] + "  " + third[1]
				+ System.lineSeparator();
		for (int i = 2; i < 7; i++) {
			lines += first[i] + addSpaces(first, i) + "  " + second[i]
					+ addSpaces(second, i) + "  " + third[i]
					+ addSpaces(third, i) + "  " + System.lineSeparator();
		}

		if (first.length == 8 || second.length == 8 || third.length == 8) {
			lines += addSpacesForLastRow(first, 7) + "  "
					+ addSpacesForLastRow(second, 7) + "  "
					+ addSpacesForLastRow(third, 7) + "  "
					+ System.lineSeparator();
		}

		return lines;
	}

	/**
	 * Return the day of week which falls on Sun or Mon by using given month and
	 * year.
	 * 
	 * @param month
	 *            An integer. Given month.
	 * @param year
	 *            An integer. Given year.
	 * @param day
	 *            An integer. 7 means Monday first while 1 means Sunday first.
	 * 
	 */
	private static int day(int givenMonth, int givenYear, int givenDay) {
		int year = givenYear - (14 - givenMonth) / 12;
		int temp = year + year / 4 - year / 100 + year / 400;
		int month = givenMonth + 12 * ((14 - givenMonth) / 12) - 2;
		int day = (givenDay + temp + (31 * month) / 12) % 7;
		return day;
	}

	/**
	 * Check whether the given year is a leap year
	 *
	 * @param year
	 *            An integer.
	 * 
	 */
	private static boolean checkLeapYear(int year) {
		if ((year % 4 == 0) && (year % 100 != 0)) {
			return true;
		}

		if (year % 400 == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Return the line of date for calendar.
	 *
	 * @param month
	 *            An array. Array of months.
	 * @param position
	 *            An integer. The current position of array item
	 * 
	 */
	private String addSpaces(String[] month, int position) {
		String lines = "";
		if (month[position].length() != 20) {
			int diffLength = 20 - month[position].length();
			for (int j = 0; j < diffLength; j++) {
				lines += " ";
			}
		}
		return lines;
	}

	/**
	 * Return the last row of date for calendar.
	 *
	 * @param month
	 *            An array. Array of months.
	 * @param position
	 *            An integer. The current position of array item
	 * 
	 */
	private String addSpacesForLastRow(String[] month, int position) {
		String lines = "";
		if (month.length == 8) {
			if (month[position].length() != 20) {
				int diffLength = 20 - month[position].length();
				lines += month[position];
				for (int i = 0; i < diffLength; i++) {
					lines += " ";
				}
			}
		} else {
			for (int i = 0; i < 20; i++) {
				lines += " ";
			}
		}
		return lines;
	}

	/**
	 * Check whether month or year is in digit format.
	 *
	 * @param userInput
	 *            A string. User's input.
	 * 
	 */
	private boolean checkInteger(String word) {
		if (word.matches("^\\d+$")) {
			return true;
		}
		return false;
	}

	/**
	 * Check whether month or year is in correct length.
	 *
	 * @param userInput
	 *            A string. User's input.
	 * 
	 */
	private boolean checkLengthOfString(String userInput) {
		// single digit or double digit for month
		if (userInput.length() == 1 || userInput.length() == 2) {
			return true;
		} else if (userInput.length() == 4) {
			return true;
		}
		return false;
	}

	/**
	 * Check whether month which in integer is valid.
	 *
	 * @param userInput
	 *            A string. User's input.
	 * 
	 */
	private boolean checkValidMonth(String userInput) {
		if (Integer.parseInt(userInput) >= 1
				&& Integer.parseInt(userInput) < 13) {
			return true;
		}
		return false;
	}

	/**
	 * Check whether month which in string is valid. Return the digit of month
	 * if is matched.
	 *
	 * @param userInput
	 *            A string. User's input.
	 * 
	 */
	private int checkValidMonthString(String userInput) {
		for (int i = 0; i < months.length; i++) {
			if (months[i].equalsIgnoreCase(userInput)
					|| shortFormatMonths[i].equalsIgnoreCase(userInput)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Check whether year is less than 1000
	 *
	 * @param userInput
	 *            A string. User's input.
	 * 
	 */
	private boolean checkValidYear(String userInput) {
		if (Integer.parseInt(userInput) >= 1000) {
			return true;
		}
		return false;
	}

	/**
	 * Gets and runs the result from user input
	 *
	 * @param args
	 *            A string. User's command.
	 * @param readConsole
	 *            A checker. To check whether read from stdin or ignore
	 */
	private String printOutputForTest(String args) {
		String[] splitArgs = args.split("\\s+");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		InputStream inputStream = new ByteArrayInputStream(new byte[1]);

		try {
			run(splitArgs, inputStream, outputStream);
		} catch (CalException e) {
			e.printStackTrace();
		}
		String result = new String(outputStream.toByteArray());
		return result.substring(0, result.length() - 1);
	}

	@Override
	public String printCal(String args) {
		return printOutputForTest(args);
	}

	@Override
	public String printCalWithMondayFirst(String args) {
		return printOutputForTest(args);
	}

	@Override
	public String printCalForMonthYear(String args) {
		return printOutputForTest(args);
	}

	@Override
	public String printCalForYear(String args) {
		return printOutputForTest(args);
	}

	@Override
	public String printCalForMonthYearMondayFirst(String args) {
		return printOutputForTest(args);
	}

	@Override
	public String printCalForYearMondayFirst(String args) {
		return printOutputForTest(args);
	}

}
