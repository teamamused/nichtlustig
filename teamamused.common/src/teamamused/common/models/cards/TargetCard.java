package teamamused.common.models.cards;

import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.cubes.CubeValue;

/**
 * Diese Klasse bildet die Zielkarten ab
 * 
 * @author Daniel Hirsbrunner
 *
 */
class TargetCard extends AbstractCard implements ITargetCard {

    private int cardValue;
    private int requiredPoints;
    private boolean isValuated;
    CubeValue[] requiredCubeValues;

    /**
     * Initialisiert eine neue Zielkarte
     * 
     * @param card
     *            Kartenkennung
     * @param cardValue
     *            Kartenwert (für die Wertung)
     * @param requiredPoints
     *            Punkte welche benötigt werden um diese Karte zu bekommen
     * @param cubeValues
     *            Würfel kombinationen welche benötigt werden um diese Karte zu
     *            bekommen
     */
    public TargetCard(GameCard card, int cardValue, int requiredPoints, CubeValue[] cubeValues) {
	super(card);
	this.cardValue = cardValue;
	this.requiredPoints = requiredPoints;
	this.requiredCubeValues = cubeValues;
    }

    /**
     * Implementierung von:
     * 
     * @see teamamused.common.interfaces.ITargetCard#getCardCalue()
     */
    @Override
    public int getCardCalue() {
	return this.cardValue;
    }

    /**
     * Implementierung von:
     * 
     * @see teamamused.common.interfaces.ITargetCard#getIsValuated()
     */
    @Override
    public boolean getIsValuated() {
	return this.isValuated;
    }

    /**
     * Implementierung von:
     * 
     * @see teamamused.common.interfaces.ITargetCard#setIsValuated()
     */
    @Override
    public void setIsValuated(boolean isValuated) {
	this.isValuated = isValuated;
    }

    /**
     * Implementierung von:
     * 
     * @see teamamused.common.interfaces.ITargetCard#getRequiredPoints()
     */
    @Override
    public int getRequiredPoints() {
	return this.requiredPoints;
    }

    /**
     * Implementierung von:
     * 
     * @see teamamused.common.interfaces.ITargetCard#getRequiredCubeValues()
     */
    @Override
    public CubeValue[] getRequiredCubeValues() {
	return this.requiredCubeValues;
    }

}
