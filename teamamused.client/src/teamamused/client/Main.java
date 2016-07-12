package teamamused.client;

import teamamused.client.gui.ByeController;
import teamamused.client.gui.ByeModel;
import teamamused.client.gui.ByeView;
import teamamused.client.gui.LogInController;
import teamamused.client.gui.LogInModel;
import teamamused.client.gui.LogInView;
import teamamused.client.gui.RegisterController;
import teamamused.client.gui.RegisterModel;
import teamamused.client.gui.RegisterView;
import teamamused.client.gui.WelcomeController;
import teamamused.client.gui.WelcomeModel;
import teamamused.client.gui.WelcomeView;
import teamamused.client.gui.splashscreen.Splash_Controller;
import teamamused.client.gui.splashscreen.Splash_Model;
import teamamused.client.gui.splashscreen.Splash_View;
import javafx.application.Application;
import javafx.stage.Stage;
import teamamused.client.gui.gameboard.GameBoardController;
import teamamused.client.gui.gameboard.GameBoardModel;
import teamamused.client.gui.gameboard.GameBoardView;

public class Main extends Application {

	private Splash_View splashView;
	private LogInView logInView;
	private WelcomeView welcomeView;
	private RegisterView registerView;
	private ByeView byeView;
	private GameBoardView gameBoardView;
	private static Main instance = null;

	@Override
	public void start(Stage primaryStage) {
		Main.instance = this;
		// Create and display the splash screen and model
		Splash_Model splashModel = new Splash_Model();
		splashView = new Splash_View(primaryStage, splashModel);
		new Splash_Controller(this, splashModel, splashView);
		splashView.start();


		// Display the splash screen and begin the initialization
		splashModel.initialize();
	}

	public void startLogIn() {

		Stage logInStage = new Stage();
		LogInModel model = new LogInModel();
		logInView = new LogInView(logInStage, model);
		new LogInController(model, logInView);
		splashView.stop();
		splashView = null;
		logInView.start();
	}

	public void startWelcome() {

		Stage welcomeStage = new Stage();
		WelcomeModel model = new WelcomeModel();
		welcomeView = new WelcomeView(welcomeStage, model);
		new WelcomeController(model, welcomeView);
		logInView.stop();
		logInView = null;
		welcomeView.start();
	}
	
	public void startRegister() {

		Stage RegisterStage = new Stage();
		RegisterModel model = new RegisterModel();
		registerView = new RegisterView(RegisterStage, model);
		new RegisterController(model, registerView);
		logInView.stop();
		logInView = null;
		registerView.start();
	}
	
	public void startBye() {

		Stage ByeStage = new Stage();
		ByeModel model = new ByeModel();
		byeView = new ByeView(ByeStage, model);
		new ByeController(model, byeView);
		welcomeView.stop();
		welcomeView = null;
		byeView.start();
	}
	
	public void startGameBoard() {
		
		Stage gameBoardStage = new Stage();
		GameBoardModel model = new GameBoardModel();
		gameBoardView = new GameBoardView(gameBoardStage, model);
		new GameBoardController (model, gameBoardView);
		gameBoardView.start();
	}

	public static Main getInstance() {
		return instance;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
