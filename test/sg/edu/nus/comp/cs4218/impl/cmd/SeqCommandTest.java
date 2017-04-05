package sg.edu.nus.comp.cs4218.impl.cmd;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.SedException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.cmd.SeqCommand;

public class SeqCommandTest {

	OutputStream stdout;
	InputStream stdin;
	ByteArrayOutputStream outputStream = null;
	String cmdline = "cat farfaryaway.txt slicing.txt ; cat farfaraway.txt slicing.txt";
	String cmdline2 = "; echo hi ;";
	String cmdline3 = "echo hi ;";
	String cmdline4 = ";;";
	String cmdline5 = "\"echo 'Travel time Singapore -> Paris is 13h and 15`'\"";
	String cmdline6 = "\"echo 'Travel time Singapore -> Paris is 13h and 15`'\"; echo hi";
	String cmdline7 = " ; ";
	String cmdline8 = null;
	String cmdline9 = "cat farfaryaway.txt slicing.txt;";

	int count = 2;
	List<String> expectedArgsArray;

	SeqCommand seqcmd = new SeqCommand(cmdline);
	SeqCommand seqcmd2 = new SeqCommand(cmdline2);
	SeqCommand seqcmd3 = new SeqCommand(cmdline3);
	SeqCommand seqcmd4 = new SeqCommand(cmdline4);
	SeqCommand seqcmd5 = new SeqCommand(cmdline5);
	SeqCommand seqcmd6 = new SeqCommand(cmdline6);
	SeqCommand seqcmd7 = new SeqCommand(cmdline7);
	SeqCommand seqcmd8 = new SeqCommand(cmdline8);
	SeqCommand seqcmd9 = new SeqCommand(cmdline9);

	@Before
	public void setup() {

	}

	@Test(expected = ShellException.class)
	public void testExceptionCase1()
			throws AbstractApplicationException, ShellException {
		seqcmd2.parse();
	}

	@Test(expected = ShellException.class)
	public void testExceptionCase2()
			throws AbstractApplicationException, ShellException {
		seqcmd3.parse();
	}

	@Test(expected = ShellException.class)
	public void testExceptionCase3()
			throws AbstractApplicationException, ShellException {
		seqcmd4.parse();
	}

	@Test
	public void testWithOneSemicolon()
			throws AbstractApplicationException, ShellException {
		seqcmd.parse();
		expectedArgsArray = Arrays.asList("cat farfaryaway.txt slicing.txt",
				"cat farfaraway.txt slicing.txt");
		assertArrayEquals(expectedArgsArray.toArray(),
				seqcmd.getArgsArray().toArray());
	}

	@Test
	public void testWithQuotes()
			throws AbstractApplicationException, ShellException {
		seqcmd5.parse();
		expectedArgsArray = Arrays.asList(
				"\"echo 'Travel time Singapore -> Paris is 13h and 15`'\"");
		assertArrayEquals(expectedArgsArray.toArray(),
				seqcmd5.getArgsArray().toArray());
	}

	@Test
	public void testWithMultiCommandAndQuotes()
			throws AbstractApplicationException, ShellException {
		seqcmd6.parse();
		expectedArgsArray = Arrays.asList(
				"\"echo 'Travel time Singapore -> Paris is 13h and 15`'\"",
				"echo hi");
		assertArrayEquals(expectedArgsArray.toArray(),
				seqcmd6.getArgsArray().toArray());
	}

	@Test(expected = ShellException.class)
	public void testEmptyArgsException()
			throws AbstractApplicationException, ShellException {
		seqcmd7.parse();
	}

	@Test(expected = ShellException.class)
	public void testNullCmd()
			throws AbstractApplicationException, ShellException {
		seqcmd8.parse();
	}

	@Test(expected = ShellException.class)
	public void testWithOneSemicolonWithOneStatement()
			throws AbstractApplicationException, ShellException {
		seqcmd9.parse();
	}

}
