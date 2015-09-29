package tr.org.mmo.asansor.exception.db;

public class DeleteException extends CRUDException {

	private static final long serialVersionUID = -423620200068077875L;

	public DeleteException(String message, Throwable cause) {
		super(message, cause);
	}
}
