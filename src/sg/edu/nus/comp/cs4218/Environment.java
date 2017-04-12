package sg.edu.nus.comp.cs4218;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Environment {

	/**
	 * Java VM does not support changing the current working directory. For this
	 * reason, we use Environment.currentDirectory instead.
	 */
	public static volatile String currentDirectory = System.getProperty("user.dir");

	private Environment() {
	};

	public static void setDefaultDirectory() {
		currentDirectory = System.getProperty("user.dir");
	}

}
