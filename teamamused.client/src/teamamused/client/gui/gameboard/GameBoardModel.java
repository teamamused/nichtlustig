package teamamused.client.gui.gameboard;

import java.util.ArrayList;
import java.util.List;

import teamamused.common.gui.AbstractModel;
import teamamused.common.interfaces.ICube;
import teamamused.common.models.GameBoard;
import teamamused.common.models.Player;

public class GameBoardModel extends AbstractModel {
	protected int btnPlayerClicked;

	// Wird vom Handler der Player-Buttons im Controller aufgerufen
	public void setBtnPlayerClicked(int btnNumber) {
		this.btnPlayerClicked = btnNumber;
	}

	// Gibt die Nummer des Spielers, wessen Button geklickt wurde, zurück
	public int getBtnPlayerClicked() {
		return btnPlayerClicked;
	}

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
			if (!cube.getIsFixed()) {
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
		// BeanGameBoard tgb =
		// BeanGameBoard.getTransportObjectFromGameBoard(gameBoard);
		// ArrayList<Player> playerList = tgb.players;
		ArrayList<Player> playerList = new ArrayList<Player>();
		return playerList;
	}

}
