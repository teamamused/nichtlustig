package teamamused.client.gui.gameboard;

import teamamused.common.gui.AbstractModel;
import teamamused.common.interfaces.ICube;
import teamamused.common.models.GameBoard;

public class GameBoardModel extends AbstractModel {
	
	// FIXME: get gameBoard from server
	private GameBoard gameBoard = new GameBoard();
	{
		gameBoard.init();
	}

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
	}
	
}
