package teamamused.client.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class LogIn extends Application {
	
	private LogInModel model;
	private LogInView view;
	private LogInController controller;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) {
		
		model = new LogInModel();
		view = new LogInView(primaryStage, model);
		controller = new LogInController(model, view);
		view.start();	
	}
	
	@Override
	public void stop() {
		if (view != null) view.stop();
	}
}
