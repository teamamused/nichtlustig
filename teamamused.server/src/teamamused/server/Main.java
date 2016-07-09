package teamamused.server;

import java.util.logging.Level;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import teamamused.common.ServiceLocator;
import teamamused.server.connect.ClientAwaiter;
import teamamused.server.gui.ServerController;
import teamamused.server.gui.ServerModel;
import teamamused.server.gui.ServerView;
	
/**
 * Die Mainklasse ist der Einstiegspunkt um die Serveranwendung zu starten. 
 * Sie erstellt den ClientAwaiter Thread, den Server und startet das GUI.
 * 
 * @author Daniel
 *
 */
public class Main extends Application {
	
	private TextAreaHandler textAreaHandler = new TextAreaHandler(); 
	
	@Override
	public void start(Stage primaryStage) {
        // Logger intialisieren
        textAreaHandler.setLevel(Level.INFO);
        ServiceLocator.getInstance().getLogger().addHandler(textAreaHandler);
		
		// Server Starten
		ClientAwaiter.getInstance().start();
        ServiceLocator.getInstance().getLogger().info("Gehe zu GUI");
		
        // Gui initialisieren
        ServerModel model = new ServerModel();
        ServerView view = new ServerView(primaryStage, model);
        view.setLoggingTxtArea(textAreaHandler.getTextArea());
        new ServerController(model, view);
        
        //defaultLogger.info("Starte View");
        // gui anzeigen
        view.start();
        
        // ClientAwaiter schliessen wenn Server geschlossen wird.
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				ClientAwaiter.getInstance().close();				
			}
        });

        ServiceLocator.getInstance().getLogger().info("Der Server läuft!");
	}
	
	public static void main(String[] args) {
		launch(args);		
	}
}
