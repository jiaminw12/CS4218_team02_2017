package sg.edu.nus.comp.cs4218.exception;

public class CalException extends AbstractApplicationException {

	public CalException(String message) {
		super("cal: " + message);
	}
}