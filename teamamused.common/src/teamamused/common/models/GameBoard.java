package teamamused.common.models;

import java.util.Arrays;
import java.util.Hashtable;

import teamamused.common.ServiceLocator;
import teamamused.common.interfaces.ICardHolder;
import teamamused.common.interfaces.ICube;
import teamamused.common.interfaces.IDeadCard;
import teamamused.common.interfaces.ISpecialCard;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.cards.CardFactory;
import teamamused.common.models.cards.GameCard;
import teamamused.common.models.cubes.CubeFactory;

/**
 * 
 * Die Klasse GameBoard bildet das Spielbrett ab. Sie wir initialisiert mit 6
 * Sonderkarten, 25 Zielkarten und 6 Todeskarten. Sie zeigt zu jedem Zeitpunkt
 * auf welche Karten noch auf dem Spielbrett vorhanden sind.
 * 
 * @author Daniel Hirsbrunner
 *
 */
public class GameBoard implements ICardHolder {

	Hashtable<GameCard, ISpecialCard> htSpecialCards;
	Hashtable<GameCard, ITargetCard> htTargetCards;
	Hashtable<GameCard, IDeadCard> htDeadCards;

	ICube[] cubes;

	public GameBoard() {
		this.init();
	}

	/**
	 * Initialiserung der Karten und Würfel
	 */
	private void init() {
		ServiceLocator.getInstance().getLogger().info("Initialisiere Spielbrett");
		// Spezialkarten Initialisieren
		this.htSpecialCards = CardFactory.getSpecialCards();
		ServiceLocator.getInstance().getLogger().info("Initialisiere Spielbrett - Spezialkarten erstellt");

		// Todeskarten initialisieren
		this.htDeadCards = CardFactory.getDeadCards();
		ServiceLocator.getInstance().getLogger().info("Initialisiere Spielbrett - Todeskarten erstellt");

		// Zielkarten initialisieren
		this.htTargetCards = CardFactory.getTargetCards();
		ServiceLocator.getInstance().getLogger().info("Initialisiere Spielbrett - Zielkarten erstellt");

		// Würfel initialisieren
		this.cubes = CubeFactory.getCubes(htSpecialCards);
		ServiceLocator.getInstance().getLogger().info("Initialisiere Spielbrett - Würfel erstellt");

	}

	/**
	 * Geter Methode für die Spezial Karten
	 * 
	 * @return Ein Array mit allen auf dem Tisch liegenden Spezialkarten
	 */
	public ISpecialCard[] getSpecialCards() {
		ISpecialCard[] retval = Arrays.copyOf(this.htSpecialCards.values().toArray(), this.htSpecialCards.values()
				.size(), ISpecialCard[].class);
		Arrays.sort(retval);
		return retval;
	}

	/**
	 * Enftern eine Spezialkarte vom Spieltisch
	 * 
	 * @param specialCard
	 *            Karte zum entfernen
	 * @return die entfernte Karte / null falls sie nicht vorhanden war
	 */
	public ISpecialCard removeSpecialCard(GameCard specialCard) {
		return this.htSpecialCards.remove(specialCard);
	}

	/**
	 * Fügt eine Spezialkarte dem Spieltisch wieder hinzu
	 * 
	 * @param specialCard
	 *            Karte zum hinzufügen
	 */
	public void addSpecialCard(ISpecialCard specialCard) {
		this.htSpecialCards.put(specialCard.getGameCard(), specialCard);
	}

	/**
	 * Geter Methode für die Todes Karten
	 * 
	 * @return Ein Array mit allen auf dem Tisch liegenden Todes Karten
	 */
	public IDeadCard[] getDeadCards() {
		IDeadCard[] retval = Arrays.copyOf(this.htDeadCards.values().toArray(), this.htDeadCards.values().size(),
				IDeadCard[].class);
		Arrays.sort(retval);
		return retval;
	}

	/**
	 * Enftern eine Todeskarte vom Spieltisch
	 * 
	 * @param deadCard
	 *            Karte zum entfernen
	 * @return die entfernte Karte / null falls sie nicht vorhanden war
	 */
	public IDeadCard removeDeadCard(GameCard deadCard) {
		return this.htDeadCards.remove(deadCard);
	}

	/**
	 * Geter Methode für die Ziel Karten
	 * 
	 * @return Ein Array mit allen auf dem Tisch liegenden Ziel Karten
	 */
	public ITargetCard[] getTargetCards() {
		ITargetCard[] retval = Arrays.copyOf(this.htTargetCards.values().toArray(), this.htTargetCards.values().size(),
				ITargetCard[].class);
		Arrays.sort(retval);
		return retval;
	}

	/**
	 * Enftern eine Zielkarte vom Spieltisch
	 * 
	 * @param targetCard
	 *            Karte zum entfernen
	 * @return die entfernte Karte / null falls sie nicht vorhanden war
	 */
	public ITargetCard removeTargetCard(GameCard targetCard) {
		return this.htTargetCards.remove(targetCard);
	}

	/**
	 * Geter Methode für die Würfel
	 * 
	 * @return Ein Array mit allen auf dem Tisch liegenden Würfeln
	 */
	public ICube[] getCubes() {
		return this.cubes;
	}
}
