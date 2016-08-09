package teamamused.client;

import javafx.application.Application;
import javafx.stage.Stage;
import teamamused.client.gui.register.RegisterController;
import teamamused.client.gui.register.RegisterModel;
import teamamused.client.gui.register.RegisterView;
import teamamused.client.gui.gameOver.GameOverController;
import teamamused.client.gui.gameOver.GameOverModel;
import teamamused.client.gui.gameOver.GameOverView;
import teamamused.client.gui.bye.ByeController;
import teamamused.client.gui.bye.ByeModel;
import teamamused.client.gui.bye.ByeView;
import teamamused.client.gui.gameboard.GameBoardController;
import teamamused.client.gui.gameboard.GameBoardModel;
import teamamused.client.gui.gameboard.GameBoardView;
import teamamused.client.gui.logIn.LogInController;
import teamamused.client.gui.logIn.LogInModel;
import teamamused.client.gui.logIn.LogInView;
import teamamused.client.gui.ranking.RankingController;
import teamamused.client.gui.ranking.RankingModel;
import teamamused.client.gui.ranking.RankingView;
import teamamused.client.gui.splashscreen.Splash_Controller;
import teamamused.client.gui.splashscreen.Splash_Model;
import teamamused.client.gui.splashscreen.Splash_View;
import teamamused.client.gui.welcome.WelcomeController;
import teamamused.client.gui.welcome.WelcomeModel;
import teamamused.client.gui.welcome.WelcomeView;
import teamamused.common.ServiceLocator;
import teamamused.common.db.Ranking;
import teamamused.common.gui.Translator;
import teamamused.common.interfaces.IUserView;

public class Main extends Application {

	private Splash_View splashView;
	private LogInView logInView;
	private WelcomeView welcomeView;
	private RegisterView registerView;
	private RankingView rankingView;
	private ByeView byeView;
	private GameBoardView gameBoardView;
	private GameOverView gameOverView;
	private static Main instance = null;

	@Override
	public void start(Stage primaryStage) {
		Main.instance = this;
		// Setzt dem ServiceLocator die JavaFX-HostServices
		ServiceLocator.getInstance().setHostServices(getHostServices());
		ServiceLocator.getInstance().setTranslator(new Translator(null));
		// Create and display the splash screen and model
		Splash_Model splashModel = new Splash_Model();
		splashView = new Splash_View(primaryStage, splashModel);
		new Splash_Controller(this, splashModel, splashView);
		splashView.start();

		// Display the splash screen and begin the initialization
		splashModel.initialize();
	}

	public void startLogIn(IUserView toClose) {

		Stage logInStage = new Stage();
		LogInModel model = new LogInModel();
		logInView = new LogInView(logInStage, model);
		new LogInController(model, logInView);

		if (toClose != null) {
			toClose.stop();
			toClose = null;
		}
		logInView.start();
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

	public void startWelcome(IUserView toClose) {

		Stage welcomeStage = new Stage();
		WelcomeModel model = new WelcomeModel();
		welcomeView = new WelcomeView(welcomeStage, model);
		new WelcomeController(model, welcomeView);

		if (toClose != null) {
			toClose.stop();
			toClose = null;
		}
		welcomeView.start();
	}

	public void startRanking(Ranking[] ranking, IUserView toClose) {

		Stage rankingStage = new Stage();
		RankingModel model = new RankingModel(ranking);
		rankingView = new RankingView(rankingStage, model);
		new RankingController(model, rankingView);

		if (toClose != null) {
			toClose.stop();
			toClose = null;
		}
		rankingView.start();
	}

	public void startGameBoard(IUserView toClose) {

		Stage gameBoardStage = new Stage();
		GameBoardModel model = new GameBoardModel();
		gameBoardView = new GameBoardView(gameBoardStage, model);
		new GameBoardController(model, gameBoardView);

		if (toClose != null) {
			toClose.stop();
			toClose = null;
		}
		gameBoardView.start();
	}

	public void startGameOver(Ranking[] ranking) {

		Stage gameOverStage = new Stage();
		GameOverModel gameOverModel = new GameOverModel(ranking);
		gameOverView = new GameOverView(gameOverStage, gameOverModel);
		new GameOverController(gameOverModel, gameOverView);
		gameBoardView.stop();
		gameBoardView = null;
		gameOverView.start();
	}
	
	public void startBye(IUserView toClose) {

		Stage byeStage = new Stage();
		ByeModel model = new ByeModel();
		byeView = new ByeView(byeStage, model);
		new ByeController(model, byeView);

		if (toClose != null) {
			toClose.stop();
			toClose = null;
		}
		byeView.start();
	}

	public static Main getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
