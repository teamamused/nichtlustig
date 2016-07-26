package teamamused.common.dtos;

import java.io.Serializable;
import java.util.ArrayList;

import teamamused.common.models.cards.GameCard;

/**
 * Transportoptimiertes Datenhaltungs Objekt für das Gameboard
 * 
 * @author Daniel
 *
 */
public class BeanGameBoard implements Serializable {

	/** Versionierung für die Serialisierung */
	private static final long serialVersionUID = 1L;

	public ArrayList<GameCard> specialCards = new ArrayList<GameCard>();
	public ArrayList<GameCard> targetCards = new ArrayList<GameCard>();
	public ArrayList<GameCard> deadCards = new ArrayList<GameCard>();
	public ArrayList<BeanPlayer> players = new ArrayList<BeanPlayer>();

	public int[] cubeValues;
	public boolean[] cubeFixed;
	
}
