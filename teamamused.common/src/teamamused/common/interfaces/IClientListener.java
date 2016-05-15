package teamamused.common.interfaces;

import java.util.ArrayList;

import teamamused.common.models.ChatMessage;
import teamamused.common.models.GameBoard;

/**
 * 
 * Dieses Interface ist für alle Klassen welche vom Client benachrichtigungen erhalten wollen.
 * Betrifft vorallem die GUI's welche die Daten anzeigen.
 * 
 * Sie können sich beim Client registrieren und erhalten dann die folgenden Meldungen.
 * 
 * Die Methoden sind alle default Methoden, so kann jedes Gui die jenigen Elemente überschreiben welche es benötigt.
 * 
 * @author Daniel
 *
 */
public interface IClientListener {
	
	/**
	 * Das Spielbrett wurde verändert
	 * 
	 * @param newGameBoard
	 */
	public default void onGameBoardChanged(GameBoard newGameBoard) {}
	
	/**
	 * Die Mitspieler auflistung hat sich verändert
	 * 
	 * @param players
	 */
	public default void onPlayersChanged(IPlayer[] players) {}
	
	/**
	 * Der Spieler ist am Zug
	 */
	public default void onPlayerIsActivedChanged(boolean isActive) {}
	
	/**
	 * Der Spieler muss eine Karte auswählen
	 *  
	 * @param allowedCards
	 */
	public default void onPlayerHasToCooseCards(ArrayList<ITargetCard> allowedCards) {}
	
	/**
	 * Der Spieler muss eine Karte auswählen
	 *  
	 * @param cards
	 */
	public default void onChatMessageRecieved(ChatMessage message) {}
}
