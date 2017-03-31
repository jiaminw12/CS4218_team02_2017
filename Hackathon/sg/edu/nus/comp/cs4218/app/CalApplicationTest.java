package sg.edu.nus.comp.cs4218.app;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.impl.app.CalApplication;
import sg.edu.nus.comp.cs4218.exception.CalException;

public class CalApplicationTest {

	private ByteArrayOutputStream out = new ByteArrayOutputStream();
	CalApplication cal;
	
	@Before
	public void setUp() {
		cal = new CalApplication();
		System.setOut(new PrintStream(out));
	}
	
	@Test(expected = CalException.class)
	public void testNegativeYearException() throws CalException {
		String[] arg = {"cal", "-m", "-1" };
		cal.run(arg, System.in, System.out);
	}
	
	@Test(expected = CalException.class)
	public void testInvalidArgumentsException() throws CalException {
		String[] arg = { "cal", "-mlw" };
		cal.run(arg, System.in, System.out);
	}

}