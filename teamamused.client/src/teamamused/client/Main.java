package teamamused.client;
	
import javafx.application.Application;
import javafx.stage.Stage;
import teamamused.client.connect.Client;
import teamamused.client.gui.gameboard.GameBoardController;
import teamamused.client.gui.gameboard.GameBoardModel;
import teamamused.client.gui.gameboard.GameBoardView;
import teamamused.common.ServiceLocator;


public class Main extends Application {
	@Override
	
	//TODO: Löschen, wenn nicht mehr benötigt
//	public void start(Stage primaryStage) {
//		try {
//			BorderPane root = new BorderPane();
//			Scene scene = new Scene(root,400,400);
//			Label l =new Label();
//			l.setText("Dies ist der Client!");
//			Button b = new Button("Start");
//			b.setOnAction(new EventHandler<ActionEvent>() {
//				
//				@Override
//				public void handle(ActionEvent event) {
//					Client.getInstance().startGame();
//					
//				}
//			});
//			root.setCenter(l);
//			root.setBottom(b);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.show();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}

	public void start(Stage primaryStage) {

		//TODO: Michelle -> Start-Button des WaitingRoom muss Client.getInstance().startGame() aufrufen
		
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
	
	public static void main(String[] args) {
		launch(args);
	}
}
