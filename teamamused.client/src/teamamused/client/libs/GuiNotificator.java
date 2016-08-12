package teamamused.client.libs;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

import teamamused.common.ServiceLocator;
import teamamused.common.db.Ranking;
import teamamused.common.dtos.TransportableChatMessage;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.GameBoard;

/**
 * Diese Klasse informiert die registrierten GUI's über Daten Änderungen.
 * 
 * @author Daniel
 *
 */
public class GuiNotificator {

	private ArrayList<IClientListener> guis = new ArrayList<IClientListener>();
	private Logger log;
	
	public GuiNotificator() {
		super();
		this.log = ServiceLocator.getInstance().getLogger();
	}

	/**
	 * Fügt das angegebene gui dem Notificator hinzu
	 * @param gui Objekt zum hinzufügen
	 * @return erfolgreich
	 */
	public boolean add(IClientListener gui) {
		return this.guis.add(gui);
	}
	
	/**
	 * Entfernt das angegebene gui dem Notificator
	 * @param gui Objekt zum entfernen
	 * @return erfolgreich
	 */
	public boolean remove(IClientListener gui) {
		return this.guis.remove(gui);
	}

	/**
	 * Wird vom Server aufgerufen wenn sich das Spielbrett verändert hat
	 * 
	 * @param board
	 *            aktualisiertes Spielbrett
	 */
	public void gameBoardChanged(GameBoard board) {
		this.log.info("Client: leite update GameBoard an Gui's weiter");
		for (IClientListener gui : this.guis) {
			gui.onGameBoardChanged(board);
		}
	}

	/**
	 * Wird vom Server aufgerufen wenn eine Chatnachricht verschickt wurde
	 * 
	 * @param message
	 *            Chat Benachrichtigung
	 */
	public void chatMessageRecieved(TransportableChatMessage message) {
		this.log.info("Client: leite Chat Nachricht an Gui's weiter");
		for (IClientListener gui : this.guis) {
			gui.onChatMessageRecieved(message);
		}
	}

	/**
	 * Wird vom Server aufgerufen wenn mehrere Zielkarten zur Auswahl stehen.
	 * 
	 * @param options
	 *            Karten die zur Auswahl stehen
	 */
	public void playerHasToCooseCards(Hashtable<Integer, List<ITargetCard>> options) {
		this.log.info("Client: leite chooseCards an Gui's weiter");
		IClientListener[] guiArray = new IClientListener[this.guis.size()];
		this.guis.toArray(guiArray);
		for (int i = 0; i<this.guis.size(); i++) {
			guiArray[i].onPlayerHasToCooseCards(options);
		}
	}

	/**
	 * Wird vom Server aufgerufen wenn der Spieler aktiviert oder deaktiviert wird
	 * 
	 * @param isActive
	 *            aktiviert ja Nein
	 */
	public void playerIsActivedChanged(boolean isActive) {
		this.log.info("Client: leite aktiven Spieler Wechsel an Gui's weiter");
		for (IClientListener gui : this.guis) {
			gui.onPlayerIsActivedChanged(isActive);
		}
	}

	/**
	 * Wird vom Server aufgerufen wenn das Spiel beendet wurde
	 * 
	 * @param rankings
	 *            Platzierungen des Spieles
	 */
	public void gameFinished(Ranking[] rankings) {
		this.log.info("Client: leite Spielbeendet an Gui's weiter");
		for (IClientListener gui : this.guis) {
			gui.onGameFinished(rankings);
		}
	}

	/**
	 * Wird vom Server aufgerufen wenn er den Client über einen Spielzug informieren möchte
	 * 
	 * @param gameMove
	 *            Spielzug welcher zu den Guis geleitet wrden soll
	 */
	public void gameMoveDone(String gameMove) {
		this.log.info("Client: leite Spielzug an Gui's weiter");
		for (IClientListener gui : this.guis) {
			gui.onNewGameMove(gameMove);
		}
	}

	/**
	 * Wird vom Server aufgerufen wenn der Player sich eingelogt hat 
	 * @param player
	 *            Spieler der sich eingelogt hat
	 */
	public void loginSuccessful(IPlayer player) {
		this.log.info("Client: leite Spieler Login erfolgreich an Gui's weiter");
		for (IClientListener gui : this.guis) {
			gui.onLoginSuccessful(player);
		}
	}

	/**
	 * Wird vom Server aufgerufen wenn der Player sich nicht einloggen konnte 
	 * @param errorMsg Fehlerbeschrieb
	 */
	public void loginFailed(String errorMsg) {
		this.log.info("Client: leite Spieler Login gescheitert an Gui's weiter");
		for (IClientListener gui : this.guis) {
			gui.onLoginFailed(errorMsg);
		}
	}

	/**
	 * Wird vom Server aufgerufen wenn der Player sich registriert hat 
	 * @param player
	 *            Spieler der sich eingelogt hat
	 */
	public void registerSuccessful(IPlayer player) {
		this.log.info("Client: leite Spieler Registrieren erfolgreich an Gui's weiter");
		for (IClientListener gui : this.guis) {
			gui.onRegisterSuccessful(player);
		}
	}

	/**
	 * Wird vom Server aufgerufen wenn der Player sich nicht registrieren konnte 
	 * @param errorMsg Fehlerbeschrieb
	 */
	public void registerFailed(String errorMsg) {
		this.log.info("Client: leite Spieler Registrieren gescheitert an Gui's weiter");
		for (IClientListener gui : this.guis) {
			gui.onRegisterFailed(errorMsg);
		}
	}

	/**
	 * Wird vom Server aufgerufen wenn der Player sich registriert hat 
	 * @param player
	 *            Spieler der sich eingelogt hat
	 */
	public void joinGameSuccessful(IPlayer player) {
		this.log.info("Client: leite Spiel beitreten erfolgreich an Gui's weiter");
		for (IClientListener gui : this.guis) {
			gui.onJoinGameSuccessful(player);
		}
	}

	/**
	 * Wird vom Server aufgerufen wenn der Player sich nicht registrieren konnte 
	 * @param errorMsg Fehlerbeschrieb
	 */
	public void joinGameFailed(String errorMsg) {
		this.log.info("Client: leite Spiel beitreten gescheitert an Gui's weiter");
		for (IClientListener gui : this.guis) {
			gui.onJoinGameFailed(errorMsg);
		}
	}

	/**
	 * Wird vom Server aufgerufen wenn die Verbindung beendet werden musste
	 */
	public void serverClosedConnection() {
		this.log.info("Client: leite Server hat die Verbindung geschlossen an Gui's weiter");
		for (IClientListener gui : this.guis) {
			gui.onServerClosedConnection();
		}
	}
	
	/**
	 * Wird vom Server aufgerufen wenn die Bestenliste angefordert wurde 
	 * @param rankings Platzierungen der Spieler
	 */
	public void rankingRecieved(Ranking[] rankings) {
		this.log.info("Client: leite Bestenliste an Gui's weiter");
		for (IClientListener gui : this.guis) {
			gui.onRankingRecieved(rankings);
		}
	}
	
	/**
	 * Wird vom Server aufgerufen um dem Spieler die verbleibenden Würfelversuche mitzuteilen 
	 * @param remDicings Verbleibende Versuche
	 */
	public void numberOfRemeiningDicingChanged(int remDicings) {
		this.log.info("Client: leite anzahl verbleibende Würfelversuche an Gui's weiter");
		for (IClientListener gui : this.guis) {
			gui.onNumberOfRemeiningDicingChanged(remDicings);
		}
	}
}
