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
	 * @param player Spieler Objekt des Clients
	 */
	void setPlayer(IPlayer player);

	/**
	 * Der Client wurde aktiviert (ist am Spiel) oder deaktiviert (ist Passiv)
	 * @param isActive aktivert oder deaktivert 
	 */
	void activeChanged(boolean isActive);
	
	/**
	 * Das Spielbrett wurde verändert und muss neu angezeigt werden
	 * @param board Neues Spielbrett
	 */
	void updateGameBoard(GameBoard board);
	
	/**
	 * Eine Chatnachricht wurde versandt
	 * @param message Chatnachricht
	 */
	void addChatMessage(ChatMessage message);
	
	/**
	 * Der Spieler muss zwischen mehreren Karten auswählen
	 * @param allowedCards Zur auswahl stehende Karten
	 */
	void chooseCards(ArrayList<ITargetCard> allowedCards);
	
}
