package teamamused.client.connect;
import java.net.Socket;
import java.util.logging.Logger;

import teamamused.common.LogHelper;
import teamamused.common.ServiceLocator;

/**
 * 
 * Diese Klasse stellt die Verbindung zum Server her.
 * Sie Startet eine ServerConnection und stellt das Socket zur Verfügung.
 * 
 * @author Daniel
 *
 */
public class ServerConnector {
	private String server;
	private String username;
	private ServerConnection connection;
	private int portNumber;
	private Socket socket;
	private Logger log = ServiceLocator.getInstance().getLogger();
	private boolean isConnected = false;

	/**
	 * Konstruktor für den Server Konektor
	 * @param server IP oder Hostname des Servers
	 * @param username Benutzername
	 * @param portNumber Server Port
	 */
	public ServerConnector(String server, String username, int portNumber) {
		this.server = server;
		this.portNumber = portNumber;
		this.username = username;
	}
	
	public boolean connect() {
		// Verbindung zum Server aufbauen
		this.log.info("Verbinde zum Server " + this.server + ":" + this.portNumber);
		try {
			socket = new Socket(this.server, this.portNumber);
			
			this.log.info("Verbindung zum Server aufgebaut. " + socket.getInetAddress() + ":" + socket.getPort());
			// Server Connection Thread starten
			this.connection = new ServerConnection(this, socket, this.username);
			this.connection.start();
			this.isConnected = true;
		} catch(Exception ex) {
		     LogHelper.LogException(ex);
			this.isConnected = false;
		}
		return this.isConnected;
	}

	public ServerConnection getConnection() {
		return this.connection;
	}

	public void close() {
		try {
			if (this.isConnected) {
				this.isConnected = false;
				this.connection.closeConnection();
			}
		} catch (Exception e) {
			LogHelper.LogException(e);
		}
	}

	/**
	 * @param isConnected besteht die Verbindung
	 */
	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	/**
	 * @return verbindung noch erwünscht / verbunden
	 */
	public boolean getConnected() {
		return this.isConnected;
	}

}
