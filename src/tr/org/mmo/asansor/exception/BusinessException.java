package tr.org.mmo.asansor.exception;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 7926000166178074411L;

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
}