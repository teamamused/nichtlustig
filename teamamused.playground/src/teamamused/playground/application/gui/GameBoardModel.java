package teamamused.playground.application.gui;


import teamamused.common.gui.AbstractModel;
import teamamused.common.models.GameBoard;
import teamamused.common.models.Player;
import teamamused.client.libs.Client;

public class GameBoardModel extends AbstractModel {

    // Test Spielbrett erzeugen
    GameBoard spielbrett;

    // Spieler angaben (vorallem wegen Username)
    Player player;
    
    // Ob der Spieler aktiv ist
    boolean isPlayerActive;
    
    // Ob Würfel Fixiert sind
    boolean[] cubesFixed = new boolean[7];
    
    // Wenn der Spieler am würfeln ist, wievielmal er noch darf
    int remainingDices = 3;
    
    /**
     * Konstruktor, initial Daten von Client holen
     */
    public GameBoardModel() {
    	this.player = (Player) Client.getInstance().getPlayer();
    }
    
}
