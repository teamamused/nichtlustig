package teamamused.playground.application;

import java.util.Optional;

import teamamused.common.models.Player;
import teamamused.playground.application.gui.GameBoardController;
import teamamused.playground.application.gui.GameBoardModel;
import teamamused.playground.application.gui.GameBoardView;
import javafx.application.Application;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		// Pseudo Login
		this.login();
		
        // Create and display the splash screen and model
        GameBoardModel model = new GameBoardModel();
        GameBoardView view = new GameBoardView(primaryStage, model);
        new GameBoardController(model, view);
        view.start();
	}

	public static void main(String[] args) {
		launch(args);
	}
	/**
	 * Pseudo Login Methode
	 */
	private void login() {
		TextInputDialog dialog = new TextInputDialog("Daniel");
		dialog.setTitle("Anmeldung");
		dialog.setHeaderText("Hallo mein nicht lustiger Freund, gib deinen Namen ein dass wir dich mit dem Server verbinden können.");
		dialog.setContentText("Geben Sie Ihren Spielernamen ein:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    Player p = new Player(result.get());
		    Client.getInstance().joinGame(p);
		}
	}
}
