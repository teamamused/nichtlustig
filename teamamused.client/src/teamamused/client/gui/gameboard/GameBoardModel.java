package teamamused.client.gui.gameboard;

import java.util.ArrayList;
import java.util.List;

import teamamused.common.dtos.TransportableGameBoard;
import teamamused.common.gui.AbstractModel;
import teamamused.common.interfaces.ICube;
import teamamused.common.models.GameBoard;
import teamamused.common.models.Player;

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
	
	/**
	 * Diese Methode liest alle Spieler in eine ArrayList und gibt diese zurück.
	 * 
	 * @return Gibt eine ArrayList mit Player-Objekten zurück.
	 */
	public List<Player> getPlayerList() {
		TransportableGameBoard tgb = TransportableGameBoard.getTransportObjectFromGameBoard(gameBoard);
		ArrayList<Player> playerList = tgb.players;
		return playerList;
	}
	
}
