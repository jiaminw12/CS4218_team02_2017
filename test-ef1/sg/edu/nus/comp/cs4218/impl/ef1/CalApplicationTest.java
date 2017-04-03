package sg.edu.nus.comp.cs4218.impl.ef1;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.CalException;
import sg.edu.nus.comp.cs4218.impl.app.CalApplication;

public class CalApplicationTest {

	private static final String MARCHSUNOUTPUT = "      March 2017\nSu Mo Tu We Th Fr Sa\n         1  2  3  4 "
			+ "\n5  6  7  8  9  10 11\n12 13 14 15 16 17 18\n19 20 21 22 23 24 25\n26 27 28 29 30 31 ";
	private static final String SUNFIRSTWEEK = "Su Mo Tu We Th Fr Sa  Su Mo Tu We Th Fr Sa  Su Mo Tu We Th Fr Sa\n";
	private static final String MONFIRSTWEEK = "Mo Tu We Th Fr Sa Su  Mo Tu We Th Fr Sa Su  Mo Tu We Th Fr Sa Su\n";
	private static final String MARCH2017MON = "      March 2017\nMo Tu We Th Fr Sa Su\n"
			+ "      1  2  3  4  5 \n6  7  8  9  10 11 12\n13 14 15 16 17 18 19\n20 21 22 23 24 25 26\n27 28 29 30 31 ";
	private static final String _2017CALMON = "                              2017\n"
			+ "      January               February               March          \n"
			+ MONFIRSTWEEK
			+ "                  1         1  2  3  4  5         1  2  3  4  5   \n"
			+ "2  3  4  5  6  7  8   6  7  8  9  10 11 12  6  7  8  9  10 11 12  \n"
			+ "9  10 11 12 13 14 15  13 14 15 16 17 18 19  13 14 15 16 17 18 19  \n"
			+ "16 17 18 19 20 21 22  20 21 22 23 24 25 26  20 21 22 23 24 25 26  \n"
			+ "23 24 25 26 27 28 29  27 28                 27 28 29 30 31        \n"
			+ "30 31                                                             \n"
			+ "        April                 May                   June    		 \n"
			+ MONFIRSTWEEK
			+ "               1  2   1  2  3  4  5  6  7            1  2  3  4   \n"
			+ "3  4  5  6  7  8  9   8  9  10 11 12 13 14  5  6  7  8  9  10 11  \n"
			+ "10 11 12 13 14 15 16  15 16 17 18 19 20 21  12 13 14 15 16 17 18  \n"
			+ "17 18 19 20 21 22 23  22 23 24 25 26 27 28  19 20 21 22 23 24 25  \n"
			+ "24 25 26 27 28 29 30  29 30 31              26 27 28 29 30        \n"
			+ "        July                 August              September        \n"
			+ MONFIRSTWEEK
			+ "               1  2      1  2  3  4  5  6               1  2  3   \n"
			+ "3  4  5  6  7  8  9   7  8  9  10 11 12 13  4  5  6  7  8  9  10  \n"
			+ "10 11 12 13 14 15 16  14 15 16 17 18 19 20  11 12 13 14 15 16 17  \n"
			+ "17 18 19 20 21 22 23  21 22 23 24 25 26 27  18 19 20 21 22 23 24  \n"
			+ "24 25 26 27 28 29 30  28 29 30 31           25 26 27 28 29 30     \n"
			+ "31                                                                \n"
			+ "      October               November              December        \n"
			+ MONFIRSTWEEK
			+ "                  1         1  2  3  4  5               1  2  3   \n"
			+ "2  3  4  5  6  7  8   6  7  8  9  10 11 12  4  5  6  7  8  9  10  \n"
			+ "9  10 11 12 13 14 15  13 14 15 16 17 18 19  11 12 13 14 15 16 17  \n"
			+ "16 17 18 19 20 21 22  20 21 22 23 24 25 26  18 19 20 21 22 23 24  \n"
			+ "23 24 25 26 27 28 29  27 28 29 30           25 26 27 28 29 30 31  \n"
			+ "30 31                                                             \n";
	private static final String _2017CAL = "                              2017\n"
			+ "      January               February               March          \n"
			+ SUNFIRSTWEEK
			+ "1  2  3  4  5  6  7            1  2  3  4            1  2  3  4   \n"
			+ "8  9  10 11 12 13 14  5  6  7  8  9  10 11  5  6  7  8  9  10 11  \n"
			+ "15 16 17 18 19 20 21  12 13 14 15 16 17 18  12 13 14 15 16 17 18  \n"
			+ "22 23 24 25 26 27 28  19 20 21 22 23 24 25  19 20 21 22 23 24 25  \n"
			+ "29 30 31              26 27 28              26 27 28 29 30 31     \n"
			+ "        April                 May                   June    		 \n"
			+ SUNFIRSTWEEK
			+ "                  1      1  2  3  4  5  6               1  2  3   \n"
			+ "2  3  4  5  6  7  8   7  8  9  10 11 12 13  4  5  6  7  8  9  10  \n"
			+ "9  10 11 12 13 14 15  14 15 16 17 18 19 20  11 12 13 14 15 16 17  \n"
			+ "16 17 18 19 20 21 22  21 22 23 24 25 26 27  18 19 20 21 22 23 24  \n"
			+ "23 24 25 26 27 28 29  28 29 30 31           25 26 27 28 29 30     \n"
			+ "30                                                                \n"
			+ "        July                 August              September        \n"
			+ SUNFIRSTWEEK
			+ "                  1         1  2  3  4  5                  1  2   \n"
			+ "2  3  4  5  6  7  8   6  7  8  9  10 11 12  3  4  5  6  7  8  9   \n"
			+ "9  10 11 12 13 14 15  13 14 15 16 17 18 19  10 11 12 13 14 15 16  \n"
			+ "16 17 18 19 20 21 22  20 21 22 23 24 25 26  17 18 19 20 21 22 23  \n"
			+ "23 24 25 26 27 28 29  27 28 29 30 31        24 25 26 27 28 29 30  \n"
			+ "30 31                                                             \n"
			+ "      October               November              December        \n"
			+ SUNFIRSTWEEK
			+ "1  2  3  4  5  6  7            1  2  3  4                  1  2   \n"
			+ "8  9  10 11 12 13 14  5  6  7  8  9  10 11  3  4  5  6  7  8  9   \n"
			+ "15 16 17 18 19 20 21  12 13 14 15 16 17 18  10 11 12 13 14 15 16  \n"
			+ "22 23 24 25 26 27 28  19 20 21 22 23 24 25  17 18 19 20 21 22 23  \n"
			+ "29 30 31              26 27 28 29 30        24 25 26 27 28 29 30  \n"
			+ "                                            31                    \n";
	private static final String _2017 = "2017";
	private CalApplication calendarApp;
	private String[] args;
	int month = 0;
	java.util.Date date = null;
	ByteArrayOutputStream baos;
	PrintStream print;

