package teamamused.client.connect;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

import teamamused.client.libs.Client;
import teamamused.client.libs.GuiNotificator;
import teamamused.common.ServiceLocator;
import teamamused.common.db.Ranking;
import teamamused.common.dtos.TransportObject;
import teamamused.common.dtos.TransportableAnswer;
import teamamused.common.dtos.TransportableChatMessage;
import teamamused.common.dtos.TransportableProcedureCall;
import teamamused.common.dtos.TransportableState;
import teamamused.common.dtos.TransportObject.TransportType;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.GameBoard;

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
	private GuiNotificator notifyGui;
	private boolean holdConnection = true;

	/**
	 * Konstruktor mit den Initialparametern
	 * @param socket	socket zum Server
	 * @param in		Input Stream des Sockets
	 * @param out		Outputstream des Sockets
	 */
	public ServerConnection(Socket socket, ObjectInputStream in, ObjectOutputStream out) {
		super();
		this.socket = socket;
		this.in = in;
		this.out = out;
		this.notifyGui = Client.getInstance().getGuiNotificator();
	}

	/**
	 * Startet den Thread welcher auf den Server hört bis die Connection geschlossen wird.
	 */
	public void run() {
		while (this.holdConnection) {
			try {
				// Auf Nachricht von Server warten
				TransportObject dtoIn = (TransportObject)in.readObject();
				// Wenn es ein status ist nicht antworten
				// Ev einführen das die letzte Nachricht gechaed wird und falls der Status 
				// NOK ist diese nochmals senden. Noch schauen wie es sich in der Praxis verhält.
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
	/**
	 * Getter für das Socket 
	 * @return Socket zum server
	 */
	public Socket getSocket() {
		return socket;
	}
	
	/**
	 * Setzt das Flag das den Thread abbricht und die Verbind schliest
	 */
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
			this.notifyGui.addChatMessage((TransportableChatMessage)dtoIn);
			dtoOut = new TransportableState(true, "Nachricht erhalten");
			break;
		case Answer:
			this.processAnswer((TransportableAnswer) dtoIn);

		case Goodbye:
			this.notifyGui.serverClosedConnection();
			this.closeConnection();
			break;
		default:
			dtoOut = new TransportableState(false, "unbekanntes Transport Objekt");
			break;
		}
		dtoOut.setClient(clientName);
		return dtoOut;
	}

	@SuppressWarnings("unchecked")
	private TransportObject executeRemoteCall(TransportableProcedureCall rpc) {
		Object[] params = rpc.getArguments();
		switch (rpc.getProcedure()) {
		case ShowGameMove:
			if (params != null && params.length >= 1) {
				if (params[0] instanceof String) {
					this.notifyGui.gameMoveDone((String)params[0]);
					return new TransportableState(true, "Client updated");
				}
			}
			break;
		case ChangeActivePlayer:
			if (params != null && params.length >= 1) {
				if (params[0] instanceof IPlayer) {
					this.notifyGui.activeChanged(((IPlayer)params[0]).getPlayerName() == Client.getInstance().getPlayer().getPlayerName());
					return new TransportableState(true, "Client updated");
				}
			}
			break;
			
		case UpdateGameBoard:
			if (params != null && params.length >= 1) {
				if (params[0] instanceof GameBoard) {
					this.notifyGui.updateGameBoard((GameBoard)params[0]);
					return new TransportableState(true, "Client updated");
				}
			}
			break;
			
		case ChooseCards:
			// Karten kommen als Hashtable of int und List of ITargetCard
			if (params != null && params.length >= 1) {
				if (params[0] instanceof Hashtable<?, ?>) {
					this.notifyGui.chooseCards((Hashtable<Integer, List<ITargetCard>>)params[0]);
					return new TransportableState(true, "Client updated");
				}
			}
			break;
			
		case FinishGame:
			if (params != null && params.length >= 1) {
				if (params[0] instanceof Ranking[]) {
					Client.getInstance().getGuiNotificator().gameFinished((Ranking[])params[0]);
					return new TransportableState(true, "Client updated");
				}
			}
			break;
		default:
			return new TransportableState(false, "Remote Procedure not implemented");
		}
		return new TransportableState(false, "Remote Procedure is undefined");
	}


	private void processAnswer(TransportableAnswer answer) {
		switch (answer.getOriginalCall().getProcedure()) {
			case StartGame:
				break;
			case RegisterPlayer:
				if (answer.isOK()) {
					Client.getInstance().setPlayer((IPlayer)answer.getReturnValue());
					this.notifyGui.registerSuccessful((IPlayer)answer.getReturnValue());
				} else {
					this.notifyGui.registerFailed((String)answer.getReturnValue());
				}
				break;
			case LoginPlayer:
				if (answer.isOK()) {
					Client.getInstance().setPlayer((IPlayer)answer.getReturnValue());
					this.notifyGui.loginSuccessful((IPlayer)answer.getReturnValue());
				} else {
					this.notifyGui.loginFailed((String)answer.getReturnValue());
				}
				break;
			case JoinGame:
				if (answer.isOK()) {
					Client.getInstance().setPlayer((IPlayer)answer.getReturnValue());
					this.notifyGui.joinGameSuccessful((IPlayer)answer.getReturnValue());
				} else {
					this.notifyGui.joinGameFailed((String)answer.getReturnValue());
				}
				break;
			default:
				break;
		}
	}
}
