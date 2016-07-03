package teamamused.client.connect;

import java.util.ArrayList;

import teamamused.client.libs.IClientListener;
import teamamused.common.ServiceLocator;
import teamamused.common.dtos.TransportObject;
import teamamused.common.dtos.TransportableChatMessage;
import teamamused.common.dtos.TransportableProcedureCall;
import teamamused.common.dtos.TransportObject.TransportType;
import teamamused.common.dtos.TransportableProcedureCall.RemoteProcedure;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.GameBoard;
import teamamused.common.models.Player;

/**
 * Der Client verarbeitet alles was über die Server Connection kommt. Er leitet
 * Anfragen an den Server weiter und erstellt aus der Antwort wieder ein Java
 * Objekt.
 * 
 * @author Daniel
 *
 */
public class Client {

	private static Client instance = null;
	private GameBoard board;
	private Player currPlayer;
	private ArrayList<IClientListener> guis = new ArrayList<IClientListener>();
	ServerConnector connector;

	/**
	 * Privater Konstruktor da Singelton
	 */
	private Client() {
	}

	/**
	 * Getter für Objektinstanz des Clients
	 * 
	 * @return Insanz des Clients
	 */
	public static Client getInstance() {
		if (instance == null) {
			instance = new Client();
		}
		return instance;
	}

	/***
	 * Baut eine Verbindung zum Server auf
	 * 
	 * @param serverName
	 *            IP oder Name des Servers
	 * @param username
	 *            Benutzername
	 * @param portNumber
	 *            Server Port
	 * @return erfolgreich
	 */
	public boolean ConnectToServer(String serverName, String username, int portNumber) {
		this.connector = new ServerConnector(serverName, username, portNumber);
		return this.connector.connect();
	}

	/**
	 * Getter fürs Spielbrett
	 * 
	 * @return Spielbrett
	 */
	public GameBoard getSpielbrett() {
		return this.board;
	}

	/**
	 * getter für Player
	 * 
	 * @return aktuelles Spieler Objekt
	 */
	public IPlayer getPlayer() {
		return this.currPlayer;
	}

	/**
	 * setter für Player
	 * @param player Spiler welcher gesetzt werden soll
	 */
	public void setPlayer(IPlayer player) {
		this.currPlayer = (Player) player;
	}


	/**
	 * Um einen GUI Controller zu registrieren damit dieser danach die
	 * Benachrichtigungen erhält muss diese Methode aufgerufen werden
	 * 
	 * @param gui
	 *            Controller klasse
	 */
	public void registerGui(IClientListener gui) {
		ServiceLocator.getInstance().getLogger().info("Client registriert Gui " + gui.getClass().toString());
		this.guis.add(gui);
	}

	/**
	 * GUI Controller deregistrieren, damit dieser keine Benachrichtigungen mehr
	 * erhält
	 * 
	 * @param gui
	 *            Controller klasse
	 */
	public void deregisterGui(IClientListener gui) {
		this.guis.remove(gui);
	}

	/**
	 * Wird vom Server aufgerufen wenn sich das Spielbrett verändert hat
	 * 
	 * @param board
	 *            aktualisiertes Spielbrett
	 */
	public void updateGameBoard(GameBoard board) {
		ServiceLocator.getInstance().getLogger().info("Client: leite update GameBoard an Gui's weiter");
		this.board = board;
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
	public void addChatMessage(TransportableChatMessage message) {
		ServiceLocator.getInstance().getLogger().info("Client: leite Chat Nachricht an Gui's weiter");
		for (IClientListener gui : this.guis) {
			gui.onChatMessageRecieved(message);
		}
	}

	/**
	 * Wird vom Gui aufgerufen um eine Chatnachricht zu versenden
	 * 
	 * @param message
	 *            Chat Benachrichtigung
	 */
	public void sendChatMessage(TransportableChatMessage message) {
		message.send(this.connector.getOutputStream());
	}

	/**
	 * Wird vom Server aufgerufen wenn mehrere Zielkarten zur Auswahl stehen.
	 * 
	 * @param allowedCards
	 *            Karten die zur Auswahl stehen
	 */
	public void chooseCards(ArrayList<ITargetCard> allowedCards) {
		ServiceLocator.getInstance().getLogger().info("Client: leite chooseCards an Gui's weiter");
		for (IClientListener gui : this.guis) {
			gui.onPlayerHasToCooseCards(allowedCards);
		}
	}

	/**
	 * wird aufgerufen wenn der Spieler aktiviert oder deaktiviert wird
	 * 
	 * @param isActive
	 *            aktiviert ja Nein
	 */
	public void activeChanged(boolean isActive) {
		for (IClientListener gui : this.guis) {
			gui.onPlayerIsActivedChanged(isActive);
		}
	}

	/**
	 * Einem Spiel beitreten
	 * 
	 * @param player
	 *            Spieler
	 */
	public void joinGame(Player player) {
		ServiceLocator.getInstance().getLogger().info("Client Spieler " + player.getPlayerName() + " tritt Spiel bei");
		this.currPlayer = player;
		// this.currPlayer.setPlayerNumber(Server.getInstance().addPlayer(this,
		// player));
	}

	/**
	 * Spiel starten
	 */
	public void startGame() {
		// ServiceLocator.getInstance().getLogger().info("Client Spieler " +
		// this.currPlayer.getPlayerName() + " startet Spiel");
		TransportableProcedureCall rpc = new TransportableProcedureCall(RemoteProcedure.StartGame);
		rpc.send(this.connector.getOutputStream());
	}

	/**
	 * Würfeln
	 * 
	 * @return anzahl verbleibende Versuche
	 */
	public int rollDices() {
		ServiceLocator.getInstance().getLogger().info("Client Spieler " + this.currPlayer.getPlayerName() + " würfelt");
		TransportableProcedureCall rpc = new TransportableProcedureCall(RemoteProcedure.RollDices);
		rpc.send(this.connector.getOutputStream());
		return 1;
	}

	/**
	 * Zielkarten ausgewählt dem Server mitteilen
	 * 
	 * @param targetCards
	 *            Zielkarten zur auswahl
	 */
	public void chooseCards(ITargetCard[] targetCards) {
		ServiceLocator.getInstance().getLogger()
				.info("Client Spieler " + this.currPlayer.getPlayerName() + " bestätigt gewählte Zielkarten");
		// Server.getInstance().allocateCards(targetCards);
	}

	/**
	 * Der Client meldet sich höfflichst vom Server ab.
	 */
	public void sayGoodbye() {
		if (this.connector != null) {
			TransportObject dto = new TransportObject(TransportType.Goodbye);
			dto.send(this.connector.getOutputStream());
			this.connector.close();
		}
	}
}