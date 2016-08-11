package teamamused.client;

import java.util.Hashtable;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
import teamamused.client.libs.Client;
import teamamused.client.libs.IClientListener;
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
	// Hier merkt sich die Mainklasse alle Clientlistener welche sich beim
	// schliessen des entsprechenden Gui's beim server abmelden sollen.
	private Hashtable<IUserView, IClientListener> toderegister;

	@Override
	public void start(Stage primaryStage) {
		Main.instance = this;
		this.toderegister = new Hashtable<IUserView, IClientListener>();
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
		
		LogInController cont = new LogInController(model, logInView);
		this.registerViewAndController(logInView, cont);

		this.closeAndDeregisterView(toClose);
		logInView.start();
	}

	public void startRegister() {

		Stage RegisterStage = new Stage();
		RegisterModel model = new RegisterModel();
		registerView = new RegisterView(RegisterStage, model);

		RegisterController cont = new RegisterController(model, registerView);
		this.registerViewAndController(registerView, cont);
		
		this.closeAndDeregisterView(logInView);
		registerView.start();
	}

	public void startWelcome(IUserView toClose) {
		
		Stage welcomeStage = new Stage();
		WelcomeModel model = new WelcomeModel();
		welcomeView = new WelcomeView(welcomeStage, model);
		
		WelcomeController cont = new WelcomeController(model, welcomeView);
		this.registerViewAndController(welcomeView, cont);

		this.closeAndDeregisterView(toClose);
		welcomeView.start();
	}

	public void startRanking(Ranking[] ranking, boolean isGamefinished, IUserView toClose) {

		Stage rankingStage = new Stage();
		RankingModel model = new RankingModel(ranking, isGamefinished);
		rankingView = new RankingView(rankingStage, model);
		
		RankingController cont = new RankingController(model, rankingView);
		this.registerViewAndController(rankingView, cont);

		this.closeAndDeregisterView(toClose);
		rankingView.start();
	}

	public void startGameBoard(IUserView toClose) {

		Stage gameBoardStage = new Stage();
		GameBoardModel model = new GameBoardModel();
		gameBoardView = new GameBoardView(gameBoardStage, model);

		GameBoardController cont = new GameBoardController(model, gameBoardView);
		this.registerViewAndController(gameBoardView, cont);

		this.closeAndDeregisterView(toClose);
		gameBoardView.start();
	}

	public void startGameOver(Ranking[] ranking) {

		Stage gameOverStage = new Stage();
		GameOverModel gameOverModel = new GameOverModel(ranking);
		gameOverView = new GameOverView(gameOverStage, gameOverModel);

		GameOverController cont = new GameOverController(gameOverModel, gameOverView);
		this.registerViewAndController(gameOverView, cont);

		this.closeAndDeregisterView(gameBoardView);
		gameOverView.start();
	}

	public void startBye(IUserView toClose) {

		Stage byeStage = new Stage();
		ByeModel model = new ByeModel();
		byeView = new ByeView(byeStage, model);
		
		ByeController cont = new ByeController(model, byeView);
		this.registerViewAndController(byeView, cont);

		this.closeAndDeregisterView(toClose);
		byeView.start();
	}

	public static Main getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Prüft ob sich die View noch beim Client abmelden muss (falls sie
	 * IClientlistener ist) und schliesst sie anschliessend
	 * 
	 * @param toClose
	 *            zuschliessende View
	 */
	private void closeAndDeregisterView(IUserView toClose) {
		if (toClose != null) {
			// Prüfen ob sich die View noch beim Client deregistrieren muss
			if (this.toderegister.containsKey(toClose)) {
				Client.getInstance().deregisterGui(this.toderegister.get(toClose));
				this.toderegister.remove(toClose);
			}
			// View schliessen
			toClose.stop();
			toClose = null;
		}
	}
	
	private void registerViewAndController(IUserView view, IClientListener cont) {
		this.toderegister.put(view, cont);
		// Maske auf schliessen oben geschlossen -> abmelden
		view.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	        	  closeAndDeregisterView(view);
	        	  Client.getInstance().sayGoodbye();
	          }
	      });      
	
	}
}
