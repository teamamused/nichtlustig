package teamamused.server;

import java.io.Serializable;
import java.util.List;

import teamamused.common.ServiceLocator;
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
	private static Game instance;
	private Game game = new Game();
	
	//gameStatus: 0 = nicht gestartet, 1 = gestartet, 2 = beendet
	private static int gameStatus = 0;
	
	private IPlayer activePlayer;
	private List<IPlayer> players;

	private Game(){
		this.startGame();
	}
	
	/**
	 * Initialisieren des Spiels (Game)
	 */
	private void startGame() {
		ServiceLocator.getInstance().getLogger().info("Initialisiere Spiel");
		
		//Spielstatus auf "gestartet" setzen
		this.gameStatus = 1;
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
	 * Getter Methode für das Spiel
	 * @return Spiel
	 */
	public Game getGame() {
		return this.game;
	}
	
	/**
	 * Spieler zu Spiel hinzufügen
	 * @param player Spieler, welcher hinzugefügt werden soll
	 */
	public void addPlayer(IPlayer player){
		if(this.players.size() <= 4){
			this.players.add(player);
		}else{
			throw new UnsupportedOperationException("Die maximale Spieleranzahl wurde bereits erreicht, bitte versuchen Sie es später erneut.");
		}
	}
	
	/**
	 * Gibt eine Liste mit mit allen Spielern zurück.
	 * @return Spielerliste
	 */
	public List<IPlayer> getPlayers(){
		return players;
	}
	
	/**
	 * Legt fest, welcher Spieler mit dem Spiel starten darf.
	 */
	public void defineStartPlayer(){
		this.activePlayer = this.getPlayers().get((int)(Math.random() * 4));
	}
	
	/**
	 * Der aktive Spieler wird geändert, sobald ein Spieler seinen Spielzug
	 * abgeschlossen und die Wertung inkl. Kartenausteilung beendet wurde.
	 */
	public void changeActivePlayer(){
		if(this.activePlayer.getPlayerNumber() != 3){
			this.activePlayer = this.getPlayers().get(this.activePlayer.getPlayerNumber() + 1);
		}else{
			this.activePlayer = this.getPlayers().get(0);
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
}
