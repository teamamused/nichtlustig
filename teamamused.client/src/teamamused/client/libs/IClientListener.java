package teamamused.client.libs;

import java.util.Hashtable;
import java.util.List;

import teamamused.common.db.Ranking;
import teamamused.common.dtos.TransportableChatMessage;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.GameBoard;

/**
 * 
 * Dieses Interface ist für alle Klassen welche vom Client Benachrichtigungen erhalten wollen.
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
	 * @param newGameBoard Neues Spielbrett
	 */
	public default void onGameBoardChanged(GameBoard newGameBoard) {}
	
	/**
	 * Der Spieler ist am Zug oder nicht mehr am Zug
	 * 
	 * @param isActive Spieler aktiv Ja / Nein
	 */
	public default void onPlayerIsActivedChanged(boolean isActive) {}
	
	/**
	 * Der Spieler muss die geünschte Ziel Karte(n) auswählen
	 * Als Value des Optionen Hashtable ist die Options Nr und als Value jeweils die Liste für die möglichen Karten
	 *  
	 * @param options Zur auswahl stehende Karten
	 */
	public default void onPlayerHasToCooseCards(Hashtable<Integer, List<ITargetCard>> options) {}
	
	/**
	 * Der Spieler hat eine Chatnachricht empfangen
	 *  
	 * @param message Chatnachricht
	 */
	public default void onChatMessageRecieved(TransportableChatMessage message) {}
	
	/**
	 * Der Server hat einen Spielzug vorgenommen, dieser wird dem Client als String Nachricht übergeben
	 *  
	 * @param newGameMove Spielzug vom Server
	 */
	public default void onNewGameMove(String newGameMove) {}
	
	/**
	 * Der Server hat das Spiel für beendet erklärt.
	 * Dem Spieler muss angezeigt werden das das Spiel vorbei ist und das Ranking eingeblendet werden
	 *  
	 * @param rankings Platzierungen der Spielrunde
	 */
	public default void onGameFinished(Ranking[] rankings) {}
	
	/**
	 * Antwort auf die Registrieren Anfrage:
	 * Der Spieler wurde erstellt
	 * 
	 * @param player neu Registrierter Spieler
	 */
	public default void onRegisterSuccessful(IPlayer player) {}

	/**
	 * Antwort auf die Registrieren Anfrage:
	 * Der Spieler wurde nicht erstellt
	 * 
	 * @param errorMsg Fehlermeldung
	 */
	public default void onRegisterFailed(String errorMsg) {}
	
	/**
	 * Antwort auf die Einloggen Anfrage:
	 * Der Spieler ist eingelogt
	 * 
	 * @param player eingelogter Spieler
	 */
	public default void onLoginSuccessful(IPlayer player) {}
	
	/**
	 * Antwort auf die Einloggen Anfrage:
	 * Das Login schlug fehl
	 * 
	 * @param errorMsg Fehlermeldung
	 */
	public default void onLoginFailed(String errorMsg) {}
	
	/**
	 * Antwort auf die Spiel beitreten Anfrage:
	 * Der Spieler ist dem Spiel beigetreten
	 * 
	 * @param player Spieler (Neu ist die Spielernummer gesetzt)
	 */
	public default void onJoinGameSuccessful(IPlayer player) {}
	
	/**
	 * Antwort auf die Spiel beitreten Anfrage:
	 * Der Spieler konnte dem Spiel nicht beitreten
	 * 
	 * @param errorMsg Fehlermeldung
	 */
	public default void onJoinGameFailed(String errorMsg) {}
	
	/**
	 * Der Server hat die Verbindung abgebrochen
	 * 
	 */
	public default void onServerClosedConnection() {}
	
	/**
	 * Der Spieler hatte den wunsch geäussert die Bestenliste einzusehen, worauf der Server uns diese geschickt hat.
	 *  
	 * @param rankings Bestenliste über alle Spiele hinweg
	 */
	public default void onRankingRecieved(Ranking[] rankings) {}

	
	/**
	 * Der Spieler hat Gewürfelt, der Server sagt Ihm wieviel mal er noch darf
	 *  
	 * @param remDices Verbleibende Würfelversuche
	 */
	public default void onNumberOfRemeiningDicingChanged(int remDices) {}
}
