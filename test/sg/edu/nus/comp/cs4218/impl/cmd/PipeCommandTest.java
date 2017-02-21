package sg.edu.nus.comp.cs4218.impl.cmd;

import static org.junit.Assert.*;

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
import sg.edu.nus.comp.cs4218.impl.app.SedApplication;

public class PipeCommandTest {
	
	private static final String[] FILE_NAMES = { "test1.txt", "test2.txt", "test3.txt" };
	private static final List<String> TEXTLIST1 = Arrays.asList(
			"The quick$%^^%% brown fox jumps over the lazy dog", 
			"Quick brown fox the jumps over the lazy dog",  
			"Quick brown fox jumps over the lazy dog))(~(!@@", 
			"@((#*#&&@@(#Quick brown fox jumps over lazy dog The",
			"thE Quick brown fox THE jumps over lazy dog the");
	private static final List<String> TEXTLIST2 = Arrays.asList(
			"lolollllolololllololol", 
			"lloolololololollllolollololoolllolololoo",
			"oooollololololollllolololllololol");
	private static final List<String> TEXTLIST3 = Arrays.asList(
			"`#*#*#*#*#*#*#`**#*#*#*`#-@!!`!!*`***`#*-@!!`!!*#-@!!`!!*-@!!`!!-@!!`!!*#", 
			"`#*#*#*#*#*#*#`**#*#*#*`#-@!!`!!*`***#*#*`#-@!!`!!*`***`#*-@!!`!!*#-@!!`!!*-@!!`!!-@!!`!!*#",  
			"`#*#*#*#*#-@!!`!!*#`**#*#*#*`#-@!!`!!*`**`*`#*-@!!`!!*#-@!!`!!*-@!!`!!-@!!`!!*#");

	private static ShellImpl shellImpl;
	private ByteArrayOutputStream outputStream;
	private String args;
	private String result;
	
	@Before
	public void setup() {
		shellImpl = new ShellImpl();
		
		try {
			Path file1 = Paths.get("test1.txt");
			Files.write(file1, TEXTLIST1, Charset.forName("UTF-8"));
			
			Path file2 = Paths.get("test2.txt");
			Files.write(file2, TEXTLIST2, Charset.forName("UTF-8"));
			
			Path file3 = Paths.get("test3.txt");
			Files.write(file3, TEXTLIST3, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPipeTwoCommandsNoArgs() {
		args = "";
		result = shellImpl.pipeTwoCommands(args);
		assertEquals("", result);
	}
	
	@Test
	public void testPipeTwoCommandsSuccess() {
		args = "cat test1.txt | sed s/dog/PINK/";
		result = shellImpl.pipeTwoCommands(args);
		assertEquals("The quick$%^^%% brown fox jumps over the lazy PINK" + System.getProperty("line.separator") 
			+ "Quick brown fox the jumps over the lazy PINK" + System.getProperty("line.separator")  
			+ "Quick brown fox jumps over the lazy PINK))(~(!@@" + System.getProperty("line.separator")
			+ "@((#*#&&@@(#Quick brown fox jumps over lazy PINK The" + System.getProperty("line.separator")
			+ "thE Quick brown fox THE jumps over lazy PINK the" + System.getProperty("line.separator"), result);
	}
	
	@Test
	public void testPipeTwoCommandsExceptionInFirstCommand() {
		args = "cat test.txt | cat test1.txt";
		result = shellImpl.pipeTwoCommands(args);
		assertEquals("cat: Exception Caught", result);
	}
	
	@Test
	public void testPipeTwoCommandsExceptionInSecondCommand() {
		args = "cat test1.txt | sed s/lol";
		result = shellImpl.pipeTwoCommands(args);
		assertEquals("sed: Invalid Replacement Rule: Insufficient arguments", result);
	}
	
	@Test
	public void testPipeMultipleCommandsNoArgs() { 
		args = "";
		result = shellImpl.pipeMultipleCommands(args);
		assertEquals("", result);
	}
	
	@Test
	public void testPipeMultipleCommandsHeadTailSuccess() {
		args = "cat test1.txt | head -n 3 | tail -n 1";
		result = shellImpl.pipeMultipleCommands(args);
		assertEquals("Quick brown fox jumps over the lazy dog))(~(!@@" + System.getProperty("line.separator"), result);
	}
	
	@Test
	public void testPipeMultipleCommandsHeadSedSuccess() {
		args = "cat test1.txt | head -n3 -n 2 | sed s/dog/PINK/";
		result = shellImpl.pipeMultipleCommands(args);
		assertEquals("The quick$%^^%% brown fox jumps over the lazy PINK" + System.getProperty("line.separator") 
		+ "Quick brown fox the jumps over the lazy PINK" + System.getProperty("line.separator"), result);
	}
	
	@Test
	public void testPipeComplexQuotesSuccess() {
		args = "echo 'This is space:`echo \" \"`.' | sed s/s/AAA/g";
		result = shellImpl.pipeMultipleCommands(args);
		assertEquals("ThiAAA iAAA AAApace:`echo \" \"`." + System.getProperty("line.separator"), result);
	}
	
	@Test
	public void testPipePipesInQuotesSuccess() {
		args = "echo 'This is space:`echo \" ||||\"`.' | sed s/s/AAA/g";
		result = shellImpl.pipeMultipleCommands(args);
		assertEquals("ThiAAA iAAA AAApace:`echo \" ||||\"`." + System.getProperty("line.separator"), result);
	}
	
	@Test
	public void testPipeMultipleCommandsWithException() {
		args = "cat test1.txt | sed s/brown/RED/ | sed s/brown/RED";
		result = shellImpl.pipeWithException(args);
		assertEquals("sed: Invalid Replacement Rule: Missing separator at the end", result);
	}
	
	@Test
	public void testPipeInvalidFirstCharSyntax() {
		args = "| cat test1.txt | sed s/brown/PINK/";
		result = shellImpl.pipeWithException(args);
		assertEquals("shell: Invalid pipe operators", result);
	}
	
	@Test
	public void testPipeInvalidLastCharSyntax() {
		args = "cat test1.txt | sed s/brown/PINK/ |";
		result = shellImpl.pipeWithException(args);
		assertEquals("shell: Invalid pipe operators", result);
	}
	
	@Test
	public void testPipeInvalidEmptyPipeCommandsSyntax() {
		args = "cat test1.txt | | sed s/brown/PINK/";
		result = shellImpl.pipeWithException(args);
		assertEquals("shell: : Invalid app.", result);
	}
	
	@After
	public void tearDown() {
		shellImpl = null;
		
		for (int i = 0; i != FILE_NAMES.length; i++) {
			File file = new File(FILE_NAMES[i]);
			file.delete();
		}
	}
}
