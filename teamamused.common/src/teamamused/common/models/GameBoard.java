package teamamused.common.models;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import teamamused.common.ServiceLocator;
import teamamused.common.dtos.TransportableGameBoard;
import teamamused.common.interfaces.ICardHolder;
import teamamused.common.interfaces.ICube;
import teamamused.common.interfaces.IDeadCard;
import teamamused.common.interfaces.IPlayer;
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
public class GameBoard implements ICardHolder, Serializable {

	/** Versionsnummer des Transport Objektes */
	private static final long serialVersionUID = 1;
	Hashtable<GameCard, ISpecialCard> htSpecialCards;
	Hashtable<GameCard, ITargetCard> htTargetCards;
	Hashtable<GameCard, IDeadCard> htDeadCards;

	ICube[] cubes;
	private List<IPlayer> players;


	/**
	 * Initialiserung der Karten und Würfel
	 */
	public void init() {
		ServiceLocator.getInstance().getLogger().info("Initialisiere Spielbrett");
		// Spieler initialisieren
		this.players = new ArrayList<IPlayer>();
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
	 * Initialiserung der Karten und Würfel
	 */
	public void initFromTransportObject(TransportableGameBoard tgb) {
		ServiceLocator.getInstance().getLogger().info("Initialisiere Spielbrett");
		// Spieler initialisieren
		this.players = new ArrayList<IPlayer>();
		for (Player p : tgb.players) {
			this.players.add(p);
		}
		// Spielkarten
		htSpecialCards = new Hashtable<GameCard, ISpecialCard>();
		htTargetCards = new Hashtable<GameCard, ITargetCard>();
		htDeadCards = new Hashtable<GameCard, IDeadCard>();
		
		Hashtable<GameCard, IDeadCard> allDeadCards = CardFactory.getDeadCards();
		for (GameCard card : tgb.deadCards) {
			this.htDeadCards.put(card, allDeadCards.get(card));
		}
		Hashtable<GameCard, ISpecialCard> allSpecialCards = CardFactory.getSpecialCards();
		for (GameCard card : tgb.specialCards) {
			this.htSpecialCards.put(card, allSpecialCards.get(card));
		}
		Hashtable<GameCard, ITargetCard> allTargetCards = CardFactory.getTargetCards();
		for (GameCard card : tgb.targetCards) {
			this.htTargetCards.put(card, allTargetCards.get(card));
		}
		// Würfel initialisieren
		this.cubes = CubeFactory.getCubes(htSpecialCards, tgb.cubeValues, tgb.cubeFixed);
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
	public ISpecialCard removeSpecialCard(ISpecialCard specialCard) {
		return this.htSpecialCards.remove(specialCard.getGameCard());
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
	public IDeadCard removeDeadCard(IDeadCard deadCard) {
		return this.htDeadCards.remove(deadCard.getGameCard());
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
	public ITargetCard removeTargetCard(ITargetCard targetCard) {
		return this.htTargetCards.remove(targetCard.getGameCard());
	}

	/**
	 * Geter Methode für die Würfel
	 * 
	 * @return Ein Array mit allen auf dem Tisch liegenden Würfeln
	 */
	public ICube[] getCubes() {
		return this.cubes;
	}
	
	public static GameBoard getGameBoardFromXMLStringOld(String xml) {
		ByteArrayInputStream baos = new ByteArrayInputStream(xml.getBytes());
		XMLDecoder decoder = new XMLDecoder(baos);
		GameBoard board = (GameBoard) decoder.readObject();
		decoder.close();
		return board;
	}
	
	public String getTransportableXMLOld() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XMLEncoder xmlEncoder = new XMLEncoder(baos);
		xmlEncoder.writeObject(this);
		xmlEncoder.close();

		return baos.toString();
	}

	
	/**
	 * Gibt eine Liste mit mit allen Spielern zurück.
	 * @return Spielerliste
	 */
	public List<IPlayer> getPlayers(){
		return players;
	}
	
	public String toString() {
		return "Spielbrett";
	}
	
}
