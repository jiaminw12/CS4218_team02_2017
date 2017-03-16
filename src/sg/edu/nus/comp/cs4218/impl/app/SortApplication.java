package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.app.Sort;
import sg.edu.nus.comp.cs4218.exception.SedException;
import sg.edu.nus.comp.cs4218.exception.SortException;

/**
 * 
 * The sort command orders the lines of the specified file or input and prints
 * the same lines but in sorted order. It compares each line character by
 * character. A special character (e.g., +) comes before numbers. A number comes
 * before capital letters. A capital letter comes before small letters, etc.
 * Within each character class, the characters are sorted according to their
 * ASCII value.
 * 
 * <p>
 * <b>Command format:</b> <code>sort[-n][FILE]</code>
 * <dl>
 * <dt>-n</dt>
 * <dd>If specified, treat the first word of a line as a number.</dd>
 * <dt>FILE</dt>
 * <dd>The name of the file(s). If not specified, use stdin.</dd>
 * </dl>
 * </p>
 */
public class SortApplication implements Application, Sort {

	/**
	 * Runs the sort application with the specified arguments.
	 * 
	 * @param args
	 *            Array of arguments for the application.
	 * @param stdin
	 *            An InputStream, not used.
	 * @param stdout
	 *            An OutputStream. Elements of args will be output to stdout,
	 *            separated by a space character.
	 * 
	 * @throws SortException
	 *             If an I/O exception occurs.
	 */
	public void run(String[] args, InputStream stdin, OutputStream stdout)
			throws SortException {

		List<String> sortedList;

		if (args == null && stdin == null) {
			throw new SortException("Null arguments");
		}
		if (stdout == null) {
			throw new SortException("OutputStream not provided");
		}

		// Remove sort command
		String[] removeCommandArgs = removeCommand(args);
		List<String> arrList = new ArrayList<>();
		if(removeCommandArgs != null) {
			for(int i = 0; i < removeCommandArgs.length; i++) {
				arrList.add(removeCommandArgs[i]);
			}
		}

		if(removeCommandArgs != null && removeCommandArgs.length == 0) {
			removeCommandArgs = null;
		}

		validate(args);

		if (removeCommandArgs == null || removeCommandArgs.length == 0) {
			// sort
			if (stdin == null) {
				throw new SortException("InputStream not provided");
			}
			arrList = stringToList(readFromInputStream(stdin));
			sortedList = bubbleSort(arrList);
		} else if(removeCommandArgs.length == 1 && removeCommandArgs[0].equals("-n")) {
			// sort -n
			if (stdin == null) {
				throw new SortException("InputStream not provided");
			}
			arrList = stringToList(readFromInputStream(stdin));
			sortedList = numericBubbleSort(arrList);
		} else {
			// sort [FILE]
			// sort -n [FILE]
			sortedList = readFromFile(removeCommandArgs);
		}

		try {
			for (int i = 0; i < sortedList.size(); i++) {
				stdout.write(sortedList.get(i).getBytes("UTF-8"));
				if(!(sortedList.size() == 1 && sortedList.get(i).trim().isEmpty())) {
					stdout.write(System.lineSeparator().getBytes("UTF-8"));
				}
			}
		} catch (IOException e) {
			throw new SortException("IO error");
		}
	}

	/**
	 * Remove "sort" command from args
	 * 
	 * @param args
	 *            Array of arguments for the application.
	 */
	String[] removeCommand(String[] args) {
		if(args != null) {
			String[] temp = new String[args.length - 1];

			if(args[0].equals("sort")) {
				for(int i = 1; i < args.length; i++) {
					temp[i - 1] = args[i]; 
				}

				return temp;
			}
		}

		return args;
	}

	/**
	 * Checks if arguments are valid
	 * 
	 * @param args
	 *            Array of arguments for the application.
	 *            
	 * @throws SortException
	 *            If argument is invalid
	 */
	public void validate(String[] args) throws SortException {
		boolean hasOption = false;

		if(args != null) {
			for(int i = 0; i < args.length; i++) {
				if(args[i].equals("-n")) {
					if(hasOption) {
						throw new SortException("Too many -n");
					} else {
						hasOption = true;
					}
				}
			}
		}
	}

	/**
	 * Reorder arguments such that -n is the first argument
	 * 
	 * @param args
	 *            Array of arguments for the application.
	 *           
	 */
	public String[] reOrder(String[] args) {
		String[] sortedList = new String[args.length];

		if(args[0].equals("-n")) {
			return args;
		} else if(args[args.length - 1].equals("-n")) {
			for(int i = args.length - 1; i >= 0; i--) {
				sortedList[Math.abs(args.length - 1 - i)] = args[i];
			}
		} else {
			sortedList[0] = "-n";
			int index = 1;
			for(int i = 0; i < args.length; i++) {
				if(!args[i].equals("-n")) {
					sortedList[index] = args[i];
					index++;
				}
			}
		}

		return sortedList;
	}

