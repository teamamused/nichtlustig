package teamamused.server.connect;

import java.util.ArrayList;

import teamamused.common.ServiceLocator;
import teamamused.common.dtos.TransportObject;

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
	 * Getter aller verbundenen Clients
	 * @return Auflistung mit allen Clients
	 */
	public ArrayList<ClientConnection> getClients() {
		return clients;
	}
	
	/**
	 * Gibt den aktiven Spieler zurück 
	 * (muss noch programmiert werden, im Demo immer der erste)
	 * @return aktuell aktiver Client
	 */
	public ClientConnection getCurrentClient() {
		return clients.get(0);
	}
	
	/**
	 * Neuer Client hat sich Connected
	 * @param client neuer Client
	 */
	public void addClient(ClientConnection client) {
		this.clients.add(client);
		//this.updateClient(client);
	}
	
	/**
	 * Client wird entfernt
	 * @param client Client
	 */
	public void removeClient(ClientConnection client) {
		this.clients.remove(client);
		//this.updateClient(client);
	}
	
	/**
	 * aktualisiert die Spiel Daten auf allen Clients
	 */
	public void updateClients(TransportObject dto) {
		ServiceLocator.getInstance().getLogger().info("ClientManager: aktualisiere alle Clients");
		for (ClientConnection client : clients) {
			this.updateClient(client, dto);
		}
	}
	
	/**
	 * aktualisiert den aktiven Client 
	 * (der der Momentan am Spiel ist)
	 */
	public void updateCurrentClient(TransportObject dto) {
		ServiceLocator.getInstance().getLogger().info("ClientManager: aktualisiere aktueller Client");
		this.updateClient(this.getCurrentClient(), dto);
	}
	
	private void updateClient(ClientConnection client, TransportObject dto) {
		client.sendDto(dto);
	}
}
