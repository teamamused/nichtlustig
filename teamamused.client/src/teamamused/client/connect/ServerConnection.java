package teamamused.client.connect;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

import teamamused.common.ServiceLocator;
import teamamused.common.dtos.TransportObject;
import teamamused.common.dtos.TransportableAnswer;
import teamamused.common.dtos.TransportableChatMessage;
import teamamused.common.dtos.TransportableProcedureCall;
import teamamused.common.dtos.TransportableState;
import teamamused.common.dtos.TransportObject.TransportType;
import teamamused.common.interfaces.IPlayer;

/**
 * 
 * Dieser Thread läuft im Hintergrund und wartet auf Benachtrichtigungen vom
 * Server.
 * 
 * @author Daniel
 *
 */
public class ServerConnection extends Thread {
	private Socket socket;
	ObjectInputStream in;
	ObjectOutputStream out;
	private Logger log = ServiceLocator.getInstance().getLogger();
	private boolean holdConnection = true;

	public ServerConnection(Socket socket, ObjectInputStream in, ObjectOutputStream out) {
		super();
		this.socket = socket;
		this.in = in;
		this.out = out;
	}

	public void run() {
		while (this.holdConnection) {
			try {
				// Auf Nachricht von Server warten
				TransportObject dtoIn = (TransportObject)in.readObject();
				if (dtoIn.getTransportType() != TransportType.State) {
					// Nachricht verarbeiten
					TransportObject answer = this.processRequest(dtoIn);
					// status zurückmelden
					answer.send(this.out);
				}
				
			} catch (Exception e) {
				log.severe(e.toString());
			}
		}
	}

	public Socket getSocket() {
		return socket;
	}
	
	public void closeConnection() {
		this.holdConnection = false;
	}

	private TransportObject processRequest(TransportObject dtoIn) {
		this.log.info("Nachricht vom Server erhalten: " + dtoIn.toString());
		String clientName = dtoIn.getClient();

		TransportObject dtoOut = null;
		switch (dtoIn.getTransportType()) {

		case State:
			dtoOut = new TransportableState(true, "");
			break;
		case RemoteProcedureCall:
			dtoOut = this.executeRemoteCall((TransportableProcedureCall) dtoIn);
			break;
		case ChatMessage:
			Client.getInstance().addChatMessage((TransportableChatMessage)dtoIn);
			dtoOut = new TransportableState(true, "Nachricht erhalten");
			break;
		case Answer:
			this.processAnswer((TransportableAnswer) dtoIn);
		default:
			dtoOut = new TransportableState(false, "unbekanntes Transport Objekt");
			break;
		}
		dtoOut.setClient(clientName);
		return dtoOut;
	}

	private TransportObject executeRemoteCall(TransportableProcedureCall rpc) {
		switch (rpc.getProcedure()) {
		case StartGame:
			break;
		case GameSpecialCard:
			break;
		case UpdateGameBoard:
			break;
		case RollDices:
			break;
		default:
			break;
		}
		return new TransportableState(false, "Remote Procedure is undefined");
	}


	private void processAnswer(TransportableAnswer answer) {
		switch (answer.getOriginalCall().getProcedure()) {
			case StartGame:
				break;
			case CreatePlayer:
				if (answer.isOK()) {
					Client.getInstance().setPlayer((IPlayer)answer.getReturnValue());
				}
				break;
			default:
				break;
		}
	}
}
