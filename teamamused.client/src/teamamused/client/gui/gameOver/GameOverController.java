package teamamused.client.gui.gameOver;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import teamamused.client.Main;
import teamamused.client.libs.Client;
import teamamused.client.libs.IClientListener;
import teamamused.common.db.Ranking;
import teamamused.common.gui.AbstractController;

/**
 * Die Controller-Klasse nimmt die Benutzerinteraktion der GameOverView entgegen.
 * 
 * @author Michelle
 *
 */
public class GameOverController extends AbstractController<GameOverModel, GameOverView> implements IClientListener {
	
	public GameOverController(GameOverModel model, GameOverView view) {
		super(model, view);
		this.model = model;
		
		// Registriert das GUI
		Client.getInstance().registerGui(this);
	
		// Der Klick auf den Schliessen-Button führt zur Tschüss-Seite
		view.btnClose.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Main.getInstance().startBye(view);
			}
		});
		
		// Der Klick auf den Button "Spiel-Ranking" führt zum aktuellen Ranking
		view.btnRanking.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Main.getInstance().startRanking(model.ranking, null);
			}
		});
		
		// Der Klick auf das Pokal-Icon führt zur Gesamt-Ranking-Seite
		view.btnTrophy.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Client.getInstance().getRanking();
			}
		});
		
	}
	
	@Override
	public void onRankingRecieved(Ranking[] rankings) {
		
		Platform.runLater(() -> {
			Main.getInstance().startRanking(model.ranking, null);
		});
	}
	

}
