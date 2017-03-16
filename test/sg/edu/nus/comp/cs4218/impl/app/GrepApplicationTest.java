package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.GrepException;
import sg.edu.nus.comp.cs4218.impl.app.GrepApplication;

public class GrepApplicationTest {
	private static final char FILE_SEPARATOR = File.separatorChar;
	private static final String REGEXMULTIOUT = "Hello Hello\nABC Hello\nello milo";
	private static final String NOMATCHFILE = "Pattern Not Found In File!";
	private static final String REGEXPATTERNOUT = "Hello Hello\nABC Hello";
	private static final String REGEXPATTERN = ".*ell";
	private static final String ABCSINGLEFILEOUT = "ABC Hello\nABCDEFGHI";
	private static final String ABCPATTERN = "ABC";
	private static final String HIEPATTERN = "hie";
	private static final String NOMATCHSTDIN = "Pattern Not Found In Stdin!";
	private GrepApplication grepApp;
	private String[] args;
	private InputStream stdin;
	private String fileName;
	private String fileName2;
	private String fileName3;
	private String directory;
	private String invalidFile;
	private ByteArrayOutputStream baos;
	PrintStream print;

	@Before
	public void setUp() throws FileNotFoundException {
		grepApp = new GrepApplication();
		directory = String.format("folder%sGrepAndSortFiles%s", FILE_SEPARATOR,
				FILE_SEPARATOR);
		stdin = new FileInputStream(directory + "greptestdoc.txt");
		fileName = directory + "greptestdoc.txt";
		fileName2 = directory + "greptestdoc2.txt";
		fileName3 = directory + "testdoc.txt";
		invalidFile = directory + "abjkcsnakjc.txt";
		baos = new ByteArrayOutputStream();
		print = new PrintStream(baos);
		System.setOut(print);
	}

	@Test
	public void grepStdInNoMatchesFromRun() throws GrepException {
		args = new String[2];
		args[0] = "grep";
		args[1] = HIEPATTERN;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(NOMATCHSTDIN + System.lineSeparator(), baos.toString());
	}

	@Test
	public void grepStdInNoMatches() throws GrepException {
		args = new String[2];
		args[0] = "grep";
		args[1] = HIEPATTERN;
		assertEquals(NOMATCHSTDIN, grepApp.grepFromStdin(args[0], stdin));
	}

	@Test
	public void grepStdInMatchesFromRun() throws GrepException {
		args = new String[2];
		args[0] = "grep";
		args[1] = ABCPATTERN;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(ABCSINGLEFILEOUT + System.lineSeparator(),
				baos.toString());
	}

	@Test
	public void grepStdInMatches() throws GrepException {
		args = new String[2];
		args[0] = "grep";
		args[1] = ABCPATTERN;
		ByteArrayInputStream in = new ByteArrayInputStream(
				ABCSINGLEFILEOUT.getBytes());
		assertEquals(ABCSINGLEFILEOUT, grepApp.grepFromStdin(args[1], in));
	}

	@Test
	public void grepStdInRegexMatchesFromRun() throws GrepException {
		args = new String[2];
		args[0] = "grep";
		args[1] = REGEXPATTERN;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(REGEXPATTERNOUT + System.lineSeparator(), baos.toString());
	}

	@Test
	public void grepStdInRegexMatches() throws GrepException {
		args = new String[1];
		args[0] = REGEXPATTERN;
		assertEquals(REGEXPATTERNOUT, grepApp.grepFromStdin(args[0], stdin));
	}

	@Test
	public void grepSingleFileNoMatchesFromRun() throws GrepException {
		args = new String[3];
		args[0] = "grep";
		args[1] = HIEPATTERN;
		args[2] = fileName;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(NOMATCHFILE + System.lineSeparator(), baos.toString());
	}

