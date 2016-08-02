package teamamused.common.dtos;

import java.io.Serializable;

import teamamused.common.models.cards.GameCard;

/**
 * Transportoptimiertes Datenhaltungs Objekt für die Zielkarten
 * 
 * @author Daniel
 *
 */
public class BeanTargetCard implements Serializable {

	/** Versionierung für die Serialisierung */
	private static final long serialVersionUID = 1L;
	
	public GameCard gamecard;
	
	public boolean isCoveredByDead;
	
	public boolean isValuated;
}
