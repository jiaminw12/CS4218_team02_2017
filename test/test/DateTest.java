package test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.*;

import sg.edu.nus.comp.cs4218.impl.app.DateApplication;

public class DateTest {

	private DateApplication dateApplication;
	private static Calendar calendar1, calendar2;

	@Before
	public void setUp() {
		dateApplication = new DateApplication();
		//dateApplication.run(null, null, null);

		// GregorianCalendar(year, month, day, hours, minutes, seconds)
		// month - Jan = 0, Dec = 11
		calendar1 = new GregorianCalendar(2013, 1, 1, 13, 24, 56);
		calendar2 = new GregorianCalendar(2017, 2, 26, 0, 0, 5);
	}

	@Test
	public void testPrintDate() {
		String date1 = dateApplication
				.printCurrentDate("date");
		String date2 = dateApplication
				.printCurrentDate(calendar2.getTime().toString());

		assertEquals(date1, "Fri Feb 01 13:24:56 SGT 2013");
		assertEquals(date2, "Sun Mar 26 00:00:05 SGT 2017");
	}

	@After
	public void tearDown() {
		dateApplication = null;
	}

}
