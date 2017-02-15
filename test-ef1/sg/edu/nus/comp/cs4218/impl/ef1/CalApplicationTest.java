package sg.edu.nus.comp.cs4218.impl.ef1;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.CalException;
import sg.edu.nus.comp.cs4218.impl.app.CalApplication;

public class CalApplicationTest {

	static CalApplication calApp;
	java.util.Date date = null;
	int month = 0;

	@BeforeClass
	public static void setUpOnce() {
		// one-time initialization code
	}

	@Before
	public void setUp() throws CalException, IOException {
		calApp = new CalApplication();
		date = new java.util.Date();
		month = date.getMonth() + 1;
	}

	@Test(expected = CalException.class)
	public void testCalAppWithNullArgument() throws CalException {
		calApp.run(null, null, System.out);
	}

	@Test(expected = CalException.class)
	public void testCalAppWithEmptyArgsWithEmptyInput() throws CalException {
		String[] args = {};
		calApp.run(args, System.in, System.out);
	}

	@Test(expected = CalException.class)
	public void testCalAppWithNullInputOutput() throws CalException {
		String[] args = {"cal", "12", "2017"};
		calApp.run(args, null, null);
	}

	@Test(expected = CalException.class)
	public void testIllegalOption() throws CalException {
		String[] args = {"cal", "12", "2017"};
		calApp.run(args, null, null);
	}

	@Test(expected = CalException.class)
	public void testIllegalYear() throws CalException {
		String[] args = {"cal", "12", "0000"};
		calApp.run(args, null, null);
	}

	@Test(expected = CalException.class)
	public void testIllegalMonth() throws CalException {
		String[] args = {"cal", "0", "2016"};
		calApp.run(args, null, null);
	}

	@Test(expected = CalException.class)
	public void testIllegalArgsLength() throws CalException {
		String[] args = {"cal", "-m", "1", "2016", "2017"};
		calApp.run(args, null, null);
	}

	@Test
	public void testSingleDigit() throws CalException {
		// cal 2 2017
		String[] args = {"cal", "2", "2016"};
		calApp.run(args, null, null);
	}

	@Test
	public void testDoubleDigits() throws CalException {
		// cal 02 2017
		String[] args = {"cal", "02", "2016"};
		calApp.run(args, null, null);
	}

