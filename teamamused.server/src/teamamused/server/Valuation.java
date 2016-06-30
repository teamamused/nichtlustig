package teamamused.server;

/**
 * 
 * Die Klasse Valuation führt die Wertung der Würfel durch, sobald ein Spieler
 * seinen Spielzug abgeschlossen hat.
 * 
 * @author Maja Velickovic
 *
 */

public class Valuation {
	
	int valuationValue;
	
	/**
	 * Wertet die Karten der Spieler aus und wertet sie anschliessend gemäss dem
	 * Wert des pinken Würfels.
	 */
	public void valuate(BoardManager board){
		valuationValue = 1; //Wert von pinkem Würfel noch zu programmieren
		board.getNotValuedCardsFromPlayer();
		board.valuatePlayerCards(valuationValue);
		board.valuePlayerCards();
	}

}
