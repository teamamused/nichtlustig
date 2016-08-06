package teamamused.common.dtos;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import teamamused.common.interfaces.ICube;
import teamamused.common.interfaces.IDeadCard;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.ISpecialCard;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.GameBoard;
import teamamused.common.models.cards.CardFactory;
import teamamused.common.models.cards.GameCard;

public class BeanHelper {

	/**
	 * Macht aus einem GameBoard Objekt ein Tranport otpimiertes GameBoardBean
	 * 
	 * @param board GameBoard Objekt
	 * @return GameBoard Bean
	 */
	public static BeanGameBoard getGameBoardBeanFromObject(GameBoard board) {
		BeanGameBoard tgb = new BeanGameBoard();
		// Karten
		tgb.specialCards = getCardListFromSpecialCardArray(board.getSpecialCards());
		tgb.targetCards = getCardListFromTargetCardArray(board.getTargetCards());
		tgb.deadCards = getCardListFromDeadCardArray(board.getDeadCards());
		
		// Würfel
		ICube[] boardCubes = board.getCubes();
		tgb.cubeFixed = new boolean[boardCubes.length];
		tgb.cubeValues = new int[boardCubes.length];
		
		for (ICube cube : boardCubes) {
			tgb.cubeValues[cube.getCubeNumber()] = boardCubes[cube.getCubeNumber()].getCurrentValue().FaceValue;
			tgb.cubeFixed[cube.getCubeNumber()] = boardCubes[cube.getCubeNumber()].getIsFixed();
		}
		// Player
		for (IPlayer player : board.getPlayers()) {
			tgb.players.add(getPlayerBeanFromObject(player));
		}
		tgb.isGameStarted = board.getGameStartet();
		return tgb;
	}
	
	/**
	 * Macht aus einem Player Objekt ein Tranport otpimiertes PlayerBean
	 * 
	 * @param player Player Objekt
	 * @return Player Bean
	 */
	public static BeanPlayer getPlayerBeanFromObject(IPlayer player) {
		BeanPlayer tp = new BeanPlayer();
		tp.playername = player.getPlayerName();
		tp.playerNumber = player.getPlayerNumber();

		// Karten
		tp.specialCards = getCardListFromSpecialCardArray(player.getSpecialCards());
		tp.targetCards = getCardListFromTargetCardArray(player.getTargetCards());
		tp.deadCards = getCardListFromDeadCardArray(player.getDeadCards());
		return tp;
	}
	
	/**
	 * Macht aus einem Server Kartenauswahl Objekt ein Tranport otpimiertes Kartenauswahl objekt.
	 * 
	 * @param options Objekt vom Server
	 * @return Objekt Array mit den Optionen je als BeanTargetCard Array
	 */
	public static Object[] getChooseCardOptionsAsBean(Hashtable<Integer, List<ITargetCard>> options) {
		// Ein Objekt Array mit der Anzahl Optionen erstellen
		Object[] retval = new Object[options.size()];
		for (int i = 1; i <= options.size(); i++) {
			// Pro Option die TargetCards als BeanTargetCard Array
			BeanTargetCard[] opitonCards = new BeanTargetCard[options.get(i).size()];
			int idx = 0;
			for (ITargetCard card : options.get(i)) {
				opitonCards[idx] = BeanHelper.getBeanByTargetCard(card);
				idx++;
			}
			// Das BeanTargetCard Array in das Objekt Array versorgen
			retval[i-1] = opitonCards;
		}
		return retval;
	}
	
	/**
	 * Macht aus einem Tranport otpimiertes Kartenauswahl  Objekt ein Hashtable Kartenauswahl objekt.
	 * 
	 * @param options Objekt vom Transport
	 * @return Hashtable mit den Optionen
	 */
	public static Hashtable<Integer, List<ITargetCard>> getChooseCardOptionsFromBean(Object[] options) {
		// Ein Objekt Array mit der Anzahl Optionen erstellen
		Hashtable<Integer, List<ITargetCard>> retval = new Hashtable<Integer, List<ITargetCard>>();
		for (int i = 0; i < options.length; i++) {
			// Optionen starten bei 1, erstellen der ITargetCard List via CardFactory
			retval.put(i + 1, CardFactory.getClientTargetCardsByBeans((BeanTargetCard[])options[i]));
		}
		return retval;
	}
	
	private static ArrayList<GameCard> getCardListFromSpecialCardArray(ISpecialCard[] cards) {
		ArrayList<GameCard> cardList = new ArrayList<GameCard>();
		for (ISpecialCard card : cards) {
			cardList.add(card.getGameCard());
		}
		return cardList;
	}
	
	private static ArrayList<GameCard> getCardListFromDeadCardArray(IDeadCard[] cards) {
		ArrayList<GameCard> cardList = new ArrayList<GameCard>();
		for (IDeadCard card : cards) {
			cardList.add(card.getGameCard());
		}
		return cardList;
	}
	
	private static ArrayList<BeanTargetCard> getCardListFromTargetCardArray(ITargetCard[] cards) {
		ArrayList<BeanTargetCard> cardList = new ArrayList<BeanTargetCard>();
		for (ITargetCard card : cards) {
			cardList.add(getBeanByTargetCard(card));
		}
		return cardList;
	}
	
	private static BeanTargetCard getBeanByTargetCard(ITargetCard card) {
		BeanTargetCard bean = new BeanTargetCard();
		bean.gamecard = card.getGameCard();
		bean.isCoveredByDead = card.getIsCoveredByDead();
		bean.isValuated = card.getIsValuated();
		return bean;
	}
}
