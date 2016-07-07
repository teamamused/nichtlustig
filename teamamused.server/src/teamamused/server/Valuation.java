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
	 * @param board Board von BoardManager übergeben
	 */
	public void valuate(BoardManager board){
		valuationValue = CubeManager.getInstance().getCurrentPinkCubeValue().FaceValue;
		board.getNotValuatedCardsFromPlayer();
		board.valuatePlayerCards(valuationValue);
		board.valuePlayerCards();
	}

}