	@Before
	public void setUp() {
		date = new java.util.Date();
		month = date.getMonth() + 1;
		calendarApp = new CalApplication();
		baos = new ByteArrayOutputStream();
		print = new PrintStream(baos);
		System.setOut(print);
	}

	@Test
	public void printCalendarWithoutInputsFromRun() throws CalException {
		args = new String[1];
		args[0] = "cal";
		calendarApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(printMonth() + System.lineSeparator(), baos.toString());
	}

	@Test
	public void printCalendarWithoutInputs() {
		String expectedResult = calendarApp.printCal("cal");
		assertEquals(printMonth(), expectedResult);
	}

	@Test
	public void printCalendarMondayWithoutInputsFromRun() throws CalException {
		args = new String[2];
		args[0] = "cal";
		args[1] = "-m";
		calendarApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(printMonthMondayFirst() + System.lineSeparator(),
				baos.toString());
	}

	@Test
	public void printCalendarMondayWithoutInputs() {
		args = new String[2];
		args[0] = "cal";
		args[1] = "-m";
		assertEquals(printMonthMondayFirst(),
				calendarApp.printCalWithMondayFirst(args[0] + " " + args[1]));
	}

	@Test
	public void printCalendarMonthYearFromRun() throws CalException {
		args = new String[3];
		args[0] = "cal";
		args[1] = "03";
		args[2] = _2017;
		calendarApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(MARCHSUNOUTPUT + System.lineSeparator(), baos.toString());
	}

