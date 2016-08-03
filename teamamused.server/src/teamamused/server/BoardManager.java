package teamamused.server;

import java.util.ArrayList;
import java.util.Arrays;
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
import teamamused.common.models.cards.GameCard;
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
	private Hashtable<Integer, List<ITargetCard>> cardsToPropose = new Hashtable<Integer, List<ITargetCard>>();
	private List<ITargetCard> notValuatedCardsFromPlayers = new ArrayList<ITargetCard>();
	private List<ITargetCard> playerTargetCardsToValuate = new ArrayList<ITargetCard>();

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
	public synchronized Hashtable<Integer, List<ITargetCard>> getCardsToPropose() {
		return cardsToPropose;
	}

	/**
	 * Gibt eine Liste mit noch nicht gewerteten Karten der Mitspieler zurück.
	 * 
	 * @return nicht gewertete Spieler-Karten
	 */
	public List<ITargetCard> getNotValuatedCardsFromPlayer() {
		notValuatedCardsFromPlayers.clear();
		for (IPlayer player : this.board.getPlayers()) {
			for (ITargetCard targetCard : player.getTargetCards()) {
				if (!targetCard.getIsValuated() && !targetCard.getIsCoveredByDead()) {
					notValuatedCardsFromPlayers.add(targetCard);
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
		if (!notValuatedCardsFromPlayers.isEmpty()) {
			for (ITargetCard card : notValuatedCardsFromPlayers) {
				if (card.getCardValue() == pinkCube) {
					playerTargetCardsToValuate.add(card);
				}
			}
		}
	}

	/**
	 * Wertet die Karten der Spieler nach einem abgeschlossenen Spielzug.
	 */
	public void valuePlayerCards() {
		if (!playerTargetCardsToValuate.isEmpty()) {
			for (ITargetCard card : playerTargetCardsToValuate) {
				card.setIsValuated(true);
				notValuatedCardsFromPlayers.remove(card);
				ClientNotificator.notifyGameMove("Karte " + card.toString() + " von Spieler " + targetCards.get(card)
						+ " wurde gewertet.");
			}

			playerTargetCardsToValuate.clear();
		}

		ClientNotificator.notifyUpdateGameBoard(board);
	}

	/**
	 * Wertet den Würfel-Wurf des Spielers aus, sobald dieser seinen Spielzug
	 * abgeschlossen hat.
	 */
	public void valuatePlayerDice() {
		// Initialisierung / Zürücksetzen der Variablen
		int sumOfCubes = 0;
		this.cardsToPropose.clear();
		this.specialCardsToDeploy.clear();
		this.targetCardsToDeploy.clear();
		this.deadCardsToDeploy.clear();
		ArrayList<CubeValue> cubeValues = new ArrayList<CubeValue>();

		// Prüfen Spezialkarten prüfen des Spielers prüfen
		// Dazu Spezialkarten prüfen
		for (ISpecialCard card : Game.getInstance().getActivePlayer().getSpecialCards()) {
			if (card.getAdditionalPoints() != 0) {
				// Wenn der Spieler die Spezialkarte Zeitmaschine hat, bekommt
				// er zusätzlich zwei Würfelaugen
				sumOfCubes += card.getAdditionalPoints();
			}
		}

		// Erreichte Würfelwerte werden geprüft
		for (ICube cube : CubeManager.getInstance().getCubes()) {
			// der Wert des pinken Würfels wird nicht zur Summe gezählt
			if (cube.getCubeColor() != CubeManager.getInstance().getCurrentPinkCube().Color) {
				sumOfCubes += cube.getCurrentValue().FaceValue;
			}
			if (cube.getCurrentValue().getSpecialCard() != null) {
				specialCardsToDeploy.add(cube.getCurrentValue().getSpecialCard());
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
			
			if (card.getGameCard().isDino() && !currentOwner.equals(newOwner)) {
				// Dinos sind sofort gewertet
				card.setIsValuated(true);
				// Entfernt die Sonderkarte SK_Zeitmaschine vom Spieler,
				// wenn die Dino-Karte verteilt wird
				for (ISpecialCard specialCard : newOwner.getSpecialCards()) {
					if (specialCard.getGameCard() == GameCard.SK_Zeitmaschine) {
						this.switchSpecialcardOwner(specialCard, null);		
					}
				}
			}
			
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
		boolean matchDeadCardOnTarget = false;
		if (deadCardsToDeploy != null) {
			for (IDeadCard card : deadCardsToDeploy) {
				ICardHolder currentOwner = deadCards.get(card);
				if (deadCards.get(card) == currentOwner && newOwner != board) {
					currentOwner.removeDeadCard(card);
					ITargetCard[] targetCardsOfNewOwner = newOwner.getTargetCards();

					// Prüft, ob die Todeskarte auf eine andere Karte
					// umgedreht
					// gelegt werden muss
					if(targetCardsOfNewOwner.length > 0)
					{
						for (ITargetCard targetCard : targetCardsOfNewOwner) {
							if(!matchDeadCardOnTarget){
								if (targetCard.getIsValuated() && !targetCard.getGameCard().isDino()) {
									// Todeskarte wird umgedreht auf gewertete Karte
									// gelegt
									newOwner.addDeadCard(card, targetCard);
									targetCard.setIsCoveredByDead(true);
									ClientNotificator.notifyGameMove("Todeskarte " + card + " wurde auf Zielkarte " +
											targetCard + " gelegt.");
									matchDeadCardOnTarget = true;
								} else {
									// Todeskarte wird normal neben Zielkarten
									// hingelegt
									newOwner.addDeadCard(card, null);
								}
							}

						}
					}else{
						newOwner.addDeadCard(card, null);
					}
					
					this.deadCards.remove(card, currentOwner);
					this.deadCards.put(card, newOwner);

					ClientNotificator.notifyGameMove("Todeskarte " + card + " wurde von Spieler " + currentOwner
							+ " zu Spieler " + newOwner + " verschoben.");
				}
			}
		}
		ClientNotificator.notifyUpdateGameBoard(board);
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
		this.specialCards.replace(sc, newOwner);
		oldOwner.removeSpecialCard(sc);
		newOwner.addSpecialCard(sc);

		ClientNotificator.notifyGameMove("Sonderkarte " + sc + " wurde von " + oldOwner + " zu Spieler " + newOwner
				+ " verschoben.");

	}

	private void checkDead() {
		// Tod relevante Spezialkarten prüfen
		IPlayer activePlayer = Game.getInstance().getActivePlayer();
		ISpecialCard playerIsForcedToDead = null;
		ISpecialCard playerIsBewaredOfDead = null;
		ArrayList<IDeadCard> allDeadCards = new ArrayList<IDeadCard>();
		ArrayList<IDeadCard> deadCardsOfPlayer = new ArrayList<IDeadCard>();
		boolean hasDeadCardsAlready = false;
		
		for (IDeadCard card : activePlayer.getDeadCards()){
			deadCardsOfPlayer.add(card);
		}
		
		for (ISpecialCard card : activePlayer.getSpecialCards()) {
			//Wenn Killervirus Spezialkarte bei Spieler
			if (card.getIsForcedOfDead()) {
				playerIsForcedToDead = card;
			}
			//Wenn Clown Spezialkarte bei Spieler
			if (card.getIsBewaredOfDead()) {
				playerIsBewaredOfDead = card;
			}
		}
		
		// Spezialkarte Killervirus wieder entfernen
		if (playerIsForcedToDead != null) {
			ClientNotificator.notifyGameMove("Der Spieler " + activePlayer.getPlayerName()
					+ " wurde vom Killervirus befallen! Die Sonderkarte Killervirus wurde " + "von Spieler "
					+ activePlayer.getPlayerName() + "auf das Spielbrett verschoben.");
			BoardManager.getInstance().switchSpecialcardOwner(playerIsForcedToDead, null);
		}
				
		if (this.targetCardsToDeploy.isEmpty() || playerIsForcedToDead != null) {
			for (IDeadCard deadCard : deadCards.keySet()) {
				allDeadCards.add(deadCard);
				if (deadCard.getCardCalue() == CubeManager.getInstance().getCurrentPinkCube().FaceValue) {
					if(!deadCardsOfPlayer.isEmpty()){
						for(IDeadCard deadCardsOfActivePlayer : deadCardsOfPlayer){
							allDeadCards.remove(deadCard);
							hasDeadCardsAlready = true;
						}
					}else{
						deadCardsToDeploy.add(deadCard);
					}					
				}
			}
			
			if(hasDeadCardsAlready){
				for(IDeadCard deadCard : allDeadCards){
					if(hasDeadCardsAlready){
						deadCardsToDeploy.add(deadCard);
						hasDeadCardsAlready = false;
					}
				}
			}
		}
		
		if(!deadCardsToDeploy.isEmpty()){
			// Falls der Spieler die Spezialkarte Clown hat diese
			// entfernen und keinen Tod zuteilen
			if (playerIsBewaredOfDead != null) {
				ClientNotificator.notifyGameMove("Der Spieler " + activePlayer.getPlayerName()
						+ " entging dem Tod indem er ihm eine Torte ins Gesicht warf!"
						+ " Die Sonderkarte Clown wurde vom Spieler " + activePlayer.getPlayerName()
						+ " auf das Spieltbrett verschoben.");
				deadCardsToDeploy.clear();
				BoardManager.getInstance().switchSpecialcardOwner(playerIsBewaredOfDead, null);
			}
		}
		
		ClientNotificator.notifyUpdateGameBoard(board);
	}

	private void checkDinoCards(int sumOfCubes) {
		// Summe für die Dinos prüfen, höchste mögliche Dinokarte merken
		ArrayList<ITargetCard> dinoCard = new ArrayList<ITargetCard>();
		// Zu prüfen sind alle Karten auf dem Spielbrett und die nicht
		// gewerteten Karten der Mitspieler
		ArrayList<ITargetCard> cardsToCheck = new ArrayList<ITargetCard>(Arrays.asList(this.board.getTargetCards()));
		cardsToCheck.addAll(this.notValuatedCardsFromPlayers);

		for (ITargetCard targetCard : cardsToCheck) {
			// Voraussetzungen für Karte vorhanden und Karte nicht bereits beim
			// aktuellen Spieler
			if (targetCard.getGameCard().isDino() && targetCard.getRequiredPoints() <= sumOfCubes
					&& !this.targetCards.get(targetCard).equals(Game.getInstance().getActivePlayer())) {
				
				// Prüfen ob der Dino besser ist als der bereits vorhandene
				if (dinoCard.isEmpty()) {
					dinoCard.clear();
					dinoCard.add(targetCard);
				} else {
					if (dinoCard.get(0).getRequiredPoints() < targetCard.getRequiredPoints()) {
						dinoCard.clear();
						dinoCard.add(targetCard);
					}
				}
			}
		}
		if (!dinoCard.isEmpty()) {
			this.log.info("Vorschlag: " + (this.cardsToPropose.size() + 1) + dinoCard);
			for (ITargetCard targetCard : dinoCard) {
				targetCardsToDeploy.add(targetCard);
			}
			this.cardsToPropose.put(this.cardsToPropose.size() + 1, dinoCard);
		}
	}

	private void checkProffessorenCards(ArrayList<CubeValue> cubeValues) {
		ArrayList<ITargetCard> proffessors = new ArrayList<ITargetCard>();
		// Zu prüfen sind alle Karten auf dem Spielbrett und die nicht
		// gewerteten Karten der Mitspieler
		ArrayList<ITargetCard> cardsToCheck = new ArrayList<ITargetCard>(Arrays.asList(this.board.getTargetCards()));
		cardsToCheck.addAll(this.notValuatedCardsFromPlayers);

		for (ITargetCard targetCard : cardsToCheck) {
			// Prüft die Summe der Würfel, und vergleicht diese mit den
			// Professorenkarten, welche nicht gewertet oder von einer
			// Todeskarte verdeckt sind
			if (targetCard.getGameCard().isProffessoren() && !targetCard.getIsValuated()
					&& !targetCard.getIsCoveredByDead()
					&& !this.targetCards.get(targetCard).equals(Game.getInstance().getActivePlayer())) {
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
			this.log.info("Vorschlag: " + (cardsToPropose.size() + 1) + " Proffessoren Karten " + proffessors);
			for (ITargetCard targetCard : proffessors) {
				targetCardsToDeploy.add(targetCard);
			}
			cardsToPropose.put(cardsToPropose.size() + 1, proffessors);
		}
	}

	private void checkRiebmannYetiLemmingeCards(ArrayList<CubeValue> cubeValues) {
		ArrayList<CubeValue> cubeValuesTemp = new ArrayList<CubeValue>();
		for (CubeValue cv : cubeValues) {
			cubeValuesTemp.add(cv);
		}

		ArrayList<ITargetCard> riebYetiLemming = new ArrayList<ITargetCard>();
		// Zu prüfen sind alle Karten auf dem Spielbrett und die nicht
		// gewerteten Karten der Mitspieler
		ArrayList<ITargetCard> cardsToCheck = new ArrayList<ITargetCard>(Arrays.asList(this.board.getTargetCards()));
		cardsToCheck.addAll(this.notValuatedCardsFromPlayers);

		for (ITargetCard targetCard : cardsToCheck) {
			if ((targetCard.getGameCard().isLemming() || targetCard.getGameCard().isRiebmann() || targetCard
					.getGameCard().isYeti())
					&& !targetCard.getIsValuated()
					&& !targetCard.getIsCoveredByDead()
					&& !this.targetCards.get(targetCard).equals(Game.getInstance().getActivePlayer())) {
				boolean match = true;
				for (CubeValue val : targetCard.getRequiredCubeValues()) {
					if (!cubeValuesTemp.contains(val)) {
						match = false;
					} else {
						cubeValuesTemp.remove(val);
					}
				}
				if (match) {
					riebYetiLemming.add(targetCard);
				}
			}
		}
		if (riebYetiLemming.size() > 0) {
			this.log.info("Vorschlag: " + (cardsToPropose.size() + 1) + " - " + riebYetiLemming);
			for (ITargetCard targetCard : riebYetiLemming) {
				targetCardsToDeploy.add(targetCard);
			}
			cardsToPropose.put(cardsToPropose.size() + 1, riebYetiLemming);
		}
	}

}