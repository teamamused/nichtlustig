package teamamused.server.connect;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Logger;

import teamamused.common.LogHelper;
import teamamused.common.ServiceLocator;

/**
 * 
 * Diese Klasse erstellt das Server Socket, welches auf Verbindungen der Clients
 * wartet. Sobald sich ein Client Verbindet wird ein neuer ClientListener Thread
 * für diesen gestartet.
 * 
 * @author Daniel
 *
 */
public class ClientAwaiter extends Thread {

	private static final Logger log = ServiceLocator.getInstance().getLogger();

	/**
	 * Standard Portnumber für unser Programm
	 */
	public final static int PORT_NUMBER = 9636;

	/**
	 * IP Adresse des aktuellen Hosts global zur Verfügung stellen.
	 */
	public static String IP_ADDRESS;
	static {
		try {
			IP_ADDRESS = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			IP_ADDRESS = "nicht verfügbar";
			e.printStackTrace();
		}
	}

	private static int currClientId;
	private static ClientAwaiter instance;
	private boolean isWaitingForClients = true;

	private ClientAwaiter() {
	}

	public static ClientAwaiter getInstance() {
		if (instance == null) {
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
				if (this.isWaitingForClients) {
					log.info("Neuer Client verbunden");
					ClientConnection client = new ClientConnection(clientSocket, currClientId++);
					
					
					// ------------------------------------------------------------
					// Versuch mit reconect logik, noch nicht das gelbe vom ei...
					// ------------------------------------------------------------
					ClientConnection oldCon = null;
					for (ClientConnection con : ClientManager.getInstance().getClients()) {
						if (con.getUsername().equals(client.getUsername())) {
							oldCon = con;
						}
					}
					if (oldCon != null) {
						log.info("Switche Client verbindung");
						ClientManager.getInstance().switchClientConnection(client, oldCon);
					} else {
						ClientManager.getInstance().addClient(client);
					}
					client.start();
				}
			}
			// Server Socket schliessen und alle Clients schliessen
			try {
				ClientManager.getInstance().closeClients();
				serverSocket.close();
			} catch (Exception e) {
				LogHelper.LogException(e);
			}
		} catch (Exception e) {
			LogHelper.LogException(e);
		}
	}

	public void close() {
		this.isWaitingForClients = false;
		// Eine Verbindung auf mich selber aufmachen, damit serverSocket.accept() 
		// ausgelöst wird und die schleife im run() weiterläuft
		try {
			new Socket("localhost", PORT_NUMBER);
		} catch (Exception e) {
		}
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
