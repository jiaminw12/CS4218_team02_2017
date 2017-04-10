package sg.edu.nus.comp.cs4218.ef1;

import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.GrepException;
import sg.edu.nus.comp.cs4218.impl.app.GrepApplication;

public class GrepApplicationTest {

	private static GrepApplication grep;

	@BeforeClass
	public static void setup() {
		grep = new GrepApplication();
	}

	@AfterClass
	public static void tearDown() {
		grep = null;
	}

	@Test(expected = GrepException.class)
	public void testRunNullStdin() throws GrepException, IOException {
		String pattern = "some";
		String[] args = { "grep", pattern };
		grep.run(args, null, System.out);
	}
}
