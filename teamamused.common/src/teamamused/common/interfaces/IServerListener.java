package teamamused.common.interfaces;

import java.util.ArrayList;

import teamamused.common.models.ChatMessage;
import teamamused.common.models.GameBoard;

/**
 * 
 * Dieses Interface ist für alle Klassen welche vom Server Nachrichten empfangen wollen.
 * 
 * Sie können sich beim Server registrieren und erhalten dann die folgenden Meldungen.
 * @author Daniel
 *
 */
public interface IServerListener {
	
	/**
	 * Spieler welcher von diesem Client aus am Spielen ist
	 * 
	 * @return
	 * 	Spieler Objekt
	 */
	IPlayer getPlayer();
	
	/**
	 * Verknüpft den Spieler (Objekt welches am Spiel teil nimmt) mit dem Serverlistener (Client welcher sich auf den Server verbindet)
	 * @param player
	 */
	void setPlayer(IPlayer player);

	/**
	 * Der Client wurde aktiviert -> ist am Spiel
	 */
	void activeChanged(boolean isActive);
	
	/**
	 * Das Spielbrett wurde verändert und muss neu angezeigt werden
	 * @param board
	 */
	void updateGameBoard(GameBoard board);
	
	/**
	 * Eine Chatnachricht wurde versandt
	 * @param message
	 */
	void addChatMessage(ChatMessage message);
	
	/**
	 * Der Spieler muss zwischen mehreren Karten auswählen
	 * @param allowedCards
	 */
	void chooseCards(ArrayList<ITargetCard> allowedCards);
	
}