	/**
	 * Reads from stdin.
	 * 
	 * @param args
	 *            Array of arguments for the application. Each array element is
	 *            the path to a file. If no files are specified stdin is used.
	 * @param stdin
	 *            An InputStream. The input for the command is read from this
	 *            InputStream if no files are specified.
	 * 
	 * @throws SortException
	 *             If the file(s) specified do not exist or are unreadable.
	 */
	public String readFromInputStream(InputStream stdin) throws SortException {
		StringBuffer text = new StringBuffer();
		String str = "";

		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stdin, "UTF-8"));
			
			while((str = bufferedReader.readLine()) != null) {
				text.append(str);
				text.append(System.getProperty("line.separator"));
			}
			
		} catch (IOException e) {
			throw new SortException("Could not read input");
		}
		
		return text.toString().trim();
	}

	/**
	 * Reads from file.
	 * 
	 * @param args
	 *            Array of arguments for the application. Each array element is
	 *            the path to a file. If no files are specified stdin is used.
	 * 
	 * @throws SedException
	 *             If the file(s) specified do not exist or are unreadable.
	 */
	public List<String> readFromFile(Path filePath) throws SortException {
		ArrayList<String> text = new ArrayList<String>();
		String str = "";
		List<String> args;

		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filePath.toString()), "UTF-8"));

			while ((str = bufferedReader.readLine()) != null) {
				text.add(str);
			}

			bufferedReader.close();
		} catch (IOException e) {
			throw new SortException("Could not read input");
		}

		args = new ArrayList<>();
		for(int i = 0; i < text.size(); i++) {
			args.add(text.get(i));
		}

		return args;
	}

	/**
	 * Checks if a file is readable.
	 * 
	 * @param filePath
	 *            File path of the file provided by the user.
	 *            
	 * @throws SortException
	 *            If the file is not readable
	 */
	boolean checkIfFileIsReadable(Path filePath) throws SortException {

		if (Files.isDirectory(filePath)) {
			throw new SortException("This is a directory");
		}
		if (Files.exists(filePath) && Files.isReadable(filePath)) {
			return true;
		} else {
			throw new SortException("Could not read file");
		}
	}

	/**
	 * Reads from file.
	 * 
	 * @param args
	 *            Array of arguments for the application. Each array element is
	 *            the path to a file. If no files are specified stdin is used.
	 * 
	 * @throws SedException
	 *             If the file(s) specified do not exist or are unreadable.
	 */
	List<String> readFromFile(String[] args) throws SortException {
		Path currentDir = Paths.get(Environment.currentDirectory);
		Path filePath;
		List<String> sortedList = new ArrayList<>();

		if (args.length == 1 && !args[0].equals("-n")) {
			// sort [FILE]
			filePath = currentDir.resolve(args[0]);
			if (checkIfFileIsReadable(filePath)) {
				sortedList = bubbleSort(readFromFile(filePath));
			}
		} else if (args.length == 2 && args[0].equals("-n") && args[1] != null && !args[1].isEmpty()) {
			// sort -n [FILE]
			filePath = currentDir.resolve(args[1]);
			if (checkIfFileIsReadable(filePath)) {
				sortedList = numericBubbleSort(readFromFile(filePath));
			}
		} else {
			String[] reOrderArgs = reOrder(args);
			List<String> text = new ArrayList<>();
			if (reOrderArgs[0].equals("-n") && reOrderArgs.length > 1) {
				for (int i = 1; i < reOrderArgs.length; i++) {
					filePath = currentDir.resolve(reOrderArgs[i]);
					if (checkIfFileIsReadable(filePath)) {
						text.addAll(readFromFile(filePath));
					}
				}

				sortedList = numericBubbleSort(text);
			} 
		}

		return sortedList;
	}

	/**
	 * Convert an array of strings to string
	 * 
	 * @param args
	 *            Array of arguments for the application. 
	 *
	 */
	public String arrToString(String[] args) {
		String text = "";

		for(int i = 0; i < args.length; i++) {
			text += args[i];
			if(i != args.length - 1) {
				text += " ";
			}
		}

		return text;
	}

	/**
	 * Convert list of strings to string
	 * 
	 * @param args
	 *            Array of arguments for the application. 
	 *
	 */
	public String listToString(List<String> args) {
		String text = "";

		for(int i = 0; i < args.size(); i++) {
			text += args.get(i) + "\n";
		}

		return text;
	}

	/**
	 * Convert list of strings to string
	 * 
	 * @param args
	 *            Array of arguments for the application. 
	 *
	 */
	public List<String> stringToList(String text) {
		List<String> args = new ArrayList<>();

		String[] splitArgs = text.split("\n");
		for(int i = 0; i < splitArgs.length; i++) {
			args.add(splitArgs[i].trim());
		}

		return args;
	}

	/**
	 * Convert string to array
	 * 
	 * @param args
	 *            Array of arguments for the application. 
	 *
	 */
	public String[] stringToArr(String text) {
		return text.split(" ");
	}

	/**
	 * Sort text according to ASCII.
	 * 
	 * @param args
	 *            Array of arguments for the application. Each array element is
	 *            the path to a file. If no files are specified stdin is used.
	 *
	 */
	public List<String> bubbleSort(List<String> args) {
		int min = 0;
		for (int i = 0; i < args.size() - 1; i++) {
			min = i;
			for (int j = i + 1; j < args.size(); j++) {
				if (args.get(j).compareTo(args.get(min)) < 0) {
					min = j;
				}
			}
			String temp = args.get(i);
			args.set(i, args.get(min));
			args.set(min, temp);
		}

		return args;
	}

	/**
	 * Sort numbers.
	 * 
	 * @param arrList
	 *            Array of arguments for the application. Each array element is
	 *            the path to a file. If no files are specified stdin is used.
	 *
	 */
	public List<String> numericBubbleSort(List<String> arrList) {
		for (int i = 0; i < arrList.size() - 1; i++) {
			int min = i;
			for (int j = i + 1; j < arrList.size(); j++) {
				String[] first = arrList.get(j).split(" ");
				String[] minString = arrList.get(min).split(" ");
				if (isNumbers(first[0], minString[0])) {
					int firstNum = Integer.parseInt(first[0]);
					int minNum = Integer.parseInt(minString[0]);
					if (firstNum < minNum) {
						min = j;
					}
				} else if (arrList.get(j).compareTo(arrList.get(min)) < 0) {

					min = j;

				}
			}
			String temp = arrList.get(i);
			arrList.set(i, arrList.get(min));
			arrList.set(min, temp);
		}

		return arrList;
	}

	/**
	 * Checks if both strings are numbers.
	 * 
	 * @param num1
	 *          First string
	 * @param num2
	 *          Second string
	 * 
	 * @return boolean 
	 * 			true if both are numbers.
	 * 
	 */
	boolean isNumbers(String num1, String num2) {

		try {
			Integer.parseInt(num1);
			Integer.parseInt(num2);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	@Override
	public String sortStringsSimple(String toSort) {
		return listToString(bubbleSort(stringToList(toSort)));
	}

	@Override
	public String sortStringsCapital(String toSort) {
		return listToString(bubbleSort(stringToList(toSort)));
	}

	@Override
	public String sortNumbers(String toSort) {
		return listToString(bubbleSort(stringToList(toSort)));
	}

	@Override
	public String sortSpecialChars(String toSort) {
		return listToString(bubbleSort(stringToList(toSort)));
	}

	@Override
	public String sortSimpleCapital(String toSort) {
		return listToString(bubbleSort(stringToList(toSort)));
	}

	@Override
	public String sortSimpleNumbers(String toSort) {
		return listToString(bubbleSort(stringToList(toSort)));
	}

	@Override
	public String sortSimpleSpecialChars(String toSort) {
		return listToString(bubbleSort(stringToList(toSort)));
	}

	@Override
	public String sortCapitalNumbers(String toSort) {
		return listToString(bubbleSort(stringToList(toSort)));
	}

	@Override
	public String sortCapitalSpecialChars(String toSort) {
		return listToString(bubbleSort(stringToList(toSort)));
	}

	@Override
	public String sortNumbersSpecialChars(String toSort) {
		return listToString(bubbleSort(stringToList(toSort)));
	}

	@Override
	public String sortSimpleCapitalNumber(String toSort) {
		return listToString(bubbleSort(stringToList(toSort)));
	}

	@Override
	public String sortSimpleCapitalSpecialChars(String toSort) {
		return listToString(bubbleSort(stringToList(toSort)));
	}

	@Override
	public String sortSimpleNumbersSpecialChars(String toSort) {
		return listToString(bubbleSort(stringToList(toSort)));
	}

	@Override
	public String sortCapitalNumbersSpecialChars(String toSort) {
		return listToString(bubbleSort(stringToList(toSort)));
	}

	@Override
	public String sortAll(String toSort) {
		return listToString(bubbleSort(stringToList(toSort)));
	}

}
