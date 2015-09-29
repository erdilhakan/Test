package tr.org.mmo.asansor.exception.db;

public class CreateException extends CRUDException {

	private static final long serialVersionUID = -1804092944249240630L;

	public CreateException(String message, Throwable cause) {
		super(message, cause);
	}
}