	@Test
	public void printCalendarMonthNameYearFromRun() throws CalException {
		args = new String[3];
		args[0] = "cal";
		args[1] = "march";
		args[2] = _2017;
		calendarApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(MARCHSUNOUTPUT + System.lineSeparator(), baos.toString());
	}

	@Test
	public void printCalendarMonthShortNameYearFromRun() throws CalException {
		args = new String[3];
		args[0] = "cal";
		args[1] = "mar";
		args[2] = _2017;
		calendarApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(MARCHSUNOUTPUT + System.lineSeparator(), baos.toString());
	}

	@Test
	public void printCalendarMonthYearFromRunSingleDigit() throws CalException {
		args = new String[3];
		args[0] = "cal";
		args[1] = "3";
		args[2] = _2017;
		calendarApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(MARCHSUNOUTPUT + System.lineSeparator(), baos.toString());
	}

	@Test
	public void printCalendarMonthYear() {
		args = new String[3];
		args[0] = "cal";
		args[1] = "03";
		args[2] = _2017;
		assertEquals(MARCHSUNOUTPUT, calendarApp
				.printCalForMonthYear(args[0] + " " + args[1] + " " + args[2]));
	}

	@Test
	public void printCalendarMonthYearMondayFromRun() throws CalException {
		args = new String[4];
		args[0] = "cal";
		args[1] = "-m";
		args[2] = "03";
		args[3] = _2017;
		calendarApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(MARCH2017MON + System.lineSeparator(), baos.toString());
	}

	@Test
	public void printCalendarMonthYearMondayFromRunSingleDigit()
			throws CalException {
		args = new String[4];
		args[0] = "cal";
		args[1] = "-m";
		args[2] = "3";
		args[3] = _2017;
		calendarApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(MARCH2017MON + System.lineSeparator(), baos.toString());
	}

	@Test
	public void printCalendarMonthYearMonday() {
		args = new String[4];
		args[0] = "cal";
		args[1] = "-m";
		args[2] = "03";
		args[3] = _2017;
		assertEquals(MARCH2017MON, calendarApp.printCalForMonthYearMondayFirst(
				args[0] + " " + args[1] + " " + args[2] + " " + args[3]));
	}

	@Test
	public void printCalendarYearFromRun() throws CalException {
		args = new String[2];
		args[0] = "cal";
		args[1] = _2017;
		calendarApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(_2017CAL + System.lineSeparator(), baos.toString());
	}

	@Test
	public void printCalendarYear() {
		args = new String[2];
		args[0] = "cal";
		args[1] = _2017;
		assertEquals(_2017CAL,
				calendarApp.printCalForYear(args[0] + " " + args[1]));
	}

	@Test
	public void printCalendarYearMondayFromRun() throws CalException {
		args = new String[3];
		args[0] = "cal";
		args[1] = "-m";
		args[2] = _2017;
		calendarApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(_2017CALMON + System.lineSeparator(), baos.toString());
	}

	@Test
	public void printCalendarYearMonday() {
		args = new String[3];
		args[0] = "cal";
		args[1] = "-m";
		args[2] = _2017;
		assertEquals(_2017CALMON, calendarApp.printCalForYearMondayFirst(
				args[0] + " " + args[1] + " " + args[2]));
	}

	@Test(expected = CalException.class)
	public void multipleMFromRun() throws CalException {
		args = new String[3];
		args[0] = "cal";
		args[1] = "-m";
		args[2] = "-m";
		calendarApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(
				"   February 2017\nSu Mo Tu We Th Fr Sa\n"
						+ "         1  2  3  4 \n5  6  7  8  9  10 11\n12 13 14 15 16 17 18\n19 20 21 22 23 24 25\n26 27 28 ",
				baos.toString());
	}

