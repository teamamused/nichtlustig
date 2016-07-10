package teamamused.server;

import teamamused.common.ServiceLocator;
import teamamused.common.dtos.TransportableChatMessage;
import teamamused.common.dtos.TransportableProcedureCall;
import teamamused.common.dtos.TransportableState;
import teamamused.server.connect.ClientManager;

/**
 * 
 * Die Server Klasse ist der zentrale Kommunikationsknoten welche bei Anfragen kontaktiert wird. 
 * Sie stellt alle Grundfunktionalitäten welche in der Anforderungsspezifikation ermittelt wurden zur Verfügung 
 * und leitet alle Anfragen gemäss ihrer Zuständigkeit an die Hilfsklassen weiter.
 * 
 * @author Daniel
 *
 */
public class Server {
	private static Server instance;
	
	private Server() {
		super();
	}
	
	public static Server getInstance() {
		if (instance == null) {
			instance = new Server();
		}
		return instance;
	}

	public TransportableState forwardChatMessage(TransportableChatMessage msg) {
		TransportableState state = null;
		try {
			ClientManager.getInstance().updateClients(msg);
			state = new TransportableState(true, "Nachricht wurde erfolgreich weitergeleitet");
		} catch (Exception ex) {
			ServiceLocator.getInstance().getLogger().severe(ex.getMessage());
			state = new TransportableState(false, "Nachricht konnte nicht weitergeleitet werden. " + ex.getMessage());
		}
		return state;
	}

	public TransportableState startGame(TransportableProcedureCall rpc) {
		return new TransportableState(false, "Funktion noch nicht implementiert");
	}

	public TransportableState rollDices(TransportableProcedureCall rpc) {
		return new TransportableState(false, "Funktion noch nicht implementiert");
	}

	public TransportableState fixDices(TransportableProcedureCall rpc) {
		return new TransportableState(false, "Funktion noch nicht implementiert");
	}

	public TransportableState cardsChosen(TransportableProcedureCall rpc) {
		return new TransportableState(false, "Funktion noch nicht implementiert");
	}

	public TransportableState gameSpecialCard(TransportableProcedureCall rpc) {
		return new TransportableState(false, "Funktion noch nicht implementiert");
	}
	
	public TransportableState connectPlayer(TransportableProcedureCall rpc) {
		return new TransportableState(false, "Funktion noch nicht implementiert");
	}

	public TransportableState createPlayer(TransportableProcedureCall rpc) {
		return new TransportableState(false, "Funktion noch nicht implementiert");
	}

	public TransportableState getTopRanking(TransportableProcedureCall rpc) {
		return new TransportableState(false, "Funktion noch nicht implementiert");
	}

	public TransportableState validatePlayerLogin(TransportableProcedureCall rpc) {
		return new TransportableState(false, "Funktion noch nicht implementiert");
	}
}
