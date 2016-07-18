package teamamused.client.gui;

import javafx.event.ActionEvent;
import teamamused.client.Main;
import teamamused.common.gui.AbstractController;

public class WelcomeController extends AbstractController<WelcomeModel, WelcomeView> {

	public WelcomeController(WelcomeModel model, WelcomeView view) {
		super(model, view);
		
		view.btnRanking.setOnAction((ActionEvent e) -> {
			Main.getInstance().startRanking();
		});
		
	}

}
