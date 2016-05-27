package teamamused.common.dtos;

import java.util.Date;

/**
 * @author Daniel
 *
 */
public class TransportableChatMessage extends TransportObject {

	/**
	 * Versionsnummer des Transport Objektes
	 */
	private static final long serialVersionUID = 1;
	
	private Date time;
	private String sender;
	private String message;

	public TransportableChatMessage(String sender, String message) {
		super(TransportType.ChatMessage);
		this.sender = sender;
		this.message = message;
				
		// new Date macht standardmässig das aktuelle Datum / Zeit
		this.time = new Date();
	}
	
	public Date getTime() {
		return this.time;
	}
	public String getSender() {
		return this.sender;
	}
	public String getMessage() {
		return this.message;
	}
}
