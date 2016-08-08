package teamamused.client.gui;

import java.util.Locale;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import teamamused.client.Main;
import teamamused.client.libs.Client;
import teamamused.client.libs.IClientListener;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractController;
import teamamused.common.interfaces.IPlayer;

public class RegisterController extends AbstractController<RegisterModel, RegisterView> implements IClientListener {

	public RegisterController(RegisterModel model, RegisterView view) {
		super(model, view);

		// Beim Client registrieren
		Client.getInstance().registerGui(this);
		
		view.btnBack.setOnAction((ActionEvent e) -> {
			Main.getInstance().startLogIn(this.view);
		});

		// Sobald sich die Auswahl der ChoiceBox für die Sprache ändert, wird
		// der Wert im ServiceLocator gesetzt.
		view.cbLang.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Locale>() {
			@Override
			public void changed(ObservableValue<? extends Locale> observable, Locale oldValue, Locale newValue) {
				Client.getInstance().setLocale(newValue);
				view.updateTexts();
			}
		});

		view.btnRegister.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Client.getInstance().registerPlayer(view.textRegUser.getText(), view.password.getText());
			}
		});

	}
	
	@Override
	public void onRegisterSuccessful(IPlayer player) {
		Platform.runLater(() -> {
		Main.getInstance().startWelcome(this.view);
		});
		ServiceLocator.getInstance().getLogger().info("Der Benutzer wurde erfolgreich registriert");
	}
	
	@Override
	public void onRegisterFailed(String msg) {
		Platform.runLater(() -> {
		Alert alert = new Alert(AlertType.ERROR,"Registrierung fehlgeschlagen" + msg);
		alert.show();
		});
	}

}
