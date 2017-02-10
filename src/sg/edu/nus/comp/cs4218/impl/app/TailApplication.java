package sg.edu.nus.comp.cs4218.impl.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.FilenameUtils;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.TailException;

/**
 * The cat command concatenates the content of given files and prints on the
 * standard output.
 * 
 * <p>
 * <b>Command format:</b> <code>TAIL [OPTIONS] [FILE]</code>
 * <dl>
 * <dt>OPTIONS</dt>
 * <dd>“-n 15” means printing 15 lines. Print first 10 lines if not specified.</dd>
 * <dt>FILES</dt>
 * <dd>the name of the file. If not specified, use stdin.</dd>
 * </dl>
 * </p>
 */
public class TailApplication implements Application{

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		// TODO Auto-generated method stub
		processArguments(args, stdin, stdout);
	}
	
	private void processArguments(String[] args, InputStream stdin, OutputStream stdout) throws TailException {
		File file;
//		System.out.println("Started Processing Arguments");
		if (args.length < 2) {
			throw new TailException("Insufficient arguments.");
		} else if (args.length > 4) {
			throw new TailException("Too many arguments.");
		} else if (args.length == 2) {
			file = new File(args[1]);
			if (checkValidFile(file)) {
				printLine(10, file, stdin, stdout);
			}
		} else if (args.length == 3) {
			int numberOfLines = 0;
			if (!args[1].substring(0,1).equals("-")) {
				throw new TailException(args[1] + "is an invalid argument.");
			} else if (!args[1].substring(1).matches("[0-9]+")){
				throw new TailException("Invalid number of lines.");
			} else {
				file = new File(args[2]);
				numberOfLines = Integer.parseInt(args[1].substring(1));
				if (checkValidFile(file)) {
					printLine(numberOfLines, file, stdin, stdout);
				}	
			}
		} else if (args.length == 4) {
			if (!args[1].equals("-n")) {
				throw new TailException(args[1] + "is an invalid argument.");
			} else if (!args[2].matches("[0-9]+")) {
				throw new TailException("Invalid number of lines.");
			} else {
				file = new File(args[3]);
				if (checkValidFile(file)) {
					printLine(Integer.parseInt(args[2]), file, stdin, stdout);
				}
			}
		}
	}

	private void printLine(int numberOfLines, File file, InputStream stdin, OutputStream stdout) 
						throws TailException {
		// TODO Auto-generated method stub
		if (stdin == null || stdout == null) {
			throw new TailException("Null Pointer Exception");
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			int tempNumberOfLines = numberOfLines;
			String[] linesArray = new String[numberOfLines];
			int count = 0;
						
			while (tempNumberOfLines > 0) {
				String line = reader.readLine();
				if (line != null) {
					linesArray[count%numberOfLines] = line;
					count++;
				} else if (line==null) {
					tempNumberOfLines = 0;
				}
			}
			
			if (numberOfLines >= count) {
				for (int i = 0; i < count; i++) {
					System.out.println(linesArray[i]);
				}
			} else if (numberOfLines < count) {
				int start = count - numberOfLines;
				for (int i = start; i < count; i++) {
					System.out.println(linesArray[i%numberOfLines]);
				}
			}
			
			reader.close();
		} catch (IOException e) {
			throw new TailException("IO Exception");
		}
		
	}

	public boolean checkValidFile(File file) throws TailException {
		String extension = FilenameUtils.getExtension(file.getAbsolutePath());
//		System.out.println(extension);
		Path filePath = file.toPath();
		
		if (!extension.equals("txt")) {
			throw new TailException("Invalid File Extension");
		}
		if (!Files.exists(filePath)) {
			throw new TailException("File does not exist.");
		} else if (!Files.isReadable(filePath)) {
			throw new TailException("Unable to read file.");
		} else if (Files.isDirectory(filePath)) {
			throw new TailException("This is a directory");
		} else {
			return true;
		}
	}

}
