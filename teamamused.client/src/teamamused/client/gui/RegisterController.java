package teamamused.client.gui;

import javafx.event.ActionEvent;
import teamamused.client.Main;
import teamamused.common.gui.AbstractController;

public class RegisterController extends AbstractController<RegisterModel, RegisterView> {

	public RegisterController(RegisterModel model, RegisterView view) {
		super(model, view);
		
		view.btnRegister.setOnAction((ActionEvent e) -> {
			Main.getInstance().startWelcome2();
		});
		
	}

}
