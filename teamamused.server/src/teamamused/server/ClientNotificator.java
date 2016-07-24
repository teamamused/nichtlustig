package teamamused.server;

import java.util.Hashtable;
import java.util.List;

import teamamused.common.db.Ranking;
import teamamused.common.models.GameBoard;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.dtos.BeanGameBoard;
import teamamused.common.dtos.TransportableProcedureCall;
import teamamused.common.dtos.BeanHelper;
import teamamused.server.connect.ClientManager;

/**
 * 
 * Damit der Server übersichtlich bleibt und wir eine hohe Kohäsion beibehalten
 * wurde der "Context of Mattern" des Servers auf eingehende Anfragen beschränkt
 * und diese Klasse für ausgehende Benachrichtigungen eingeführt.
 * 
 * @author Daniel
 *
 */
public class ClientNotificator {

	/**
	 * Benachrichtigung an alle Clients Der Server hat einen Spielzug gemacht
	 * 
	 * @param message
	 *            Spielzug des Server
	 */
	public static void notifyGameMove(String message) {
		TransportableProcedureCall gameMessage = new TransportableProcedureCall(
				TransportableProcedureCall.RemoteProcedure.ShowGameMove, new Object[] { message });
		ClientManager.getInstance().updateClients(gameMessage);
	}

	/**
	 * Benachrichtigung an den aktiven Client Dein Spielzug hat mehrere Optionen
	 * zur Kartenauswahl. Wähle die gewünschte Option aus.
	 * 
	 * @param options Auswahlmöglichkeiten für den Spieler
	 */
	public static void notifyCardsToChoose(Hashtable<Integer, List<ITargetCard>> options) {
		TransportableProcedureCall chooseCards = new TransportableProcedureCall(
				TransportableProcedureCall.RemoteProcedure.ChooseCards, new Object[] { options });
		ClientManager.getInstance().updateClients(chooseCards);
	}

	/**
	 * Benachrichtigung alle Clients: Das Spielbrett hat geändert, bitte
	 * aktulaisiert dieses
	 * 
	 * @param board
	 *            Neues Spielbrett
	 */
	public static void notifyUpdateGameBoard(GameBoard board) {
		BeanGameBoard tboard =  BeanHelper.getGameBoardBeanFromObject(board);
		TransportableProcedureCall updateBoard = new TransportableProcedureCall(
				TransportableProcedureCall.RemoteProcedure.UpdateGameBoard, new Object[] { tboard });
		ClientManager.getInstance().updateClients(updateBoard);
	}

	/**
	 * Benachrichtigung alle Clients: Der Spieler hat gewechselt, jetzt ist der
	 * übergebene Spieler an der Reihe
	 * 
	 * @param activePlayer
	 *            Spieler der neu an die Reihe kommt
	 */
	public static void notifyPlayerChanged(IPlayer activePlayer) {
		TransportableProcedureCall playerChanged = new TransportableProcedureCall(
				TransportableProcedureCall.RemoteProcedure.ChangeActivePlayer, new Object[] { activePlayer });
		ClientManager.getInstance().updateClients(playerChanged);
	}

	/**
	 * Benachrichtigung alle Clients: Das Spiel ist fertig, folgende
	 * Platzierungen wurden erreicht
	 * 
	 * @param inGameRanks
	 *            Array mit den Platzierungen
	 */
	public static void notifyGameFinished(Ranking[] inGameRanks) {
		TransportableProcedureCall gameFinished = new TransportableProcedureCall(
				TransportableProcedureCall.RemoteProcedure.FinishGame, new Object[] { inGameRanks });
		ClientManager.getInstance().updateClients(gameFinished);
	}

}
