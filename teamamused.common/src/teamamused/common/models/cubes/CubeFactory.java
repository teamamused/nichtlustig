package teamamused.common.models.cubes;

import java.util.Hashtable;

import teamamused.common.interfaces.ICube;
import teamamused.common.interfaces.ISpecialCard;
import teamamused.common.models.cards.GameCard;

/**
 * Diese Klasse ist die Fabrik Klasse für die Würfel, welche nur inerhalb des
 * Packages sichtbar sind.
 * 
 * @author Daniel Hirsbrunner
 *
 */
public class CubeFactory {
	/**
	 * Erstellt die 7 Spielbrett Würfel und weist Ihnen die jeweilige
	 * Spezialkarte zu
	 * 
	 * @param htSpecialCards
	 *            Hashtable mit den Spezialkarten
	 * @return Array mit den Würfeln
	 */
	public static ICube[] getCubes(Hashtable<GameCard, ISpecialCard> htSpecialCards) {
		Cube white1 = new Cube(CubeColor.White, htSpecialCards.get(GameCard.SK_Zeitmaschine));
		Cube white2 = new Cube(CubeColor.White, htSpecialCards.get(GameCard.SK_KillerVirus));
		Cube red1 = new Cube(CubeColor.Red, htSpecialCards.get(GameCard.SK_Clown));
		Cube red2 = new Cube(CubeColor.Red, htSpecialCards.get(GameCard.SK_RoboterNF700));
		Cube black1 = new Cube(CubeColor.Black, htSpecialCards.get(GameCard.SK_Ente));
		Cube black2 = new Cube(CubeColor.Black, htSpecialCards.get(GameCard.SK_UFO));
		Cube dead = new Cube(CubeColor.Pink, null);
		return new ICube[] { white1, white2, red1, red2, black1, black2, dead };
	}
}