	@Test
	public void testPrintCalWithCalOnly() throws CalException {
		String expectedResult = null;
		switch (month) {
		case 2:
			expectedResult = "   February 2017\n" + "Su Mo Tu We Th Fr Sa\n"
					+ "         1  2  3  4 \n" + "5  6  7  8  9  10 11 \n"
					+ "12 13 14 15 16 17 18 \n" + "19 20 21 22 23 24 25 \n"
					+ "26 27 28 \n";
			break;

		case 3:
			expectedResult = "   March 2017\n" + "Su Mo Tu We Th Fr Sa\n"
					+ "         1  2  3  4 \n" + " 5  6  7  8  9  10 11 \n"
					+ "12 13 14 15 16 17 18 \n" + "19 20 21 22 23 24 25 \n"
					+ "26 27 28 29 30 31 \n";
			break;

		case 4:
			expectedResult = "   April 2017\n" + "Su Mo Tu We Th Fr Sa\n"
					+ "                   1 \n" + "2  3  4  5  6  7  8  \n"
					+ "9  10 11 12 13 14 15 \n" + "16 17 18 19 20 21 22 \n"
					+ "23 24 25 26 27 28 29 \n" + "30 \n";
			break;
		}

		String actualResult = calApp.printCal("cal");
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testPrintCalWithMondayFirst() throws CalException {

		String expectedResult = null;
		switch (month) {
		case 2:
			expectedResult = "   February 2017\n" + "Mo Tu We Th Fr Sa Su\n"
					+ "      1  2  3  4  5  \n" + "6  7  8  9  10 11 12 \n"
					+ "13 14 15 16 17 18 19 \n" + "20 21 22 23 24 25 26 \n"
					+ "27 28 \n";
			break;

		case 3:
			expectedResult = "   March 2016\n" + "Mo Tu We Th Fr Sa Su\n"
					+ "      1  2  3  4  5  \n" + "6  7  8  9  10 11 12 \n"
					+ "13 14 15 16 17 18 19 \n" + "20 21 22 23 24 25 26 \n"
					+ "27 28 29 30 31 \n";
			break;

		case 4:
			expectedResult = "   April 2016\n" + "Mo Tu We Th Fr Sa Su\n"
					+ "               1  2  \n" + "3  4  5  6  7  8  9  \n"
					+ "10 11 12 13 14 15 16 \n" + "17 18 19 20 21 22 23 \n"
					+ "24 25 26 27 28 29 30 \n";
			break;
		}

		String actualResult = calApp.printCalWithMondayFirst("cal -m");
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testPrintCalForMonthYear() throws CalException {
		String expectedResult = "   January 2017\n" + "Su Mo Tu We Th Fr Sa\n"
				+ "                   1 \n" + "2  3  4  5  6  7  8  \n"
				+ "9  10 11 12 13 14 15 \n" + "16 17 18 19 20 21 22 \n"
				+ "23 24 25 26 27 28 29 \n" + "30 31 \n";
		String actualResult = calApp.printCalForMonthYear("cal 1 2017");
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testPrintCalForYear() throws CalException {
		String expectedResult = "   January 2017\t		   February 2017	\t   March 2017\n"
				+ "Su Mo Tu We Th Fr Sa\t	Su Mo Tu We Th Fr Sa	Su Mo Tu We Th Fr Sa\n"
				+ "1  2  3  4  5  6  7 \t 	         1  2  3  4  	         1  2  3  4 \n"
				+ "8  9  10 11 12 13 14\t 	5  6  7  8  9  10 11 	5  6  7  8  9  10 11\n"
				+ " 15 16 17 18 19 20 21\t	12 13 14 15 16 17 18 	12 13 14 15 16 17 18\n"
				+ "22 23 24 25 26 27 28\t 	19 20 21 22 23 24 25 	19 20 21 22 23 24 25\n"
				+ "29 30 31 \t		26 27 28 \t		26 27 28 29 30 31 \n"
				+ "April 2017\t		   May 2017\t		   June 2017\n"
				+ "Su Mo Tu We Th Fr Sa\t	Su Mo Tu We Th Fr Sa\t	Su Mo Tu We Th Fr Sa\n"
				+ "                  1 \t 	   1  2  3  4  5  6 \t 	            1  2  3 \n"
				+ "2  3  4  5  6  7  8 \t 	7  8  9  10 11 12 13\t	4  5  6  7  8  9  10\n"
				+ "9  10 11 12 13 14 15\t	14 15 16 17 18 19 20\t 	11 12 13 14 15 16 17\n"
				+ "16 17 18 19 20 21 22\t 	21 22 23 24 25 26 27\t 	18 19 20 21 22 23 24\n"
				+ "23 24 25 26 27 28 29\t 	28 29 30 31 	    \t 25 26 27 28 29 30 \n"
				+ "30 \n"
				+ "July 2017\t		   August 2017\t	   September 2017\n"
				+ "Su Mo Tu We Th Fr Sa\t	Su Mo Tu We Th Fr Sa\t	Su Mo Tu We Th Fr Sa\n"
				+ "                  1 \t 	      1  2  3  4  5 \t 	               1  2 \n"
				+ "2  3  4  5  6  7  8 \t 	6  7  8  9  10 11 12\t 	3  4  5  6  7  8  9 \n"
				+ "9  10 11 12 13 14 15\t 	13 14 15 16 17 18 19\t 	10 11 12 13 14 15 16\n"
				+ "16 17 18 19 20 21 22\t 	20 21 22 23 24 25 26\t 	17 18 19 20 21 22 23\n"
				+ "23 24 25 26 27 28 29\t 	27 28 29 30 31 \t	      24 25 26 27 28 29 30 \n"
				+ "30 31 \n"
				+ "   October 2017		   November 2017	   December 2017\n"
				+ "Su Mo Tu We Th Fr Sa	Su Mo Tu We Th Fr Sa	Su Mo Tu We Th Fr Sa\n"
				+ "1  2  3  4  5  6  7  	         1  2  3  4  	               1  2 \n"
				+ "8  9  10 11 12 13 14 	5  6  7  8  9  10 11 	3  4  5  6  7  8  9 \n"
				+ "15 16 17 18 19 20 21 	12 13 14 15 16 17 18 	10 11 12 13 14 15 16\n"
				+ "22 23 24 25 26 27 28 	19 20 21 22 23 24 25 	17 18 19 20 21 22 23\n"
				+ "29 30 31 		26 27 28 29 30 	24 25 26 27 28 29 30 \n"
				+ "31\n ";

		String actualResult = calApp.printCalForYear("cal 2017");
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testPrintCalForMonthYearMondayFirst() throws CalException {
		String expectedResult = "   February 2017\n" + "Mo Tu We Th Fr Sa Su\n"
				+ "      1  2  3  4  5  \n" + "6  7  8  9  10 11 12 \n"
				+ "13 14 15 16 17 18 19 \n" + "20 21 22 23 24 25 26 \n"
				+ "27 28 \n";
		String actualResult = calApp
				.printCalForMonthYearMondayFirst("cal -m 2 2017");
		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testPrintCalForYearMondayFirst() throws CalException {
		String expectedResult = "   January 2017\t\t	   February 2017\t	   March 2017\n"
				+ "Mo Tu We Th Fr Sa Su\t	Mo Tu We Th Fr Sa Su\t	Mo Tu We Th Fr Sa Su\n"
				+ "                  1  \t	      1  2  3  4  5  \t	      1  2  3  4  5 \n"
				+ "  2  3  4  5  6  7  8  \t	6  7  8  9  10 11 12 \t	6  7  8  9  10 11 12 \n"
				+ " 9  10 11 12 13 14 15 \t	13 14 15 16 17 18 19 \t	13 14 15 16 17 18 19 \n"
				+ "16 17 18 19 20 21 22 \t	20 21 22 23 24 25 26 \t	20 21 22 23 24 25 26 \n"
				+ " 23 24 25 26 27 28 29 \t	27 28 		     \t	27 28 29 30 31 \n30 31 \n"
				+ "   April 2017\t\t	   May 2017\t		   June 2017\n"
				+ "Mo Tu We Th Fr Sa Su\t	Mo Tu We Th Fr Sa Su\t	Mo Tu We Th Fr Sa Su\n"
				+ "               1  2 \t 	1  2  3  4  5  6  7 \t 	         1  2  3  4   \n"
				+ "3  4  5  6  7  8  9 \t 	8  9  10 11 12 13 14\t 	5  6  7  8  9  10 11  \n"
				+ "10 11 12 13 14 15 16\t	15 16 17 18 19 20 21\t 	12 13 14 15 16 17 18  \n"
				+ "17 18 19 20 21 22 23\t 	22 23 24 25 26 27 28\t 	19 20 21 22 23 24 25  \n"
				+ "24 25 26 27 28 29 30\t 	29 30 31 \t		26 27 28 29 30  \n"
				+ "July 2017\t\t	   August 2017\t	    September 2017 \n"
				+ "Mo Tu We Th Fr Sa Su\t	Mo Tu We Th Fr Sa Su\t	Mo Tu We Th Fr Sa Su \n"
				+ "              1  2 \t 	   1  2  3  4  5  6 \t	            1  2  3   \n"
				+ "3  4  5  6  7  8  9 \t 	7  8  9  10 11 12 13\t 	4  5  6  7  8  9  10  \n"
				+ "10 11 12 13 14 15 16\t 	14 15 16 17 18 19 20\t 	11 12 13 14 15 16 17  \n"
				+ "17 18 19 20 21 22 23\t 	21 22 23 24 25 26 27\t	18 19 20 21 22 23 24  \n"
				+ "24 25 26 27 28 29 30\t 	28 29 30 31 \t		25 26 27 28 29 30  \n"
				+ "31  \n"
				+ "   October 2017\t\t	   November 2017\t	   December 2017 \n"
				+ "Mo Tu We Th Fr Sa Su\t	Mo Tu We Th Fr Sa Su\t	Mo Tu We Th Fr Sa Su \n"
				+ "                  1 \t 	      1  2  3  4  5 \t	            1  2  3   \n"
				+ "2  3  4  5  6  7  8 \t 	6  7  8  9  10 11 12\t 	4  5  6  7  8  9  10  \n"
				+ "9  10 11 12 13 14 15\t 	13 14 15 16 17 18 19\t 	11 12 13 14 15 16 17  \n"
				+ "16 17 18 19 20 21 22\t 	20 21 22 23 24 25 26\t 	18 19 20 21 22 23 24  \n"
				+ "23 24 25 26 27 28 29\t 	27 28 29 30 \t		25 26 27 28 29 30 31  \n"
				+ "30 31  \n";
		String actualResult = calApp.printCalForYearMondayFirst("cal -m 2017");
		assertEquals(expectedResult, actualResult);
	}

}
