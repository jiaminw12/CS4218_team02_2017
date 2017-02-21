package sg.edu.nus.comp.cs4218.impl.app;

import org.junit.runners.JUnit4;
//@RunWith(JUnit4.class)

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
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

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.CatException;
import sg.edu.nus.comp.cs4218.exception.SedException;
import sg.edu.nus.comp.cs4218.impl.app.CatApplication;


public class CatApplicationTest {
	
	private static final String TEXT = "The quick brown fox&@#**#HDN(*#&&#*#*# jumps over((@*@&*#)_$ the(***&& lazy @@dog#*#*#))(*&^^";
	private static final String[] FILE_NAMES = { "test1.txt", "test2.txt", "test3.txt" };
	private static final List<String> TEXT1 = Arrays.asList(
			"The quick$%^^%% brown fox jumps over the lazy dog", 
			"Quick brown fox the jumps over the lazy dog");  
	private static final List<String> TEXT2 = Arrays.asList(
			"lolollllolololllololol", 
			"lloolololololollllolollololoolllolololoo",
			"oooollololololollllolololllololol");
	private static final List<String> TEXT3 = Arrays.asList(
			"`#*#*#*#*#*#*#`**#*#*#*`#-@!!`!!*`***`#*-@!!`!!*#-@!!`!!*-@!!`!!-@!!`!!*#", 
			"`#*#*#*#*#*#*#`**#*#*#*`#-@!!`!!*`***#*#*`#-@!!`!!*`***`#*-@!!`!!*#-@!!`!!*-@!!`!!-@!!`!!*#",  
			"`#*#*#*#*#-@!!`!!*#`**#*#*#*`#-@!!`!!*`**`*`#*-@!!`!!*#-@!!`!!*-@!!`!!-@!!`!!*#");
	
	public static String input;
	private CatApplication catApplication;
	private InputStream stdin;
	private OutputStream stdout;
	
	@Before
	public void setup() {
		catApplication = new CatApplication();
		
		try {
			Path file1 = Paths.get("test1.txt");
			Files.write(file1, TEXT1, Charset.forName("UTF-8"));
			
			Path file2 = Paths.get("test2.txt");
			Files.write(file2, TEXT2, Charset.forName("UTF-8"));
			
			Path file3 = Paths.get("test3.txt");
			Files.write(file3, TEXT3, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = CatException.class)
	public void testCatNullArgs() throws CatException {
		String[] args = null;
		stdin = null;
		stdout = null;
		catApplication.run(args, stdin, stdout);
	}
	
	@Test
	public void testCatInsufficientArgs() throws CatException {
		String[] args = new String[] { "cat" };
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		catApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = CatException.class)
	public void testExtraArgs() throws CatException {
		String[] args = new String[] { "cat", "s/hi/bye/", "cxintro02.txt","cybody40.txt" };
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		catApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = CatException.class)
	public void testEmptyArgs() throws CatException {
		String[] args = new String[] {};
		stdin = null;
		stdout = null;
		catApplication.run(args, stdin, stdout);
	}
	
	@Test
	public void testProperArgs() throws CatException {
		String[] args = new String[] {"cat","muttest.txt","slicing.txt"};
		stdin = new ByteArrayInputStream(TEXT.getBytes());;
		stdout = System.out;
		catApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = CatException.class)
	public void testNoStdout() throws CatException {
		String[] args = new String[] {"cat","cxintro02.txt","cybody40.txt"};
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		catApplication.run(args, stdin, stdout);
	} 
	
	@Test
	public void testOnlyArgsNull() throws CatException {
		String[] args = null;
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		catApplication.run(args, stdin, stdout);
	} 
	
	//to test for IOexception handling - need modification
	@Test(expected = IOException.class)
	public void testOnlyArgsIOException() throws CatException, IOException {
		String[] args = null;
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		catApplication.run(args, stdin, stdout);
		throw new IOException("Test IOException");
	}

	
	@Test(expected = CatException.class)
	public void testCouldNoteWriteToOutputCatException() throws CatException, IOException {
		String[] args = new String[] {"cat","sweet.txt","sweet.txt"};
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		catApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = CatException.class)
	public void testDirectoryCatException() throws CatException, IOException {
		String[] args = new String[] {"cat","/Users/varunica/Documents","/Users/varunica/Desktop"};
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		catApplication.run(args, stdin, stdout);
	}
	
	@After
	public void tearDown() {
		catApplication = null;
		
		for (int i = 0; i != FILE_NAMES.length; i++) {
			File file = new File(FILE_NAMES[i]);
			file.delete();
		}
	}

}
