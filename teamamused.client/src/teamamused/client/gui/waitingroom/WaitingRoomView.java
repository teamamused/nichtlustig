package teamamused.client.gui.waitingroom;

import javafx.scene.Scene;
import javafx.stage.Stage;
import teamamused.common.gui.AbstractView;

/**
 * TODO: Klasse beschreiben
 * 
 * @author Michelle
 *
 */
public class WaitingRoomView extends AbstractView<WaitingRoomModel>{

	public WaitingRoomView(Stage stage, WaitingRoomModel model) {
		super(stage, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Scene createGUI() {
		// TODO Auto-generated method stub
		return null;
	}

	//TODO: Anzeigen, welche Spieler sich verbunden haben (mit Namen). Erst, wenn alle Spieler auf "Spiel starten"
	//geklickt haben, wird das GameBoard initialisiert.
	
}
