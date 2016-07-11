package teamamused.server;

import java.io.Serializable;
import java.util.List;

import teamamused.common.ServiceLocator;
import teamamused.common.db.GameInfoRepository;
import teamamused.common.interfaces.IPlayer;

/**
 * 
 * Die Klasse Game steuert das Spiel gesamtahaft und ist zuständig dafür das Spiel
 * zu aktivieren, die aktiven Spieler zu ändern und das Spiel abzuschliessen,
 * wenn nur noch 5 oder weniger Zielkarten auf dem Spielbrett vorhanden sind.
 * 
 * @author Maja Velickovic
 *
 */

public class Game implements Serializable{
	private static final long serialVersionUID = 1L;
	private static Game instance;
	
	//gameStatus: 0 = nicht gestartet, 1 = gestartet, 2 = beendet
	private static int gameStatus = 0;
	private int gameId;
	
	private IPlayer activePlayer;

	private Game(){
		super();
		this.startGame();
	}
	
	/**
	 * Initialisieren des Spiels (Game)
	 */
	private void startGame() {
		ServiceLocator.getInstance().getLogger().info("Initialisiere Spiel");
		gameId = GameInfoRepository.getNextGameId();
		//Spielstatus auf "gestartet" setzen
		gameStatus = 1;
	}
	
	/**
	 * Statischer Getter für Instanz (Singleton Pattern)
	 * @return Instanz des Games
	 */
	public static Game getInstance() {
		if (instance == null && gameStatus !=2) {
			instance = new Game();
		}
		return instance;
	}
	
	/**
	 * Spieler zu Spiel hinzufügen
	 * @param player Spieler, welcher hinzugefügt werden soll
	 */
	public void addPlayer(IPlayer player){
		if(this.getPlayersFromGameboard().size() <= 4){
			player.setPlayerNumber(this.getPlayersFromGameboard().size());
			this.getPlayersFromGameboard().add(player);
			ClientNotificator.notifyGameMove("Spieler " + player.getPlayerName() + " ist dem Spiel beigetreten.");
			ClientNotificator.notifyUpdateGameBoard(BoardManager.getInstance().getGameBoard());
		}else{
			throw new UnsupportedOperationException("Die maximale Spieleranzahl wurde bereits erreicht, bitte versuchen Sie es später erneut.");
		}
	}
	
	/**
	 * Legt fest, welcher Spieler mit dem Spiel starten darf.
	 */
	public void defineStartPlayer(){
		this.activePlayer = this.getPlayersFromGameboard().get((int)(Math.random() * 4));
	}
	
	/**
	 * Der aktive Spieler wird geändert, sobald ein Spieler seinen Spielzug
	 * abgeschlossen und die Wertung inkl. Kartenausteilung beendet wurde.
	 */
	public void changeActivePlayer(){
		if(this.activePlayer.getPlayerNumber() != 3){
			this.activePlayer = this.getPlayersFromGameboard().get(this.activePlayer.getPlayerNumber() + 1);
		}else{
			this.activePlayer = this.getPlayersFromGameboard().get(0);
		}
	}
	
	/**
	 * Game-Status auf "beendet" ändern.
	 */
	public void setGameIsFinished(){
		gameStatus = 2;
	}
	
	/**
	 * Gibt den aktiven Spieler zurück, welcher am Würfeln ist.
	 * @return aktiver Spieler
	 */
	public IPlayer getActivePlayer(){
		return activePlayer;
	}
	
	/**
	 * Gibt die Game-ID des Spiels zurück.
	 * @return Game-ID als Integer
	 */
	public Integer getGameID(){
		return this.gameId;
	}
	
	private List<IPlayer> getPlayersFromGameboard() {
		return BoardManager.getInstance().getGameBoard().getPlayers();
	}
	
}
