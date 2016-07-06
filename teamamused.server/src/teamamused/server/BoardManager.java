package teamamused.server;

import java.util.Hashtable;
import java.util.List;

import teamamused.common.interfaces.ICardHolder;
import teamamused.common.interfaces.ICube;
import teamamused.common.interfaces.IDeadCard;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.ISpecialCard;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.GameBoard;
import teamamused.common.models.cubes.CubeColor;
import teamamused.common.models.cubes.CubeValue;

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
	private List<ICardHolder> currentOwners;
	private ICardHolder newOwner;
	private List<ITargetCard> targetCardsToDeploy;
	private List<ISpecialCard> specialCardsToDeploy;
	private List<IDeadCard> deadCardsToDeploy;
	private Hashtable<Integer, List<ITargetCard>> cardsToPropose;
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
	public List<ITargetCard> getNotValuatedCardsFromPlayer(){
		notValuatedCardsFromPlayers = null;
		
		for(IPlayer player: game.getPlayers()){
			for(ITargetCard targetCard : player.getTargetCards()){
				if(!targetCard.getIsValuated()){
					if(!targetCard.getIsCoveredByDead()){
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
	 * @param pinkCube Wert von pinkem Würfel zur Wertung
	 */
	public void valuatePlayerCards(int pinkCube){
		this.pinkCubeValue = pinkCube;
		playerTargetCardsToValuate = notValuatedCardsFromPlayers;
		
		for(ITargetCard card : playerTargetCardsToValuate){
			if(card.getCardValue() != pinkCube){
				playerTargetCardsToValuate.remove(card);
			}else{
				notValuatedCardsFromPlayers.remove(card);
			}
		}
	}
	
	/**
	 * Wertet die Karten der Spieler nach einem abgeschlossenen Spielzug.
	 */
	public void valuePlayerCards(){
		for(ITargetCard card : playerTargetCardsToValuate){
			card.setIsValuated(true);
			ClientNotificator.notifyGameMove("Karte " + card.toString() + " von Spieler " + targetCards.get(card) + " wurde gewertet.");
		}
		playerTargetCardsToValuate = null;
		ClientNotificator.notifyUpdateGameBoard(board);
	}
	
	/**
	 * Wertet den Würfel-Wurf des Spielers aus, sobald dieser seinen Spielzug
	 * abgeschlossen hat.
	 */
	@SuppressWarnings("null")
	public void valuatePlayerDice(){
		ICube cubes[] = CubeManager.getInstance().getCubes();
		int sumOfCubes = 0;
		CubeValue[] cardValues;
		int matchPoints = 0;
		List <ICube> cubesToCompare = null;
		List <ITargetCard> cardsToProposeTemp = null;
		List <ITargetCard> cardsToProposeTemp2 = null;
		List <ICube> cubesToCompareTemp = null;
		
		for(ICube cube : cubes){
			cubesToCompare.add(cube);
		}

		for(ITargetCard targetCard : notValuatedCardsFromPlayers){
			//Prüft die Summe der Würfel, und vergleicht diese mit der Dino-Karte
			if(targetCard.getGameCard().name().matches("ZK_Dino"+"[1-5]")){
				for(ICube cube : cubes){
					if(cube.getCubeColor() != CubeColor.Pink){
						sumOfCubes += cube.getCurrentValue().FaceValue;
					}
				}
				if(targetCard.getRequiredPoints() <= sumOfCubes){
					cardsToProposeTemp.add(targetCard);
				}
			//Wenn Professoren-Karte spezielle andere Kalkulation
			}else if(targetCard.getGameCard().name().matches("ZK_Professor"+"[1-5]")){
				cardValues = targetCard.getRequiredCubeValues();
				for(CubeValue cardValue : cardValues){
					for(ICube cube : cubesToCompare){
						if(cube.getCubeColor() != CubeColor.Pink){
							if(cardValue == cube.getCurrentValue()){
								matchPoints += 1;
							}
						}
					}
				}
				if(matchPoints > 2){
					cardsToProposeTemp.add(targetCard);
				}
				matchPoints = 0;
			//Wenn nicht Dino-Karte oder Professoren-Karte
			}else{
				cardValues = targetCard.getRequiredCubeValues();
				for(CubeValue cardValue : cardValues){
					for(ICube cube : cubesToCompare){
						if(cube.getCubeColor() != CubeColor.Pink){
							if(cardValue == cube.getCurrentValue()){
								matchPoints += 1;
							}
						}
					}
				}
				if(matchPoints > 1){
					cardsToProposeTemp.add(targetCard);
				}
				matchPoints = 0;
			}
		}
		
		while(!cardsToProposeTemp.isEmpty()){
			cubesToCompareTemp = cubesToCompare;
			
			for(ITargetCard card : cardsToProposeTemp){
				CubeValue cubeValuesTemp[] = card.getRequiredCubeValues();
				
				/*Wenn kein Dino-Karte, da diese sicher dem Spieler ohne weitere
				 * Karten zur Auswahl steht. Andere Zielkarten können allenfalls
				 * miteinander ausgewählt werden.
				 */
				if(!card.getGameCard().name().matches("ZK_Dino"+"[1-5]")){
					for(CubeValue cubeValue : cubeValuesTemp){
						for(ICube cube : cubesToCompareTemp){
							if(cube.getCurrentValue() == cubeValue){
								cubesToCompareTemp.remove(cube);
							}
						}
						
					}
				}		
				
				cardsToProposeTemp.remove(card);
				cardsToProposeTemp2.add(card);
			}
			cardsToPropose.put(cardsToPropose.size()+1, cardsToProposeTemp2);	
			
			cardsToProposeTemp2 = null;
		}
		
		
	}
	
	/**
	 * Hat der Spieler mehrere Karten zur Auswahl, wird ihm mit dieser Methode
	 * eine Auswahl an Karten gegeben, welche er nehmen kann.
	 * @param targetCardsToChoose Zielkarten, welche dem Spieler zur Auswahl präsentiert werden
	 */
	public Hashtable<Integer, List<ITargetCard>> proposeCards(List<ITargetCard> targetCardsToChoose){
		return cardsToPropose;
	}
	
	/**
	 * Hat der Spieler mehrere Karten zur Auswahl, wird über das GUI mit dieser
	 * Methode aufgerufen, für welche Zielkarten sich der Spieler entschieden
	 * hat. Die entsprechenden Karten werden ihm anschliessend zugewiesen.
	 * @param targetCardsToTake Zielkarten, welche der Spieler nehmen möchte
	 */
	public void takeProposedCards(List<ITargetCard> targetCardsToTake){
		for(ITargetCard card : targetCardsToTake){
			this.targetCardsToDeploy.add(card);
		}
	}
	
	/**
	 * Verteilt die Karten (Ziel- ,Sonder-, und/oder Todeskarten) an den Spieler
	 */
	public void deployCards(){
		
		this.newOwner = Game.getInstance().getActivePlayer();
		this.currentOwners.add(board);
		
		for(IPlayer player : Game.getInstance().getPlayers()){
			this.currentOwners.add(player);
		}
		
		
		for(ICardHolder ownerNow : currentOwners){
			this.currentOwner = ownerNow;
			
			//Verteilen der Spezialkarten
			for(ISpecialCard card : specialCardsToDeploy) {
				if(specialCards.get(card) == currentOwner){
					this.currentOwner.removeSpecialCard(card);
					this.newOwner.addSpecialCard(card);
					this.specialCards.remove(card, currentOwner);
					this.specialCards.put(card, newOwner);
					ClientNotificator.notifyGameMove("Spezialkarte " + card + " wurde von Spieler " + currentOwner + " zu Spieler " + newOwner + " verschoben.");
				}		
			}
			
			//Verteilen der Todeskarten
			for(IDeadCard card : deadCardsToDeploy) {
				if(deadCards.get(card) == currentOwner && newOwner != board){
					this.currentOwner.removeDeadCard(card);
					
					ITargetCard[] targetCardsOfNewOwner = newOwner.getTargetCards();
					
					//Prüft, ob die Todeskarte auf eine andere Karte umgedreht gelegt werden muss
					for(ITargetCard targetCard : targetCardsOfNewOwner){
						if(targetCard.getIsValuated() &&
								(targetCard.getGameCard().name().matches("ZK_Lemming"+"[1-5]") ||
								targetCard.getGameCard().name().matches("ZK_Yeti"+"[1-5]") ||
								targetCard.getGameCard().name().matches("ZK_Riebmann"+"[1-5]") ||
								targetCard.getGameCard().name().matches("ZK_Lemming"+"[1-5]") ||
								targetCard.getGameCard().name().matches("ZK_Professoren"+"[1-5]"))){
							//Todeskarte wird umgedreht auf gewertete Karte gelegt
							this.newOwner.addDeadCard(card, targetCard);
							targetCard.setIsCoveredByDead(true);
						}else{
							//Todeskarte wird normal neben Zielkarten hingelegt
							this.newOwner.addDeadCard(card, null);
							targetCard.setIsValuated(false);
						}
					}
										
					this.deadCards.remove(card, currentOwner);
					this.deadCards.put(card, newOwner);
					
					ClientNotificator.notifyGameMove("Todeskarte "  + card + " wurde von Spieler " + currentOwner + " zu Spieler " + newOwner + " verschoben.");
				}
			}
			
			//Verteilen der Zielkarten
			for(ITargetCard card : targetCardsToDeploy) {
				if(targetCards.get(card) == currentOwner){
					this.currentOwner.removeTargetCard(card);
					this.newOwner.addTargetCard(card);
					this.targetCards.remove(card, currentOwner);
					this.targetCards.put(card, newOwner);
					ClientNotificator.notifyGameMove("Zielkarte "  + card + " wurde von Spieler " + currentOwner + " zu Spieler " + newOwner + " verschoben.");
				}
			}
			
			ClientNotificator.notifyUpdateGameBoard(board);
		}
		
		specialCardsToDeploy = null;
		deadCardsToDeploy = null;
		targetCardsToDeploy = null;
		cardsToPropose = null;
		currentOwner = null;
		currentOwners = null;
		newOwner = null;
	}
	
	/**
	 * Wertet die Karten der Spieler nach einem abgeschlossenen Spielzug.
	 */
	public void valuate(){
		Valuation valuateFunction = new Valuation();
		valuateFunction.valuate(instance);
	}

}
