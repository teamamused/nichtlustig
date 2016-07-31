package teamamused.common.dtos;

import java.util.ArrayList;

import teamamused.common.interfaces.ICube;
import teamamused.common.interfaces.IDeadCard;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.ISpecialCard;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.GameBoard;
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
			BeanTargetCard bean = new BeanTargetCard();
			bean.gamecard = card.getGameCard();
			bean.isCoveredByDead = card.getIsCoveredByDead();
			bean.isValuated = card.getIsValuated();
			cardList.add(bean);
		}
		return cardList;
	}
}
