package sg.edu.nus.comp.cs4218.cmd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.cmd.CallCommand;

public class CallCommandTest {
	
	@BeforeClass
	public static void setup() {
		
	}
	
	@Before
	public void setupBeforeTest() {
		
	}
	
	@Test(expected = ShellException.class)
	public void testCallCommandNull() throws AbstractApplicationException, ShellException {
		String input = null;
		CallCommand call = new CallCommand(input);
		call.parse();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(new byte[0]);
		call.evaluate(in, out);
	}
}
