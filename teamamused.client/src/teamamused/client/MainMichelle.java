package teamamused.client;

import javafx.application.Application;
import javafx.stage.Stage;
import teamamused.client.gui.ByeController;
import teamamused.client.gui.ByeModel;
import teamamused.client.gui.ByeView;
import teamamused.client.gui.LogInController;
import teamamused.client.gui.LogInModel;
import teamamused.client.gui.LogInView;
import teamamused.client.gui.RankingController;
import teamamused.client.gui.RankingModel;
import teamamused.client.gui.RankingView;
import teamamused.client.gui.WelcomeController;
import teamamused.client.gui.WelcomeModel;
import teamamused.client.gui.WelcomeView;
import teamamused.client.gui.GameOver.GameOverController;
import teamamused.client.gui.GameOver.GameOverModel;
import teamamused.client.gui.GameOver.GameOverView;
import teamamused.client.gui.MovingPopup.MovingPopupView;
import teamamused.client.gui.gameboard.GameBoardController;
import teamamused.client.gui.gameboard.GameBoardModel;
import teamamused.client.gui.gameboard.GameBoardView;
import teamamused.client.gui.splashscreen.Splash_View;
import teamamused.client.gui.waitingroom.WaitingRoomController;
import teamamused.client.gui.waitingroom.WaitingRoomModel;
import teamamused.client.gui.waitingroom.WaitingRoomView;
import teamamused.client.libs.Client;
import teamamused.common.ServiceLocator;
import teamamused.common.db.Ranking;

/*
 * TODO: NICHT EINCHECKEN!!!!
 */
public class MainMichelle extends Application {

	private Splash_View splashView;
	private LogInView logInView;
	private WelcomeView welcomeView;
	private GameBoardView gameBoardView;
	private ByeView byeView;
	private RankingView rankingView;
	private MovingPopupView movingPopupView;
	private GameOverView gameOverView;
	private WaitingRoomView waitingRoomView;
	private static MainMichelle instance = null;

	@Override
	public void start(Stage primaryStage) {
		instance = this;

		
		// der server sollte schon verbunden sein
		Client.getInstance().connectToServer("localhost", "michelle", 9636);
		// spieler sollte schon registriert sein
		Client.getInstance().logIn("Michelle", "1234");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
//      // Initialisierung des GameBoard-GUI
      GameBoardModel model = new GameBoardModel();
      gameBoardView = new GameBoardView(primaryStage, model);
      new GameBoardController(model, gameBoardView);
		ServiceLocator.getInstance().setHostServices(getHostServices());
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Client.getInstance().joinGame();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Spiel starten
//		Client.getInstance().startGame();
        ServiceLocator.getInstance().getLogger().info("Gehe zu GUI");
		Client.getInstance().startGame();
        
        // Initialisierung des MovingPopups
//        movingPopupView = new MovingPopupView(primaryStage, model);
//        new MovingPopupController(model, movingPopupView);
//        
//        // Initialisierung der GameOver-GUI
//        GameOverModel model2 = new GameOverModel(Ranking[] ranking);
//        gameOverView = new GameOverView(primaryStage, model2);
//        new GameOverController(model2, gameOverView);
        
        //defaultLogger.info("Starte View");
        // gui anzeigen
        gameBoardView.start();
//        movingPopupView.start();
//        gameOverView.start();

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
	
	public void startWaitingRoom2() {

		Stage waitingRoomStage = new Stage();
		WaitingRoomModel model = new WaitingRoomModel();
		waitingRoomView = new WaitingRoomView(waitingRoomStage, model);
		new WaitingRoomController(model, waitingRoomView);
		gameOverView.stop();
		gameOverView = null;
		waitingRoomView.start();
	}
	
	public void startRanking2(Ranking[] ranking) {

		Stage RankingStage = new Stage();
		RankingModel model = new RankingModel(ranking);
		rankingView = new RankingView(RankingStage, model);
		new RankingController(model, rankingView);
		welcomeView.stop();
		welcomeView = null;
		rankingView.start();
	}
	
	public void startBye3() {

		Stage ByeStage = new Stage();
		ByeModel model = new ByeModel();
		byeView = new ByeView(ByeStage, model);
		new ByeController(model, byeView);
		gameBoardView.stop();
		gameBoardView = null;
		byeView.start();
	}
	
	public void startBye4() {

		Stage ByeStage = new Stage();
		ByeModel model = new ByeModel();
		byeView = new ByeView(ByeStage, model);
		new ByeController(model, byeView);
		gameOverView.stop();
		gameOverView = null;
		byeView.start();
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
