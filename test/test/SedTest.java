package test;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.*;

import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.SedException;
import sg.edu.nus.comp.cs4218.impl.app.SedApplication;

public class SedTest {

	public static final String TEXT = "The quick brown fox jumps over the lazy dog";
	private static final String[] FILE_NAMES = { "test1.txt", "test2.txt", "test3.txt", "test4.txt", "test5.txt" };
	private static final List<String> lines = Arrays.asList("We learn from our mistakes,", "from the wrong turns we take,",  
			"from the fake friends we make,", "and from the times we almost break.");
	
	SedApplication sedApplication;
	String[] args;
	InputStream stdin;
	OutputStream stdout;

	@Before
	public void setup() {
		sedApplication = new SedApplication();
		
		for (int i = 0; i != FILE_NAMES.length; i++) {
			try {
				Path file = Paths.get(FILE_NAMES[i]);
				Files.write(file, lines, Charset.forName("UTF-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test(expected = SedException.class)
	public void testSedNullArgs() throws SedException {
		args = null;
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		sedApplication.run(args, stdin, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedNullStdin() throws SedException {
		args = new String[1];
		stdin = null;
		stdout = System.out;
		sedApplication.run(args, stdin, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedNullStdout() throws SedException {
		args = new String[1];
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = null;
		sedApplication.run(args, stdin, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedEmptyArgs() throws SedException {
		args = new String[0];
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		sedApplication.run(args, stdin, stdout);
	}

	@Test(expected = SedException.class)
	public void testSedOneArgs() throws SedException {
		args = new String[] { "sed" };
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testSedFourArgs() throws SedException {
		args = new String[] { "sed", "s/the/HAHAHA", "test1.txt", "test2.txt" };
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		sedApplication.run(args, stdin, stdout);
	}
	
//	@Test(expected = SedException.class) TODO
//	public void testSedStdinEmptyInput() throws SedException {
//		String args = "sed arg1";
//		sedApplication.replaceSubstringWithInvalidReplacement(args);
//	}
	
	@Test(expected = SedException.class)
	public void testSedStdinInsufficientRule() throws SedException {
		String args = "sed s//";
		sedApplication.replaceSubstringWithInvalidRule(args);
	}
	
	@Test(expected = SedException.class)
	public void testSedStdinMissingS() throws SedException {
		String args = "sed /the/to";
		sedApplication.replaceSubstringWithInvalidRule(args);
	}
	
	@Test(expected = SedException.class)
	public void testSedStdinWithoutS() throws SedException {
		String args = "sed p/the/to";
		sedApplication.replaceSubstringWithInvalidRule(args);
	}
	
	@Test(expected = SedException.class)
	public void testSedStdinLongS() throws SedException {
		String args = "sed sss/the/to";
		sedApplication.replaceSubstringWithInvalidRule(args);
	}
	
	@Test(expected = SedException.class)
	public void testSedStdinRegexWithSeparator() throws SedException {
		String args = "sed s/t/he/to";
		sedApplication.replaceSubstringWithInvalidRule(args);
	}
	
	@Test(expected = SedException.class)
	public void testSedStdinReplacementWithSeparator() throws SedException {
		String args = "sed s/the/t/o";
		sedApplication.replaceSubstringWithInvalidRule(args);
	}
	
	@Test(expected = SedException.class)
	public void testSedStdinReplacementExtraSeparator() throws SedException {
		String args = "sed s/the/to/";
		sedApplication.replaceSubstringWithInvalidRule(args);
	}
	
	@Test(expected = SedException.class)
	public void testSedStdinEmptyRegex() throws SedException {
		String args = "sed s/the//g";
		sedApplication.replaceSubstringWithInvalidRule(args);
	}
	
	@Test(expected = SedException.class)
	public void testSedStdinEmptyReplacement() throws SedException {
		String args = "sed s//to/g";
		sedApplication.replaceSubstringWithInvalidRule(args);
	}

	@Test(expected = SedException.class)
	public void testSedStdinEmptyRegexReplacement() throws SedException {
		String args = "sed s///g";
		sedApplication.replaceSubstringWithInvalidRule(args);
	}
	
	@Test(expected = SedException.class)
	public void testSedStdinSpaceRegexReplacement() throws SedException {
		String args = "sed s/ / /g";
		sedApplication.replaceSubstringWithInvalidRule(args);
	}
	
	@Test(expected = SedException.class)
	public void testSedStdinMissingG() throws SedException {
		String args = "sed s/the/to/";
		sedApplication.replaceSubstringWithInvalidRule(args);
	}
	
	@Test(expected = SedException.class)
	public void testSedStdinWithoutG() throws SedException {
		String args = "sed s/the/to/t";
		sedApplication.replaceSubstringWithInvalidRule(args);
	}
	
	@Test(expected = SedException.class)
	public void testSedStdinLongG() throws SedException {
		String args = "sed s/the/to/ggg";
		sedApplication.replaceSubstringWithInvalidRule(args);
	}
	
	@Test(expected = SedException.class)
	public void testSedStdinAllSubstringRegexWithSeparator() throws SedException {
		String args = "sed s/t/he/to/g";
		sedApplication.replaceSubstringWithInvalidRegex(args);
	}
	
	@Test(expected = SedException.class)
	public void testSedStdinAllSubstringReplacementWithSeparator() throws SedException {
		String args = "sed s/the/t/o/g";
		sedApplication.replaceSubstringWithInvalidReplacement(args);
	}
	
	@Test(expected = SedException.class)
	public void testSedStdinAllSubstringReplacementExtraSeparator() throws SedException {
		String args = "sed /s/the/to/g";
		sedApplication.replaceSubstringWithInvalidRule(args);
	}

	@Test
	public void testSedReadFromStdinReplaceFirstRule() throws SedException {
		String args = "sed " + "s/the/HAHAHA";
		String actual = sedApplication.replaceFirstSubStringFromStdin(args);
		assertEquals("HAHAHA quick brown fox jumps over the lazy dog", actual);
	}

	@Test
	public void testSedReadFromStdinReplaceAllRule() throws SedException {
		String args = "sed " + "s/the/HAHAHA/g";
		String actual = sedApplication.replaceAllSubstringsInStdin(args);
		assertEquals("HAHAHA quick brown fox jumps over HAHAHA lazy dog", actual);
	}

	@After
	public void tearDown() {
		sedApplication = null;
		
//		for (int i = 0; i != FILE_NAMES.length; i++) {
//			File file = new File(FILE_NAMES[i]);
//			file.delete();
//		}
	}

}
