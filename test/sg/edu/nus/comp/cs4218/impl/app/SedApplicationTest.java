package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.SedException;

public class SedApplicationTest {
	
	private static final String TEXT = "The quick brown fox&@#**#HDN(*#&&#*#*# jumps over((@*@&*#)_$ the(***&& lazy @@dog#*#*#))(*&^^";
	private static final String[] FILE_NAMES = { "test1.txt", "test2.txt", "test3.txt" };
	private static final List<String> TEXT1 = Arrays.asList(
			"The quick$%^^%% brown fox jumps over the lazy dog", 
			"Quick brown fox the jumps over the lazy dog",  
			"Quick brown fox jumps over the lazy dog))(~(!@@", 
			"@((#*#&&@@(#Quick brown fox jumps over lazy dog The",
			"thE Quick brown fox THE jumps over lazy dog the");
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
	public void testReplaceSubstringWithInvalidRuleExtraSeparator() throws SedException {
		String args = "sed s/hi//bye/";
		String result = sedApplication.replaceSubstringWithInvalidRule(args);
		assertEquals("Invalid Replacement Rule: Empty arguments", result);
	}
	
	@Test
	public void testReplaceSubstringWithInvalidRuleExtraSeparators() throws SedException {
		String args = "sed s/hi///g";
		String result = sedApplication.replaceSubstringWithInvalidRule(args);
		assertEquals("Invalid Replacement Rule: Empty arguments", result);
	}
	
	@Test
	public void testReplaceSubstringWithInvalidRuleExtraSpace() throws SedException {
		String args = "sed s/hi/ /bye/";
		String result = sedApplication.replaceSubstringWithInvalidRule(args);
		assertEquals("Invalid Replacement Rule: Insufficient arguments", result);
	}
	
	@Test
	public void testReplaceSubstringWithInvalidRuleInsufficientArgs() throws SedException {
		String args = "sed s/g/" ;
		String result = sedApplication.replaceSubstringWithInvalidRule(args);
		assertEquals("Invalid Replacement Rule: Insufficient arguments", result);
	}
	
	@Test
	public void testReplaceSubstringWithInvalidRuleExtraArgs() throws SedException {
		String args = "sed s/hi/test/bye/g";
		String result = sedApplication.replaceSubstringWithInvalidRule(args);
		assertEquals("Invalid Replacement Rule: Extra arguments", result);
	}
	
	@Test
	public void testReplaceSubstringWithInvalidRuleMissingSeparatorBehind() throws SedException {
		String args = "sed s/hi/bye";
		String result = sedApplication.replaceSubstringWithInvalidRule(args);
		assertEquals("Invalid Replacement Rule: Missing separator at the end", result);
	}
	
	@Test
	public void testReplaceSubstringWithInvalidRuleS() throws SedException {
		String args = "sed g/hi/bye/";
		String result = sedApplication.replaceSubstringWithInvalidRule(args);
		assertEquals("Invalid Replacement Rule: Missing \"s\" in front", result);
	}
	
	@Test
	public void testReplaceSubstringWithInvalidRuleG() throws SedException {
		String args = "sed s/hi/bye/s";
		String result = sedApplication.replaceSubstringWithInvalidRule(args);
		assertEquals("Invalid Replacement Rule: Missing \"g\" at the end", result);
	}
	
	@Test
	public void testReplaceSubstringWithInvalidRuleLongS() throws SedException {
		String args = "sed sss/hi/bye/";
		String result = sedApplication.replaceSubstringWithInvalidRule(args);
		assertEquals("Invalid Replacement Rule: Empty arguments", result);
	}
	
	@Test
	public void testReplaceSubstringWithInvalidRuleLongG() throws SedException {
		String args = "sed s/hi/bye/ggg";
		String result = sedApplication.replaceSubstringWithInvalidRule(args);
		assertEquals("Invalid Replacement Rule: Missing \"g\" at the end", result);
	}
	
	@Test
	public void testReplaceSubstringWithInvalidRuleMissingSeparator() throws SedException {
		String args = "sed s/hi/bye";
		String result = sedApplication.replaceSubstringWithInvalidRule(args);
		assertEquals("Invalid Replacement Rule: Missing separator at the end", result);
	}
	
	@Test
	public void testReplaceSubstringWithInvalidRuleRedundantSeparator() throws SedException {
		String args = "sed s/hi/bye/g/";
		String result = sedApplication.replaceSubstringWithInvalidRule(args);
		assertEquals("Invalid Replacement Rule: Extra separator at the end", result);
	}
	
