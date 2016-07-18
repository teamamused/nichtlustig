package teamamused.client.gui.gameboard;

import java.util.HashSet;
import java.util.Set;

import teamamused.common.gui.AbstractModel;
import teamamused.common.interfaces.ICube;
import teamamused.common.models.GameBoard;

public class GameBoardModel extends AbstractModel {
	
	private GameBoard gameBoard = new GameBoard();
//	private Set<GameBoardView> observers = new HashSet<>(); 

	public ICube[] getCubes() {
		return gameBoard.getCubes();
	}

	public void dice() {
		for (ICube cube : getCubes()) {
			cube.dice();
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
