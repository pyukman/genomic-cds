package meduniwien.msc.exception;

/**
 * Represents an error in the base64 codification of a safetycode number.
 * 
 * @author Jose Antonio Mi�arro Gim�nez
 * */
public class BadFormedBase64NumberException extends Exception {

	private static final long serialVersionUID = 1L;

	public BadFormedBase64NumberException(String message) {
		super(message);
	}
}