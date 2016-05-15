package teamamused.playground.application;

import java.util.ArrayList;

import teamamused.common.ServiceLocator;
import teamamused.common.interfaces.IClientListener;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.IServerListener;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.ChatMessage;
import teamamused.common.models.GameBoard;
import teamamused.common.models.Player;

/**
 * Der Client hat die gleichen Funktionalitäten wie der Server.
 * Er leitet die Anfragen an den Server weiter und erstellt aus der Antwort wieder ein Java Objekt.
 * 
 * @author Daniel
 *
 */
public class Client implements IServerListener {
	
	private static Client instance = null;
	private GameBoard board;
	private Player currPlayer;
	private ArrayList<IClientListener> guis = new ArrayList<IClientListener>();
	
	/**
	 * Privater KOnstruktor da Singelton
	 */
	private Client() {
		Server.getInstance().connectClient(this);
	}
	
	/**
	 * Getter für Objektinstanz des Clients
	 * @return Insanz des Clients
	 */
	public static Client getInstance() {
		if (instance == null) {
			instance = new Client();
		}
		return instance;
	}
	
	/**
	 * Getter fürs Spielbrett
	 * @return Spielbrett
	 */
	public GameBoard getSpielbrett() {
		return this.board;
	}
	
	/**
	 * getter für Player
	 * @return aktuelles Spieler Objekt
	 */
	@Override
	public IPlayer getPlayer() {
		return this.currPlayer;
	}
	
	/**
	 * Setter für Player
	 * @param player Spieler Objekt
	 */
	@Override
	public void setPlayer(IPlayer player) {
		this.currPlayer = (Player) player;
	}
	
	/**
	 * Um einen GUI Controller zu registrieren
	 * damit dieser danach die Benachrichtigungen erhält
	 * muss diese Methode aufgerufen werden
	 * @param gui Controller klasse
	 */
	public void registerGui(IClientListener gui) {
		ServiceLocator.getInstance().getLogger().info("Client registriert Gui " + gui.getClass().toString());
		this.guis.add(gui);
	}

	/**
	 * GUI Controller deregistrieren,
	 * damit dieser keine Benachrichtigungen mehr erhält
	 * @param gui Controller klasse
	 */
	public void deregisterGui(IClientListener gui) {
		this.guis.remove(gui);
	}
	
	/**
	 * Wird vom Server aufgerufen wenn sich das Spielbrett verändert hat 
	 * @param board aktualisiertes Spielbrett
	 */
	@Override
	public void updateGameBoard(GameBoard board) {
		ServiceLocator.getInstance().getLogger().info("Client: leite update GameBoard an guis weiter");
		this.board = board;
		for (IClientListener gui : this.guis) {
			gui.onGameBoardChanged(board);
		}
	}
	
	/**
	 * Wird vom Server aufgerufen wenn eine Chatnachricht verschickt wurde 
	 * @param message Chat Benachrichtigung
	 */
	@Override
	public void addChatMessage(ChatMessage message) {
		for (IClientListener gui : this.guis) {
			gui.onChatMessageRecieved(message);
		}
	}
	
	/**
	 * Wird vom Server aufgerufen wenn mehrere Zielkarten zur Auswahl stehen.
	 * @param allowedCards Karten die zur Auswahl stehen
	 */
	@Override
	public void chooseCards(ArrayList<ITargetCard> allowedCards) {
		ServiceLocator.getInstance().getLogger().info("Client: leite chooseCards an guis weiter");
		for (IClientListener gui : this.guis) {
			gui.onPlayerHasToCooseCards(allowedCards);
		}
	}

	/**
	 * wird aufgerufen wenn der Spieler aktiviert oder deaktiviert wird
	 * @param isActive aktiviert ja Nein
	 */
	@Override
	public void activeChanged(boolean isActive) {
		for (IClientListener gui : this.guis) {
			gui.onPlayerIsActivedChanged(isActive);
		}		
	}
	
	/**
	 * Einem Spiel beitreten
	 * @param player Spieler
	 */
	public void joinGame(Player player) {
		ServiceLocator.getInstance().getLogger().info("Client Spieler " + player.getPlayerName() + " tritt Spiel bei");
		this.currPlayer = player;
		this.currPlayer.setPlayerNumber(Server.getInstance().addPlayer(this, player));
	}

	/**
	 * Spiel starten
	 */
	public void startGame() {
		ServiceLocator.getInstance().getLogger().info("Client Spieler " + this.currPlayer.getPlayerName() + " startet Spiel");
		Server.getInstance().startGame();
	}
	
	/**
	 * Würfeln
	 * @return anzahl verbleibende Versuche
	 */
	public int rollDices() {
		ServiceLocator.getInstance().getLogger().info("Client Spieler " + this.currPlayer.getPlayerName() + " würfelt");
		return Server.getInstance().rollDices();
	}

	/**
	 * Zielkarten ausgewählt dem Server mitteilen
	 * @param targetCards Zielkarten zur auswahl
	 */
	public void chooseCards(ITargetCard[] targetCards) {
		ServiceLocator.getInstance().getLogger().info("Client Spieler " + this.currPlayer.getPlayerName() + " bestätigt gewählte Zielkarten");
		Server.getInstance().allocateCards(targetCards);
	}
	
}
