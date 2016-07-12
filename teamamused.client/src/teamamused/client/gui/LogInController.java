package teamamused.client.gui;

import javafx.event.ActionEvent;
import teamamused.client.Main;
import teamamused.common.gui.AbstractController;

public class LogInController extends AbstractController<LogInModel, LogInView> {
	
	public LogInController(LogInModel model, LogInView view) {
		super(model, view);
		
		view.btnLogin.setOnAction((ActionEvent e) -> {
			Main.getInstance().startWelcome();
		});
		
		view.linkReg.setOnAction((ActionEvent e) -> {
			Main.getInstance().startRegister();
		});
		
	}

}
