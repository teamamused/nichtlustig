package teamamused.client.gui;

import javafx.event.ActionEvent;
import teamamused.client.Main;
import teamamused.common.gui.AbstractController;

public class RankingController extends AbstractController<RankingModel, RankingView> {

	public RankingController(RankingModel model, RankingView view) {
		super(model, view);
		
		view.btnBack.setOnAction((ActionEvent e) -> {
			Main.getInstance().startWelcome3();
		});
		
		view.btnExit.setOnAction((ActionEvent e) -> {
			Main.getInstance().startBye2();
		});
		
	}

}
