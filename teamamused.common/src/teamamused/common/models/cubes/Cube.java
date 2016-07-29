package teamamused.common.models.cubes;

import java.io.Serializable;

import teamamused.common.interfaces.ICube;
import teamamused.common.interfaces.ISpecialCard;
import teamamused.common.models.cards.GameCard;

/**
 * Hauptklasse welche die Grundfunktionalität der Würfel implementiert
 * 
 * @author Daniel Hirsbrunner
 *
 */
class Cube implements ICube, Serializable {

	/** Versionsnummer des Transport Objektes */
	private static final long serialVersionUID = 1;

	private int faceValue;
	private int cubeNumber;
	private boolean isFixed = false;
	private CubeValue[] cubeValues;
	private CubeColor color;

	/**
	 * Initialisierung des Würfels
	 * 
	 * @param cubeNumber
	 *            Interne Nummer des Würfels
	 * @param color
	 *            Würfelfarbe
	 * @param specialCard
	 *            Spezialkarten
	 */
	Cube(int cubeNumber, CubeColor color, GameCard specialCard) {
		super();
		this.cubeNumber = cubeNumber;
		this.cubeValues = new CubeValue[6];
		this.color = color;
		this.cubeValues[0] = new CubeValue(color, 0, specialCard);
		for (int i = 1; i < 6; i++) {
			this.cubeValues[i] = new CubeValue(color, i);
		}
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
	 * Setzen des Würfel wertes. Nur inerhalb des Packages erlaubt
	 * 
	 */
	protected void setFaceValue(int faceValue) {
		this.faceValue = faceValue;
	}

	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.ICube#getSpecialCard()
	 */
	@Override
	public ISpecialCard getSpecialCard() {
		return this.cubeValues[0].getSpecialCard();
	}

	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.ICube#resetCubeValues()
	 */
	@Override
	public void resetCubeValues() {
		faceValue = 1;
	}

	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.ICube#getCubeNumber()
	 */
	@Override
	public int getCubeNumber() {
		return this.cubeNumber;
	}
}
