package sg.edu.nus.comp.cs4218.app;

import java.io.InputStream;
import java.io.OutputStream;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.DirectoryNotFoundException;
import sg.edu.nus.comp.cs4218.impl.app.CdApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CdApplicationTest {

	private InputStream input;
	private OutputStream output;
	private CdApplication cdApp;
	private static String revertDir = Environment.currentDirectory;

	@Before
	public void setUpBeforeTest() throws Exception {
		cdApp = new CdApplication();
	}

	@After
	public void tearDownAfterTest() {
		Environment.currentDirectory = revertDir;
	}

	@Test(expected = DirectoryNotFoundException.class)
	public void testCdDirectoryUnspecifiedException()
			throws DirectoryNotFoundException, AbstractApplicationException {
		String[] args = { "cd" };
		cdApp.run(args, input, output);
	}

}