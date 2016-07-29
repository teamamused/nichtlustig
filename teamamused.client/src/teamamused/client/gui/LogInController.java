package teamamused.client.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import teamamused.client.Main;
import teamamused.client.libs.Client;
import teamamused.client.libs.IClientListener;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractController;
import teamamused.common.interfaces.IPlayer;

public class LogInController extends AbstractController<LogInModel, LogInView>  implements IClientListener {
	
	public LogInController(LogInModel model, LogInView view) {
		super(model, view);

		// Beim Client registrieren
		Client.getInstance().registerGui(this);
		
		view.btnLogin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Client.getInstance().logIn(view.textUser.getText(),view.textPassword.getText());
			}
		});
		
		view.btnConnectServer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean erfolgreich = Client.getInstance().connectToServer(view.textServer.getText(), view.textUser.getText(), Integer.parseInt(view.textPort.getText()));
				if (erfolgreich) {
					ServiceLocator.getInstance().getLogger().info("Erfolgreich zum Server verbunden");
				} else {
					ServiceLocator.getInstance().getLogger().info("Verbindung zum Server konnte nicht hergestellt werden.");
				}
			}
		});
		
		view.linkReg.setOnAction((ActionEvent e) -> {
			Main.getInstance().startRegister();
		});
		
	}

	@Override
	public void onLoginSuccessful(IPlayer player) {
		ServiceLocator.getInstance().getLogger().info("Login für Spieler " + player.getPlayerName() + " erfolgreich");	
		// wenn öbis vom Server ufgruefe wird, wo im Gui gmacht sell werte muess es immer mit dem Platform.runLater ufgruefe werde
		Platform.runLater(() -> {
			Main.getInstance().startWelcome();
		});
		LogInModel.loggedInPlayer = player;
	}
	@Override
	public void onLoginFailed(String msg) {
		ServiceLocator.getInstance().getLogger().info("Login gescheitert: " + msg);		
		// Irgend es Label ihblände mit em Text "Fehler beim anmelden: "+ msg
	}

}