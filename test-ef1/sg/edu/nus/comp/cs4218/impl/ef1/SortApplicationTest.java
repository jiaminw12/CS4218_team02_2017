package sg.edu.nus.comp.cs4218.impl.ef1;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.nus.comp.cs4218.exception.SortException;
import sg.edu.nus.comp.cs4218.exception.WcException;
import sg.edu.nus.comp.cs4218.impl.app.SortApplication;

public class SortApplicationTest {

	private SortApplication sortApp;

	@BeforeClass
	public static void setUpOnce() {
	}

	@AfterClass
	public static void tearDown() {
	}

	@Before
	public void setUp() throws SortException, IOException {
		sortApp = new SortApplication();
	}
	
	@Test(expected = SortException.class)
	public void testWcAppWithEmptyArgsWithEmptyInput() throws SortException {
		String[] args = {};
		sortApp.run(args, null, System.out);
	}
	
	@Test(expected = SortException.class)
	public void testWcAppWithOneItemInArgs() throws SortException {
		String[] args = {"sort"};
		sortApp.run(args, System.in, System.out);
	}
	
	@Test(expected = SortException.class)
	public void testIllegalOption() throws SortException {
		String[] args = { "sort", "-z", "muttest.txt" };
		sortApp.run(args, System.in, System.out);
	}
	
	@Test(expected = SortException.class)
	public void testIllegalFile() throws SortException {
		String[] args = { "sort", "-n", "muttest" };
		sortApp.run(args, System.in, System.out);
	}
	
	@Test
	public void testArgsWithoutOption() throws SortException {
		String[] args = { "sort", "muttest.txt" };
		sortApp.run(args, System.in, System.out);
	}
	
	@Test
	public void testArgsWithOption() throws SortException {
		String[] args = { "sort", "-n", "muttest.txt" };
		sortApp.run(args, System.in, System.out);
	}
	
	@Test
	public void testSortStringsSimple() {
		String arr = "simple apple fox";
		String expected = "apple" + System.lineSeparator() + "fox"
				+ System.lineSeparator() + "simple";
		assertEquals(expected, sortApp.sortStringsSimple(arr));
	}

	@Test
	public void testSortStringsCapital() {
		String arr = "SIMPLE APPLE FOX";
		String expected = "APPLE" + System.lineSeparator() + "FOX"
				+ System.lineSeparator() + "SIMPLE";
		assertEquals(expected, sortApp.sortStringsCapital(arr));
	}

	@Test
	public void testSortNumbers() {
		String arr = "1 2 10";
		String expected = "1" + System.lineSeparator() + "10"
				+ System.lineSeparator() + "2";
		assertEquals(expected, sortApp.sortNumbers(arr));
	}

	@Test
	public void testSortSpecialChars() {
		String arr = "^% *( $) ! @; \n \t";
		String expected = "!" + System.lineSeparator() + "$)"
				+ System.lineSeparator() + "*(" + System.lineSeparator() + "@;"
				+ System.lineSeparator() + "\n" + System.lineSeparator() + "\t"
				+ System.lineSeparator() + "^%";

		assertEquals(expected, sortApp.sortSpecialChars(arr));
	}

	@Test
	public void testSortSimpleCapital() {
		String arr = "simplE Simple simPle SIMPLE";
		String expected = "SIMPLE" + System.lineSeparator() + "Simple"
				+ System.lineSeparator() + "simPle" + System.lineSeparator()
				+ "simplE";
		assertEquals(expected, sortApp.sortSimpleCapital(arr));
	}

	@Test
	public void testSortSimpleNumbers() {
		// NOT SURE
		String arr = "simpl3 4impl5 s1mp12 7 100 4";
		String expected = "4" + System.lineSeparator() + "100"
				+ System.lineSeparator() + "4impl5" + System.lineSeparator()
				+ "7" + System.lineSeparator() + "s1mp12"
				+ System.lineSeparator() + "simpl3";
		assertEquals(expected, sortApp.sortSimpleNumbers(arr));
	}

	@Test
	public void testSortSimpleSpecialChars() {
		// NOT SURE
		String arr = "s+mpl* simple s!mp!e $imple % \n";
		String expected = "$imple" + System.lineSeparator() + "%"
				+ System.lineSeparator() + "\n" + System.lineSeparator()
				+ "s!mp!e" + System.lineSeparator() + "s+mpl*"
				+ System.lineSeparator() + "simple";
		assertEquals(expected, sortApp.sortSimpleSpecialChars(arr));
	}

