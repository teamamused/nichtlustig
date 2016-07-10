package teamamused.playground.application.serverKlassen;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import teamamused.common.ServiceLocator;
import teamamused.common.interfaces.ICardHolder;
import teamamused.common.interfaces.ICube;
import teamamused.common.interfaces.IDeadCard;
import teamamused.common.interfaces.ISpecialCard;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.GameBoard;
import teamamused.common.models.cubes.CubeColor;
import teamamused.common.models.cubes.CubeValue;

/**
 * Ansatz des Boardmanagers
 * 
 * @author Daniel
 *
 */
public class Boardmanager {
	private static Boardmanager instance;
	// Instanz vom Spielbrett
	private GameBoard board = new GameBoard();
	// Hier merkt sich der Boardmanager bei welchem Kartenhalter welche Karten sind
	// Kartenhalter sind entweder die Spieler oder das Spielbrett.
	private Hashtable<ISpecialCard, ICardHolder> specialCards = new Hashtable<ISpecialCard, ICardHolder>();
	private Hashtable<IDeadCard, ICardHolder> deadCards = new Hashtable<IDeadCard, ICardHolder>();
	private Hashtable<ITargetCard, ICardHolder> targetCards = new Hashtable<ITargetCard, ICardHolder>();
	// Karten welche gemäss Würfel erlaubt sind
	private Hashtable<Integer, List<ITargetCard>> allowedCards;
	
	/**
	 * Privater Konstruktor da Sinelton Pattern
	 */
	private Boardmanager() {
		// Zu beginn liegen noch alle Karten auf dem Tisch
		for(ISpecialCard card : board.getSpecialCards()) {
			this.specialCards.put(card, board);
		}
		for(IDeadCard card : board.getDeadCards()) {
			this.deadCards.put(card, board);
		}
		for(ITargetCard card : board.getTargetCards()) {
			this.targetCards.put(card, board);
		}
	}
	
	/**
	 * Statischer Getter für Instanz da Singelton Pattern
	 * @return Instanz des Boardmanagers
	 */
	public static Boardmanager getInstance() {
		if (instance == null) {
			instance = new Boardmanager();
		}
		return instance;
	}

	/**
	 * Getter Methode für das Spielbrett
	 * @return Spielbrett
	 */
	public GameBoard getGameBoard() {
		return this.board;
	}
	
	/**
	 * Zwischen welchen Zielkarten darf der Spieler auswählen
	 * @return zur auswahlstehende Karten
	 */
	public ArrayList<ITargetCard> checkAllowedCards() {
		// gewürfelte Punkte und Würfelwerte ermitteln
		int erreichtePunkte = 0;
		ArrayList<CubeValue> erreichteValues = new ArrayList<CubeValue>();
		
		for(ICube cube : board.getCubes()) {
			// Alle Würfelwerte auser dem pinken zählen
			if (cube.getCubeColor() != CubeColor.Pink) {
				erreichtePunkte += cube.getCurrentValue().FaceValue;
				erreichteValues.add(cube.getCurrentValue()); 
			}
		}
		
		// Achtung diese Prüfung zur Zuteilung ist noch falsch...
		// Muss Maja dann richtig machen
		ArrayList<ITargetCard> erlaubteKarten = new ArrayList<ITargetCard>();
		for (ITargetCard card : targetCards.keySet()) {
			if (!card.getIsValuated()) {
				boolean darfIchHaben = true;
				for (CubeValue val : card.getRequiredCubeValues()) {
					if (!erreichteValues.contains(val)) {
						darfIchHaben = false;
					}
				}
				if (darfIchHaben && erreichtePunkte >= card.getRequiredPoints()) {
					erlaubteKarten.add(card);
				}
			}
		}
		// Hack: erlaubte Karten Riebmann hinzufügen
		erlaubteKarten.add(this.board.getTargetCards()[2]);
		return erlaubteKarten;
	}
	
	/**
	 * Beispiel mehrere Zielkarten einem neuen Spieler zuweisen
	 * @param newHolder Neuer Kartenhalter (Spieler der die Karten bekommt)
	 * @param cards Karten welche der Spieler bekommt
	 */
	public void allocateTargetCards(ICardHolder newHolder, ITargetCard[] cards) {
		 for (ITargetCard card : cards) {
			 this.allocateTargetCard(newHolder, card);
		 }
		 ClientManager.getInstance().updateClients();
	 }
	
	/**
	 * Würfeln
	 * @return
	 * 	wievielmal verbleibend
	 */
	public int rollDices() {
		int remainingDices = CubeManager.getInstance().rollDices();
		if (remainingDices == 0) {
			ServiceLocator.getInstance().getLogger().info("Boardmanager: Runde fertig");
			// Zuerst die Wertung machen
			// Valuation.getInstance().doValuation();
			
			// Danach prüfen was wir für Karten haben können
			allowedCards = new Hashtable<Integer, List<ITargetCard>>();
			allowedCards.put(1, Boardmanager.getInstance().checkAllowedCards());
			ClientManager.getInstance().getCurrentClient().chooseCards(this.allowedCards);
		}
		return remainingDices;
	}

	/**
	 * Beispiel eine Zielkarte einem neuen Spieler zuweisen
	 * @param newHolder Neuer Kartenhalter (Spieler der die Karten bekommt)
	 * @param card Karte welche der Spieler bekommt
	 */
	private void allocateTargetCard(ICardHolder newHolder, ITargetCard card) {
		// Dem neuen Kartenhalter zuteilen
		newHolder.addTargetCard(card);
		// Schauen wer die Karte zuvor hatte
		ICardHolder oldHolder = this.targetCards.get(card);
		// dem der die Karte zuvor hatte die Karte entfernen.
		oldHolder.removeTargetCard(card);
	}
}
