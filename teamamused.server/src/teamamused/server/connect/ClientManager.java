package teamamused.server.connect;

import java.util.ArrayList;

import teamamused.common.ServiceLocator;
import teamamused.common.dtos.TransportObject;
import teamamused.common.interfaces.IPlayer;
import teamamused.server.lib.BoardManager;
import teamamused.server.lib.ClientNotificator;
import teamamused.server.lib.Game;

/**
 * 
 * Verwaltet die Clients und die Spieler
 * 
 * @author Daniel
 *
 */
public class ClientManager {
	private static ClientManager instance;

	// Instanzvariable mit den clients
	private ArrayList<ClientConnection> clients = new ArrayList<ClientConnection>();

	/**
	 * Private Konstruktor
	 */
	private ClientManager() {
	}
	
	/**
	 * Getter zur Instanz des ClientManagers
	 * @return Instanz des ClientManagers
	 */
	public static ClientManager getInstance() {
		if (instance == null) {
			instance = new ClientManager();
		}
		return instance;
	}
	/**
	 * Setzt den ClientManager zurück
	 */
	public static void resetClientManager() {
		instance = new ClientManager();
	}
	/**
	 * Getter aller verbundenen Clients
	 * @return Auflistung mit allen Clients
	 */
	public ArrayList<ClientConnection> getClients() {
		return clients;
	}
	
	/**
	 * Gibt den aktiven Spieler zurück 
	 * 
	 * @return aktuell aktiver Client
	 */
	public ClientConnection getCurrentClient() {
		if (Game.getInstance().getActivePlayer() != null) {
			String currPlayerName = Game.getInstance().getActivePlayer().getPlayerName();
			for (ClientConnection cc : this.clients) {
				if (cc.getPlayer().getPlayerName().equals(currPlayerName)) {
					return cc;
				}
			}
		}
		return clients.get(0);
	}
	
	/**
	 * Neuer Client hat sich Connected
	 * @param client neuer Client
	 */
	public void addClient(ClientConnection client) {
		this.clients.add(client);
	}
	
	/**
	 * Gleicher Client baut verbindung neu auf, infos übernehmen vom alten
	 * @param client neuer Client
	 */
	public void switchClientConnection(ClientConnection newCon, ClientConnection oldCon) {
		newCon.setPlayer(oldCon.getPlayer());
		this.addClient(newCon);
		this.clients.remove(oldCon);
	}
	
	/**
	 * Client wird entfernt
	 * @param client Client
	 */
	public void removeClient(ClientConnection client) {
		// Spieler auf dem Gameboard als abwesend markieren
		for (IPlayer player : BoardManager.getInstance().getGameBoard().getPlayers()) {
			if (player.getPlayerName().equals(client.getUsername())) {
				player.setConnected(false);
			}
		}
		// Falls der Spieler aktiv war eine neue Runde starten
		if (Game.getInstance().getActivePlayer().getPlayerName().equals(client.getUsername())) {
			Game.getInstance().startNextRound();
		}
		// Textmeldung schicken
		ClientNotificator.notifyGameMove("Spieler " + client.getUsername() + " hat das Spiel verlassen");
		// aus der Liste der zu informierenden entfernen
		this.clients.remove(client);
	}
	
	/**
	 * aktualisiert die Spiel Daten auf allen Clients
	 * @param dto TransferObjekt welches den Clients gesendet werden soll
	 */
	public synchronized void updateClients(TransportObject dto) {
		ServiceLocator.getInstance().getLogger().info("ClientManager: aktualisiere alle Clients");
		for (ClientConnection client : clients) {
			this.updateClient(client, dto);
		}
	}
	
	/**
	 * aktualisiert den aktiven Client 
	 * (der der Momentan am Spiel ist)
	 * @param dto TransferObjekt welches dem Client gesendet werden soll
	 */
	public void updateCurrentClient(TransportObject dto) {
		ServiceLocator.getInstance().getLogger().info("ClientManager: aktualisiere aktueller Client");
		this.updateClient(this.getCurrentClient(), dto);
	}
	
	/**
	 * Verbindungen zu allen Clients schliessen
	 */
	public void closeClients() {
		ServiceLocator.getInstance().getLogger().info("ClientManager: schliese alle Client Connections");
		for (ClientConnection client : clients) {
			client.close();
		}
	}
	
	private void updateClient(ClientConnection client, TransportObject dto) {
		client.sendDto(dto);
	}
}
