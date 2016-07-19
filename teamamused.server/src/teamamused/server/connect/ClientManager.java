package teamamused.server.connect;

import java.util.ArrayList;

import teamamused.common.ServiceLocator;
import teamamused.common.dtos.TransportObject;
import teamamused.common.dtos.TransportableProcedureCall;
import teamamused.common.dtos.TransportableProcedureCall.RemoteProcedure;
import teamamused.common.interfaces.ICube;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.models.GameBoard;
import teamamused.server.Game;

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
		if (Game.getInstance().getActivePlayer() != null) {
			String currPlayerName = Game.getInstance().getActivePlayer().getPlayerName();
			for (ClientConnection cc : this.clients) {
				if (cc.getPlayer().getPlayerName() == currPlayerName) {
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
	 * Client wird entfernt
	 * @param client Client
	 */
	public void removeClient(ClientConnection client) {
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
		// TODO: zu Debugzwecken, muss wieder entfernt werden
		if (dto instanceof TransportableProcedureCall) {
			if (((TransportableProcedureCall)dto).getProcedure() == RemoteProcedure.UpdateGameBoard) {
				GameBoard x = ((GameBoard)((TransportableProcedureCall)dto).getArguments()[0]);

				for (ICube cube: x.getCubes()) {
				    System.out.println("ClientManager: " + cube.getCurrentValue().FaceValue);
				}
			} else if (((TransportableProcedureCall)dto).getProcedure() == RemoteProcedure.ChangeActivePlayer) {
				IPlayer p = ((IPlayer)((TransportableProcedureCall)dto).getArguments()[0]);
			    System.out.println("ClientManager - ChangeActivePlayer: " + p.getPlayerName());
				
			}
		}
		client.sendDto(dto);
	}
}
