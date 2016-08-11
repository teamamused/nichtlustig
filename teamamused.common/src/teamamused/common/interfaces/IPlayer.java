package teamamused.common.interfaces;

import java.util.Locale;

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
	 * und initialisiert Ihn für eine Neues Spiel
	 * @param number
	 * 	Spieler Nummer
	 */
	void initForGame(int number);
	
	/**
	 * Gibt den Namen des Spielers zurück
	 * 
	 * @return Name des Spielers
	 */
	String getPlayerName();
	
	/**
	 * Gibt die Zielkarte zurück welche beim Spieler unter der übergebenen Todeskarte liegt
	 * 
	 * @param deadCard Todeskarte
	 * @return Zielkarte oder NULL falls die Todeskarte auf keiner Zielkarte liegt
	 */
	ITargetCard getTargetCardUnderDeadCard(IDeadCard deadCard);
	
	/**
	 * Gibt die Sprach und Regionseinstellung des Spielers
	 * @return local
	 */
	Locale getLocal();
	
	/**
	 * Setzt die Sprach und Regionseinstellung für den Spieler
	 * @param local neue Regionseinstellungen des Spielers
	 */
	void setLocal(Locale local);
	
	/**
	 * Gibt zurück ob der Spieler noch mit dem Server verbunden ist.
	 * @return
	 */
	boolean getConnected();

	/**
	 * Setzt den Spieler auf Verbunden oder nicht verbunden
	 * @param Boolean ob Verbunden
	 */
	void setConnected(boolean connected);
}
