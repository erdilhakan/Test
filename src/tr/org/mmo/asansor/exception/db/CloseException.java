package tr.org.mmo.asansor.exception.db;

public class CloseException extends CRUDException {

	private static final long serialVersionUID = -2053784224419720687L;

	public CloseException(String message, Throwable cause) {
		super(message, cause);
	}
}
