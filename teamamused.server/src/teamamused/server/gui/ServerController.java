package teamamused.server.gui;

import teamamused.common.gui.AbstractController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class ServerController extends AbstractController<ServerModel, ServerView> {
    
    public ServerController(ServerModel model, ServerView view) {
    	super(model, view);
        this.model = model;
        this.view = view;
                
        // register ourselves to handle window-closing event
        view.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                view.stop();     // closes the GUI
                Platform.exit(); // ends any JavaFX activities
                System.exit(0);  // end all activities (our server task) - not good code
            }
        });
    }
}