	@Test
	public void grepSingleFileMultipleMatchesInALineFromRun()
			throws GrepException {
		args = new String[3];
		args[0] = "grep";
		args[1] = "h";
		args[2] = fileName3;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(
				"Boisterous he on understood attachment as entreaties ye devonshire."
						+ System.lineSeparator()
						+ "Extremely ham any his departure for contained curiosity defective."
						+ System.lineSeparator()
						+ "Way now instrument had eat diminution melancholy expression sentiments stimulated."
						+ System.lineSeparator()
						+ "Mrs interested now his affronting inquietude contrasted cultivated."
						+ System.lineSeparator()
						+ "Lasting showing expense greater on colonel no."
						+ System.lineSeparator()
						+ "Prepared do an dissuade be so whatever steepest."
						+ System.lineSeparator()
						+ "Yet her beyond looked either day wished nay."
						+ System.lineSeparator()
						+ "Now curiosity you explained immediate why behaviour."
						+ System.lineSeparator()
						+ "An dispatched impossible of of melancholy favourable."
						+ System.lineSeparator()
						+ "Our quiet not heart along scale sense timed."
						+ System.lineSeparator()
						+ "Consider may dwelling old him her surprise finished families graceful."
						+ System.lineSeparator()
						+ "Is at purse tried jokes china ready decay an."
						+ System.lineSeparator()
						+ "Small its shy way had woody downs power."
						+ System.lineSeparator()
						+ "Procured shutters mr it feelings."
						+ System.lineSeparator()
						+ "To or three offer house begin taken am at."
						+ System.lineSeparator()
						+ "As dissuade cheerful overcame so of friendly he indulged unpacked."
						+ System.lineSeparator()
						+ "An seeing feebly stairs am branch income me unable."
						+ System.lineSeparator()
						+ "Celebrated contrasted discretion him sympathize her collecting occasional."
						+ System.lineSeparator()
						+ "Do answered bachelor occasion in of offended no concerns."
						+ System.lineSeparator()
						+ "Supply worthy warmth branch of no ye."
						+ System.lineSeparator() + "Though wished merits or be."
						+ System.lineSeparator()
						+ "Alone visit use these smart rooms ham."
						+ System.lineSeparator()
						+ "Course sir people worthy horses add entire suffer."
						+ System.lineSeparator()
						+ "Strictly numerous outlived kindness whatever on we no on addition."
						+ System.lineSeparator()
						+ "Are sentiments apartments decisively the especially alteration."
						+ System.lineSeparator()
						+ "Thrown shy denote ten ladies though ask saw."
						+ System.lineSeparator()
						+ "Or by to he going think order event music."
						+ System.lineSeparator()
						+ "Led income months itself and houses you. After nor you leave might share court balls."
						+ System.lineSeparator(),
				baos.toString());
	}

	@Test
	public void grepSingleFileNoMatches() throws GrepException {
		args = new String[2];
		args[0] = HIEPATTERN;
		args[1] = fileName;
		assertEquals(NOMATCHFILE,
				grepApp.grepFromOneFile(args[0] + " " + args[1]));
	}

	@Test
	public void grepSingleFileMatchesFromRun() throws GrepException {
		args = new String[3];
		args[0] = "grep";
		args[1] = ABCPATTERN;
		args[2] = fileName;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(ABCSINGLEFILEOUT + System.lineSeparator(),
				baos.toString());
	}

	@Test
	public void grepSingleFileMatches() throws GrepException {
		args = new String[2];
		args[0] = ABCPATTERN;
		args[1] = fileName;
		assertEquals(ABCSINGLEFILEOUT,
				grepApp.grepFromOneFile(args[0] + " " + args[1]));
	}

	@Test
	public void grepSingleFileRegexMatchesFromRun() throws GrepException {
		args = new String[3];
		args[0] = "grep";
		args[1] = REGEXPATTERN;
		args[2] = fileName;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(REGEXPATTERNOUT + System.lineSeparator(), baos.toString());
	}

	@Test
	public void grepSingleFileRegexMatches() throws GrepException {
		args = new String[2];
		args[0] = REGEXPATTERN;
		args[1] = fileName;
		assertEquals(REGEXPATTERNOUT,
				grepApp.grepFromOneFile(args[0] + " " + args[1]));
	}

	@Test
	public void grepMultipleFileNoMatchesFromRun() throws GrepException {
		args = new String[4];
		args[0] = "grep";
		args[1] = HIEPATTERN;
		args[2] = fileName;
		args[3] = fileName2;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(NOMATCHFILE + System.lineSeparator(), baos.toString());
	}

	@Test
	public void grepMultipleFileNoMatches() throws GrepException {
		args = new String[3];
		args[0] = HIEPATTERN;
		args[1] = fileName;
		args[2] = fileName2;
		assertEquals(NOMATCHFILE, grepApp.grepFromMultipleFiles(
				args[0] + " " + args[1] + " " + args[2]));
	}

