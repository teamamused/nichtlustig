package teamamused.common.interfaces;

import teamamused.common.models.cards.GameCard;
import javafx.scene.image.Image;
/**
 * IGameCard ist das Hauptinterface für alle Karten welche im Spiel vorhanden sind.
 * 
 * @author Daniel Hirsbrunner
 *
 */
public interface IGameCard extends Comparable<IGameCard>  {
	/**
	 * Gibt die Identifizierung zur aktuellen Karte zurück
	 * @return GameCard Eintrag
	 */
	GameCard getGameCard();
	/**
	 * Hintergrundbild der Spielkarte
	 * @return Hintergrundbild
	 */
	Image getBackgroundImage();
	/**
	 * Vordergrundbild der Spielkarte
	 * @return Vordergrundbild 
	 */
	Image getForegroundImage();
}
