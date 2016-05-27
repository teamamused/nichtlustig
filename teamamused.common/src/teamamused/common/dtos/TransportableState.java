package teamamused.common.dtos;

/**
 * Diese Klasse ist eine spezialisierung des Transport Objektes.
 * Sie gibt einen Status zurück
 * @author Daniel
 *
 */
public class TransportableState extends TransportObject {

	/**
	 * Versionsnummer des Transport Objektes
	 */
	private static final long serialVersionUID = 1;
	
	boolean isOK;
	String message;
	
	public TransportableState(boolean isOK, String message) {
		super(TransportType.State);
		this.isOK = isOK;
		this.message = message;
	}

	public boolean isOK() {
		return isOK;
	}
	public void setOK(boolean isOK) {
		this.isOK = isOK;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
