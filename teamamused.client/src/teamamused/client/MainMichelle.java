package teamamused.client;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;
import teamamused.client.gui.LogInController;
import teamamused.client.gui.LogInModel;
import teamamused.client.gui.LogInView;
import teamamused.client.gui.WelcomeController;
import teamamused.client.gui.WelcomeModel;
import teamamused.client.gui.WelcomeView;
import teamamused.client.gui.gameboard.GameBoardController;
import teamamused.client.gui.gameboard.GameBoardModel;
import teamamused.client.gui.gameboard.GameBoardView;
import teamamused.client.gui.splashscreen.Splash_View;
import teamamused.common.ServiceLocator;

/*
 * TODO: NICHT EINCHECKEN!!!!
 */
public class MainMichelle extends Application {

	private Splash_View splashView;
	private LogInView logInView;
	private WelcomeView welcomeView;
	private GameBoardView gameBoardView;
	private static MainMichelle instance = null;

	@Override
	public void start(Stage primaryStage) {
		
		ServiceLocator.getInstance().setHostServices(getHostServices());

		//TODO: Sandra -> Start-Button muss Client.getInstance().startGame() aufrufen
		
		// Spiel starten
//		Client.getInstance().startGame();
        ServiceLocator.getInstance().getLogger().info("Gehe zu GUI");
		
        // Initialisierung des GameBoard-GUI
        GameBoardModel model = new GameBoardModel();
        GameBoardView view = new GameBoardView(primaryStage, model);
        new GameBoardController(model, view);
        
        //defaultLogger.info("Starte View");
        // gui anzeigen
        view.start();
        

        ServiceLocator.getInstance().getLogger().info("Client ist verbunden!");
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
	public void startWelcome2() {

		Stage welcomeStage = new Stage();
		WelcomeModel model = new WelcomeModel();
		welcomeView = new WelcomeView(welcomeStage, model);
		new WelcomeController(model, welcomeView);
		welcomeView.start();
	}
	
	public void startGameBoard() {
		
		Stage gameBoardStage = new Stage();
		GameBoardModel model = new GameBoardModel();
		gameBoardView = new GameBoardView(gameBoardStage, model);
		new GameBoardController (model, gameBoardView);
		gameBoardView.start();
	}

	public static MainMichelle getInstance() {
		return instance;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
