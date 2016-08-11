package teamamused.client.connect;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

import teamamused.client.libs.Client;
import teamamused.client.libs.GuiNotificator;
import teamamused.common.LogHelper;
import teamamused.common.ServiceLocator;
import teamamused.common.db.Ranking;
import teamamused.common.dtos.BeanHelper;
import teamamused.common.dtos.BeanTargetCard;
import teamamused.common.dtos.TransportObject;
import teamamused.common.dtos.TransportableAnswer;
import teamamused.common.dtos.TransportableChatMessage;
import teamamused.common.dtos.BeanGameBoard;
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
	private ServerConnector connector;

	/**
	 * Konstruktor mit den Initialparametern
	 * 
	 * @param connector
	 *            Konnektor welcher den Thread erzeugt
	 * @param socket
	 *            socket zum Server
	 * @param username
	 *            Benutzername
	 */
	public ServerConnection(ServerConnector connector, Socket socket, String username) {
		super();
		this.socket = socket;
		this.connector = connector;

		try {
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.out.writeObject(username);
			this.out.flush();

			this.in = new ObjectInputStream(socket.getInputStream());

		} catch (Exception ex) {
			LogHelper.LogException(ex);
		}
		this.notifyGui = Client.getInstance().getGuiNotificator();
	}

	/**
	 * Startet den Thread welcher auf den Server hört bis die Connection
	 * geschlossen wird.
	 */
	public void run() {
		try {
			while (this.holdConnection) {
				// Auf Nachricht von Server warten
				try {
					Object obj = in.readObject();
					if (obj instanceof TransportObject) {
						TransportObject dtoIn = (TransportObject) obj;
						// Wenn es ein status ist nicht antworten
						// Ev einführen das die letzte Nachricht gecached wird
						// und falls der Status NOK ist diese nochmals senden.
						// Noch schauen wie es sich in der Praxis verhält.
						if (dtoIn.getTransportType() != TransportType.State) {
							// Nachricht verarbeiten
							TransportObject answer = this.processRequest(dtoIn);
							if (answer != null) {
								// status zurückmelden
								this.sendTransportObject(answer);
							}
							dtoIn = null;
						}
					}
				} catch (EOFException eof) {
					LogHelper.LogException(eof);
					this.log.info("End of Stream Exception, socket info: " + this.socket + " - "
							+ this.socket.isInputShutdown());
					// Neue Verbindung aufbauen
					this.connector.connect();
				}
			}

		} catch (Exception e) {
			LogHelper.LogException(e);
		} finally {

			try {
				if (this.in != null) {
					this.in.close();
				}
			} catch (Exception e) {
			}
			try {
				if (this.out != null) {
					this.out.close();
				}
			} catch (Exception e) {
			}
			try {
				if (this.socket != null) {
					this.socket.close();
				}
			} catch (Exception e) {
			}
			this.connector.setConnected(false);
		}
	}

	public void sendTransportObject(TransportObject dto) {
		if (this.out != null) {
			dto.send(this.out);
		}
	}

	/**
	 * Setzt das Flag das den Thread abbricht und die Verbindung schliesst sagt
	 * dem Server Goodbye
	 */
	public void closeConnection() {
		this.holdConnection = false;
		this.sendTransportObject(new TransportObject(TransportType.Goodbye));
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
			this.notifyGui.chatMessageRecieved((TransportableChatMessage) dtoIn);
			dtoOut = new TransportableState(true, "Nachricht erhalten");
			break;
		case Answer:
			this.processAnswer((TransportableAnswer) dtoIn);
			break;
		case Goodbye:
			// Server sagt Goodbye, wir verabschieden uns ebenfalls von ihm
			// und informieren das Gui darüber
			if (this.holdConnection) {
				this.holdConnection = false;
				this.notifyGui.serverClosedConnection();
				dtoOut = new TransportObject(TransportType.Goodbye);
			}
			// ansonsten: wir haben goodbye gesagt, der server hat freundlich
			// geanwortet nichts mehr zu tun
			break;
		default:
			dtoOut = new TransportableState(false, "unbekanntes Transport Objekt");
			break;
		}
		// Wenn es eine Antwort vom Server war oder der Server auf unser goodbye
		// antwortet sagen wir nichts
		if (dtoOut != null) {
			dtoOut.setClient(clientName);
		}
		return dtoOut;
	}

	private TransportObject executeRemoteCall(TransportableProcedureCall rpc) {
		Object[] params = rpc.getArguments();
		switch (rpc.getProcedure()) {
		case ShowGameMove:
			if (params != null && params.length >= 1) {
				if (params[0] instanceof String) {
					this.notifyGui.gameMoveDone((String) params[0]);
					return new TransportableState(true, "Client updated");
				}
			}
			break;
		case ChangeActivePlayer:
			if (params != null && params.length >= 1) {
				if (params[0] instanceof IPlayer && params[1] instanceof Integer) {
					Client.getInstance().setActivePlayer((IPlayer) params[0]);
					this.notifyGui.playerIsActivedChanged(((IPlayer) params[0]).getPlayerName().equals(
							Client.getInstance().getPlayer().getPlayerName()));
					this.notifyGui.numberOfRemeiningDicingChanged((Integer) params[1]);
					return new TransportableState(true, "Client updated");
				}
			}
			break;

		case UpdateGameBoard:
			if (params != null && params.length >= 1) {
				if (params[0] instanceof BeanGameBoard) {
					GameBoard gb = new GameBoard();
					gb.initFromTransportObject((BeanGameBoard) params[0]);
					this.notifyGui.gameBoardChanged(gb);
					return new TransportableState(true, "Client updated");
				} 
			}
			break;

		case ChooseCards:
			// Karten kommen als Hashtable of int und List of ITargetCard
			if (params != null && params.length >= 1) {
				if (params[0] instanceof BeanTargetCard[]) {
					Hashtable<Integer, List<ITargetCard>> options = BeanHelper.getChooseCardOptionsFromBean(params);
					this.notifyGui.playerHasToCooseCards(options);
					return new TransportableState(true, "Client updated");
				}
			}
			break;

		case FinishGame:
			if (params != null && params.length >= 1) {
				if (params[0] instanceof Ranking[]) {
					Client.getInstance().getGuiNotificator().gameFinished((Ranking[]) params[0]);
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
				Client.getInstance().setPlayer((IPlayer) answer.getReturnValue());
				this.notifyGui.registerSuccessful((IPlayer) answer.getReturnValue());
			} else {
				this.notifyGui.registerFailed((String) answer.getReturnValue());
			}
			break;
		case LoginPlayer:
			if (answer.isOK()) {
				Client.getInstance().setPlayer((IPlayer) answer.getReturnValue());
				this.notifyGui.loginSuccessful((IPlayer) answer.getReturnValue());
			} else {
				this.notifyGui.loginFailed((String) answer.getReturnValue());
			}
			break;
		case JoinGame:
			if (answer.isOK() && answer.getReturnValue() instanceof Integer) {
				int playerNumber = (int) answer.getReturnValue();
				Client.getInstance().getPlayer().initForGame(playerNumber);
				this.notifyGui.joinGameSuccessful((IPlayer) Client.getInstance().getPlayer());
			} else {
				if (answer.getReturnValue() instanceof String) {
					this.notifyGui.joinGameFailed((String) answer.getReturnValue());
				}
			}
			break;
		case GetTopRanking:
			if (answer.isOK() && answer.getReturnValue() instanceof Ranking[]) {
				this.notifyGui.rankingRecieved((Ranking[])answer.getReturnValue());
			}
			break;
		case RollDices:
			if (answer.isOK() && answer.getReturnValue() instanceof Integer) {
				// Wenn kein versuch mehr verbleibend, handelt das finish Round den rest
				if ((int) answer.getReturnValue() > 0) {
					this.notifyGui.numberOfRemeiningDicingChanged((int) answer.getReturnValue());
				}
			}
			break;
		default:
			break;
		}
	}
}
