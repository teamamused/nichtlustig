package teamamused.server;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

import teamamused.common.ServiceLocator;
import teamamused.common.interfaces.ICardHolder;
import teamamused.common.interfaces.ICube;
import teamamused.common.interfaces.IDeadCard;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.ISpecialCard;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.GameBoard;
import teamamused.common.models.cubes.CubeValue;

/**
 * 
 * Die Klasse BoardManager steuert die Kartenzuteilung von den Zielkarten,
 * Spezial- karten und Todeskarten, welche sich auf dem Board oder bei den
 * Mitspielern befinden. Ausserdem beinhaltet der BoardManager verschiedene
 * Funktionen, um die Karten bzw. den Spielzug der Spieler auszuwerten, um
 * anschliessend die Karten auf dem Spielbrett zu verschieben.
 * 
 * @author Maja Velickovic
 *
 */

public class BoardManager {
	private static BoardManager instance;
	private GameBoard board;
	private Logger log;
	private List<ITargetCard> targetCardsToDeploy = new ArrayList<ITargetCard>();
	private List<ISpecialCard> specialCardsToDeploy = new ArrayList<ISpecialCard>();
	private List<IDeadCard> deadCardsToDeploy = new ArrayList<IDeadCard>();
	private Hashtable<Integer, List<ITargetCard>> cardsToPropose;
	private List<ITargetCard> notValuatedCardsFromPlayers = new ArrayList<ITargetCard>();
	private List<ITargetCard> playerTargetCardsToValuate;

	// Hash-Tables, um zu speichern, wo welche Karten liegen (auf Spielbrett
	// oder bei Spieler
	private Hashtable<IDeadCard, ICardHolder> deadCards = new Hashtable<IDeadCard, ICardHolder>();
	private Hashtable<ISpecialCard, ICardHolder> specialCards = new Hashtable<ISpecialCard, ICardHolder>();
	private Hashtable<ITargetCard, ICardHolder> targetCards = new Hashtable<ITargetCard, ICardHolder>();

	private BoardManager() {
		this.log = ServiceLocator.getInstance().getLogger();
		board = new GameBoard();
		board.init();
		/*
		 * Die Karten werden beim initialisieren des Spiels in der Mitte auf dem
		 * Spielbrett verteilt (keine Zuteileung an Spieler)
		 */
		for (ISpecialCard card : board.getSpecialCards()) {
			this.specialCards.put(card, board);
		}
		for (IDeadCard card : board.getDeadCards()) {
			this.deadCards.put(card, board);
		}
		for (ITargetCard card : board.getTargetCards()) {
			this.targetCards.put(card, board);
		}
	}

	/**
	 * Statischer Getter für Instanz (Singleton Pattern)
	 * 
	 * @return Instanz des Boardmanagers
	 */
	public static BoardManager getInstance() {
		if (instance == null) {
			instance = new BoardManager();
		}
		return instance;
	}

	/**
	 * Setzt das aktuelle Spiel zurück. Kein Speichern des Spielstandes Spiel
	 * startet von vorne
	 */
	public static void resetBoardManager() {
		instance = new BoardManager();
	}

	/**
	 * Getter Methode für das Spielbrett
	 * 
	 * @return Spielbrett
	 */
	public GameBoard getGameBoard() {
		return this.board;
	}

	/**
	 * Getter für die Karten welche dem Spieler gemäss der Methode
	 * valuatePlayerDice zur Auswahl stehen
	 * 
	 * @return Karten, welche dem Spieler zur Auswahl vorgeschlagt werden
	 */
	public Hashtable<Integer, List<ITargetCard>> getCardsToPropose() {
		return cardsToPropose;
	}

	/**
	 * Gibt eine Liste mit noch nicht gewerteten Karten der Spieler zurück.
	 * 
	 * @return nicht gewertete Spieler-Karten
	 */
	public List<ITargetCard> getNotValuatedCardsFromPlayer() {

		for (IPlayer player : this.board.getPlayers()) {
			for (ITargetCard targetCard : player.getTargetCards()) {
				if (!targetCard.getIsValuated()) {
					if (!targetCard.getIsCoveredByDead()) {
						notValuatedCardsFromPlayers.add(targetCard);
					}
				}
			}
		}

		return notValuatedCardsFromPlayers;
	}

