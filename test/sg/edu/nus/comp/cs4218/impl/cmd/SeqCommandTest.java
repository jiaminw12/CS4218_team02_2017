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

import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.SedException;
import sg.edu.nus.comp.cs4218.exception.ShellException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.cmd.SeqCommand;

public class SeqCommandTest {
	
	OutputStream stdout;
	InputStream stdin;
	ByteArrayOutputStream outputStream = null;
	String cmdline = "cat cxintro02.txt cybody40.txt ; cat cxintro02.txt cybody40.txt";
	String cmdline2 = "; echo hi ;";
	String cmdline3 = "echo hi ;";
	String cmdline4 = ";;";
	int count = 2;
	
	SeqCommand seqcmd = new SeqCommand(cmdline);
	SeqCommand seqcmd2 = new SeqCommand(cmdline2);
	SeqCommand seqcmd3 = new SeqCommand(cmdline3);
	SeqCommand seqcmd4 = new SeqCommand(cmdline4);
	
	
	@Before
	public void setup(){
		ShellImpl shellimpl = new ShellImpl();
	}
	
	@Test
	public void testEvaluate() throws AbstractApplicationException, ShellException{
		seqcmd.evaluate(stdin,stdout);
		assertEquals(count, seqcmd.getArgsLength());	
	}
	
	@Test(expected = ShellException.class)
	public void testExceptionCase1() throws AbstractApplicationException, ShellException{
		seqcmd2.evaluate(stdin, stdout);
	}
	
	@Test(expected = ShellException.class)
	public void testExceptionCase2() throws AbstractApplicationException, ShellException{
		seqcmd3.evaluate(stdin, stdout);
	}
	
	@Test(expected = ShellException.class)
	public void testExceptionCase3() throws AbstractApplicationException, ShellException{
		seqcmd4.evaluate(stdin, stdout);
	}
	
	@Test(expected = ShellException.class)
	public void testErrorException() throws AbstractApplicationException, ShellException{
		seqcmd4.setErrorTrue(true);
		seqcmd4.evaluate(stdin, stdout);
	}
	

}
