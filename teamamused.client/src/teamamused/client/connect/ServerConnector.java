package teamamused.client.connect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

import teamamused.common.LogHelper;
import teamamused.common.ServiceLocator;

/**
 * 
 * Diese Klasse stellt die Verbindung zum server her.
 * Sie Startet eine ServerConnection und stellt das Socket zur Verfügung.
 * 
 * @author Daniel
 *
 */
public class ServerConnector {
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private String server;
	private String username;
	private ServerConnection connection;
	private int portNumber;
	private Logger log = ServiceLocator.getInstance().getLogger();

	public ServerConnector(String server, String username, int portNumber) {
		this.server = server;
		this.portNumber = portNumber;
		this.username = username;

	}
	
	public boolean connect() {
		// Verbindung zum Server aufbauen
		this.log.info("Verbinde zum Server " + this.server + ":" + this.portNumber);
		try {
			this.socket = new Socket(this.server, this.portNumber);
		} catch(Exception ex) {
		     LogHelper.LogException(ex);
			return false;
		}
		this.log.info("Verbindung zum Server aufgebaut. " + this.socket.getInetAddress() + ":" + this.socket.getPort());
		try {
			out = new ObjectOutputStream(socket.getOutputStream());

			try
			{
				out.writeObject(username);
			} catch (IOException eIO) {
				return false;
			}
			
			in = new ObjectInputStream(socket.getInputStream());
		} catch(Exception ex) {
		     LogHelper.LogException(ex);
			return false;
		}
		// Server Connection Thread starten
		connection = new ServerConnection(this.socket, this.in, this.out);
		connection.start();
		return true;
	}


	public void close() {
		try {
			this.connection.closeConnection();
			this.in.close();
			this.out.close();
			this.socket.close();
		} catch (Exception e) {
			this.log.severe(e.toString());
		}
	}
	
	public Socket getSocket() {
		return this.socket;
	}
	
	public ObjectInputStream getInputStream() {
		return this.in;
	}
	
	public ObjectOutputStream getOutputStream() {
		return this.out;
	}

}
