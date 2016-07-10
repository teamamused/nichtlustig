package teamamused.playground.chat;

import java.text.DateFormat;

import teamamused.client.libs.Client;
import teamamused.client.libs.IClientListener;
import teamamused.common.ServiceLocator;
import teamamused.common.dtos.TransportableChatMessage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ChatClient extends Application implements IClientListener {
	TextArea txtChat;
	TextField txtUser;
	TextField txtNewMsg;
	
	@Override
	public void start(Stage primaryStage) {
		// uns beim Client Registrieren
		Client.getInstance().registerGui(this);
		
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 600, 400);
		
		// Titel und verbinden
		VBox topBox = new VBox();
		HBox topBox2 = new HBox();
		Label lTitel = new Label("Team amused Test Chat Client");
		lTitel.getStyleClass().add("titelLabel");
		TextField txtServer = new TextField("localhost");
		txtUser = new TextField("Username");
		TextField txtPort = new TextField("9636");
		Button btnStartChat = new Button("verbinde");
		topBox.getChildren().addAll(lTitel, topBox2);
		topBox2.getChildren().addAll(txtServer, txtUser, txtPort, btnStartChat);
		// Chat selber
		txtChat = new TextArea("");
		// Nachricht senden
		HBox bottomBox = new HBox();
		txtNewMsg = new TextField("Nachricht senden");
		txtNewMsg.prefWidthProperty().bind(txtChat.widthProperty().multiply(0.8));
		Button btnSenden = new Button("senden");
		btnSenden.prefWidthProperty().bind(txtChat.widthProperty().multiply(0.2));
		txtNewMsg.setDisable(true);
		btnSenden.setDisable(true);
		bottomBox.getChildren().addAll(txtNewMsg, btnSenden);
		
		// Alle Elemente im Gui anordnen
		root.setTop(topBox);
		root.setCenter(txtChat);
		root.setBottom(bottomBox);
		// Styling
		try {
			// CSS Gestaltungs File laden
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Controller Part...
		btnStartChat.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean erfolgreich = Client.getInstance().ConnectToServer(txtServer.getText(), txtUser.getText(), Integer.parseInt(txtPort.getText()));
				if (erfolgreich) {
					ServiceLocator.getInstance().getLogger().info("Erfolgreich zum Server verbunden");
					txtNewMsg.setDisable(false);
					btnSenden.setDisable(false);
				} else {
					ServiceLocator.getInstance().getLogger().info("Verbindung zum Server konnte nicht hergestellt werden.");
				}
			}
		});
		btnSenden.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sendMessage();
			}
		});
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent event) {
				Client.getInstance().sayGoodbye();
			};
		});
		
		// Anzeigen
		primaryStage.setTitle("Super dupper Test Chat");
		primaryStage.setScene(scene);
		primaryStage.show();		
	}

	@Override
	public void onChatMessageRecieved(TransportableChatMessage message) {
		DateFormat df = DateFormat.getTimeInstance();
		this.txtChat.appendText(df.format(message.getTime()) + " - " + message.getSender() + ": " + message.getMessage());
		this.txtChat.appendText("\n");
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private void sendMessage() {
		TransportableChatMessage message = new TransportableChatMessage(txtUser.getText(), txtNewMsg.getText());
		Client.getInstance().sendChatMessage(message);
		txtNewMsg.setText("");
	}
}
