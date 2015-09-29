package tr.org.mmo.asansor.exception.db;

public class ReadException extends CRUDException {

	private static final long serialVersionUID = 1366555108672372426L;

	public ReadException(String message, Throwable cause) {
		super(message, cause);
	}
}
