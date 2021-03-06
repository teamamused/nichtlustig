package teamamused.client.gui.register;

import java.util.Locale;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;
import teamamused.client.Main;
import teamamused.client.libs.Client;
import teamamused.client.libs.IClientListener;
import teamamused.common.LogHelper;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractController;
import teamamused.common.interfaces.IPlayer;

public class RegisterController extends AbstractController<RegisterModel, RegisterView>implements IClientListener {

	public RegisterController(RegisterModel model, RegisterView view) {
		super(model, view);

		// Beim Client registrieren
		Client.getInstance().registerGui(this);

		// Wenn Zurück-Button durch Benutzer angeklickt wird, wird er zurück auf
		// die Loginseite gebracht
		view.btnBack.setOnAction((ActionEvent e) -> {
			Main.getInstance().startLogIn(this.view);
		});

		// Sobald sich die Auswahl der ChoiceBox für die Sprache ändert, wird
		// Wert im ServiceLocator gesetzt.
		view.cbLang.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Locale>() {
			@Override
			public void changed(ObservableValue<? extends Locale> observable, Locale oldValue, Locale newValue) {
				Client.getInstance().setLocale(newValue);
				view.updateTexts();
			}
		});

		// Wenn Button "Registrieren" durch Benutzer angeklickt wird...
		view.btnRegister.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (view.password.getText().equals(view.password2.getText())) {
					Client.getInstance().registerPlayer(view.textRegUser.getText(), view.password.getText());
				} else {
					showAlertMessage("Die beiden Passwörter sind nicht identisch");
				}
			}
		});
	}

	// Methode leitet Benutzer auf die Welcome-Seite weiter, wenn Registrierung
	// erfolgreich
	@Override
	public void onRegisterSuccessful(IPlayer player) {
		Platform.runLater(() -> {
			Main.getInstance().startWelcome(this.view);
		});
		ServiceLocator.getInstance().getLogger().info("Der Benutzer wurde erfolgreich registriert");
	}

	// Methode gibt dem Benutzer eine Fehlermeldung zurück, wenn Registrierung
	// nicht korrekt funktioniert hat
	@Override
	public void onRegisterFailed(String msg) {
		Platform.runLater(() -> {
			showAlertMessage("Registrierung fehlgeschlagen\n" + msg);
		});
	}
	
	private void showAlertMessage(String msg) {
			Alert alertDialog = new Alert(AlertType.ERROR, msg);
			DialogPane dialogPane = alertDialog.getDialogPane();
			try {
				dialogPane.getStylesheets().add(getClass().getResource("..\\application.css").toExternalForm());
			} catch (Exception e) {
				LogHelper.LogException(e);
			}
			alertDialog.showAndWait();
		
	}

}
