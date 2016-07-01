package teamamused.server.gui;

import java.util.logging.Level;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractView;
import teamamused.server.TextAreaHandler;
import teamamused.server.connect.ClientAwaiter;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ServerView{
	private ServerModel model;
	private Stage stage;
	private Button restartButton;
	private Label label1, label2, label3, label4, label5, label6;
	private TextField txtField;
	private Image logo;
	private ScrollPane scroll;
	
	protected ServerView(Stage stage, ServerModel model){
		this.stage = stage;
		this.model = model;
		
		stage.setTitle("Team Amused: Server");
		
		BorderPane root = new BorderPane();
		Scene scene = new Scene (root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		
		logo = new Image(getClass().getResourceAsStream("logo1"));
		label1 = new Label("Dein Server läuft!");
		label2 = new Label("", new ImageView(logo));
		label3 = new Label("Spieler können sich verbinden via:");
		label4 = new Label("Port: " + ClientAwaiter.PORT_NUMBER);
		label5 = new Label("IP: ");
		label6 = new Label("Protokoll:");
		restartButton = new Button("Server neustarten");
		txtField = new TextField();
		scroll = new ScrollPane();
		scroll.setContent(txtField);
		
		HBox hBoxTop = new HBox();
		hBoxTop.getChildren().addAll(label1, label2);
		root.setTop(hBoxTop);
		
		VBox vBoxBottom = new VBox();
		vBoxBottom.getChildren().addAll(label6, txtField);
		root.setLeft(vBoxBottom);
		
		VBox vBoxCenter = new VBox();
		vBoxCenter.getChildren().addAll(label3, label4, label5);
		root.setCenter(vBoxCenter);
				
	}
	
	public void start(){
		stage.show();
	}
	
	public void stop(){
		stage.hide();
	}
	
	public Stage getStage(){
		return stage;
	}

	@Override
	protected Scene createGUI() {
		// TODO Auto-generated method stub
		return null;
	}


}

//public class ServerView extends AbstractView<ServerModel> {
//	TextAreaHandler textAreaHandler;
//    protected TextArea txtLog;
//    
//    public ServerView(Stage stage, ServerModel model) {//, TextArea txtLog) {
//    	super (stage, model);
//        stage.setTitle("Team amused Server");
//    }
//    
//
//	@Override
//	protected Scene createGUI() {
//        
//        // Logger intialisieren (wieso kann ich das nicht in die Mainklasse verschieben?)
//        this.textAreaHandler = new TextAreaHandler();
//        textAreaHandler.setLevel(Level.INFO);
//        ServiceLocator.getInstance().getLogger().addHandler(textAreaHandler);
//        this.txtLog = textAreaHandler.getTextArea();
//        
//		BorderPane root = new BorderPane();
//		Scene scene = new Scene(root,400,400);
//
//
//        HBox topBox = new HBox();
//        topBox.setId("TopBox");
//        Region spacer = new Region();
//        HBox.setHgrow(spacer, Priority.ALWAYS);
//		Label l =new Label("Dein Server läuft!");
//	    Label lblPort = new Label(" Läuft auf Port");
//	    TextField txtPort = new TextField(ClientAwaiter.PORT_NUMBER + "");
//        topBox.getChildren().addAll(l, lblPort, spacer, txtPort);
//        root.setTop(topBox);
//		
//        ScrollPane scrollPane = new ScrollPane();
//        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
//        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
//        scrollPane.setFitToHeight(true);
//        scrollPane.setFitToWidth(true);
//        root.setCenter(scrollPane);
//        scrollPane.setContent(this.txtLog);
//        this.txtLog.setWrapText(true);
//
//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//		return scene;
//	}
//
//    public void start() {
//        stage.show();
//    }
//    
//    /**
//     * Stopping the view - just make it invisible
//     */
//    public void stop() {
//        stage.hide();
//    }
//    
//    /**
//     * Getter for the stage, so that the controller can access window events
//     */
//    public Stage getStage() {
//        return stage;
//    }
//}
