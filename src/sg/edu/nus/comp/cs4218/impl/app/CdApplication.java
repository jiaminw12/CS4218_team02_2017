package sg.edu.nus.comp.cs4218.impl.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sg.edu.nus.comp.cs4218.Application;
import sg.edu.nus.comp.cs4218.Environment;
import sg.edu.nus.comp.cs4218.exception.AbstractApplicationException;
import sg.edu.nus.comp.cs4218.exception.DirectoryNotFoundException;

/**
 * The cat command concatenates the content of given files and prints on the
 * standard output.
 * 
 * <p>
 * <b>Command format:</b> <code>date</code>
 * <dl>
 * <dt>OPTIONS</dt>
 * <dd>“-n 15” means printing 15 lines. Print first 10 lines if not specified.</dd>
 * <dt>FILES</dt>
 * <dd>the name of the file. If not specified, use stdin.</dd>
 * </dl>
 * </p>
 */
public class CdApplication implements Application{

	@Override
	public void run(String[] args, InputStream stdin, OutputStream stdout) throws AbstractApplicationException {
		try {
			setDirectory(args);
		} catch (DirectoryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param args
	 * 			Array of elements for the application. 2nd argument of the array contains the file path that the 
	 * 			would like to go to.
	 * @throws DirectoryNotFoundException
	 * 				If application has errors
	 */
	public void setDirectory(String[] args) throws DirectoryNotFoundException {
		if (args == null) {
			throw new DirectoryNotFoundException("Invalid Arguments.");
		} else if (args.length != 2) {
			throw new DirectoryNotFoundException("Too many Arguments.");
		}
		
		String filePath = args[1];
		File file = new File(filePath);
		
		if (!file.exists()) {
			throw new DirectoryNotFoundException("File does not exists.");
		} else if (!file.isDirectory()) {
			throw new DirectoryNotFoundException("Invalid Arguments.");
		}
		
		try {
			Environment.currentDirectory = file.getCanonicalPath();
		} catch (IOException e) {
			new IOException("Unable to change Directory.");
		}
	}

}
