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
		return CubeFactory.getCubes(htSpecialCards, null, null);
	}
	/**
	 * Erstellt die 7 Spielbrett Würfel, weist Ihnen die jeweilige
	 * Spezialkarte zu und setzt das CubeValue
	 * 
	 * @param htSpecialCards
	 *            Hashtable mit den Spezialkarten
	 * @param values
	 *            FaceValues der Würfel
	 * @param fixed
	 *            ob die Würfel fixiert sind
	 * @return Array mit den Würfeln
	 */
	public static ICube[] getCubes(Hashtable<GameCard, ISpecialCard> htSpecialCards, int[] values, boolean[] fixed) {
		Cube white1 = new Cube(0, CubeColor.White, GameCard.SK_Zeitmaschine);
		Cube white2 = new Cube(1, CubeColor.White, GameCard.SK_KillerVirus);
		Cube red1 = new Cube(2, CubeColor.Red, GameCard.SK_Clown);
		Cube red2 = new Cube(3, CubeColor.Red, GameCard.SK_RoboterNF700);
		Cube black1 = new Cube(4, CubeColor.Black, GameCard.SK_Ente);
		Cube black2 = new Cube(5, CubeColor.Black, GameCard.SK_UFO);
		Cube dead = new Cube(6, CubeColor.Pink, null);

		if (values != null) {
			white1.setFaceValue(values[white1.getCubeNumber()]);
			white2.setFaceValue(values[white2.getCubeNumber()]);
			red1.setFaceValue(values[red1.getCubeNumber()]);
			red2.setFaceValue(values[red2.getCubeNumber()]);
			black1.setFaceValue(values[black1.getCubeNumber()]);
			black2.setFaceValue(values[black2.getCubeNumber()]);
			dead.setFaceValue(values[dead.getCubeNumber()]);
		}
		if (fixed != null) {
			white1.setIsFixed(fixed[white1.getCubeNumber()]);
			white2.setIsFixed(fixed[white2.getCubeNumber()]);
			red1.setIsFixed(fixed[red1.getCubeNumber()]);
			red2.setIsFixed(fixed[red2.getCubeNumber()]);
			black1.setIsFixed(fixed[black1.getCubeNumber()]);
			black2.setIsFixed(fixed[black2.getCubeNumber()]);
			dead.setIsFixed(fixed[dead.getCubeNumber()]);
		}
		return new ICube[] { white1, white2, red1, red2, black1, black2, dead };
	}
}
