package teamamused.playground.application;

import teamamused.common.ServiceLocator;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.IServerListener;
import teamamused.common.interfaces.ITargetCard;
import teamamused.playground.application.serverKlassen.Boardmanager;
import teamamused.playground.application.serverKlassen.ClientManager;
import teamamused.playground.application.serverKlassen.Game;


/**
 * Der Server nimmt die Netzwerkanfragen entegen und leitet alles nötige weiter.
 * Diese Klasse ist die öffentliche Schnittstelle für den Client.
 * 
 * Hier wird es für jedes UseCase aus "Server in Game Funktionalität" eine Methode geben.
 * 
 * @author Daniel
 *
 */
public class Server {

	private static Server instance = null;
	
	/**
	 * private Konstruktor da Singelton
	 */
	private Server() {
	}
	
	public static Server getInstance() {
		if (instance == null) {
			instance = new Server();
		}
		return instance;
	}
	
	/**
	 * Client connected sich auf Server
	 * @param client
	 */
	public void connectClient(IServerListener client) {
		ServiceLocator.getInstance().getLogger().info("Server verbinde Client " + client.toString());
		ClientManager.getInstance().addClient(client);
	}

	/**
	 * Spieler tritt dem Spiel bei
	 * @param client
	 * @param player
	 */
	public int addPlayer(IServerListener client, IPlayer player) {
		ServiceLocator.getInstance().getLogger().info("Server füge Spieler zu Spiel hinzu " + player.toString());
		return Game.getInstance().addPlayer(client, player);
	}
	
	/**
	 * Spiel starten
	 */
	public void startGame() {
		ServiceLocator.getInstance().getLogger().info("Server starte neues Spiel");
		Game.getInstance().StartGame();
	}

	/**
	 * Würfeln
	 * @return
	 */
	public int rollDices() {
		return Boardmanager.getInstance().rollDices();
	}

	/**
	 * Karten zuteilen
	 * @param targetCards
	 */
	public void allocateCards(ITargetCard[] targetCards) {
		Boardmanager.getInstance().allocateTargetCards(ClientManager.getInstance().getCurrentClient().getPlayer(), targetCards);
	}
}
