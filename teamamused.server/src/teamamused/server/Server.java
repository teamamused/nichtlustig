package teamamused.server;

import java.util.List;

import teamamused.common.LogHelper;
import teamamused.common.ServiceLocator;
import teamamused.common.db.RankingRepository;
import teamamused.common.dtos.TransportObject;
import teamamused.common.dtos.TransportableAnswer;
import teamamused.common.dtos.TransportableChatMessage;
import teamamused.common.dtos.TransportableProcedureCall;
import teamamused.common.dtos.TransportableState;
import teamamused.common.interfaces.ICube;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.ITargetCard;
import teamamused.server.connect.ClientManager;

/**
 * 
 * Die Server Klasse ist der zentrale Kommunikationsknoten welche bei Anfragen
 * kontaktiert wird. Sie stellt alle Grundfunktionalitäten welche in der
 * Anforderungsspezifikation ermittelt wurden zur Verfügung und leitet alle
 * Anfragen gemäss ihrer Zuständigkeit an die Hilfsklassen weiter.
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

	public TransportObject forwardChatMessage(TransportableChatMessage msg) {
		TransportableState state = null;
		try {
			ClientManager.getInstance().updateClients(msg);
			state = new TransportableState(true, "Nachricht wurde erfolgreich weitergeleitet");
		} catch (Exception ex) {
			// Fehlerfall: Logen und dem Client melden
			ServiceLocator.getInstance().getLogger().severe(ex.getMessage());
			state = new TransportableState(false, "Nachricht konnte nicht weitergeleitet werden. " + ex.getMessage());
		}
		return state;
	}

	public TransportObject startGame(TransportableProcedureCall rpc) {
		TransportableState state = null;
		try {
			Game.getInstance().defineStartPlayer();
			ClientNotificator.notifyGameMove("Der Spieler " + rpc.getClient() + " hat das Spiel gestartet");
			ClientNotificator.notifyPlayerChanged(Game.getInstance().getActivePlayer());
			state = new TransportableState(true, "Das Spiel wurde erfolgreich gestartet.");
		} catch (Exception ex) {
			// Fehlerfall: Logen und dem Client melden
			ServiceLocator.getInstance().getLogger().severe(ex.getMessage());
			state = new TransportableState(false, "Das Spiel konnte nicht gestartet werden. " + ex.getMessage());
		}
		return state;
	}

	public TransportObject rollDices(TransportableProcedureCall rpc) {
		TransportableAnswer answer = null;
		try {
			// Würfel rollen
			int triesLeft = CubeManager.getInstance().rollDices();
			// Clients informieren
			ClientNotificator.notifyGameMove("Der Spieler " + rpc.getClient() + " ist am würfeln.");
			ClientNotificator.notifyUpdateGameBoard(BoardManager.getInstance().getGameBoard());
			answer = new TransportableAnswer(rpc, true, triesLeft);
			// wenn nicht mehr gewürfelt werden kann, Runde beenden
			if (triesLeft == 0) {
				Game.getInstance().finishRound();
			}
		} catch (Exception ex) {
			// Fehlerfall: Logen und dem Client melden
			ServiceLocator.getInstance().getLogger().severe(ex.getMessage());
			answer = new TransportableAnswer(rpc, false, "Ein Fehler tratt beim Würfeln auf. " + ex.getMessage());
		}
		return answer;
	}

	public TransportObject fixDices(TransportableProcedureCall rpc) {
		if (rpc != null && rpc.getArguments() != null && rpc.getArguments().length >= 1
				&& rpc.getArguments()[0] instanceof List<?>) {
			try {
				// Würfel fixieren
				@SuppressWarnings("unchecked")
				List<ICube> cubesToFix = (List<ICube>) rpc.getArguments()[0];
				CubeManager.getInstance().saveFixedDices(cubesToFix);
				// Clients informieren
				ClientNotificator.notifyGameMove("Der Spieler " + rpc.getClient() + " hat würfel fixiert.");
				ClientNotificator.notifyUpdateGameBoard(BoardManager.getInstance().getGameBoard());
				// falls alle Würfel fixiert sind kann die Runde beendet werden
				if (CubeManager.getInstance().getAllCubesFixed()) {
					Game.getInstance().finishRound();
				}
				return new TransportableState(true, "Die fixierten Würfel würden gespeichert");
			} catch (Exception ex) {
				// Fehlerfall: Logen und dem Client melden
				ServiceLocator.getInstance().getLogger().severe(ex.getMessage());
				return new TransportableState(false, "Die fixierten Würfel konnten nicht gespeichert werden"
						+ ex.getMessage());
			}
		}
		return new TransportableState(false, "Die fixierten Würfel konnten nicht gespeichert werden");
	}

	public TransportObject cardsChosen(TransportableProcedureCall rpc) {
		if (rpc != null && rpc.getArguments() != null && rpc.getArguments().length >= 1
				&& rpc.getArguments()[0] instanceof List<?>) {
			try {
				// Dem Brett sagen welche Karten genommen werden
				@SuppressWarnings("unchecked")
				List<ITargetCard> targetCardsToTake = (List<ITargetCard>) rpc.getArguments()[0];
				BoardManager.getInstance().takeProposedCards(targetCardsToTake);
				BoardManager.getInstance().deployCards();
				// Nächste Runde starten
				Game.getInstance().startNextRound();
				return new TransportableState(true, "Die ausgewählten Karten wurden dem Server gemeldet und die Spielrunde abgeschlossen");
			} catch (Exception ex) {
				// Fehlerfall: Logen und dem Client melden
				ServiceLocator.getInstance().getLogger().severe(ex.getMessage());
				return new TransportableState(false, "Die ausgewählten Karten konnten nicht übergeben werden"
						+ ex.getMessage());
			}
		}
		return new TransportableState(false, "Die ausgewählten Karten konnten nicht übergeben werden");
	}

	public TransportObject connectPlayerToGame(TransportableProcedureCall rpc) {
		if (rpc != null && rpc.getArguments() != null && rpc.getArguments().length >= 1
				&& rpc.getArguments()[0] instanceof IPlayer) {
			IPlayer player = (IPlayer) rpc.getArguments()[0];
			Game.getInstance().addPlayer(player);
			try {
				return new TransportableAnswer(rpc, true, player.getPlayerNumber());
			} catch (Exception ex) {
				// Fehlerfall: Logen und dem Client melden
				LogHelper.LogException(ex);
				return new TransportableAnswer(rpc, false, ex.toString());
			}
		}
		return new TransportableAnswer(rpc, false, "Der Spieler konnte dem Spiel nicht hinzugefügt werden");
	}

	public TransportableAnswer getTopRanking(TransportableProcedureCall rpc) {
		return new TransportableAnswer(rpc, true, RankingRepository.getTopRanking());
	}
}
