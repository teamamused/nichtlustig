package teamamused.common.interfaces;

import teamamused.common.models.cubes.CubeColor;
import teamamused.common.models.cubes.CubeValue;

/**
 * 
 * Interface für einen Würfel
 * 
 * @author Daniel Hirsbrunner
 *
 */
public interface ICube {

	/**
	 * Würfeln, erzeugt eine Zufallszahl zwischen 0 und 5 und weisst diese dem aktuellen faceValue zu.
	 * @return neues CubeValue
	 */
	CubeValue dice();
	/**
	 * Gibt die Farbe des aktuellen Würfels zurück
	 * @return Würfelfalbe (Rot, Schwarz, Weiss oder Pinkt)
	 */
	CubeColor getCubeColor();
	
	/**
	 * gibt das akutell angezeigte Cube Value zurück
	 * @return aktuelles CubeValue
	 */
	CubeValue getCurrentValue();
	
	/**
	 * Gibt zurück ob der Würfel fixiert ist.
	 * @return fixiert Ja/Nein
	 */
	boolean getIsFixed();
	
	/**
	 * Setzt die Eigenschaft fixiert auf dem aktuellen Würfel.
	 * @param isFixed
	 */
	void setIsFixed(boolean isFixed);
	
	/**
	 * Die zu dem Würfel gehörende Spezialkarte
	 * @return Spezialkarte des Würfels bei FaceValue 0
	 */
	ISpecialCard getSpecialCard();
}