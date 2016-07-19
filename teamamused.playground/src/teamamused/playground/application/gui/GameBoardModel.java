package teamamused.playground.application.gui;

import java.util.Hashtable;
import java.util.List;

import teamamused.common.gui.AbstractModel;
import teamamused.common.interfaces.ITargetCard;
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
    
    // Falls es mehrere Zielkarten gibt zum auswählen, hier die Auswahl
    Hashtable<Integer, List<ITargetCard>> cardsToChooseOptions;
    
	List<ITargetCard> cardsChoosen = null;
    
    // Wenn der Spieler am würfeln ist, wievielmal er noch darf
    int remainingDices = 0;
    
    /**
     * Konstruktor, initial Daten von Client holen
     */
    public GameBoardModel() {
    	this.player = (Player) Client.getInstance().getPlayer();
    }
    
}
