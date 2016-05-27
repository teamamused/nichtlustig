package teamamused.server.connect;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

import teamamused.common.ServiceLocator;
import teamamused.common.dtos.TransportObject;
import teamamused.common.dtos.TransportObject.TransportType;
import teamamused.common.dtos.TransportableProcedureCall;
import teamamused.common.dtos.TransportableState;
import teamamused.common.dtos.TransportableChatMessage;
import teamamused.common.interfaces.IPlayer;
import teamamused.server.Server;

/**
 * 
 * Der ClientListener ist ein Thread welcher im Hintergrund läuft und auf
 * Anfragen der Clients reagiert. Es besteht pro verbundener Client ein Thread.
 * Er nimmt anfragen entgegen und leitet sie an die zuständige Stelle weiter
 * 
 * Angelehnt an die Klasse "ServerThreadForClient" aus dem Modul Software
 * Architektur 2 mit Brad.
 * 
 * @author Daniel
 *
 */
public class ClientConnection extends Thread {
	private final Logger logger = ServiceLocator.getInstance().getLogger();
	private Socket clientSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private int clientId = 0;
	private String username = "";
	private IPlayer player = null;
	private boolean isListeningForClient = true;

	public ClientConnection(Socket clientSocket, int clientId) {
		this.clientSocket = clientSocket;
		this.clientId = clientId;
		try {
			ServiceLocator.getInstance().getLogger().info("erstelle Input und Output Streams");
			this.in = new ObjectInputStream(clientSocket.getInputStream());
			try {
				username = (String) this.in.readObject();
			} catch (Exception ex) {
				this.logger.severe(ex.getMessage());
			}
			this.out = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			this.logger.severe(e.getMessage());
		}
		ServiceLocator.getInstance().getLogger().info("Streams erstellt");
	}

	public int getClientId() {
		return this.clientId;
	}

	/**
	 * Die Run Methode läuft während dem gesamten Spiel in einem Loop und wartet
	 * auf Anfragen
	 */
	@Override
	public void run() {
		// Anfrage loggen
		this.logger.info("Anfrage von Client " + clientSocket.getInetAddress().toString() + " für Server "
				+ clientSocket.getLocalAddress().toString());

		try {
			while (this.isListeningForClient) {
				// Inkommendes Transport Objekt empfangen
				TransportObject dtoIn = TransportObject.receive(this.in);

				// Client eine Antwort schicken, ausser wenn ein State zurückkam, dann gibt's keine mehr.
				if (dtoIn.getTransportType() != TransportType.State) {
					// inkommendes Objekt verarbeiten
					TransportObject dtoOut = processRequest(dtoIn);
					if (dtoOut != null) {
						dtoOut.send(this.out);
						// Antwort loggen
						this.logger.info("Antwort an Client: " + dtoOut.toString());
					}
				}
			}
		} catch (Exception e) {
			// Fehler loggen
			this.logger.severe(e.toString());
		} finally {
			// Socket schliessen
			this.close();
		}
	}

	public void close() {
		try {
			this.clientSocket.getInputStream().close();
			this.clientSocket.getOutputStream().close();
			this.clientSocket.close();
		} catch (Exception e) {
			this.logger.severe(e.toString());
		}
	}

	private TransportObject processRequest(TransportObject dtoIn) {
		this.logger.info("Nachricht vom Client erhalten: " + dtoIn.toString());

		TransportObject dtoOut = null;
		switch (dtoIn.getTransportType()) {

		case State:
			dtoOut = new TransportableState(true, "");
			break;
		case RemoteProcedureCall:
			dtoOut = this.executeRemoteCall((TransportableProcedureCall) dtoIn);
			break;
		case ChatMessage:
			dtoOut = Server.getInstance().forwardChatMessage((TransportableChatMessage)dtoIn);
			break;
		case Goodbye:
			this.isListeningForClient = false;
			ClientManager.getInstance().removeClient(this);
			dtoOut = new TransportableState(true, "Goodbye Client");
			break;
		default:
			dtoOut = new TransportableState(false, "unbekanntes Transport Objekt");
			break;
		}
		dtoOut.setClient(this.username);
		return dtoOut;
	}

	private TransportObject executeRemoteCall(TransportableProcedureCall rpc) {
		switch (rpc.getProcedure()) {
		case StartGame:
			return Server.getInstance().startGame(rpc);
		case GameSpecialCard:
			return Server.getInstance().gameSpecialCard(rpc);
		case RollDices:
			return Server.getInstance().rollDices(rpc);
		case FixDices:
			return Server.getInstance().fixDices(rpc);
		case CardsChosen:
			return Server.getInstance().cardsChosen(rpc);
		default:
			break;
		}
		return new TransportableState(false, "Remote Procedure is undefined");
	}
	
	public void sendDto(TransportObject dto) {
		dto.send(this.out);
	}
	
	public void setPlayer(IPlayer player) {
		this.player = player;
	}
	
	public IPlayer getPlayer() {
		return this.player;
	}

}
