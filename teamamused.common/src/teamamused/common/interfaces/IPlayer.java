package teamamused.common.interfaces;

/** 
 * Interface für einen Spieler Ein Spieler ist ein Kartenhalter und hat noch
 * zusätzliche Eigenschaften
 * 
 * @author Daniel
 */
public interface IPlayer extends ICardHolder {

	/**
	 * Gibt die Nummer des Spielers zurück
	 * 
	 * @return Nummer des Spielers
	 */
	int getPlayerNumber();
	
	/**
	 * Weisst dem Spieler seine Nummer zu
	 * @param number
	 * 	Spieler Nummer
	 */
	void setPlayerNumber(int number);
	
	/**
	 * Gibt den Namen des Spielers zurück
	 * 
	 * @return Name des Spielers
	 */
	String getPlayerName();
}
