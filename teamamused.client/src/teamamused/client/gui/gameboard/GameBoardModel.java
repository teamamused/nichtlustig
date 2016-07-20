package teamamused.client.gui.gameboard;

import teamamused.common.gui.AbstractModel;
import teamamused.common.interfaces.ICube;
import teamamused.common.models.GameBoard;

public class GameBoardModel extends AbstractModel {
	
	private GameBoard gameBoard = new GameBoard();
//	private Set<GameBoardView> observers = new HashSet<>(); 

	/**
	 * Methode für die aktuellen Werte der Würfel
	 * 
	 * @return Gibt die aktuellen Würfelwerte zurück
	 */
	public ICube[] getCubes() {
		return gameBoard.getCubes();
	}

	/**
	 * Diese Methode würfelt die Würfel
	 * 
	 */
	public void dice() {
		for (ICube cube : getCubes()) {
			if(!cube.getIsFixed()) {
				cube.dice();				
			}
		}
//		notifyObservers();
	}
	
//	public void register(GameBoardView view) {
//		observers.add(view);
//	}
//	
//	private void notifyObservers() {
//		observers.forEach(o -> o.getStage().setScene(o.createGUI()));
//	}
	
}
