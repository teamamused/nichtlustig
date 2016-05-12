package teamamused.common.models.cubes;

import teamamused.common.interfaces.ICube;
import teamamused.common.interfaces.ISpecialCard;

/**
 * Hauptklasse welche die Grundfunktionalität der Würfel implementiert
 * 
 * @author Daniel Hirsbrunner
 *
 */
class Cube implements ICube {

    private int faceValue;
    private boolean isFixed = false;
    private CubeValue[] cubeValues;
    private CubeColor color;

    /**
     * Initialisierung des Würfels
     * 
     * @param color
     *            Würfelfarbe
     * @param card
     *            Spezialkarte
     */
    Cube(CubeColor color, ISpecialCard card) {
	this.cubeValues = new CubeValue[6];
	this.color = color;
	for (int i = 0; i < 6; i++) {
	    this.cubeValues[i] = new CubeValue(color, i);
	}
	this.cubeValues[0].SpecialCard = card;
	this.faceValue = 1;
    }

    /**
     * Implementierung von:
     * 
     * @see teamamused.common.interfaces.ICube#dice()
     */
    @Override
    public CubeValue dice() {
	faceValue = (int) (Math.random() * 6);
	return cubeValues[faceValue];
    }

    /**
     * Implementierung von:
     * 
     * @see teamamused.common.interfaces.ICube#getIsFixed()
     */
    @Override
    public boolean getIsFixed() {
	return this.isFixed;
    }

    /**
     * Implementierung von:
     * 
     * @see teamamused.common.interfaces.ICube#setIsFixed()
     */
    @Override
    public void setIsFixed(boolean isFixed) {
	this.isFixed = isFixed;
    }

    /**
     * Implementierung von:
     * 
     * @see teamamused.common.interfaces.ICube#getCubeColor()
     */
    @Override
    public CubeColor getCubeColor() {
	return this.color;
    }

    /**
     * Implementierung von:
     * 
     * @see teamamused.common.interfaces.ICube#getCurrentValue()
     */
    @Override
    public CubeValue getCurrentValue() {
	return this.cubeValues[this.faceValue];
    }

    /**
     * Implementierung von:
     * 
     * @see teamamused.common.interfaces.ICube#getSpecialCard()
     */
    @Override
    public ISpecialCard getSpecialCard() {
	return this.cubeValues[0].SpecialCard;
    }
}
