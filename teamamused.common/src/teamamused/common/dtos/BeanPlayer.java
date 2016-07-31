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
public class BeanPlayer implements Serializable {

	/** Versionierung für die Serialisierung */
	private static final long serialVersionUID = 1L;

	public String playername;
	public int playerNumber = 0;
	
	public ArrayList<GameCard> specialCards = new ArrayList<GameCard>();
	public ArrayList<BeanTargetCard> targetCards = new ArrayList<BeanTargetCard>();
	public ArrayList<GameCard> deadCards = new ArrayList<GameCard>();
	
	
}
