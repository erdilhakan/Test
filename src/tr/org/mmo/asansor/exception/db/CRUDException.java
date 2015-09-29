package tr.org.mmo.asansor.exception.db;

public class CRUDException extends Exception {

	private static final long serialVersionUID = 5504461877414349131L;

	public CRUDException(String message, Throwable cause) {
		super(message, cause);
	}
}