package teamamused.playground.application.gui;

import teamamused.common.gui.AbstractModel;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.GameBoard;
import teamamused.common.models.Player;
import teamamused.playground.application.Client;

public class GameBoardModel extends AbstractModel {

    // Test Spielbrett erzeugen
    GameBoard spielbrett;

    // Spieler angaben (vorallem wegen Username)
    Player player;
    
    // Ob der Spieler aktiv ist
    boolean isPlayerActive;
    
    // Falls es mehrere Zielkarten gibt zum auswählen, hier die Auswahl
	ITargetCard[] cardsToChoose = null;
    
    /**
     * Konstruktor, initial Daten von Client holen
     */
    public GameBoardModel() {
    	this.spielbrett = Client.getInstance().getSpielbrett();
    	this.player = (Player) Client.getInstance().getPlayer();
    }
    
}
