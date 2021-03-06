package teamamused.common.interfaces;

import javafx.scene.canvas.Canvas;
import teamamused.common.models.cubes.CubeValue;
/**
 * 
 * Interface für die Zielkarten welche in der Mitte des Tisches liegen.
 * @author Daniel Hirsbrunner
 *
 */
public interface ITargetCard extends IGameCard {
	/**
	 * Welche Augenzahl hat diese Zielkarte (wichtig für Wertung)
	 * @return Augenzahl (int zwischen 0 und 5)
	 */
	int getCardValue();

	/**
	 * Wurde diese Karte bereits gewertet.
	 * @return Karte gewertet ja / nein
	 */
	boolean getIsValuated();

	/**
	 * Setzt das gewertet Flag auf dieser Karte.
	 * @param isValuated Karte gewertet ja / nein
	 */
	void setIsValuated(boolean isValuated);

	/**
	 * Liegt auf dieser Karte ein Tod.
	 * @return Karte von Tod abgedeckt ja / nein
	 */
	boolean getIsCoveredByDead();

	/**
	 * Setzt das Flag isCoveredByDead welches sagt ob die KArte von einem Tod abgedeckt ist.
	 * @param isCovered Karte von Tod abgedeckt ja / nein
	 */
	void setIsCoveredByDead(boolean isCovered);
	
	/**
	 * Wieviele Punkte müssen erWürfelt werden um diese Karte zu erhalten.
	 * Nur bei Dinos einen Wert, ansonsten 0
	 * @return Punktzahl (int zwischen 0 und 28)
	 */
	int getRequiredPoints();

	/**
	 * Welche Würfelwerte müssen geWürfelt werden damit man diese Karte erhalten kann.
	 * Bei Dinos keine, da via Points. Bei 2er Yeti Z.B eine Liste mit den 2 Eintr�gen { CubeValue(rot, 2), CubeValue(rot, 2)} 
	 * @return Punktzahl (int zwischen 0 und 28)
	 */
	CubeValue[] getRequiredCubeValues();
	
	/**
	 * Gibt das Bild der entsprechenden Karte je nach Gewertet und gestorben status als Canvas zurück
	 * @param size Auflösung des Canvas
	 * @return Kartenbild
	 */
	Canvas toCanvas(int size);
}
