package teamamused.client.gui;

import javafx.event.ActionEvent;
import teamamused.client.Main;
import teamamused.client.libs.Client;
import teamamused.client.libs.IClientListener;
import teamamused.common.gui.AbstractController;

public class RankingController extends AbstractController<RankingModel, RankingView> implements IClientListener {

	public RankingController(RankingModel model, RankingView view) {
		super(model, view);
		
		// Beim Client registrieren
		Client.getInstance().registerGui(this);
		
		view.btnBack.setOnAction((ActionEvent e) -> {
			Main.getInstance().startWelcome(this.view);
		});
		
	}

}
