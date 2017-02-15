package sg.edu.nus.comp.cs4218.exception;

public class SortException extends AbstractApplicationException {

	public SortException(String message) {
		super("sort: " + message);
	}
}