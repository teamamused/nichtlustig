package teamamused.client.gui.ranking;

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
		
		// Wenn Zurück-Button durch Benutzer angeklickt wird, wird er zurück auf die Welcomeseite gebracht
		view.btnBack.setOnAction((ActionEvent e) -> {
			if (model.isGameFinished) {
				view.stop();
			} else {
				Main.getInstance().startWelcome(this.view);
			}
		});
		
	}

}
