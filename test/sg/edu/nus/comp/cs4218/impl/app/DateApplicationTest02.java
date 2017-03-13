package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.DateException;

public class DateApplicationTest02 {

	private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
	private static DateApplication app;
	static ByteArrayOutputStream stdout;
	static PrintStream print;

	@BeforeClass
	public static void init() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		app = new DateApplication();
		stdout = new ByteArrayOutputStream();
		print = new PrintStream(stdout);
	}

	@Test
	public void testDateWithNullStdin() throws DateException {
		String message = "date - test with null stdin";
		String[] args = {"cal"};
		app.run(args, null, stdout);
		System.out.flush();
		Calendar cal = Calendar.getInstance();
		assertEquals(message, DEFAULT_DATE_FORMAT.format(cal.getTime()), stdout.toString().trim());
	}

	@Test(expected = DateException.class)
	public void testDateWithNullStdout() throws DateException {
		app.run(null, System.in, null);
	}

	@Test(expected = DateException.class)
	public void testDateWithNullStdinAndStdout() throws DateException {
		app.run(null, null, null);
	}

	@Test
	public void testDateWithCurrentTimeDateWithNullStdin() throws DateException {
		String message = "date - test with current time and date with null stdin";
		String[] args = {"cal"};
		app.run(args, null, stdout);
		System.out.flush();
		Calendar cal = Calendar.getInstance();
		assertEquals(message, DEFAULT_DATE_FORMAT.format(cal.getTime()), stdout.toString().trim());
	}

	@Test
	public void testDateWithCurrentTimeDate() throws DateException {
		String message = "date - test with current time and date";
		String[] args = {"cal"};
		app.run(args, null, stdout);
		System.out.flush();
		Calendar cal = Calendar.getInstance();
		assertEquals(message, DEFAULT_DATE_FORMAT.format(cal.getTime()), stdout.toString().trim());
	}

	@Test
	public void testDateWithWaitOneSecond() throws DateException, InterruptedException {
		String message = "date - test with current time and wait seconds";
		String[] args = {"cal"};
		app.run(args, System.in, stdout);
		System.out.flush();
		Thread.sleep(1000);
		Calendar cal = Calendar.getInstance();
		assertNotSame(message, DEFAULT_DATE_FORMAT.format(cal.getTime()), stdout.toString().trim());
	}

}