	/**
	 * Prüft, welche Karten der Spieler gewertet werden können nach einem
	 * abgeschlossenen Spielzug.
	 * 
	 * @param pinkCube
	 *            Wert von pinkem Würfel zur Wertung
	 */
	public void valuatePlayerCards(int pinkCube) {
		if (notValuatedCardsFromPlayers != null) {
			playerTargetCardsToValuate = notValuatedCardsFromPlayers;

			for (ITargetCard card : playerTargetCardsToValuate) {
				if (card.getCardValue() != pinkCube) {
					playerTargetCardsToValuate.remove(card);
				}
			}
		}
	}

	/**
	 * Wertet die Karten der Spieler nach einem abgeschlossenen Spielzug.
	 */
	public void valuePlayerCards() {
		if (playerTargetCardsToValuate != null) {
			for (ITargetCard card : playerTargetCardsToValuate) {
				card.setIsValuated(true);
				notValuatedCardsFromPlayers.remove(card);
				ClientNotificator.notifyGameMove("Karte " + card.toString() + " von Spieler " + targetCards.get(card)
						+ " wurde gewertet.");
			}
		}
		playerTargetCardsToValuate = null;
		ClientNotificator.notifyUpdateGameBoard(board);
	}

	/**
	 * Wertet den Würfel-Wurf des Spielers aus, sobald dieser seinen Spielzug
	 * abgeschlossen hat.
	 */
	public void valuatePlayerDice() {
		// Initialisierung der Variablen
		int sumOfCubes = 0;
		this.cardsToPropose = new Hashtable<Integer, List<ITargetCard>>();
		ArrayList<CubeValue> cubeValues = new ArrayList<CubeValue>();
		this.specialCardsToDeploy = new ArrayList<ISpecialCard>();
		this.targetCardsToDeploy = new ArrayList<ITargetCard>();
		this.deadCardsToDeploy = new ArrayList<IDeadCard>();

		// Prüfen Spezialkarten prüfen des Spielers prüfen
		// Dazu Spezialkarten prüfen
		for (ISpecialCard card : Game.getInstance().getActivePlayer().getSpecialCards()) {
			if (card.getAdditionalPoints() != 0) {
				// TODO: falls der Spieler dank der Spezialkarte Zeitreise einen Dino bekommt,
				// muss diese ihm wieder entfernt werden
				sumOfCubes += card.getAdditionalPoints();
			}
		}

		// Erreichte Würfelwerte werden geprüft
		for (ICube cube : CubeManager.getInstance().getCubes()) {
			sumOfCubes += cube.getCurrentValue().FaceValue;
			if (cube.getCurrentValue().SpecialCard != null) {
				specialCardsToDeploy.add(cube.getCurrentValue().SpecialCard);
			} else {
				cubeValues.add(cube.getCurrentValue());
			}
		}
		this.log.info("Erreichte Punkte: " + sumOfCubes + " Spezialkarten: " + specialCardsToDeploy.size());

		// Falls eine DinoKarte möglich ist, ist diese Option 1
		this.checkDinoCards(sumOfCubes);

		// Falls eine Professoren Karten möglich sind, sind diese Option 2
		this.checkProffessorenCards(cubeValues);

		// Zuletzt die Riebmann, Yeti und Lemminge, welche jeweils 2 das gleiche
		// Symbol brauchen
		this.checkRiebmannYetiLemmingeCards(cubeValues);

		// Verteilung von Todeskarten wird geprüft
		this.checkDead();
	}
	/**
	 * Hat der Spieler mehrere Karten zur Auswahl, wird über das GUI mit dieser
	 * Methode aufgerufen, für welche Zielkarten sich der Spieler entschieden
	 * hat. Die entsprechenden Karten werden ihm anschliessend zugewiesen.
	 * 
	 * @param targetCardsToTake
	 *            Zielkarten, welche der Spieler nehmen möchte
	 */
	public void takeProposedCards(List<ITargetCard> targetCardsToTake) {
		targetCardsToDeploy = new ArrayList<ITargetCard>(targetCardsToTake);
	}

