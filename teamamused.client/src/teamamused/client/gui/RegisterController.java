package teamamused.client.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import teamamused.client.Main;
import teamamused.client.libs.Client;
import teamamused.common.gui.AbstractController;

public class RegisterController extends AbstractController<RegisterModel, RegisterView> {

	public RegisterController(RegisterModel model, RegisterView view) {
		super(model, view);
		
		view.btnRegister.setOnAction((ActionEvent e) -> {
			Main.getInstance().startWelcome2();
		});
		
		view.btnRegister.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Client.getInstance().registerPlayer(view.textRegUser.getText(),view.textRegPassword.getText());
			}
		});
		
	}

}
