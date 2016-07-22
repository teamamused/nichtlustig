package teamamused.client.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBase;
import teamamused.client.Main;
import teamamused.client.libs.Client;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractController;

public class LogInController extends AbstractController<LogInModel, LogInView> {
	
	public LogInController(LogInModel model, LogInView view) {
		super(model, view);
		
		view.btnLogin.setOnAction((ActionEvent e) -> {
			Main.getInstance().startWelcome();
		});
		
		view.btnLogin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Client.getInstance().logIn(view.textUser.getText(),view.textPassword.getText());
			}
		});
		
		view.btnConnectServer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean erfolgreich = Client.getInstance().ConnectToServer(view.textServer.getText(), view.textUser.getText(), Integer.parseInt(view.textPort.getText()));
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

}
