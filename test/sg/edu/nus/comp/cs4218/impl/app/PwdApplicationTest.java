package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.PwdException;

public class PwdApplicationTest {
	
	private PwdApplication pwdApp;
	
	@Before
	public void setUp() {
		pwdApp = new PwdApplication();
	}
	
	@Test(expected = PwdException.class)
	public void testNullArguments() throws PwdException {
		pwdApp.run(null, null, System.out);
	}
	
	@Test(expected = PwdException.class)
	public void testIllegalLengthArguments() throws PwdException {
		String[] args = {"pwd", "-v"};
		pwdApp.run(args, null, System.out);
	}
	
	@Test
	public void testPrintCurrentWorkingPath() throws PwdException {
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();
		String[] args = {"pwd"};
		pwdApp.run(args, null, stdout);
		assertEquals(Environment.currentDirectory, stdout.toString().trim());
	}

}