	@Test(expected = CalException.class)
	public void multipleYearFromRun() throws CalException {
		args = new String[3];
		args[0] = "cal";
		args[1] = "1991";
		args[2] = "2007";
		calendarApp.run(args, null, System.out);
	}

	@Test(expected = CalException.class)
	public void multipleMonthFromRun() throws CalException {
		args = new String[3];
		args[0] = "cal";
		args[1] = "11";
		args[2] = "12";
		calendarApp.run(args, null, System.out);
	}

	@Test(expected = CalException.class)
	public void multipleMonthStringFromRun() throws CalException {
		args = new String[3];
		args[0] = "cal";
		args[1] = "may";
		args[2] = "april";
		calendarApp.run(args, null, System.out);
		System.out.flush();
	}

	@Test(expected = CalException.class)
	public void multipleMonthStringInShortFullFormatFromRun()
			throws CalException {
		args = new String[3];
		args[0] = "cal";
		args[1] = "september";
		args[2] = "apr";
		calendarApp.run(args, null, System.out);
		System.out.flush();
	}

	@Test(expected = CalException.class)
	public void invalidMonthFromRun() throws CalException {
		args = new String[2];
		args[0] = "cal";
		args[1] = "19";
		calendarApp.run(args, null, System.out);
		System.out.flush();
	}

	@Test(expected = CalException.class)
	public void invalidYearFromRun() throws CalException {
		args = new String[2];
		args[0] = "cal";
		args[1] = "10000";
		calendarApp.run(args, null, System.out);
		System.out.flush();
	}

	@Test(expected = CalException.class)
	public void invalidYear1FromRun() throws CalException {
		args = new String[2];
		args[0] = "cal";
		args[1] = "300";
		calendarApp.run(args, null, System.out);
		System.out.flush();
	}

	@Test
	public void unorderedFromRun() throws CalException {
		args = new String[4];
		args[0] = "cal";
		args[1] = _2017;
		args[2] = "-m";
		args[3] = "03";
		calendarApp.run(args, null, System.out);
		System.out.flush();
		assertEquals(MARCH2017MON + System.lineSeparator(), baos.toString());
	}

	private String printMonth() {
		String expectedResult = null;
		switch (month) {
		case 2:
			expectedResult = "   February 2017\n" + "Su Mo Tu We Th Fr Sa\n"
					+ "         1  2  3  4 \n" + "5  6  7  8  9  10 11\n"
					+ "12 13 14 15 16 17 18\n" + "19 20 21 22 23 24 25\n"
					+ "26 27 28 ";
			break;
		case 3:
			expectedResult = MARCHSUNOUTPUT;
			break;
		case 4:
			expectedResult = "      April 2017\n" + "Su Mo Tu We Th Fr Sa\n"
					+ "                  1 \n" + "2  3  4  5  6  7  8 \n"
					+ "9  10 11 12 13 14 15\n" + "16 17 18 19 20 21 22\n"
					+ "23 24 25 26 27 28 29\n" + "30 ";
			break;
		}

		return expectedResult;
	}

	private String printMonthMondayFirst() {
		String expectedResult = null;
		switch (month) {
		case 2:
			expectedResult = "   February 2017\n" + "Mo Tu We Th Fr Sa Su\n"
					+ "      1  2  3  4  5 \n" + "6  7  8  9  10 11 12\n"
					+ "13 14 15 16 17 18 19\n" + "20 21 22 23 24 25 26\n"
					+ "27 28";
			break;

		case 3:
			expectedResult = MARCH2017MON;
			break;

		case 4:
			expectedResult = "      April 2017\n" + "Mo Tu We Th Fr Sa Su\n"
					+ "               1  2 \n" + "3  4  5  6  7  8  9 \n"
					+ "10 11 12 13 14 15 16\n" + "17 18 19 20 21 22 23\n"
					+ "24 25 26 27 28 29 30";
			break;
		}

		return expectedResult;
	}

}
