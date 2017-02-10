package sg.edu.nus.comp.cs4218.impl.app;

import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.HeadException;

/**
 * The cat command concatenates the content of given files and prints on the
 * standard output.
 * 
 * <p>
 * <b>Command format:</b> <code>head [OPTIONS] [FILE]</code>
 * <dl>
 * <dt>OPTIONS</dt>
 * <dd>“-n 15” means printing 15 lines. Print first 10 lines if not specified.</dd>
 * <dt>FILES</dt>
 * <dd>the name of the file. If not specified, use stdin.</dd>
 * </dl>
 * </p>
 */
public class HeadApplication implements Application {

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		// TODO Auto-generated method stub
		processArguments(args, stdin, stdout);
	}

	private void processArguments(String[] args, InputStream stdin, OutputStream stdout) throws HeadException {
		File file;
//		System.out.println("Started Processing Arguments");
		if (args.length < 2) {
			throw new HeadException("Insufficient arguments.");
		} else if (args.length > 4) {
			throw new HeadException("Too many arguments.");
		} else if (args.length == 2) {
			file = new File(args[1]);
			if (checkValidFile(file)) {
				printLine(10, file, stdin, stdout);
			}
		} else if (args.length == 3) {
			int numberOfLines = 0;
			if (!args[1].substring(0,1).equals("-")) {
				throw new HeadException(args[1] + "is an invalid argument.");
			} else if (!args[1].substring(1).matches("[0-9]+")){
				throw new HeadException("Invalid number of lines.");
			} else {
				file = new File(args[2]);
				numberOfLines = Integer.parseInt(args[1].substring(1));
				if (checkValidFile(file)) {
					printLine(numberOfLines, file, stdin, stdout);
				}	
			}
		} else if (args.length == 4) {
			if (!args[1].equals("-n")) {
				throw new HeadException(args[1] + "is an invalid argument.");
			} else if (!args[2].matches("[0-9]+")) {
				throw new HeadException("Invalid number of lines.");
			} else {
				file = new File(args[3]);
				if (checkValidFile(file)) {
					printLine(Integer.parseInt(args[2]), file, stdin, stdout);
				}
			}
		}
	}

	private void printLine(int numberOfLines, File file, InputStream stdin, OutputStream stdout) 
						throws HeadException {
		// TODO Auto-generated method stub
		if (stdin == null || stdout == null) {
			throw new HeadException("Null Pointer Exception");
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while (numberOfLines > 0) {
				String line = reader.readLine();
				if (line != null) {
					System.out.println(line);
				}
				numberOfLines --;
			}
			reader.close();
		} catch (IOException e) {
			throw new HeadException("IO Exception");
		}
	}

	public boolean checkValidFile(File file) throws HeadException {
		String extension = FilenameUtils.getExtension(file.getAbsolutePath());
//		System.out.println(extension);
		Path filePath = file.toPath();
		
		if (!extension.equals("txt")) {
			throw new HeadException("Invalid File Extension");
		}
		if (!Files.exists(filePath)) {
			throw new HeadException("File does not exist.");
		} else if (!Files.isReadable(filePath)) {
			throw new HeadException("Unable to read file.");
		} else if (Files.isDirectory(filePath)) {
			throw new HeadException("This is a directory");
		} else {
			return true;
		}
	}

}
