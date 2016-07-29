package teamamused.playground.login;

import teamamused.client.libs.Client;
import teamamused.client.libs.IClientListener;
import teamamused.common.LogHelper;
import teamamused.common.ServiceLocator;
import teamamused.common.db.Ranking;
import teamamused.common.db.DataBaseHelper;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.models.GameBoard;
import teamamused.playground.application.gui.GameBoardController;
import teamamused.playground.application.gui.GameBoardModel;
import teamamused.playground.application.gui.GameBoardView;
import teamamused.playground.application.gui.RankingModel;
import teamamused.playground.application.gui.RankingView;
import teamamused.playground.application.gui.RankingController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoginTest extends Application implements IClientListener {
	static LoginTest instance;
	TextField txtServer;
	TextField txtPort;
	Button btnConnectServer;
	
	TextField txtUser;
	TextField txtPassword;
	Button btnLogin;
	
	TextField txtRegisterUser;
	TextField txtRegisterPassword;
	Button btnRegister;

	Button btnJoinGame;
	Button btnCreateDemoRanking;
	Button btnGetRanking;
	
	
	TextArea output;
	
	@Override
	public void start(Stage primaryStage) {
		instance = this;
		// uns beim Client Registrieren
		Client.getInstance().registerGui(this);
		VBox root = new VBox(10);
		Scene scene = new Scene(root, 600, 400);
		
		// Titel 
		Label lTitel = new Label("Team amused Test Login");
		lTitel.getStyleClass().add("titelLabel");
		// verbinden
		HBox connectServer = new HBox();
		Label lVerbindung = new Label("Verbinde zum Server: ");
		txtServer = new TextField("localhost");
		txtPort = new TextField("9636");
		btnConnectServer = new Button("verbinde");
		connectServer.getChildren().addAll(lVerbindung, txtServer, txtPort, btnConnectServer);
		
		// Login
		HBox login = new HBox();
		Label lLogin = new Label("Anmelden: ");
		txtUser = new TextField("Benutzername");
		txtPassword = new TextField("Passwort");
		btnLogin = new Button("Login");
		login.getChildren().addAll(lLogin, txtUser, txtPassword, btnLogin);
		
		// Register
		HBox register = new HBox();
		Label lRegister = new Label("Registrieren: ");
		txtRegisterUser = new TextField("Benutzername");
		txtRegisterPassword = new TextField("Passwort");
		btnRegister = new Button("Registrieren");
		register.getChildren().addAll(lRegister, txtRegisterUser, txtRegisterPassword, btnRegister);
		
		// Join Game
		this.btnJoinGame = new Button("Spiel beitreten");
		
		// btnCreateDemoRanking Ranking
		this.btnCreateDemoRanking = new Button("Demo Bestenlisten Daten anlegen(vor Server start, da Server diese Cached)");

		// Get Ranking
		this.btnGetRanking = new Button("Bestenliste anzeigen");
		
		// Output
		this.output = new TextArea();
		this.output.minHeight(100);
		
		root.getChildren().addAll(lTitel, connectServer, register, login, this.btnJoinGame, this.btnGetRanking, this.btnCreateDemoRanking, this.output);
		
		
		
		
		
		
		// Controller Part...
		btnConnectServer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean erfolgreich = Client.getInstance().connectToServer(txtServer.getText(), txtUser.getText(), Integer.parseInt(txtPort.getText()));
				if (erfolgreich) {
					ServiceLocator.getInstance().getLogger().info("Erfolgreich zum Server verbunden");
				} else {
					ServiceLocator.getInstance().getLogger().info("Verbindung zum Server konnte nicht hergestellt werden.");
				}
			}
		});
		btnLogin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Client.getInstance().logIn(txtUser.getText(),txtPassword.getText());
			}
		});
		
		btnRegister.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Client.getInstance().registerPlayer(txtRegisterUser.getText(),txtRegisterPassword.getText());
			}
		});
		
		btnCreateDemoRanking.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DataBaseHelper.createDemoData(true, true, true);
				ServiceLocator.getInstance().getDBContext().saveContext();
			}
		});
		
		btnJoinGame.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				instance.startGameBoard();
				Client.getInstance().joinGame();
			}
		});
		
		btnGetRanking.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Client.getInstance().getRanking();
			}
		});
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent event) {
				Client.getInstance().sayGoodbye();
			};
		});
		
		// Anzeigen
		primaryStage.setTitle("Test Login Funktionalitäten");
		primaryStage.setScene(scene);
		primaryStage.show();		
	}

	@Override
	public void onLoginSuccessful(IPlayer player) {
		this.addOutput("Login erfolgreich");		
	}
	@Override
	public void onLoginFailed(String msg) {
		this.addOutput("Login gescheitert: " + msg);		
	}

	@Override
	public void onRegisterSuccessful(IPlayer player) {
		this.addOutput("Register erfolgreich");		
	}
	@Override
	public void onRegisterFailed(String msg) {
		this.addOutput("Register gescheitert: " + msg);		
	}


	@Override
	public void onJoinGameSuccessful(IPlayer player) {
		this.addOutput("JoinGame erfolgreich");		
	}
	@Override
	public void onJoinGameFailed(String msg) {
		this.addOutput("JoinGame gescheitert: " + msg);		
	}

	@Override
	public  void onGameBoardChanged(GameBoard newGameBoard) {
		this.addOutput("Spielbrett wurde vom Server gesendet: " + newGameBoard);		
	}

	@Override
	public void onRankingRecieved(Ranking[] rankings) {
		
		for (Ranking rank : rankings) {
			this.addOutput(rank.toString());
		}
		Platform.runLater(() -> {
			Stage rs = new Stage();
			RankingModel model = new RankingModel(rankings);
			RankingView rv = new RankingView(rs, model);
			new RankingController (model, rv);
			rv.start();
		});
	}
	
	public static void main(String[] args) {
		try {
			launch(args);
		} catch (Exception ex){
			LogHelper.LogException(ex);
		}
	}

	
	public void startGameBoard() {
		Stage gameBoardStage = new Stage();
		GameBoardModel model = new GameBoardModel();
		GameBoardView gameBoardView = new GameBoardView(gameBoardStage, model);
		new GameBoardController (model, gameBoardView);
		gameBoardView.start();
	}
	
	private void addOutput(String message) {
		this.output.setText(this.output.getText() + "\n" + message);
	}
}