	@Test
	public void testSortCapitalNumbers() {
		// NOT SURE
		String arr = "S!MP!E $APPLE S+MPL* % HE** ***";
		String expected = "$APPLE" + System.lineSeparator() + "%"
				+ System.lineSeparator() + "***" + System.lineSeparator()
				+ "HE**" + System.lineSeparator() + "S!MP!E"
				+ System.lineSeparator() + "S+MPL*";
		assertEquals(expected, sortApp.sortCapitalNumbers(arr));
	}

	@Test
	public void testSortCapitalSpecialChar() {
		String arr = "APP1E APPL3 A55LE ANNA 10 1O";
		String expected = "10" + System.lineSeparator() + "1O"
				+ System.lineSeparator() + "A55LE" + System.lineSeparator()
				+ "ANNA" + System.lineSeparator() + "APP1E"
				+ System.lineSeparator() + "APPL3";
		assertEquals(expected, sortApp.sortCapitalSpecialChars(arr));
	}

	@Test
	public void testsSortNumbersSpecialChars() {
		String arr = "1##90 $3412* 1^*23 3* (%# ||";
		String expected = "$3412*" + System.lineSeparator() + "(%#"
				+ System.lineSeparator() + "A55LE" + System.lineSeparator()
				+ "1##90" + System.lineSeparator() + "1^*23"
				+ System.lineSeparator() + "3*" + System.lineSeparator() + "||";
		assertEquals(expected, sortApp.sortNumbersSpecialChars(arr));
	}

	@Test
	public void testSortSimpleCapitalNumbe() {
		String arr = "Simpl3 4impl5 S1mp12 7 100 aPP13 anna}";
		String expected = "100" + System.lineSeparator() + "4impl5"
				+ System.lineSeparator() + "7" + System.lineSeparator()
				+ "S1mp12" + System.lineSeparator() + "Simpl3"
				+ System.lineSeparator() + "aPP13" + System.lineSeparator()
				+ "anna}";
		assertEquals(expected, sortApp.sortSimpleCapitalNumber(arr));
	}

	@Test
	public void testSortSimpleCapitalSpecialChars() {
		String arr = "S!MP!E $APPLE apple look S*MPL* %( < 90";
		String expected = "$APPLE" + System.lineSeparator() + "%( "
				+ System.lineSeparator() + "90" + System.lineSeparator() + "<"
				+ System.lineSeparator() + "S!MP!E" + System.lineSeparator()
				+ "S*MPL*" + System.lineSeparator() + "apple"
				+ System.lineSeparator() + "look";
		assertEquals(expected, sortApp.sortSimpleCapitalSpecialChars(arr));
	}

	@Test
	public void testSortSimpleNumbersSpecialChars() {
		String arr = "app!?e look a__a 1234 109 a99() book";
		String expected = "109" + System.lineSeparator() + "1234 "
				+ System.lineSeparator() + "a99()" + System.lineSeparator()
				+ "a__a" + System.lineSeparator() + "app!?e"
				+ System.lineSeparator() + "book" + System.lineSeparator()
				+ "look";
		assertEquals(expected, sortApp.sortSimpleNumbersSpecialChars(arr));
	}

	@Test
	public void testSortCapitalNumbersSpecialChars() {
		String arr = "S!MP!1E $A33LE S*MP!* A00A JUST";
		String expected = "$A33LE" + System.lineSeparator() + "A00A"
				+ System.lineSeparator() + "JUST" + System.lineSeparator()
				+ "S!MP!1E" + System.lineSeparator() + "S*MP!*";
		assertEquals(expected, sortApp.sortCapitalNumbersSpecialChars(arr));
	}

	@Test
	public void testSortAll() {
		String arr = "s!MP!1e $a33Le S*MP!* A1MP^& anna al1G JUST";
		String expected = "$A33LE" + System.lineSeparator() + "A1MP^&"
				+ System.lineSeparator() + "JUST" + System.lineSeparator()
				+ "S*MP!*" + System.lineSeparator() + "al1G"
				+ System.lineSeparator() + "anna" + System.lineSeparator()
				+ "s!MP!1e";
		assertEquals(expected, sortApp.sortAll(arr));
	}
}
