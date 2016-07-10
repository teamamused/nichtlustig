package teamamused.common.db;

/**
 * Spielerinformationen welche gespeichert werden
 * 
 * @author Daniel
 *
 */
public class PlayerInfo {
	/** Spielername */ 
	public String Username;
	/** HashCode des Passwortes */
	public int PwHash;

	/**
	 * Konstruktor
	 */
	public PlayerInfo() {
		super();
	}
	/**
	 * Initialisiert ein neues Playerinfo Objekt mit den übergebenen Parametern
	 * @param username	Spielername
	 * @param pwHash	HashCode des Passwortes
	 */
	public PlayerInfo(String username, int pwHash) {
		this();
		this.Username = username;
		this.PwHash = pwHash;
	}
}
