package teamamused.common.models.cards;

import java.util.Hashtable;
import java.util.ArrayList;

import teamamused.common.interfaces.IDeadCard;
import teamamused.common.interfaces.ISpecialCard;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.cubes.CubeColor;
import teamamused.common.models.cubes.CubeValue;
import teamamused.common.dtos.BeanTargetCard;

/**
 * Diese Klasse hat eine Fabrik funktion und ist für das erstellen der Karten
 * zust�ndig Die Karten klassen selbst sind nicht public und daher nur inerhalb
 * des Packages sichtbar.
 * 
 * @author Daniel Hirsbrunner *
 */
public class CardFactory {
	/**
	 * Initialisiert die 6 Spezialkarten
	 * 
	 * @return HashTable mit den Spezialkarten
	 */
	public static Hashtable<GameCard, ISpecialCard> getSpecialCards() {
		Hashtable<GameCard, ISpecialCard> htSpecialCards = new Hashtable<GameCard, ISpecialCard>();
		htSpecialCards.put(GameCard.SK_Ente, new SpecialCard(GameCard.SK_Ente,
				"EnteLogo.png", 1, 0, false, false, false));
		htSpecialCards.put(GameCard.SK_UFO, new SpecialCard(GameCard.SK_UFO,
				"UFOLogo.png", 0, 0, true, false, false));
		htSpecialCards.put(GameCard.SK_Clown, new SpecialCard(
				GameCard.SK_Clown, "ClownLogo.png", 0, 0, false, true, false));
		htSpecialCards.put(GameCard.SK_RoboterNF700, new SpecialCard(
				GameCard.SK_RoboterNF700, "RoboterLogo.png", -1, 0, false,
				false, false));
		htSpecialCards.put(GameCard.SK_Zeitmaschine, new SpecialCard(
				GameCard.SK_Zeitmaschine, "ZeitmaschineLogo.png", 0, 2, false,
				false, false));
		htSpecialCards.put(GameCard.SK_KillerVirus, new SpecialCard(
				GameCard.SK_KillerVirus, "KillervirusLogo.png", 0, 0, false,
				false, true));
		return htSpecialCards;
	}

	/**
	 * Initialisiert die 6 Todeskarten
	 * 
	 * @return HashTable mit den Todeskarten
	 */
	public static Hashtable<GameCard, IDeadCard> getDeadCards() {
		Hashtable<GameCard, IDeadCard> htDeadCards = new Hashtable<GameCard, IDeadCard>();
		htDeadCards
				.put(GameCard.Tod_Pudel, new DeadCard(GameCard.Tod_Pudel, 0));
		htDeadCards.put(GameCard.Tod_Eins, new DeadCard(GameCard.Tod_Eins, 1));
		htDeadCards.put(GameCard.Tod_Zwei, new DeadCard(GameCard.Tod_Zwei, 2));
		htDeadCards.put(GameCard.Tod_Drei, new DeadCard(GameCard.Tod_Drei, 3));
		htDeadCards.put(GameCard.Tod_Vier, new DeadCard(GameCard.Tod_Vier, 4));
		htDeadCards
				.put(GameCard.Tod_Fuenf, new DeadCard(GameCard.Tod_Fuenf, 5));
		return htDeadCards;
	}

