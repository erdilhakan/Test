package tr.org.mmo.asansor.exception.db;

public class UpdateException extends CRUDException {

	private static final long serialVersionUID = 8661759882588450676L;

	public UpdateException(String message, Throwable cause) {
		super(message, cause);
	}
}