	/**
	 * Verteilt die Karten (Ziel- ,Sonder-, und/oder Todeskarten) an den Spieler
	 */
	public void deployCards() {
		ICardHolder newOwner = Game.getInstance().getActivePlayer();

		// Verteilen der Zielkarten
		for (ITargetCard card : targetCardsToDeploy) {
			ICardHolder currentOwner = targetCards.get(card);
			if (!currentOwner.equals(newOwner)) {
				currentOwner.removeTargetCard(card);
				newOwner.addTargetCard(card);
				this.targetCards.put(card, newOwner);
				ClientNotificator.notifyGameMove("Zielkarte " + card + " wurde von Spieler " + currentOwner
						+ " zu Spieler " + newOwner + " verschoben.");
			}
		}

		// Verteilen der Spezialkarten
		for (ISpecialCard card : specialCardsToDeploy) {
			this.switchSpecialcardOwner(card, newOwner);
		}

		// Verteilen der Todeskarten
		if (deadCardsToDeploy != null) {
			for (IDeadCard card : deadCardsToDeploy) {
				ICardHolder currentOwner = deadCards.get(card);
				if (deadCards.get(card) == currentOwner && newOwner != board) {
					currentOwner.removeDeadCard(card);

					ITargetCard[] targetCardsOfNewOwner = newOwner.getTargetCards();

					// Prüft, ob die Todeskarte auf eine andere Karte
					// umgedreht
					// gelegt werden muss
					for (ITargetCard targetCard : targetCardsOfNewOwner) {
						if (targetCard.getIsValuated() && !targetCard.getGameCard().isDino()) {
							// Todeskarte wird umgedreht auf gewertete Karte
							// gelegt
							newOwner.addDeadCard(card, targetCard);
							targetCard.setIsCoveredByDead(true);
						} else {
							// Todeskarte wird normal neben Zielkarten
							// hingelegt
							newOwner.addDeadCard(card, null);
							targetCard.setIsValuated(false);
						}
					}

					this.deadCards.remove(card, currentOwner);
					this.deadCards.put(card, newOwner);

					ClientNotificator.notifyGameMove("Todeskarte " + card + " wurde von Spieler " + currentOwner
							+ " zu Spieler " + newOwner + " verschoben.");
				}
			}
			ClientNotificator.notifyUpdateGameBoard(board);
		}
	}

	/**
	 * Wertet die Karten der Spieler nach einem abgeschlossenen Spielzug.
	 */
	public void valuate() {
		Valuation valuateFunction = new Valuation();
		valuateFunction.valuate(instance);
	}
	
	/**
	 * Spezial Kartenhalter wechseln.
	 * 
	 * @param sc
	 *            Spezialkarte welche einen neuen Besitzer hat.
	 * @param newOwner
	 *            neuer Besitzer, Falls NULL gameboard
	 */
	public void switchSpecialcardOwner(ISpecialCard sc, ICardHolder newOwner) {
		// Wenn kein expliziter neuer Owner angegeben, auf Gameboard legen
		if (newOwner == null) {
			newOwner = this.board;
		}
		ICardHolder oldOwner = this.specialCards.get(sc);
		this.specialCards.put(sc, newOwner);
		oldOwner.removeSpecialCard(sc);
		newOwner.addSpecialCard(sc);
	}

