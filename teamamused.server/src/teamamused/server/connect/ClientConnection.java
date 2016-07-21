package teamamused.server.connect;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

import teamamused.common.LogHelper;
import teamamused.common.ServiceLocator;
import teamamused.common.db.PlayerRepository;
import teamamused.common.dtos.TransportObject;
import teamamused.common.dtos.TransportObject.TransportType;
import teamamused.common.dtos.TransportableAnswer;
import teamamused.common.dtos.TransportableProcedureCall;
import teamamused.common.dtos.TransportableState;
import teamamused.common.dtos.TransportableChatMessage;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.models.Player;
import teamamused.server.Server;

/**
 * 
 * Der ClientListener ist ein Thread welcher im Hintergrund läuft und auf
 * Anfragen der Clients reagiert. Es besteht pro verbundener Client ein Thread.
 * Er nimmt Anfragen entgegen und leitet sie an die zuständige Stelle weiter
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
			this.logger.info("erstelle Input und Output Streams");
			this.in = new ObjectInputStream(clientSocket.getInputStream());
			try {
				username = (String) this.in.readObject();
			} catch (Exception ex) {
				LogHelper.LogException(ex);
			}
			this.out = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			LogHelper.LogException(e);
		}
		this.logger.info("Streams erstellt");
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
				// Wenn null: Client hat Connection geschlossen					
				// Sonst: Client eine Antwort schicken, ausser wenn ein State zurückkam, dann gibt's keine mehr.
				if (dtoIn != null && dtoIn.getTransportType() != TransportType.State) {
					// inkommendes Objekt verarbeiten
					TransportObject dtoOut = processRequest(dtoIn);
					if (dtoOut != null) {
						dtoOut.send(this.out);
						// Antwort loggen
						this.logger.info("Antwort an Client: " + dtoOut.toString());
					}
				} else if (dtoIn == null) {
					// Verbindung wurde unterbrochen, loop beenden
					this.isListeningForClient = false;
				}
			}
		} catch (Exception e) {
			// Fehler loggen
			LogHelper.LogException(e);
		} finally {
			// Socket und streams alles sauber schliessen
			try { 
				if(this.in != null) {
					this.in.close();
				}
			}
			catch(Exception e) {} 
			try {
				if(this.out != null) {
					this.out.close();
				}
			}
			catch(Exception e) {}
	        try{
				if(this.clientSocket != null) {
					this.clientSocket.close();
				}
			}
			catch(Exception e) {}
		}
	}
	/**
	 * Schliesst die Connection vom Server her.
	 * Hierzu wird dem Client ein nettes Goodby gesendet. 
	 * Dieser schliesst dan von seiner seite das Socket.
	 */
	public void close() {
		try {
			this.isListeningForClient = false;
			this.sendDto(new TransportObject(TransportType.Goodbye));
		} catch (Exception e) {
			LogHelper.LogException(e);
		}
	}

	/**
	 * Verarbeitet die Clientanfrage je nach Anfrage Typ
	 * @param dtoIn Anfrage vom Client
	 * @return Antwort vom Server
	 */
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
			ClientManager.getInstance().removeClient(this);
			// Wenn er noch auf den Client wartet, kamm das goodbye vom Client sonst vom Server
			if (this.isListeningForClient) {
				this.isListeningForClient = false;
				dtoOut = new TransportObject(TransportType.Goodbye);
			} else {
				return null;
			}
			break;
		default:
			dtoOut = new TransportableState(false, "unbekanntes Transport Objekt");
			break;
		}
		dtoOut.setClient(this.username);
		return dtoOut;
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


	/**
	 * Verarbeitet Prozedur anfragen vom Client, individuell nach Prozedur
	 * @param rpc Anfrage vom Client
	 * @return Antwort vom Server
	 */
	private TransportObject executeRemoteCall(TransportableProcedureCall rpc) {
		switch (rpc.getProcedure()) {
		case StartGame:
			return Server.getInstance().startGame(rpc);
		case RollDices:
			return Server.getInstance().rollDices(rpc);
		case FixDices:
			return Server.getInstance().fixDices(rpc);
		case CardsChosen:
			return Server.getInstance().cardsChosen(rpc);
		case LoginPlayer:
			return this.validateLogin(rpc);
		case RegisterPlayer:
			return this.createLogin(rpc);
		case JoinGame:
			return Server.getInstance().connectPlayerToGame(rpc);
		case GetTopRanking:
			return Server.getInstance().getTopRanking(rpc);
		default:
			break;
		}
		return new TransportableState(false, "Remote Procedure is undefined");
	}
	
	/**
	 * Prüft ob der Spieler vorhanden ist und das Passwort stimmt
	 * @param rpc Aufruf vom Client
	 * @return Transportable Answer mit False und NULL fals nicht erfolgreich, true und Player fals erfolgreich 
	 */
	private TransportObject validateLogin(TransportableProcedureCall rpc) {
		if (rpc.getArguments() != null && rpc.getArguments().length == 2) {
			String username = (String)rpc.getArguments()[0];
			String password = (String)rpc.getArguments()[1];
			Player p = PlayerRepository.validatePlayerLogin(username, password);
			if (p != null) {
				this.player = p;
				this.username = username;
				return new TransportableAnswer(rpc, true, p);
			} else {
				if (PlayerRepository.isUsernameTaken(username)) {
					return new TransportableAnswer(rpc, false, "Passwort ungültig");
				}
				return new TransportableAnswer(rpc, false, "Benutzername ungültig");
			}
		}
		return new TransportableAnswer(rpc, false, "Sie müssen einen Benutzer namen und ein Passwort mitgeben");
	}
	
	/**
	 * Prüft ob der Spieler vorhanden ist und das Passwort stimmt
	 * @param rpc Aufruf vom Client
	 * @return Transportable Answer mit False und NULL fals nicht erfolgreich, true und Player fals erfolgreich 
	 */
	private TransportObject createLogin(TransportableProcedureCall rpc) {
		if (rpc.getArguments() != null && rpc.getArguments().length == 2) {
			String username = (String)rpc.getArguments()[0];
			String password = (String)rpc.getArguments()[1];
			Player p;
			try {
				p = PlayerRepository.createPlayer(username, password);
				if (p != null) {
					this.player = p;
					this.username = username;
					return new TransportableAnswer(rpc, true, p);
				}
			} catch (Exception e) {
				LogHelper.LogException(e);
				return new TransportableAnswer(rpc, false, e.getMessage());
			}
		}
		return new TransportableAnswer(rpc, false, "Sie müssen einen Benutzer namen und ein Passwort mitgeben");
	}

}
