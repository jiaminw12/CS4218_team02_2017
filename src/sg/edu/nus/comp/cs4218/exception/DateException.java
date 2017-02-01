package sg.edu.nus.comp.cs4218.exception;

public class DateException extends AbstractApplicationException {

	public DateException(String message) {
		super("date: " + message);
	}
}