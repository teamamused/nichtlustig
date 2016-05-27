package teamamused.server.connect;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import teamamused.common.ServiceLocator;

/**
 * 
 * Diese Klasse erstellt das Server Socket, welches auf Verbindungen der Clients wartet.
 * Sobald sich ein Client Verbindet wird ein neuer ClientListener Thread für diesen gestartet.
 * 
 * @author Daniel
 *
 */
public class ClientAwaiter extends Thread {

	/**
	 * Standard Portnumber für unser Programm
	 */
	public final static int PORT_NUMBER = 9636;

	private static int currClientId;
	private static ClientAwaiter instance;
	private boolean isWaitingForClients = true;

	private ClientAwaiter() {
	}
	
	public static ClientAwaiter getInstance() {
		if (instance == null){
			instance = new ClientAwaiter();
		}
		return instance;
	}

	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
			// Solange isWaitingForClients nicht von auserhalb auf false gesetzt
			// wurde warten wir auf Clients
			while (this.isWaitingForClients) {
				Socket clientSocket = serverSocket.accept();
				ServiceLocator.getInstance().getLogger().info("Neuer Client verbunden");
				ClientConnection client = new ClientConnection(clientSocket, currClientId++);
				ClientManager.getInstance().addClient(client);
				client.start();
			}
			// Server Socket schliessen und alle Clients schliessen
			try {
				serverSocket.close();
				for (ClientConnection cl : ClientManager.getInstance().getClients()) {
					cl.close();
				}
			} catch (Exception e) {
				ServiceLocator.getInstance().getLogger().severe(e.toString());
			}
		} catch (Exception e) {
			ServiceLocator.getInstance().getLogger().severe(e.toString());
		}
	}

	public void close() {
		this.isWaitingForClients = false;
	}

	synchronized void removeClient(int clientId) {
		// scan the array list until we found the Id
		List<ClientConnection> clients = ClientManager.getInstance().getClients();
		for (ClientConnection cl : clients) {
			if (cl.getClientId() == clientId) {
				cl.close();
				ClientManager.getInstance().removeClient(cl);
				return;
			}
		}
	}

}