	/**
	 * Initialisiert die 25 Zielkarten
	 * 
	 * @return HashTable mit den Todeskarten
	 */
	public static Hashtable<GameCard, ITargetCard> getTargetCards() {
		Hashtable<GameCard, ITargetCard> htTargetCards = new Hashtable<GameCard, ITargetCard>();
		// Riebmanns
		htTargetCards.put(GameCard.ZK_Riebmann1, new TargetCard(
				GameCard.ZK_Riebmann1, 1, 0, new CubeValue[] {
						new CubeValue(CubeColor.Black, 1),
						new CubeValue(CubeColor.Black, 1) }));
		htTargetCards.put(GameCard.ZK_Riebmann2, new TargetCard(
				GameCard.ZK_Riebmann2, 2, 0, new CubeValue[] {
						new CubeValue(CubeColor.Black, 2),
						new CubeValue(CubeColor.Black, 2) }));
		htTargetCards.put(GameCard.ZK_Riebmann3, new TargetCard(
				GameCard.ZK_Riebmann3, 3, 0, new CubeValue[] {
						new CubeValue(CubeColor.Black, 3),
						new CubeValue(CubeColor.Black, 3) }));
		htTargetCards.put(GameCard.ZK_Riebmann4, new TargetCard(
				GameCard.ZK_Riebmann4, 4, 0, new CubeValue[] {
						new CubeValue(CubeColor.Black, 4),
						new CubeValue(CubeColor.Black, 4) }));
		htTargetCards.put(GameCard.ZK_Riebmann5, new TargetCard(
				GameCard.ZK_Riebmann5, 5, 0, new CubeValue[] {
						new CubeValue(CubeColor.Black, 5),
						new CubeValue(CubeColor.Black, 5) }));
		// Yetis
		htTargetCards.put(GameCard.ZK_Yeti1, new TargetCard(GameCard.ZK_Yeti1,
				1, 0, new CubeValue[] { new CubeValue(CubeColor.Red, 1),
						new CubeValue(CubeColor.Red, 1) }));
		htTargetCards.put(GameCard.ZK_Yeti2, new TargetCard(GameCard.ZK_Yeti2,
				2, 0, new CubeValue[] { new CubeValue(CubeColor.Red, 2),
						new CubeValue(CubeColor.Red, 2) }));
		htTargetCards.put(GameCard.ZK_Yeti3, new TargetCard(GameCard.ZK_Yeti3,
				3, 0, new CubeValue[] { new CubeValue(CubeColor.Red, 3),
						new CubeValue(CubeColor.Red, 3) }));
		htTargetCards.put(GameCard.ZK_Yeti4, new TargetCard(GameCard.ZK_Yeti4,
				4, 0, new CubeValue[] { new CubeValue(CubeColor.Red, 4),
						new CubeValue(CubeColor.Red, 4) }));
		htTargetCards.put(GameCard.ZK_Yeti5, new TargetCard(GameCard.ZK_Yeti5,
				5, 0, new CubeValue[] { new CubeValue(CubeColor.Red, 5),
						new CubeValue(CubeColor.Red, 5) }));
		// Lemmings
		htTargetCards.put(GameCard.ZK_Lemming1, new TargetCard(
				GameCard.ZK_Lemming1, 1, 0, new CubeValue[] {
						new CubeValue(CubeColor.White, 1),
						new CubeValue(CubeColor.White, 1) }));
		htTargetCards.put(GameCard.ZK_Lemming2, new TargetCard(
				GameCard.ZK_Lemming2, 2, 0, new CubeValue[] {
						new CubeValue(CubeColor.White, 2),
						new CubeValue(CubeColor.White, 2) }));
		htTargetCards.put(GameCard.ZK_Lemming3, new TargetCard(
				GameCard.ZK_Lemming3, 3, 0, new CubeValue[] {
						new CubeValue(CubeColor.White, 3),
						new CubeValue(CubeColor.White, 3) }));
		htTargetCards.put(GameCard.ZK_Lemming4, new TargetCard(
				GameCard.ZK_Lemming4, 4, 0, new CubeValue[] {
						new CubeValue(CubeColor.White, 4),
						new CubeValue(CubeColor.White, 4) }));
		htTargetCards.put(GameCard.ZK_Lemming5, new TargetCard(
				GameCard.ZK_Lemming5, 5, 0, new CubeValue[] {
						new CubeValue(CubeColor.White, 5),
						new CubeValue(CubeColor.White, 5) }));
		// Professorens
		htTargetCards.put(GameCard.ZK_Professoren1, new TargetCard(
				GameCard.ZK_Professoren1, 1, 0, new CubeValue[] {
						new CubeValue(CubeColor.White, 1),
						new CubeValue(CubeColor.Black, 1),
						new CubeValue(CubeColor.Red, 1) }));
		htTargetCards.put(GameCard.ZK_Professoren2, new TargetCard(
				GameCard.ZK_Professoren2, 2, 0, new CubeValue[] {
						new CubeValue(CubeColor.White, 2),
						new CubeValue(CubeColor.Black, 2),
						new CubeValue(CubeColor.Red, 2) }));
		htTargetCards.put(GameCard.ZK_Professoren3, new TargetCard(
				GameCard.ZK_Professoren3, 3, 0, new CubeValue[] {
						new CubeValue(CubeColor.White, 3),
						new CubeValue(CubeColor.Black, 3),
						new CubeValue(CubeColor.Red, 3) }));
		htTargetCards.put(GameCard.ZK_Professoren4, new TargetCard(
				GameCard.ZK_Professoren4, 4, 0, new CubeValue[] {
						new CubeValue(CubeColor.White, 4),
						new CubeValue(CubeColor.Black, 4),
						new CubeValue(CubeColor.Red, 4) }));
		htTargetCards.put(GameCard.ZK_Professoren5, new TargetCard(
				GameCard.ZK_Professoren5, 5, 0, new CubeValue[] {
						new CubeValue(CubeColor.White, 5),
						new CubeValue(CubeColor.Black, 5),
						new CubeValue(CubeColor.Red, 5) }));
		// Dinosauriers
		htTargetCards.put(GameCard.ZK_Dinosaurier1, new TargetCard(
				GameCard.ZK_Dinosaurier1, 1, 24, new CubeValue[0]));
		htTargetCards.put(GameCard.ZK_Dinosaurier2, new TargetCard(
				GameCard.ZK_Dinosaurier2, 2, 25, new CubeValue[0]));
		htTargetCards.put(GameCard.ZK_Dinosaurier3, new TargetCard(
				GameCard.ZK_Dinosaurier3, 3, 26, new CubeValue[0]));
		htTargetCards.put(GameCard.ZK_Dinosaurier4, new TargetCard(
				GameCard.ZK_Dinosaurier4, 4, 27, new CubeValue[0]));
		htTargetCards.put(GameCard.ZK_Dinosaurier5, new TargetCard(
				GameCard.ZK_Dinosaurier5, 5, 28, new CubeValue[0]));

		return htTargetCards;
	}
	
	/**
	 * Erzeugt Targetcards für den Client anhand der übergebenen Beans
	 * Dabei wird die Logik für die Wertung ausgelassen
	 * 
	 * @param beans Transportoptimierte Objekte
	 * @return Liste mit den ITargetCards
	 */
	public static ArrayList<ITargetCard> getClientTargetCardsByBeans(BeanTargetCard[] beans) {
		ArrayList<ITargetCard> retval = new ArrayList<ITargetCard>();
		for (BeanTargetCard bean : beans) {
			ITargetCard card = new TargetCard(bean.gamecard, 0, 0, new CubeValue[0]);
			retval.add(card);
		}
		return retval;
	}

}