	@Test
	public void testReplaceSubstringWithInvalidRegex() {
		String args = "sed s/lol***/the/g";
		String result = sedApplication.replaceAllSubstringsInStdin(args);
		assertEquals("sed: Invalid Regex", result);
	}
	
	@Test
	public void testReplaceSubstringWithInvalidReplacement() { //TODO
//		String args = "sed s/lol/\s/g";
//		String result = sedApplication.replaceAllSubstringsInStdin(args);
//		assertEquals("sed: Invalid Replacement", result);
	}
	
	@Test
	public void testReplaceFirstSubStringFromStdin() {
		String args = "sed s/the/to-her/";
		input = TEXT;
		String result = sedApplication.replaceFirstSubStringFromStdin(args);
		assertEquals("The quick brown fox&@#**#HDN(*#&&#*#*# jumps over((@*@&*#)_$ to-her(***&& lazy @@dog#*#*#))(*&^^", result);
	}
	
	@Test
	public void testReplaceFirstSubStringFromStdinRepeatedWords() {
		String args = "sed s/lol/yea/";
		input = "lololololololololololololloololo";
		String result = sedApplication.replaceFirstSubStringFromStdin(args);
		assertEquals("yeaolololololololololololloololo", result);
	}
	
	@Test
	public void testReplaceAllSubStringInStdin() {
		String args = "sed s/the/to-her/g";
		input = TEXT;
		String result = sedApplication.replaceAllSubstringsInStdin(args);
		assertEquals("The quick brown fox&@#**#HDN(*#&&#*#*# jumps over((@*@&*#)_$ to-her(***&& lazy @@dog#*#*#))(*&^^", result);
	}
	
	@Test
	public void testReplaceAllSubStringInStdinRepeatedWords() {
		String args = "sed s/lol/~yea~/";
		input = "lololololololololololololloololo";
		String result = sedApplication.replaceAllSubstringsInStdin(args);
		assertEquals("~yea~o~yea~o~yea~o~yea~o~yea~o~yea~olloo~yea~o", result);
	}
	
	@Test
	public void testReplaceFirstSubStringInFile() {
		String args = "sed s/the/to-her/ test1.txt";
		input = TEXT;
		String result = sedApplication.replaceFirstSubStringInFile(args);
		assertEquals("The quick$%^^%% brown fox jumps over to-her lazy dog" + System.getProperty("line.separator") 
				+ "Quick brown fox to-her jumps over the lazy dog" + System.getProperty("line.separator") 
				+ "Quick brown fox jumps over to-her lazy dog))(~(!@@" + System.getProperty("line.separator")  
				+ "@((#*#&&@@(#Quick brown fox jumps over lazy dog The" + System.getProperty("line.separator") 
				+ "thE Quick brown fox THE jumps over lazy dog to-her", result);
	}
	
	@Test
	public void testReplaceFirstSubStringInFileRepeatedWords() {
		String args = "sed s/lol/yea!/ test2.txt";
		String result = sedApplication.replaceFirstSubStringInFile(args);
		assertEquals("yea!ollllolololllololol" + System.getProperty("line.separator") 
				+ "llooyea!olololollllolollololoolllolololoo" + System.getProperty("line.separator")
				+ "oooolyea!olololollllolololllololol", result);
	}
	
	@Test
	public void testReplaceAllSubStringsInFile() {
		String args = "sed s/the/to-her/g test1.txt";
		input = TEXT;
		String result = sedApplication.replaceAllSubstringsInFile(args);
		assertEquals("The quick$%^^%% brown fox jumps over to-her lazy dog" + System.getProperty("line.separator")  
				+ "Quick brown fox to-her jumps over to-her lazy dog" + System.getProperty("line.separator")   
				+ "Quick brown fox jumps over to-her lazy dog))(~(!@@" + System.getProperty("line.separator")  
				+ "@((#*#&&@@(#Quick brown fox jumps over lazy dog The" + System.getProperty("line.separator") 
				+ "thE Quick brown fox THE jumps over lazy dog to-her", result);
	}
	
	@Test
	public void testReplaceAllSubStringsInFileRepeatedWords() {
		String args = "sed s/lol/~yea~/g test2.txt";
		String result = sedApplication.replaceAllSubstringsInFile(args);
		assertEquals("~yea~olll~yea~o~yea~l~yea~o~yea~" + System.getProperty("line.separator") 
				+ "lloo~yea~o~yea~o~yea~ll~yea~ol~yea~olooll~yea~o~yea~oo" + System.getProperty("line.separator")
				+ "ooool~yea~o~yea~o~yea~ll~yea~o~yea~l~yea~o~yea~", result);
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
