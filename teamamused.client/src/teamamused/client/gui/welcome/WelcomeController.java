package teamamused.client.gui.welcome;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import teamamused.client.Main;
import teamamused.client.libs.Client;
import teamamused.client.libs.IClientListener;
import teamamused.common.db.Ranking;
import teamamused.common.gui.AbstractController;

public class WelcomeController extends AbstractController<WelcomeModel, WelcomeView>implements IClientListener {

	public WelcomeController(WelcomeModel model, WelcomeView view) {
		super(model, view);

		// Beim Client registrieren (s. Observer Pattern)
		Client.getInstance().registerGui(this);

		view.btnTrophy.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Client.getInstance().getRanking();
			}
		});

		// Wenn Multi-Player-Button durch Benutzer angeklickt wird, wird er auf das GameBoard weitergeleitet
		view.btnMulti.setOnAction((ActionEvent e) -> {
			Main.getInstance().startGameBoard(this.view);
		});

		// Wenn der Exit-Button durch Benutzer angeklickt wird, wird er auf die Tschüss-Seite weitergeleitet
		view.btnExit.setOnAction((ActionEvent e) -> {
			Main.getInstance().startBye(this.view);
		});

	}

	@Override
	public void onRankingRecieved(Ranking[] rankings) {
		
		Platform.runLater(() -> {
			Main.getInstance().startRanking(rankings, false, this.view);
		});
	}
	
}
