package teamamused.client.gui.GameOver;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import teamamused.client.Main;
import teamamused.client.libs.Client;
import teamamused.common.gui.AbstractController;

/**
 * Die Controller-Klasse nimmt die Benutzerinteraktion der GameOverView entgegen.
 * 
 * @author Michelle
 *
 */
public class GameOverController extends AbstractController<GameOverModel, GameOverView>{
	
	public GameOverController(GameOverModel model, GameOverView view) {
		super(model, view);
		this.model = model;
	
		// Der Klick auf den Schliessen-Button führt zur Tschüss-Seite
		view.btnClose.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Main.getInstance().startBye4();
			}
		});
		
		// Der Klick auf den Button "Spiel erneut spielen" führt in den WaitingRoom
		view.btnNewStart.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Main.getInstance().startWaitingRoom2();;
			}
		});
		
		// Der Klick auf das Pokal-Icon führt zur Ranking-Seite
		view.btnTrophy.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Client.getInstance().getRanking();
			}
		});
		
	}

}
