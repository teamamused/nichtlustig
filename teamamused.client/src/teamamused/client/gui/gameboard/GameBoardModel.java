package teamamused.client.gui.gameboard;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import teamamused.client.libs.Client;
import teamamused.common.gui.AbstractModel;
import teamamused.common.interfaces.ICube;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.models.GameBoard;
import teamamused.common.models.Player;

/**
 * 
 * @author Michelle
 *
 */
public class GameBoardModel extends AbstractModel {
	IPlayer player;
	
    public IPlayer getPlayer() {
		return player;
	}

	public void setPlayer(IPlayer player) {
		this.player = player;
	}

	/**
     * Konstruktor, der einen übergebenen Player setzt (für buildPlayer)
     */
    public GameBoardModel(IPlayer player) {
    	super();
    	this.player = player;
    }
    
    /**
     * Konstruktor, der den currentPlayer setzt
     */
	public GameBoardModel() {
		gameBoard.init();
		this.player = Client.getInstance().getPlayer();
	}
    
	boolean specialCardsNeedsUpdate = true, targetCardsNeedsUpdate = true, deadCardsNeedsUpdate =true;
	
	// Gibt an, wie oft der Spieler noch würfeln darf
    int remainingDices;
    
    // Speichert, ob der Spieler aktiv ist oder nicht
    boolean playerIsActive;

	// In der ObserverList werden Nachrichten gespeichert
	protected ObservableList<String> chatMessages = FXCollections.observableArrayList();

	public GameBoard gameBoard = new GameBoard();

	/**
	 * Methode für die aktuellen Werte der Würfel
	 * 
	 * @return Gibt die aktuellen Würfelwerte zurück
	 */
	public ICube[] getCubes() {
		if (gameBoard != null) {
			return gameBoard.getCubes();
		} else {
			return new ICube[0];
		}
	}

	/**
	 * Diese Methode würfelt die Würfel
	 * 
	 */
	public void dice() {
		Client.getInstance().rollDices();
	}

	/**
	 * Diese Methode liest alle Spieler in eine ArrayList und gibt diese zurück.
	 * 
	 * @return Gibt eine ArrayList mit Player-Objekten zurück.
	 */
	public List<Player> getPlayerList() {
		ArrayList<Player> playerList = new ArrayList<Player>();
		return playerList;
	}

}