	private void checkDead(){
		// Tod relevante Spezialkarten prüfen
		IPlayer player = Game.getInstance().getActivePlayer();
		ISpecialCard playerIsForcedToDead = null;
		ISpecialCard playerIsBewaredOfDead = null;
		for (ISpecialCard card : player.getSpecialCards()) {
			if (card.getIsForcedOfDead()) {
				playerIsForcedToDead = card;
			}
			if (card.getIsBewaredOfDead()) {
				playerIsBewaredOfDead = card;
			}
		}
		if (this.cardsToPropose.isEmpty()) {
			// Spezialkarte Killervirus wieder entfernen
			if (playerIsForcedToDead != null) {
				ClientNotificator.notifyGameMove("Der Spieler " + player.getPlayerName()
						+ " starb durch einen Killervirus");
				BoardManager.getInstance().switchSpecialcardOwner(playerIsForcedToDead, null);
			}
			// Falls der Spieler die Spezialkarte IsBewaredOfDead hat diese
			// entfernen und keinen Tod zuteilen
			if (playerIsBewaredOfDead != null) {
				ClientNotificator.notifyGameMove("Der Spieler " + player.getPlayerName()
						+ " entging dem Tod indem er ihm eine Torte ins Gesicht warf!");
				BoardManager.getInstance().switchSpecialcardOwner(playerIsForcedToDead, null);
			} else {
				for (IDeadCard deadCard : deadCards.keySet()) {
					if (deadCard.getCardCalue() == CubeManager.getInstance().getCurrentPinkCube().FaceValue) {
						deadCardsToDeploy.add(deadCard);
					}
				}
			}
		}
	}
	
	private void checkDinoCards(int sumOfCubes) {
		// Summe für die Dinos prüfen, höchste mögliche Dinokarte merken
		ITargetCard dinoCard = null;
		for (ITargetCard targetCard : targetCards.keySet()) {
			if (targetCard.getGameCard().isDino() && targetCard.getRequiredPoints() <= sumOfCubes) {
				// Prüfen ob der Dino besser ist als der bereits vorhandene
				if (dinoCard == null || dinoCard.getRequiredPoints() < targetCard.getRequiredPoints()) {
					dinoCard = targetCard;
				}
			}
		}
		if (dinoCard != null) {
			this.log.info("Vorschlag: " + this.cardsToPropose.size() + 1 + dinoCard);
			ArrayList<ITargetCard> dinos = new ArrayList<ITargetCard>();
			dinos.add(dinoCard);
			this.cardsToPropose.put(this.cardsToPropose.size() + 1, dinos);
		}
	}

	private void checkProffessorenCards(ArrayList<CubeValue> cubeValues) {
		ArrayList<ITargetCard> proffessors = new ArrayList<ITargetCard>();
		for (ITargetCard targetCard : targetCards.keySet()) {
			// Prüft die Summe der Würfel, und vergleicht diese mit der
			// Nicht Dino-Karten
			if (targetCard.getGameCard().isProffessoren() && !targetCard.getIsValuated()) {
				boolean match = true;
				for (CubeValue val : targetCard.getRequiredCubeValues()) {
					if (!cubeValues.contains(val)) {
						match = false;
					}
				}
				if (match) {
					proffessors.add(targetCard);
				}
			}
		}
		if (proffessors.size() > 0) {
			this.log.info("Vorschlag: " + cardsToPropose.size() + 1 + " Proffessoren Karten " + proffessors);
			cardsToPropose.put(cardsToPropose.size() + 1, proffessors);
		}
	}

	private void checkRiebmannYetiLemmingeCards(ArrayList<CubeValue> cubeValues) {
		ArrayList<ITargetCard> restliche = new ArrayList<ITargetCard>();
		for (ITargetCard targetCard : targetCards.keySet()) {
			if ((targetCard.getGameCard().isLemming() || targetCard.getGameCard().isRiebmann() || targetCard
					.getGameCard().isYeti()) && !targetCard.getIsValuated()) {
				boolean match = true;
				for (CubeValue val : targetCard.getRequiredCubeValues()) {
					if (!cubeValues.contains(val)) {
						match = false;
					} else {
						cubeValues.remove(val);
					}
				}
				if (match) {
					restliche.add(targetCard);
				}
			}
		}
		if (restliche.size() > 0) {
			this.log.info("Vorschlag: " + cardsToPropose.size() + 1 + " - " + restliche);
			cardsToPropose.put(cardsToPropose.size() + 1, restliche);
		}
	}

}