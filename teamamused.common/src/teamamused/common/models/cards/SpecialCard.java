package teamamused.common.models.cards;

import java.util.logging.Level;

import javafx.scene.image.Image;
import teamamused.common.ResourceLoader;
import teamamused.common.ServiceLocator;
import teamamused.common.interfaces.ISpecialCard;

/**
 * In deser Klasse werden die Eigenschaften der Spzeialkarten abgebildet.
 * 
 * @author Daniel Hirsbrunner
 *
 */
class SpecialCard extends AbstractCard implements ISpecialCard {
	
	private Image cubeSymbol;
	private int additionalDicing;
	private int additionalPoints;
	private boolean hasToSkip;
	private boolean isBewaredOfDead;
	private boolean isForcedOfDead;
	
	/**
	 * Konstruktor zu den Spezialkarten
	 * @param card - Enum Eintrag der Spezialkarte
	 * @param cubeSymbolImageName - Symbol auf dem Würfel
	 * @param additionalDicing
	 * @param additionalPoints
	 * @param hasToSkip
	 * @param isBewaredOfDead
	 * @param isForcedOfDead
	 */
	public SpecialCard(GameCard card, String cubeSymbolImageName, int additionalDicing, int additionalPoints, boolean hasToSkip, boolean isBewaredOfDead, boolean isForcedOfDead) {
		super(card);
		try {
			this.cubeSymbol = ResourceLoader.getImage(cubeSymbolImageName);
		} catch (Exception ex) {
		    ServiceLocator.getInstance().getLogger().log(Level.SEVERE, ex.getMessage(), ex);
		}
		this.additionalDicing = additionalDicing;
		this.additionalPoints = additionalPoints;
		this.hasToSkip = hasToSkip;
		this.isBewaredOfDead = isBewaredOfDead;
		this.isForcedOfDead = isForcedOfDead;
	}

	/**
	 * Implementierung von: 
	 * @see teamamused.common.interfaces.ISpecialCard#getCubeSymbol()
	 */
	@Override
	public Image getCubeSymbol() {
		return this.cubeSymbol;
	}

	/**
	 * Implementierung von: 
	 * @see teamamused.common.interfaces.ISpecialCard#getAdditionalDicing()
	 */
	@Override
	public int getAdditionalDicing() {
		return this.additionalDicing;
	}

	/**
	 * Implementierung von: 
	 * @see teamamused.common.interfaces.ISpecialCard#getAdditionalPoints()
	 */
	@Override
	public int getAdditionalPoints() {
		return this.additionalPoints;
	}

	/**
	 * Implementierung von: 
	 * @see teamamused.common.interfaces.ISpecialCard#getHasToSkip()
	 */
	@Override
	public boolean getHasToSkip() {
		return this.hasToSkip;
	}

	/**
	 * Implementierung von: 
	 * @see teamamused.common.interfaces.ISpecialCard#getIsForcedOfDead()
	 */
	@Override
	public boolean getIsForcedOfDead() {
		return this.isForcedOfDead;
	}

	/**
	 * Implementierung von: 
	 * @see teamamused.common.interfaces.ISpecialCard#getIsBewaredOfDead()
	 */
	@Override
	public boolean getIsBewaredOfDead() {
		return this.isBewaredOfDead;
	}

}
