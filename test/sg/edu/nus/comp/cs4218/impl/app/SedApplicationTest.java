package sg.edu.nus.comp.cs4218.impl.app;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
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

public class SedApplicationTest {
	
	private static final String TEXT1 = "The quick brown fox&@#**#HDN(*#&&#*#*# jumps over((@*@&*#)_$ the(***&& lazy @@dog#*#*#))(*&^^";
	private static final String TEXT2 = "lololololololololololololloololo";
	private static final String TEXT3 = "lol ololo olol olo lol olol olol olo lolloololo olol olo";
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
	
	public static String input;
	private SedApplication sedApplication;
	private InputStream stdin;
	private OutputStream stdout;

	@Before
	public void setup() {
		sedApplication = new SedApplication();
		stdout = new ByteArrayOutputStream();
		
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
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testExtraArgs() throws SedException {
		String[] args = new String[] { "sed", "s/hi/bye/", "test1.txt", "test2.txt" };
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleExtraSeparator() throws SedException {
		String[] args = new String[] { "sed", "s/hi//bye/" };
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleExtraSeparators() throws SedException {
		String[] args = new String[] { "sed", "s/hi///g" };
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleExtraSpace() throws SedException {
		String[] args = new String[] { "sed", "s/hi/ /bye/" };
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleInsufficientArgs() throws SedException {
		String[] args = new String[] { "sed", "s/g/" };
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleExtraArgs() throws SedException {
		String[] args = new String[] { "sed", "s/hi/test/bye/g" };
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleMissingSeparatorBehind() throws SedException {
		String[] args = new String[] { "sed", "s/hi/bye" };
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleS() throws SedException {
		String[] args = new String[] { "sed", "g/hi/bye/" };
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleG() throws SedException {
		String[] args = new String[] { "sed", "s/hi/bye/s" };
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleLongS() throws SedException {
		String[] args = new String[] { "sed", "sss/hi/bye/" };
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleLongG() throws SedException {
		String[] args = new String[] { "sed", "s/hi/bye/ggg" };
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleMissingSeparator() throws SedException {
		String[] args = new String[] { "sed", "s/hi/bye" };
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidReplacementRuleRedundantSeparator() throws SedException {
		String[] args = new String[] { "sed", "s/hi/bye/g/" };
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		sedApplication.run(args, stdin, stdout);
	}
	
	@Test
	public void testReplaceFirstSubStringFromStdinFromRun() {
		String[] args = { "sed", "s/the/to-her/" };
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		
		try {
			sedApplication.run(args, stdin, stdout);
		} catch (SedException e) {
			fail("Should not fail: " + e.getMessage());
		}
	}
	
	@Test
	public void testReplaceFirstSubStringFromStdinRepeatedWordsFromRun() {
		String[] args = { "sed", "s/lol/yea/" };
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		
		try {
			sedApplication.run(args, stdin, stdout);
		} catch (SedException e) {
			fail("Should not fail: " + e.getMessage());
		}
	}
	
	@Test
	public void testReplaceAllSubStringInStdinFromRun() {
		String[] args = { "sed", "s/the/to-her/g" };
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		
		try {
			sedApplication.run(args, stdin, stdout);
		} catch (SedException e) {
			fail("Should not fail: " + e.getMessage());
		}
	}
	
	@Test
	public void testReplaceAllSubStringInStdinRepeatedWordsFromRun() {
		String[] args = { "sed", "s/lol/~yea~/" };
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		
		try {
			sedApplication.run(args, stdin, stdout);
		} catch (SedException e) {
			fail("Should not fail: " + e.getMessage());
		}
	}
	
	@Test
	public void testReplaceFirstSubStringInFileFromRun() {
		String[] args = { "sed", "s/the/to-her/", "test1.txt" };
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		
		try {
			sedApplication.run(args, stdin, stdout);
		} catch (SedException e) {
			fail("Should not fail: " + e.getMessage());
		}
	}
	
	@Test
	public void testReplaceFirstSubStringInFileRepeatedWordsFromRun() {
		String[] args = { "sed", "s/lol/yea!/", "test2.txt" };
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		
		try {
			sedApplication.run(args, stdin, stdout);
		} catch (SedException e) {
			fail("Should not fail: " + e.getMessage());
		}
	}
	
	@Test
	public void testReplaceAllSubStringsInFileFromRun() {
		String[] args = { "sed", "s/the/to-her/g", "test1.txt" };
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		
		try {
			sedApplication.run(args, stdin, stdout);
		} catch (SedException e) {
			fail("Should not fail: " + e.getMessage());
		}
	}
	
	@Test
	public void testReplaceAllSubStringsInFileRepeatedWordsFromRun() {
		String[] args = { "sed", "s/lol/~yea~/g", "test2.txt" };
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		
		try {
			sedApplication.run(args, stdin, stdout);
		} catch (SedException e) {
			fail("Should not fail: " + e.getMessage());
		}
	}
	
	@Test
	public void testInvalidFileName() throws SedException {
		String args = "sed s/hi/bye/g hello.txt" ;
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
		String result = sedApplication.replaceFirstSubStringInFile(args);
		assertEquals("sed: Could not read file", result);
	}
	
	@Test(expected = SedException.class)
	public void testInvalidFileNameGivenDirectory() throws SedException {
		String[] args = new String[] { "sed", "s/hi/bye/g/", "folder" };
		stdin = new ByteArrayInputStream(TEXT1.getBytes());
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
		InputStream stdin = new ByteArrayInputStream(TEXT1.getBytes());
		String result = sedApplication.replaceAllSubstringsInStdin(args, stdin);
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
		InputStream stdin = new ByteArrayInputStream(TEXT1.getBytes());
		String result = sedApplication.replaceFirstSubStringFromStdin(args, stdin);
		assertEquals("The quick brown fox&@#**#HDN(*#&&#*#*# jumps over((@*@&*#)_$ to-her(***&& lazy @@dog#*#*#))(*&^^", result);
	}
	
	@Test
	public void testReplaceFirstSubStringFromStdinRepeatedWords() {
		String args = "sed s/lol/yea/";
		InputStream stdin = new ByteArrayInputStream(TEXT2.getBytes());
		String result = sedApplication.replaceFirstSubStringFromStdin(args, stdin);
		assertEquals("yeaolololololololololololloololo", result);
	}
	
	@Test
	public void testReplaceAllSubStringInStdin() {
		String args = "sed s/the/to-her/g";
		InputStream stdin = new ByteArrayInputStream(TEXT1.getBytes());
		String result = sedApplication.replaceAllSubstringsInStdin(args, stdin);
		assertEquals("The quick brown fox&@#**#HDN(*#&&#*#*# jumps over((@*@&*#)_$ to-her(***&& lazy @@dog#*#*#))(*&^^", result);
	}
	
	@Test
	public void testReplaceAllSubStringInStdinRepeatedWords() {
		String args = "sed s/lol/~yea~/";
		InputStream stdin = new ByteArrayInputStream(TEXT2.getBytes());
		String result = sedApplication.replaceAllSubstringsInStdin(args, stdin);
		assertEquals("~yea~o~yea~o~yea~o~yea~o~yea~o~yea~olloo~yea~o", result);
	}
	
	@Test
	public void testReplaceFirstSubStringInFile() {
		String args = "sed s/the/to-her/ test1.txt";
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
	
	@Test
	public void testReplaceFirstSubStringFromStdinWithSpace() {
		String args = "sed s/the/'to her'/";
		InputStream stdin = new ByteArrayInputStream(TEXT1.getBytes());
		String result = sedApplication.replaceFirstSubStringFromStdin(args, stdin);
		assertEquals("The quick brown fox&@#**#HDN(*#&&#*#*# jumps over((@*@&*#)_$ to her(***&& lazy @@dog#*#*#))(*&^^", result);
	}
	
	@Test
	public void testReplaceFirstSubStringFromStdinRepeatedWordsWithSpace() {
		String args = "sed s/lol/\"yea yea\"/";
		InputStream stdin = new ByteArrayInputStream(TEXT2.getBytes());
		String result = sedApplication.replaceFirstSubStringFromStdin(args, stdin);
		assertEquals("yea yeaolololololololololololloololo", result);
	}
	
	@Test
	public void testReplaceAllSubStringInStdinWithSpace() {
		String args = "sed s/the/'to-her'/g";
		InputStream stdin = new ByteArrayInputStream(TEXT1.getBytes());
		String result = sedApplication.replaceAllSubstringsInStdin(args, stdin);
		assertEquals("The quick brown fox&@#**#HDN(*#&&#*#*# jumps over((@*@&*#)_$ to-her(***&& lazy @@dog#*#*#))(*&^^", result);
	}
	
	@Test
	public void testReplaceAllSubStringInStdinRepeatedWordsWithSpace() {
		String args = "sed s/\"olol olo\"/'~yea man~'/";
		InputStream stdin = new ByteArrayInputStream(TEXT3.getBytes());
		String result = sedApplication.replaceAllSubstringsInStdin(args, stdin);
		assertEquals("lol ololo ~yea man~ lol ~yea man~l olo lolloololo ~yea man~", result);
	}
	
	@Test
	public void testReplaceFirstSubStringInFileWithSpace() {
		String args = "sed s/the/'to her'/ test1.txt";
		String result = sedApplication.replaceFirstSubStringInFile(args);
		assertEquals("The quick$%^^%% brown fox jumps over to her lazy dog" + System.getProperty("line.separator") 
				+ "Quick brown fox to her jumps over the lazy dog" + System.getProperty("line.separator") 
				+ "Quick brown fox jumps over to her lazy dog))(~(!@@" + System.getProperty("line.separator")  
				+ "@((#*#&&@@(#Quick brown fox jumps over lazy dog The" + System.getProperty("line.separator") 
				+ "thE Quick brown fox THE jumps over lazy dog to her", result);
	}
	
	@Test
	public void testReplaceFirstSubStringInFileRepeatedWordsWithSpace() {
		String args = "sed s/lol/\"yea man!\"/ test2.txt";
		String result = sedApplication.replaceFirstSubStringInFile(args);
		assertEquals("yea man!ollllolololllololol" + System.getProperty("line.separator") 
				+ "llooyea man!olololollllolollololoolllolololoo" + System.getProperty("line.separator")
				+ "oooolyea man!olololollllolololllololol", result);
	}
	
	@Test
	public void testReplaceAllSubStringsInFileWithSpace() {
		String args = "sed s/the/'to her'/g test1.txt";
		String result = sedApplication.replaceAllSubstringsInFile(args);
		assertEquals("The quick$%^^%% brown fox jumps over to her lazy dog" + System.getProperty("line.separator")  
				+ "Quick brown fox to her jumps over to her lazy dog" + System.getProperty("line.separator")   
				+ "Quick brown fox jumps over to her lazy dog))(~(!@@" + System.getProperty("line.separator")  
				+ "@((#*#&&@@(#Quick brown fox jumps over lazy dog The" + System.getProperty("line.separator") 
				+ "thE Quick brown fox THE jumps over lazy dog to her", result);
	}
	
	@Test
	public void testReplaceAllSubStringsInFileRepeatedWordsWithSpace() {
		String args = "sed s/'lol'/'~yea man~'/g test2.txt";
		String result = sedApplication.replaceAllSubstringsInFile(args);
		assertEquals("~yea man~olll~yea man~o~yea man~l~yea man~o~yea man~" + System.getProperty("line.separator") 
				+ "lloo~yea man~o~yea man~o~yea man~ll~yea man~ol~yea man~olooll~yea man~o~yea man~oo" + System.getProperty("line.separator")
				+ "ooool~yea man~o~yea man~o~yea man~ll~yea man~o~yea man~l~yea man~o~yea man~", result);
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
