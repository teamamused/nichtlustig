package teamamused.server;

import java.util.ArrayList;
import java.util.List;

import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.GameBoard;

/**
 * Klasse um zu trixen
 * In erster Linie um das Testing zu erleichtern eingeführt
 *  
 * @author Daniel
 *
 */
public class GameManipulator {
	
	/**
	 * Verteilt alle Ziel Karten bis nur noch 6 auf dem Spielbrett liegen
	 * Bald ist das Spiel fertig
	 */
	public static void deployCardsRandomly() {
		GameBoard board = BoardManager.getInstance().getGameBoard();
		List<IPlayer> players = board.getPlayers();
		ITargetCard[] cards = board.getTargetCards();
		ArrayList<Integer> deployedCards = new ArrayList<Integer>();
		while (deployCardsHasToGoOn(cards, deployedCards)) {
			for (IPlayer player : players) {
				int randCard = (int)(Math.random() * (double)cards.length);
				if (!deployedCards.contains(randCard) && deployCardsHasToGoOn(cards, deployedCards)) {
					deployedCards.add(randCard);
					// ca 80% der Karten sollten gewertet sein plus alle Dinos
					cards[randCard].setIsValuated((Math.random() < 0.8) || cards[randCard].getGameCard().isDino());
					board.removeTargetCard(cards[randCard]);
					player.addTargetCard(cards[randCard]);
					
				}
			}
		}
		ClientNotificator.notifyUpdateGameBoard(board);
	}
	
	private static boolean deployCardsHasToGoOn(ITargetCard[] cards, ArrayList<Integer> deployedCards) {
		return cards.length - 6 > deployedCards.size();
	}

}
