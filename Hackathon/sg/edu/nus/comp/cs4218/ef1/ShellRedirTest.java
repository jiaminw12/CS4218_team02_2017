package sg.edu.nus.comp.cs4218.ef1;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;

public class ShellRedirTest {

	private static ShellImpl shell;
	static ByteArrayOutputStream out;

	@BeforeClass
	public static void setup() {
		shell = new ShellImpl();
		out = new ByteArrayOutputStream();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		File file = new File("out");
		if (file.exists()) {
			file.delete();
		}
	}


	@Test
	public void nullInputWithEcho()
			throws AbstractApplicationException, ShellException {
		String command = "echo < '' >out; cat out ";
		shell.parseAndEvaluate(command, out);
		assertEquals("" + System.lineSeparator() + "" + System.lineSeparator(),
				out.toString());

		File file = new File("out");
		if (file.exists()) {
			file.delete();
		}
	}

	@Test
	public void beforePipeNormalInput()
			throws AbstractApplicationException, ShellException, IOException {
		File file = new File("out");
		file.createNewFile();
		
		String command = "echo < out | cat";
		shell.parseAndEvaluate(command, out);
		assertEquals(System.lineSeparator(), out.toString());

		if (file.exists()) {
			file.delete();
		}
	}

}
