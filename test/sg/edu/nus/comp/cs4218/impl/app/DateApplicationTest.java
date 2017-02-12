package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.DateException;

public class DateApplicationTest {
	
	private DateApplication dateApplication;
	ByteArrayInputStream inputStream;
	ByteArrayOutputStream outputStream;

	@Before
	public void setUp() {
		dateApplication = new DateApplication();
		inputStream = new ByteArrayInputStream(new byte[1]);
		outputStream = new ByteArrayOutputStream();
	}
	
	@Test(expected = DateException.class)
	public void testPrintNullParameter() throws DateException{
		dateApplication.run(null, inputStream, outputStream);
	}
	
	@Test(expected = DateException.class)
	public void testPrintIllegalOptions() throws DateException{
		String[] args = {"date", "-l"};
		dateApplication.run(args, inputStream, outputStream);
	}

	@Test
	public void testPrintDate() throws DateException {
		String expectedResult = dateApplication.printCurrentDate("date");
		
		Calendar now = Calendar.getInstance();
		String actualResult = now.getTime().toString();
		assertEquals(expectedResult, actualResult);
	}

	@After
	public void tearDown() {
		dateApplication = null;
	}
}
