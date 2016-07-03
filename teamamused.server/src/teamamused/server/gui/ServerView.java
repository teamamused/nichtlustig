package teamamused.server.gui;

import teamamused.common.gui.AbstractView;
import teamamused.server.TextAreaHandler;
import teamamused.server.connect.ClientAwaiter;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class ServerView extends AbstractView<ServerModel> {
	//TextAreaHandler textAreaHandler;
    protected TextArea txtLog;
    
    public ServerView(Stage stage, ServerModel model) {
    	super (stage, model);
        stage.setTitle("Team amused Server");
    }

	@Override
    protected void initView() {
		TextAreaHandler handler = TextAreaHandler.getInstance(); 
    	this.txtLog = handler.getTextArea();		
	}

	@Override
	protected Scene createGUI() {
        
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root,400,400);


        HBox topBox = new HBox();
        topBox.setId("TopBox");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
		Label l =new Label("Dies ist der Server! Juppie!");
	    Label lblPort = new Label(" Läuft auf Port");
	    TextField txtPort = new TextField(ClientAwaiter.PORT_NUMBER + "");
        topBox.getChildren().addAll(l, lblPort, spacer, txtPort);
        root.setTop(topBox);
		
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        root.setCenter(scrollPane);
        scrollPane.setContent(this.txtLog);
        this.txtLog.setWrapText(true);

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return scene;
	}

    public void start() {
        stage.show();
    }
    
    /**
     * Stopping the view - just make it invisible
     */
    public void stop() {
        stage.hide();
    }
    
    /**
     * Getter for the stage, so that the controller can access window events
     */
    public Stage getStage() {
        return stage;
    }
}
