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
	 * @param isFixed Würfel fixiert Ja / Nein
	 */
	void setIsFixed(boolean isFixed);
	
	/**
	 * Die zu dem Würfel gehörende Spezialkarte
	 * @return Spezialkarte des Würfels bei FaceValue 0
	 */
	ISpecialCard getSpecialCard();
	
	/**
	 * Setzt die Cube-Werte zurück auf CubeValue 1 für alle Würfel.
	 */
	void resetCubeValues();

	
	/**
	 * Gibt die interne Nummer des Würfels zurück.
	 * 0 basiert, bis 6
	 * @return 0 basierte interne Nummer
	 */
	int getCubeNumber();
}
