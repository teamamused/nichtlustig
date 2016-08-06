package teamamused.client.gui.waitingroom;

import javafx.event.ActionEvent;
import teamamused.client.Main;
import teamamused.client.libs.Client;
import teamamused.common.gui.AbstractController;

public class WaitingRoomController extends AbstractController<WaitingRoomModel, WaitingRoomView> {
	
	public WaitingRoomController(WaitingRoomModel model, WaitingRoomView view) {
		super(model, view);
		
		view.btnPlayGame.setOnAction((ActionEvent e) -> {
			Client.getInstance().startGame();
			Main.getInstance().startGameBoard();
		});
		
	
	}

}
