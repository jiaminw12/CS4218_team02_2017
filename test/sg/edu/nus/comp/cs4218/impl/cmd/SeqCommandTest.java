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

import sg.edu.nus.comp.cs4218.exception.SedException;
import sg.edu.nus.comp.cs4218.impl.ShellImpl;
import sg.edu.nus.comp.cs4218.impl.cmd.SeqCommand;

public class SeqCommandTest {
	
	OutputStream stdout;
	InputStream stdin;
	ByteArrayOutputStream outputStream = null;
	String cmdline = "cat cxintro02.txt cybody40.txt ; cat cxintro02.txt cybody40.txt";
	
	
	@Before
	public void setup(){
		ShellImpl shellimpl = new ShellImpl();
		SeqCommand seqcmd = new SeqCommand(cmdline);
	}
	
	@Test
	public void testEvaluate(){
		//TODO
		//String [] args = seqcmd.evaluate(stdin,stdout);
	}
	
	
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
