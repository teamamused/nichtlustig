package teamamused.client.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import teamamused.client.Main;
import teamamused.client.libs.Client;
import teamamused.client.libs.IClientListener;
import teamamused.common.db.Ranking;
import teamamused.common.gui.AbstractController;

public class RankingController extends AbstractController<RankingModel, RankingView> implements IClientListener {

	public RankingController(RankingModel model, RankingView view) {
		super(model, view);

		// Beim Client registrieren (s. Observer Pattern)
		Client.getInstance().registerGui(this);
		
		view.btnBack.setOnAction((ActionEvent e) -> {
			Main.getInstance().startWelcome3();
		});
		
		view.btnExit.setOnAction((ActionEvent e) -> {
			Main.getInstance().startBye2();
		});
		
	}

}
