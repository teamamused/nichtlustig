package teamamused.common.dtos;

import java.io.Serializable;
import java.util.ArrayList;

import teamamused.common.interfaces.ICube;
import teamamused.common.interfaces.IDeadCard;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.ISpecialCard;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.GameBoard;
import teamamused.common.models.Player;
import teamamused.common.models.cards.GameCard;

/**
 * Transportoptimiertes Datenhaltungs Objekt für das Gameboard
 * 
 * @author Daniel
 *
 */
public class TransportableGameBoard implements Serializable {

	/** Versionierung für die Serialisierung */
	private static final long serialVersionUID = 1L;

	public ArrayList<GameCard> specialCards = new ArrayList<GameCard>();
	public ArrayList<GameCard> targetCards = new ArrayList<GameCard>();
	public ArrayList<GameCard> deadCards = new ArrayList<GameCard>();
	public ArrayList<Player> players = new ArrayList<Player>();

	public int[] cubeValues;
	public boolean[] cubeFixed;
	
	public static TransportableGameBoard getTransportObjectFromGameBoard(GameBoard board) {
		// Karten
		TransportableGameBoard tgb = new TransportableGameBoard();
		for (ISpecialCard card : board.getSpecialCards()) {
			tgb.specialCards.add(card.getGameCard());
		}
		for (ITargetCard card : board.getTargetCards()) {
			tgb.targetCards.add(card.getGameCard());
		}
		for (IDeadCard card : board.getDeadCards()) {
			tgb.deadCards.add(card.getGameCard());
		}
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
			tgb.players.add(new Player(player.getPlayerName(), player.getPlayerNumber()));
		}
		
		return tgb;
	}
	
}
