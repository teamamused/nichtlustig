package teamamused.common.interfaces;

import javafx.scene.image.Image;

/**
 * Interface für die Spezialkarten welche im Spiel vorhanden sind.
 * Durch die hier default definierten Methoden k�nnen alle Spezialfunktionen der Sonderkarten abgedeckt werden.
 * 
 * @author Daniel Hirsbrunner
 */
public interface ISpecialCard extends IGameCard {
	/**
	 * Gibt die Anzahl zus�tzlicher Würfelw�rfe zurück (positiv oder negativ)
	 * Bei Ente + 1, bei Roboter - 1
	 * @return +/- Zusatz W�rfe
	 */
	int getAdditionalDicing();

	/**
	 * Gibt die Anzahl zus�tzlicher Punkte beim Würfeln zurück
	 * Zeitmaschine: + Zwei Punkte beim Würfeln
	 * @return +/- Zusatz W�rfe
	 */
	int getAdditionalPoints();
	
	/**
	 * Muss der User aussetzen?
	 * UFO: eine Runde aussetzen
	 * @return aussetzen ja / nein
	 */
	boolean getHasToSkip();
	
	/**
	 * Muss der User ende Runde zwingend eine Todeskarte nehmen? 
	 * Killervirus: Du must einen Tod nehmen
	 * @return Todeskarte nehmen ja / nein
	 */
	boolean getIsForcedOfDead();
	
	/**
	 * Muss der User ende Runde keine Todeskarte nehmen? 
	 * Clown: besch�tzt vom Tod
	 * @return besch�tzt vom Tod ja / nein
	 */
	boolean getIsBewaredOfDead();
	
	/**
	 * Symbol welches dieses SpecialCard auf dem Würfel haben soll
	 * @return Würfelsymbol
	 */
	Image getCubeSymbol();

}
