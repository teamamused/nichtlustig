package teamamused.client.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import teamamused.client.Main;
import teamamused.client.libs.Client;
import teamamused.common.gui.AbstractController;

public class WelcomeController extends AbstractController<WelcomeModel, WelcomeView> {

	public WelcomeController(WelcomeModel model, WelcomeView view) {
		super(model, view);
		
		view.btnTrophy.setOnAction((ActionEvent e) -> {
			Main.getInstance().startRanking();
		});
		
		view.btnTrophy.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Client.getInstance().getRanking();
			}
		});
		
		view.btnMulti.setOnAction((ActionEvent e) -> {
			Main.getInstance().startWaitingRoom();
		});
		
		view.btnExit.setOnAction((ActionEvent e) -> {
			Main.getInstance().startBye();
		});
		
	}

}
