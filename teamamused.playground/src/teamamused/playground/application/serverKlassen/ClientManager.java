package teamamused.playground.application.serverKlassen;

import java.util.ArrayList;

import teamamused.common.ServiceLocator;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.IServerListener;

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
	private ArrayList<IServerListener> clients = new ArrayList<IServerListener>();

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
	 * Getter aller verbundenen Clients
	 * @return Auflistung mit allen Clients
	 */
	public ArrayList<IServerListener> getClients() {
		return clients;
	}
	
	/**
	 * Gibt den aktiven Spieler zurück 
	 * (muss noch programmiert werden, im Demo immer der erste)
	 * @return aktuell aktiver Client
	 */
	public IServerListener getCurrentClient() {
		return clients.get(0);
	}
	
	/**
	 * Neuer Client hat sich Connected
	 * @param client neuer Client
	 */
	public void addClient(IServerListener client) {
		this.clients.add(client);
		this.updateClient(client);
	}
	
	/**
	 * Weisst dem Client den Spieler zu
	 * @param client Klient
	 * @param player Spieler
	 * @return
	 * 	erfolgreich Ja / Nein
	 */
	public boolean addPlayerToClient(IServerListener client, IPlayer player) {
		for (IServerListener cl : this.clients) {
			if (cl.equals(client)) {
				cl.setPlayer(player);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * aktualisiert die Spiel Daten auf allen Clients
	 */
	public void updateClients() {
		ServiceLocator.getInstance().getLogger().info("ClientManager: aktualisiere alle Clients");
		for (IServerListener client : clients) {
			this.updateClient(client);
		}
	}
	
	/**
	 * aktualisiert den aktiven Client 
	 * (der der Momentan am Spiel ist)
	 */
	public void updateCurrentClient() {
		ServiceLocator.getInstance().getLogger().info("ClientManager: aktualisiere aktueller Client");
		this.updateClient(this.getCurrentClient());
	}
	
	private void updateClient(IServerListener client) {
		client.updateGameBoard(Boardmanager.getInstance().getGameBoard());
	}
}