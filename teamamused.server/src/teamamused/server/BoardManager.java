package teamamused.server;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import teamamused.common.interfaces.ICardHolder;
import teamamused.common.interfaces.ICube;
import teamamused.common.interfaces.IDeadCard;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.ISpecialCard;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.GameBoard;

/**
 * 
 * Die Klasse BoardManager steuert die Kartenzuteilung von den Zielkarten, Spezial-
 * karten und Todeskarten, welche sich auf dem Board oder bei den Mitspielern
 * befinden. Ausserdem beinhaltet der BoardManager verschiedene Funktionen, um
 * die Karten bzw. den Spielzug der Spieler auszuwerten, um anschliessend die
 * Karten auf dem Spielbrett zu verschieben.
 * 
 * @author Maja Velickovic
 *
 */

public class BoardManager {
	private static BoardManager instance;
	private GameBoard board = new GameBoard();
	private Game game = Game.getInstance();
	private ICardHolder currentOwner;
	private ICardHolder newOwner;
	private List<ITargetCard> targetCardsToDeploy;
	private List<ISpecialCard> specialCardsToDeploy;
	private List<IDeadCard> deadCardsToDeploy;
	private List<ITargetCard> cardsToPropose;
	private List<ITargetCard> notValuatedCardsFromPlayers;
	private List<ITargetCard> playerTargetCardsToValuate;
	private int pinkCubeValue;
	
	//Hash-Tables, um zu speichern, wo welche Karten liegen (auf Spielbrett oder bei Spieler
	private Hashtable<IDeadCard, ICardHolder> deadCards = new Hashtable<IDeadCard, ICardHolder>();
	private Hashtable<ISpecialCard, ICardHolder> specialCards = new Hashtable<ISpecialCard, ICardHolder>();
	private Hashtable<ITargetCard, ICardHolder> targetCards = new Hashtable<ITargetCard, ICardHolder>();
	
	private BoardManager() {
		/*Die Karten werden beim initialisieren des Spiels in der Mitte
		auf dem Spielbrett verteilt (keine Zuteileung an Spieler)*/
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
	 * Statischer Getter für Instanz (Singleton Pattern)
	 * @return Instanz des Boardmanagers
	 */
	public static BoardManager getInstance() {
		if (instance == null) {
			instance = new BoardManager();
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
	 * Gibt eine Liste mit noch nicht gewerteten Karten der Spieler zurück.
	 */
	public List<ITargetCard> getNotValuedCardsFromPlayer(){
		notValuatedCardsFromPlayers = null;
		
		for(IPlayer player: game.getPlayers()){
			for(ITargetCard targetCard : player.getTargetCards()){
				if(!targetCard.getIsValuated()){
					notValuatedCardsFromPlayers.add(targetCard);
				}
			}
		}

		return notValuatedCardsFromPlayers;
	}
	
	/**
	 * Prüft, welche Karten der Spieler gewertet werden können nach einem
	 * abgeschlossenen Spielzug.
	 * @param pinkCube Wert von pinkem Würfel zur Wertung
	 */
	public void valuatePlayerCards(int pinkCube){
		this.pinkCubeValue = pinkCube;
		
		for(ITargetCard card : playerTargetCardsToValuate){
			if(card.getCardValue() != pinkCube){
				playerTargetCardsToValuate.remove(card);
			}
		}
	}
	
	/**
	 * Wertet die Karten der Spieler nach einem abgeschlossenen Spielzug.
	 */
	public void valuePlayerCards(){
		for(ITargetCard card : playerTargetCardsToValuate){
			card.setIsValuated(true);
		}
		playerTargetCardsToValuate = null;
	}
	
	/**
	 * Wertet den Würfel-Wurf des Spielers aus, sobald dieser seinen Spielzug
	 * abgeschlossen hat.
	 * @param dicedCubes Gewürfelte Würfel des Spielers
	 */
	public void valuatePlayerDice(List<ICube> dicedCubes){
		//noch zu programmieren
	}
	
	/**
	 * Hat der Spieler mehrere Karten zur Auwahl, wird ihm mit dieser Methode
	 * eine Auswahl an Karten gegeben, welche er nehmen kann.
	 * @param targetCardsToChoose Zielkarten, welche dem Spieler zur Auswahl präsentiert werden
	 */
	public List<ITargetCard> proposeCards(List<ITargetCard> targetCardsToChoose){
		return cardsToPropose;
	}
	
	/**
	 * Hat der Spieler mehrere Karten zur Auswahl, wird über das GUI mit dieser
	 * Methode aufgerufen, für welche Zielkarten sich der Spieler entschieden
	 * hat. Die entsprechenden Karten werden ihm anschliessend zugewiesen.
	 * @param Zielkarten, welche der Spieler nehmen möchte
	 */
	public void takeProposedCards(List<ITargetCard> targetCardsToTake){
		for(ITargetCard card : targetCardsToTake){
			this.targetCardsToDeploy.add(card);
		}
	}
	
	/**
	 * Verteilt die Karten (Ziel- ,Sonder-, und/oder Todeskarten) an den Spieler
	 * @param currentOwner aktueller Besitzer der Spielkarte (Spieler oder Board)
	 * @param newOwner Spieler oder Board, wo die Karten erhält
	 */
	public void deployCards(ICardHolder currentOwner, ICardHolder newOwner){
		this.currentOwner = currentOwner;
		this.newOwner = newOwner;
		
		//Verteilen der Spezialkarten
		for(ISpecialCard card : specialCardsToDeploy) {
			this.currentOwner.removeSpecialCard(card);
			this.currentOwner.addSpecialCard(card);
			this.specialCards.remove(card, currentOwner);
			this.specialCards.put(card, newOwner);			
		}
		
		//Verteilen der Todeskarten
		for(IDeadCard card : deadCardsToDeploy) {
			this.currentOwner.removeDeadCard(card);
			this.currentOwner.addDeadCard(card, null); // noch zu ändern -> richtige Target-Karte angeben
			this.deadCards.remove(card, currentOwner);
			this.deadCards.put(card, newOwner);
		}
		
		//Verteilen der Zielkarten
		for(ITargetCard card : targetCardsToDeploy) {
			this.currentOwner.removeTargetCard(card);
			this.currentOwner.addTargetCard(card);
			this.targetCards.remove(card, currentOwner);
			this.targetCards.put(card, newOwner);
		}
		
		specialCardsToDeploy = null;
		deadCardsToDeploy = null;
		targetCardsToDeploy = null;
	}
	
	/**
	 * Wertet die Karten der Spieler nach einem abgeschlossenen Spielzug.
	 */
	public void valuate(){
		Valuation valuateFunction = new Valuation();
		valuateFunction.valuate(instance);
	}

}
