package teamamused.client.libs;

import java.util.logging.Logger;

import teamamused.client.connect.ServerConnector;
import teamamused.common.ServiceLocator;
import teamamused.common.dtos.TransportObject;
import teamamused.common.dtos.TransportableChatMessage;
import teamamused.common.dtos.TransportableProcedureCall;
import teamamused.common.dtos.TransportableProcedureCall.RemoteProcedure;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.ITargetCard;
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
	private Logger log;
	private GuiNotificator guis;
	private Player currPlayer;
	ServerConnector connector;

	/**
	 * Privater Konstruktor da Singelton
	 */
	private Client() {
		super();
		this.log = ServiceLocator.getInstance().getLogger();
		this.guis = new GuiNotificator();
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
	 * Gibt die Instanz um Gui Benachrichtiger zurück
	 * @return instanz
	 */
	public GuiNotificator getGuiNotificator() {
		return this.guis;
	}
	/**
	 * Um einen GUI Controller zu registrieren damit dieser danach die
	 * Benachrichtigungen erhält muss diese Methode aufgerufen werden
	 * 
	 * @param gui
	 *            Controller klasse
	 */
	public void registerGui(IClientListener gui) {
		this.log.info("Client registriert Gui " + gui.getClass().toString());
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
	 * Cliente möchte eine Chatnachricht versenden
	 * 
	 * @param message
	 *            Chat Benachrichtigung
	 */
	public void sendChatMessage(TransportableChatMessage message) {
		this.log.info("Client " + this.currPlayer.getPlayerName() + " sendet Chatnachricht");
		this.send(message);
	}

	/**
	 * Cliente möchte einem Spiel beitreten
	 * 
	 */
	public void joinGame() {
		this.log.info("Client Spieler " + this.currPlayer.getPlayerName() + " tritt Spiel bei");
		this.send(new TransportableProcedureCall(RemoteProcedure.JoinGame, new Object[] { this.currPlayer}));
	}

	/**
	 * Cliente möchte ein Spiel starten
	 */
	public void startGame() {
		this.log.info("Client Spieler " + this.currPlayer.getPlayerName() + " startet Spiel");
		this.send(new TransportableProcedureCall(RemoteProcedure.StartGame));
	}

	/**
	 * Cliente möchte würfeln
	 * 
	 * @return anzahl verbleibende Versuche
	 */
	public void rollDices() {
		this.log.info("Client Spieler " + this.currPlayer.getPlayerName() + " würfelt");
		this.send(new TransportableProcedureCall(RemoteProcedure.RollDices));
	}

	/**
	 * Cliente möchte die ausgewählten Zielkarten dem Server mitteilen
	 * 
	 * @param targetCards
	 *            Zielkarten zur auswahl
	 */
	public void cardsChoosen(ITargetCard[] targetCards) {
		this.log.info("Client Spieler " + this.currPlayer.getPlayerName() + " bestätigt gewählte Zielkarten");
		// Server.getInstance().allocateCards(targetCards);
	}

	/**
	 * Cliente möchte sich höfflichst vom Server abmelden.
	 */
	public void sayGoodbye() {
		if (this.connector != null) {
			this.connector.close();
		}
	}

	/**
	 * Spieler möchte sich einloggen.
	 * @param username Benutzername
	 * @param password Passwort
	 */
	public void logIn(String username, String password) {
		this.log.info("Benutzer " + username + " will sich anmelden");
		this.send(new TransportableProcedureCall(RemoteProcedure.LoginPlayer, new Object[] { username, password }));
	}

	/**
	 * Spieler möchte sich registrieren.
	 * @param username Benutzername
	 * @param password Passwort
	 */
	public void registerPlayer(String username, String password) {
		this.log.info("Benutzer " + username + " will sich anmelden");
		this.send(new TransportableProcedureCall(RemoteProcedure.RegisterPlayer, new Object[] { username, password }));
	}


	/**
	 * Spieler möchte die Bestenliste betrachten.
	 */
	public void getRanking() {
		this.log.info("Benutzer will die Bestenliste betrachten");
		this.send(new TransportableProcedureCall(RemoteProcedure.GetTopRanking, new Object[] {}));
	}
	
	
	private void send(TransportObject dto) {
		if (this.connector != null) {
			this.connector.getConnection().sendTransportObject(dto);
		}
	}
}