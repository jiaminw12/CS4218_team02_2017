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

	private static final String TEXT = "The quick brown fox&@#**#HDN(*#&&#*#*# jumps over((@*@&*#)_$ the(***&& lazy @@dog#*#*#))(*&^^";
	private static final String[] FILE_NAMES = { "test1.txt", "test2.txt", "test3.txt" };
	private static final List<String> TEXT1 = Arrays.asList(
			"The quick$%^^%% brown fox jumps over the$$%#(#&##lazy dog", 
			"Quick brown fox the^%%$##*#*#*# jumps over#*#*#*# the lazy dog",  
			"Quick brown fox jumps)(@## over the lazy dog))(~(!@@", 
			"@((#*#&&@@(#Quick brown fox jumps over((#*#*# lazy dog The",
			"thE Quick brown fox THE)#(#*$*$$# jumps over lazy dog the");
	private static final List<String> TEXT2 = Arrays.asList(
			"lolollllolololllololol", 
			"lloolololololollllolollololoolllolololoo",
			"oooollololololollllolololllololol");
	private static final List<String> TEXT3 = Arrays.asList(
			"`#*#*#*#*#*#*#`**#*#*#*`#-@!!`!!*`***`#*-@!!`!!*#-@!!`!!*-@!!`!!-@!!`!!*#", 
			"`#*#*#*#*#*#*#`**#*#*#*`#-@!!`!!*`***#*#*`#-@!!`!!*`***`#*-@!!`!!*#-@!!`!!*-@!!`!!-@!!`!!*#",  
			"`#*#*#*#*#-@!!`!!*#`**#*#*#*`#-@!!`!!*`**`*`#*-@!!`!!*#-@!!`!!*-@!!`!!-@!!`!!*#");
	
	public static String input;
	private SedApplication sedApplication;
	private InputStream stdin;
	private OutputStream stdout;

	@Before
	public void setup() {
		sedApplication = new SedApplication();
		
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

	@Test(expected = SedException.class)
	public void testSedNullArgs() throws SedException {
		String[] args = null;
		stdin = null;
		stdout = null;
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testSedInsufficientArgs() throws SedException {
		String[] args = new String[] { "sed" };
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testExtraArgs() throws SedException {
		String[] args = new String[] { "sed", "s/hi/bye/", "test1.txt", "test2.txt" };
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleExtraSeparator() throws SedException {
		String[] args = new String[] { "sed", "s/hi//bye/" };
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleExtraSeparators() throws SedException {
		String[] args = new String[] { "sed", "s/hi///g" };
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleExtraSpace() throws SedException {
		String[] args = new String[] { "sed", "s/hi/ /bye/" };
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleInsufficientArgs() throws SedException {
		String[] args = new String[] { "sed", "s/g/" };
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleExtraArgs() throws SedException {
		String[] args = new String[] { "sed", "s/hi/test/bye/g" };
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleMissingSeparatorBehind() throws SedException {
		String[] args = new String[] { "sed", "s/hi/bye" };
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleS() throws SedException {
		String[] args = new String[] { "sed", "g/hi/bye/" };
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleG() throws SedException {
		String[] args = new String[] { "sed", "s/hi/bye/s" };
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleLongS() throws SedException {
		String[] args = new String[] { "sed", "sss/hi/bye/" };
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleLongG() throws SedException {
		String[] args = new String[] { "sed", "s/hi/bye/ggg" };
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleMissingSeparator() throws SedException {
		String[] args = new String[] { "sed", "s/hi/bye" };
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleRedundantSeparator() throws SedException {
		String[] args = new String[] { "sed", "s/hi/bye/g/" };
		stdin = new ByteArrayInputStream(TEXT.getBytes());
		stdout = System.out;
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test
	public void testReplaceFirstSubStringFromStdin() {
		String args = "sed s/the/to-her/";
		input = TEXT;
		String result = sedApplication.replaceFirstSubStringFromStdin(args);
		assertEquals("The quick brown fox&@#**#HDN(*#&&#*#*# jumps over((@*@&*#)_$ to-her(***&& lazy @@dog#*#*#))(*&^^", result);
	}
	
	@Test
	public void testReplaceFirstSubStringFromStdinSymbols() {
		String args = "sed s/#*#*#/REPLACE!/";
		input = TEXT;
		String result = sedApplication.replaceFirstSubStringFromStdin(args);
		assertEquals("The quick brown fox&@#**#HDN(*#&&REPLACE! jumps over((@*@&*#)_$ the(***&& lazy @@dog#*#*#))(*&^^", result);
	}
	
	@Test
	public void testReplaceFirstSubStringFromStdinRepeatedWords() {
		String args = "sed s/lol/~yea~/";
		input = "lololololololololololololloololo";
		String result = sedApplication.replaceFirstSubStringFromStdin(args);
		assertEquals("~yea~olololololololololololloololo", result);
	}
	
	@Test
	public void testReplaceFirstSubStringFromStdinRepeatedSymbols() {
		String args = "sed s/*#/`-@!!!!/";
		input = "#*#*#*#*#*#*#**#*#*#*#*#****#**#*#*#**#*#*#";
		String result = sedApplication.replaceFirstSubStringFromStdin(args);
		assertEquals("#`-@!!!!*#*#*#*#*#**#*#*#*#*#****#**#*#*#**#*#*#", result);
	}
	
	@Test
	public void testReplaceAllSubStringInStdin() {
		String args = "sed s/the/to-her/g";
		input = TEXT;
		String result = sedApplication.replaceAllSubstringsInStdin(args);
		assertEquals("The quick brown fox&@#**#HDN(*#&&#*#*# jumps over((@*@&*#)_$ to-her(***&& lazy @@dog#*#*#))(*&^^", result);
	}
	
	@Test
	public void testReplaceAllSubStringInStdinSymbols() {
		String args = "sed s/#*#*#/REPLACE!/g";
		input = TEXT;
		String result = sedApplication.replaceAllSubstringsInStdin(args);
		assertEquals("The quick brown fox&@#**#HDN(*#&&REPLACE! jumps over((@*@&*#)_$ the(***&& lazy @@dogREPLACE!))(*&^^", result);
	}
	
	@Test
	public void testReplaceAllSubStringInStdinRepeatedWords() {
		String args = "sed s/lol/~yea~/";
		input = "lololololololololololololloololo";
		String result = sedApplication.replaceAllSubstringsInStdin(args);
		assertEquals("~yea~o~yea~o~yea~o~yea~o~yea~o~yea~olloo~yea~o", result);
	}
	
	@Test
	public void testReplaceAllSubStringInStdinRepeatedSymbols() {
		String args = "sed s/*#/`-@!!!!/";
		input = "#*#*#*#*#*#*#**#*#*#*#*#****#**#*#*#**#*#*#";
		String result = sedApplication.replaceAllSubstringsInStdin(args);
		assertEquals("#`-@!!!!`-@!!!!`-@!!!!`-@!!!!`-@!!!!`-@!!!!*`-@!!!!`-@!!!!`-@!!!!`-@!!!!"
				+ "`-@!!!!***`-@!!!!*`-@!!!!`-@!!!!`-@!!!!*`-@!!!!`-@!!!!`-@!!!!", result);
	}
	
	@Test
	public void testReplaceFirstSubStringInFile() {
		String args = "sed s/the/to-her/ test1.txt";
		input = TEXT;
		String result = sedApplication.replaceFirstSubStringInFile(args);
		assertEquals("The quick$%^^%% brown fox jumps over to-her$$%#(#&##lazy dog" + System.getProperty("line.separator") 
				+ "Quick brown fox to-her^%%$##*#*#*# jumps over#*#*#*# the lazy dog" + System.getProperty("line.separator")   
				+ "Quick brown fox jumps)(@## over to-her lazy dog))(~(!@@" + System.getProperty("line.separator")  
				+ "@((#*#&&@@(#Quick brown fox jumps over((#*#*# lazy dog The" + System.getProperty("line.separator") 
				+ "thE Quick brown fox THE)#(#*$*$$# jumps over lazy dog to-her", result);
	}
	
	@Test
	public void testReplaceFirstSubStringInFileSymbols() {
		String args = "sed s/#*#*#/REPLACE!/ test1.txt";
		input = TEXT;
		String result = sedApplication.replaceFirstSubStringInFile(args);
		assertEquals("The quick$%^^%% brown fox jumps over the$$%#(#&##lazy dog" + System.getProperty("line.separator") 
				+ "Quick brown fox the^%%$#REPLACE!*# jumps over#*#*#*# the lazy dog" + System.getProperty("line.separator")   
				+ "Quick brown fox jumps)(@## over the lazy dog))(~(!@@" + System.getProperty("line.separator")  
				+ "@((#*#&&@@(#Quick brown fox jumps over((REPLACE! lazy dog The" + System.getProperty("line.separator") 
				+ "thE Quick brown fox THE)#(#*$*$$# jumps over lazy dog the", result);
	}
	
	@Test
	public void testReplaceFirstSubStringInFileRepeatedWords() {
		String args = "sed s/lol/~yea~/ test2.txt";
		String result = sedApplication.replaceFirstSubStringInFile(args);
		assertEquals("~yea~ollllolololllololol" + System.getProperty("line.separator") 
				+ "lloo~yea~olololollllolollololoolllolololoo" + System.getProperty("line.separator")
				+ "ooool~yea~olololollllolololllololol", result);
	}
	
	@Test
	public void testReplaceFirstSubStringInFileRepeatedSymbols() {
		String args = "sed s/*#/`-@!!!!/ test3.txt";
		input = "#*#*#*#*#*#*#**#*#*#*#*#****#**#*#*#**#*#*#";
		String result = sedApplication.replaceFirstSubStringInFile(args);
		assertEquals("`#`-@!!!!*#*#*#*#*#`**#*#*#*`#-@!!`!!*`***`#*-@!!`!!*#-@!!`!!*-@!!`!!-@!!`!!*#" + System.getProperty("line.separator")
				+ "`#`-@!!!!*#*#*#*#*#`**#*#*#*`#-@!!`!!*`***#*#*`#-@!!`!!*`***`#*-@!!`!!*#-@!!`!!*-@!!`!!-@!!`!!*#" + System.getProperty("line.separator")  
				+ "`#`-@!!!!*#*#*#-@!!`!!*#`**#*#*#*`#-@!!`!!*`**`*`#*-@!!`!!*#-@!!`!!*-@!!`!!-@!!`!!*#", result);
	}
	
	//-------
	@Test
	public void testReplaceAllSubStringsInFile() {
		String args = "sed s/the/to-her/g test1.txt";
		input = TEXT;
		String result = sedApplication.replaceAllSubstringsInFile(args);
		assertEquals("The quick$%^^%% brown fox jumps over to-her$$%#(#&##lazy dog" + System.getProperty("line.separator") 
				+ "Quick brown fox to-her^%%$##*#*#*# jumps over#*#*#*# to-her lazy dog" + System.getProperty("line.separator")   
				+ "Quick brown fox jumps)(@## over to-her lazy dog))(~(!@@" + System.getProperty("line.separator")  
				+ "@((#*#&&@@(#Quick brown fox jumps over((#*#*# lazy dog The" + System.getProperty("line.separator") 
				+ "thE Quick brown fox THE)#(#*$*$$# jumps over lazy dog to-her", result);
	}
	
	@Test
	public void testReplaceAllSubStringsInFileSymbols() {
		String args = "sed s/#*#*#/REPLACE!/g test1.txt";
		input = TEXT;
		String result = sedApplication.replaceAllSubstringsInFile(args);
		assertEquals("The quick$%^^%% brown fox jumps over the$$%#(#&##lazy dog" + System.getProperty("line.separator") 
				+ "Quick brown fox the^%%$#REPLACE!*# jumps overREPLACE!*# the lazy dog" + System.getProperty("line.separator")   
				+ "Quick brown fox jumps)(@## over the lazy dog))(~(!@@" + System.getProperty("line.separator")  
				+ "@((#*#&&@@(#Quick brown fox jumps over((REPLACE! lazy dog The" + System.getProperty("line.separator") 
				+ "thE Quick brown fox THE)#(#*$*$$# jumps over lazy dog the", result);
	}
	
	@Test
	public void testReplaceAllSubStringsInFileRepeatedWords() {
		String args = "sed s/lol/~yea~/g test2.txt";
		String result = sedApplication.replaceAllSubstringsInFile(args);
		assertEquals("~yea~olll~yea~o~yea~l~yea~o~yea~" + System.getProperty("line.separator") 
				+ "lloo~yea~o~yea~o~yea~ll~yea~ol~yea~olooll~yea~o~yea~oo" + System.getProperty("line.separator")
				+ "ooool~yea~o~yea~o~yea~ll~yea~o~yea~l~yea~o~yea~", result);
	}
	
	@Test
	public void testReplaceAllSubStringsInFileRepeatedSymbols() {
		String args = "sed s/`*#/-@!!`!!/g test3.txt";
		input = "#*#*#*#*#*#*#**#*#*#*#*#****#**#*#*#**#*#*#";
		String result = sedApplication.replaceAllSubstringsInFile(args);
		assertEquals("`#*#*#*#*#*#*#`**#*#*#*`#-@!!`!!*`***`#*-@!!`!!*#-@!!`!!*-@!!`!!-@!!`!!*#" + System.getProperty("line.separator") 
				+ "`#*#*#*#*#*#*#`**#*#*#*`#-@!!`!!*`***#*#*`#-@!!`!!*`***`#*-@!!`!!*#-@!!`!!*-@!!`!!-@!!`!!*#" + System.getProperty("line.separator")  
				+ "`#*#*#*#*#-@!!`!!*#`**#*#*#*`#-@!!`!!*`**`*`#*-@!!`!!*#-@!!`!!*-@!!`!!-@!!`!!*#", result);
	}
	
	@Test
	public void testReplaceSubstringWithInvalidRule() {
		String args = "sed s/\b/\\/LALA/g";
		input = "\b/\\\b/\\\b/\\\b/\\\b/\\";
		String result = sedApplication.replaceSubstringWithInvalidRule(args);
		assertEquals("\b/\\\b/\\\b/\\\b/\\\b/\\", result);
	}
	
	@Test
	public void testReplaceSubstringWithInvalidReplacement() {
		String args = "sed s/t/he/to-her/g";
		input = "the quick brown fox jumps over the lazy dog";
		String result = sedApplication.replaceAllSubstringsInStdin(args);
		assertEquals("the quick brown fox jumps over the lazy dog", result);
	}
	
	@Test
	public void testReplaceSubstringWithInvalidRegex() {
		String args = "sed s/the/to/her/g";
		input = "the quick brown fox jumps over the lazy dog";
		String result = sedApplication.replaceAllSubstringsInStdin(args);
		assertEquals("the quick brown fox jumps over the lazy dog", result);
	}
	
	@After
	public void tearDown() {
		sedApplication = null;
		
		for (int i = 0; i != FILE_NAMES.length; i++) {
			File file = new File(FILE_NAMES[i]);
			file.delete();
		}
	}

}
