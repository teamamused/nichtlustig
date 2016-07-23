package teamamused.playground.application.serverKlassen;

import java.util.Hashtable;

import teamamused.common.ServiceLocator;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.IServerListener;

/**
 * Ansatz der Game Klasse
 * 
 * @author Daniel
 *
 */
public class Game {

	private static Game instance;
    // Alle registrierten Spieler. Zuordnung via Spielernummer.
	private Hashtable<Integer, IPlayer> players = new Hashtable<Integer, IPlayer>();
	// Spieler der an der Reihe ist
	private int activePlayer;
	
	/**
	 * private Konstruktor da Singelton Pattern
	 */
	private Game() {
		
	}
	
	/**
	 * Statischer Getter für Instanz da Singelton Pattern
	 * @return Instanz zum Game
	 */
	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}
	
	/**
	 * Gibt die Nummer des aktiven Spielers zurück
	 * @return Aktiver Spieler Nummer
	 */
	public int getActivePlayer() {
		return activePlayer;
	}

	/**
	 * Startet das Spiel
	 */
	public void StartGame() {
		// Startspieler bestimmen
		this.activatePlayer(((int)(Math.random() * this.players.size())) + 1);
	}
	
	/**
	 * Fügt dem Spiel einen Player hinzu und gibt desen Nummer zurück
	 * @param client Klient 
	 * @param player Spieler
	 * @return Nummer des Spielers im Game
	 */
	public int addPlayer(IServerListener client, IPlayer player) {
		int playerNumber = players.size() + 1;
		this.players.put(playerNumber, player);
		player.initForGame(playerNumber);
		ClientManager.getInstance().addPlayerToClient(client, player);
		return playerNumber;
	}
	
	/**
	 * Aktiviert einen Spieler
	 * @param playerNumber Spieler Nummer 
	 */
	public void activatePlayer(int playerNumber) {
		ServiceLocator.getInstance().getLogger().info("Game: aktiviere Spieler Nummer " + playerNumber);
		// Alter Spieler inaktivieren
		if (ClientManager.getInstance().getCurrentClient() != null) {
			ClientManager.getInstance().getCurrentClient().activeChanged(false);
		}
		// Neuer Spieler setzen
		this.activePlayer = playerNumber;
		// Neuer Spieler aktivieren
		ClientManager.getInstance().getCurrentClient().activeChanged(true);
		// Würfel wieder zurücksetzen
		CubeManager.getInstance().initForNextRound(0);
	}
	
}
