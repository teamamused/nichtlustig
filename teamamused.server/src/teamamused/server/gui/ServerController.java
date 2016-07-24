package teamamused.server.gui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractController;
import teamamused.server.Server;

public class ServerController extends AbstractController<ServerModel, ServerView> {
	
	private static final Logger LOG = ServiceLocator.getInstance().getLogger();
    
    public ServerController(ServerModel model, ServerView view) {
    	super(model, view);

    	view.restartButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				view.loggingTxtArea.clear();
				LOG.info("Server wird neu gestartet");
				Server.resetServer();
			}
    	});
    	
    	
    	//TODO: Implementation der ChoiceBox in Verbindung mit Translator
    	
    	
    	
    	//TODO: Für was ist das?
//        // register ourselves to handle window-closing event
//        view.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent event) {
//                view.stop();     // closes the GUI
//                Platform.exit(); // ends any JavaFX activities
//                System.exit(0);  // end all activities (our server task) - not good code
//            }
//        });
    }
}
