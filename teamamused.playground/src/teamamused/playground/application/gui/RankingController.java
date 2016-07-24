package teamamused.playground.application.gui;

import javafx.event.ActionEvent;
import teamamused.common.gui.AbstractController;

public class RankingController extends AbstractController<RankingModel, RankingView> {

	public RankingController(RankingModel model, RankingView view) {
		super(model, view);
		
		view.btnExit.setOnAction((ActionEvent e) -> {
			view.stop();
		});
		
	}

}