	@Test
	public void grepMultipleFileMatchesFromRun() throws GrepException {
		args = new String[4];
		args[0] = "DEF";
		args[1] = "DEF";
		args[2] = fileName;
		args[3] = fileName2;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals("ABCDEFGHI" + System.lineSeparator() + "DEF"
				+ System.lineSeparator(), baos.toString());
	}

	@Test
	public void grepMultipleFileMatches() throws GrepException {
		args = new String[3];
		args[0] = "DEF";
		args[1] = fileName;
		args[2] = fileName2;
		assertEquals("ABCDEFGHI" + System.lineSeparator() + "DEF",
				grepApp.grepFromMultipleFiles(
						args[0] + " " + args[1] + " " + args[2]));
	}

	@Test
	public void grepMultipleFileInvalidMatches() throws GrepException {
		args = new String[4];
		args[0] = "DEF";
		args[1] = invalidFile;
		args[2] = fileName2;
		args[3] = fileName;
		assertEquals("DEF" + System.lineSeparator() + "ABCDEFGHI",
				grepApp.grepFromMultipleFiles(args[0] + " " + args[1] + " "
						+ args[2] + " " + args[3]));
	}

	@Test
	public void grepMultipleFileRegexMatchesFromRun() throws GrepException {
		args = new String[4];
		args[0] = "grep";
		args[1] = REGEXPATTERN;
		args[2] = fileName;
		args[3] = fileName2;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(REGEXMULTIOUT + System.lineSeparator(), baos.toString());
	}

	@Test
	public void grepMultipleFileRegexMatches() throws GrepException {
		args = new String[3];
		args[0] = REGEXPATTERN;
		args[1] = fileName;
		args[2] = fileName2;
		assertEquals(REGEXMULTIOUT, grepApp.grepFromMultipleFiles(
				args[0] + " " + args[1] + " " + args[2]));
	}

	@Test
	public void grepUnorderedInput1FromRun() throws GrepException {
		args = new String[4];
		args[0] = "grep";
		args[1] = fileName;
		args[2] = REGEXPATTERN;
		args[3] = fileName2;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(REGEXMULTIOUT + System.lineSeparator(), baos.toString());
	}

	@Test
	public void grepUnorderedInput2FromRun() throws GrepException {
		args = new String[4];
		args[0] = "grep";
		args[1] = fileName;
		args[2] = fileName2;
		args[3] = REGEXPATTERN;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
		assertEquals(REGEXMULTIOUT + System.lineSeparator(), baos.toString());
	}

	@Test(expected = GrepException.class)
	public void grepNoPatternFileFromRun() throws GrepException {
		args = new String[2];
		args[0] = "grep";
		args[1] = fileName;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
	}

	@Test(expected = GrepException.class)
	public void grepNoPatternMultipleFileFromRun() throws GrepException {
		args = new String[3];
		args[0] = "grep";
		args[1] = fileName;
		args[2] = fileName2;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
	}

	@Test(expected = GrepException.class)
	public void grepNoPatternStdInFromRun() throws GrepException {
		args = new String[1];
		args[0] = "grep";
		grepApp.run(args, stdin, System.out);
		System.out.flush();
	}

	@Test(expected = GrepException.class)
	public void grepMultiplePatternFromRun() throws GrepException {
		args = new String[4];
		args[0] = "grep";
		args[1] = REGEXPATTERN;
		args[2] = ABCPATTERN;
		args[3] = fileName;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
	}
	
	@Test(expected = GrepException.class)
	public void invalidRegexFileFromRun() throws GrepException {
		args = new String[4];
		args[0] = "grep";
		args[1] = "[";
		args[2] = fileName2;
		args[3] = fileName;
		grepApp.run(args, stdin, System.out);
		System.out.flush();
	}
	
	@Test
	public void invalidPatternInFile() throws GrepException {
		args = new String[3];
		args[0] = "grep";
		args[1] = "[";
		args[2] = fileName2;
		assertEquals("grep: No pattern detected", grepApp.grepInvalidPatternInFile(args[0] + " " + args[1] + " " + args[2]));
	}
	
	@Test(expected = GrepException.class)
	public void invalidPatternInStdin() throws GrepException {
		args = new String[2];
		args[0] = "grep";
		args[1] = "[";
		ByteArrayInputStream in = new ByteArrayInputStream(fileName.getBytes());
		grepApp.run(args, in, System.out);
		System.out.flush();
	}

}
