package teamamused.client.gui.gameboard;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import teamamused.client.libs.Client;
import teamamused.common.LogHelper;
import teamamused.common.ResourceLoader;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractView;
import teamamused.common.gui.LangText;
import teamamused.common.gui.Translator;
import teamamused.common.interfaces.ICube;
import teamamused.common.interfaces.IDeadCard;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.ISpecialCard;
import teamamused.common.interfaces.ITargetCard;

/**
 * Diese Klasse stellt die grafische Oberfläche für das Spielfeld dar.
 * 
 * @author Michelle
 *
 */
public class GameBoardView extends AbstractView<GameBoardModel> {

	protected GridPane rootFixPart, cardPane, dicesPane, targetCardsPane, specialCardsPane, deadCardsPane;
	protected BorderPane root, dicePane;
	protected VBox navigation;
	protected FlowPane playerPane;
	protected HBox titlePane, linkPane;
	protected ImageView logo, linkIcon, exitIcon;
	protected Image linkImage, exitImage;
	protected Hyperlink linkAnleitung;
	protected Button btnPlayer, btnWuerfeln, btnLink, btnExit, btnSenden, btnStart;
	protected TextArea txtChatScreen, txtGameMove;
	protected TextField txtChatInput;
	protected ScrollPane scrollTxt, scrollPane, scrollMoving;
	protected Label labelSpielfeld, labelRollDices, labelSelectedDices;
	protected String url;
	protected DiceControl[] diceControlArray;
	protected List<Button> btnArray;

	public GameBoardView(Stage stage, GameBoardModel model) {
		super(stage, model);
	}

	@Override
	protected Scene createGUI() {
		stage.setTitle("Nicht Lustig: Spielfeld");

		// Definition der Haupt-Pane
		root = new BorderPane();
		rootFixPart = GameBoardView.initializeGridPane();

		Scene scene = new Scene(root, 1600, 900);

		// Instanziierung und Zuweisung des Controlls zur Title-Pane
		labelSpielfeld = new Label(String.format("Spielfeld von Spieler %s", model.player.getPlayerName()));
		labelSpielfeld.setId("labelSpielfeld");
		labelSpielfeld.setAlignment(Pos.CENTER);
		
		titlePane = new HBox();
		titlePane.getChildren().addAll(labelSpielfeld);
		titlePane.setAlignment(Pos.CENTER_LEFT);
		titlePane.setPadding(new Insets(5, 5, 5, 5));
		
		// Instanziierung und Zuweisung der Controlls zur Link-Pane
		url = "http://www.kosmos.de/_files_media/mediathek/downloads/anleitungen/1351/nicht_lustig.pdf";
		try {
			linkImage = ResourceLoader.getImage("IconFragezeichen.png");
		} catch (FileNotFoundException e) {
			LogHelper.LogException(e);
		}
		linkIcon = new ImageView(linkImage);
		linkIcon.setFitHeight(40);
		linkIcon.setPreserveRatio(true);
		btnLink = new Button("", linkIcon);
		btnLink.setId("btnTransparent");
		try {
			exitImage = ResourceLoader.getImage("Exit.png");
		} catch (FileNotFoundException e) {
			LogHelper.LogException(e);
		}
		exitIcon = new ImageView(exitImage);
		exitIcon.setFitHeight(30);
		exitIcon.setPreserveRatio(true);
		btnExit = new Button("", exitIcon);
		btnExit.setId("btnTransparent");

		linkPane = new HBox();
		linkPane.getChildren().addAll(btnLink, btnExit);
		linkPane.setAlignment(Pos.CENTER_RIGHT);
		
		// Instanziierung und Zuweisung der Controlls zur linken Navigations-Pane
		navigation = new VBox(5);
		navigation.setPadding(new Insets(20, 20, 20, 20));

		try {
			logo = new ImageView(ResourceLoader.getImage("Logo_1.png"));
		} catch (FileNotFoundException e) {
			LogHelper.LogException(e);
		}
		logo.setFitWidth(200);
		logo.setPreserveRatio(true);

		playerPane = new FlowPane(5, 5);
		btnArray = new ArrayList<Button>();
		// Erstellt dynamisch die Anzahl Buttons gemäss der Anzahl Spieler
		buildPlayer();

		btnStart = GameBoardView.initializeButton("Spiel starten");
		txtChatScreen = new TextArea();
		txtChatScreen.setMaxHeight(360);
		txtChatScreen.setEditable(false);
		txtChatInput = new TextField();
		txtChatInput.setMaxHeight(30);
		txtChatScreen.getStyleClass().add("gameBoardContent");
		txtChatInput.getStyleClass().add("gameBoardContent");
		txtChatInput.setPromptText("Starte zu chatten");
		btnSenden = new Button("Nachricht senden");
		btnSenden.setAlignment(Pos.CENTER);
		scrollTxt = new ScrollPane();
		scrollTxt.setContent(txtChatScreen);
		Tooltip chatInputTool = new Tooltip("Hier kannst du deine Chatnachrichten eingeben");
		Tooltip.install(txtChatInput, chatInputTool);
		
		// Instanziierung und Zuordnung des Controlls zur "movingPane"
		txtGameMove = new TextArea();
		txtGameMove.setEditable(false);
		txtGameMove.getStyleClass().add("gameBoardContent");
		ScrollPane scrollMoving = new ScrollPane();
		scrollMoving.setContent(txtGameMove);
		navigation.getChildren().addAll(btnStart, logo, playerPane, txtGameMove, txtChatScreen, txtChatInput, btnSenden);

		// Definition der Pane für die Spielkarten
		targetCardsPane = GameBoardView.initializeGridPane();
		deadCardsPane = GameBoardView.initializeGridPane();
		specialCardsPane = GameBoardView.initializeGridPane();
		cardPane = GameBoardView.initializeGridPane();
		cardPane.setPadding(new Insets(0,0,0,0));
		cardPane.add(specialCardsPane, 0, 0);
		cardPane.add(targetCardsPane, 1, 0);
		cardPane.add(deadCardsPane, 2, 0);
		buildCards();
		
		// Definition der Pane für den Würfel-Bereich
		dicePane = new BorderPane();
		dicesPane = GameBoardView.initializeGridPane();

		// Instanziierung und Zuordnung der Controlls zur "dicePane"
		labelRollDices = new Label();
		labelRollDices.getStyleClass().add("gameBoardContent");
		updateTextOnLabelRollDices();

		labelSelectedDices = new Label("Deine gesetzten Würfel:");
		labelSelectedDices.getStyleClass().add("gameBoardContent");
		VBox diceLabels = new VBox(10);
		diceLabels.minWidth(400);
		diceLabels.getChildren().addAll(labelRollDices, labelSelectedDices);
		btnWuerfeln = GameBoardView.initializeButton("würfeln");
		dicesPane.add(btnWuerfeln, 10, 0);

		dicePane.setLeft(diceLabels);
		dicePane.setCenter(dicesPane);
		
		// Zuordnung der Sub-Panes zur Haupt-Pane "root"
		rootFixPart.add(titlePane, 1, 0);
		rootFixPart.add(linkPane, 1, 0);
		rootFixPart.add(cardPane, 1, 1);
		rootFixPart.add(dicePane, 1, 3, 1, 9);
		root.setCenter(navigation);
		root.setRight(rootFixPart);

		// Auf der ObserverList wird ein ListChangeListener registriert, welcher
		// immer, wenn etwas der Liste hinzugefügt wird, dieses auf dem Screen
		// ausgibt. get(0), da immer nur etwas daher kommt.
		model.chatMessages.addListener(new ListChangeListener<String>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> change) {
				if (!change.next())
					return;
				if (change.wasAdded()) {
					txtChatScreen.appendText("\n" + change.getAddedSubList().get(0));
				}
			}
		});

		// Zuweisung des Stylesheets
		try {
			scene.getStylesheets().add(getClass().getResource("..\\application.css").toExternalForm());
		} catch (Exception e) {
			LogHelper.LogException(e);
		}

		updateTexts();
		
		return scene;
	}

	/**
	 * Nur für Bilder der Spielfeldkarten verwenden!
	 * 
	 * Dieser Support-Methode kann als String ein Bildname übergeben werden,
	 * anhand welchem der ResourceLoader ein Image-Objekt zurückgibt. Dieses
	 * Objekt wird einer ImageView übergeben und so in der Grösse für dem
	 * Spielfeld entsprechend angepasst und schlussendlich zurückgegeben.
	 * 
	 * @param imageName
	 *            Methode nimmt den Bildnamen (inkl. Endung) als String entgegen
	 * @return Methode gibt eine ImageView des gewünschten Bildes zurück
	 */
	private ImageView getImageView(Image image) {
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(115);
		imageView.setPreserveRatio(true);
		return imageView;
	}

	/**
	 * Die Support-Methode instanziiert eine GridPane und gibt dieser
	 * Layout-Vorgaben mit (Wiederverwendbarkeit von Code)
	 * 
	 * @return Die Methode gibt eine "formatierte" GridPane zurück
	 */
	private static GridPane initializeGridPane() {
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(5, 5, 5, 5));
		pane.setHgap(5);
		pane.setVgap(5);
		pane.setGridLinesVisible(false);
		return pane;
	}

	/**
	 * Die Support-Methode instanziiert einen Button und gibt diesen formatiert
	 * zurück (Wiederverwendbarkeit von Code)
	 * 
	 * @param buttonText
	 *            Bezeichnung des Buttons als String
	 * @return formatiertes Button-Objekt
	 */
	private static Button initializeButton(String buttonText) {
		Button btn = new Button(buttonText);
		btn.setPrefSize(200, 40);
		btn.setAlignment(Pos.CENTER);
		return btn;
	}

	/**
	 * Diese Methode zeichnet die Karten auf dem GameBoard neu, wenn sich eine
	 * Änderung ergeben hat.
	 */
	protected void buildCards() {
		// Zielkarten werden gezeichnet
		int zeileTarget = 0;
		if (model.targetCardsNeedsUpdate) {
			model.targetCardsNeedsUpdate = false;
			if (this.targetCardsPane.getChildren() != null) {
				this.targetCardsPane.getChildren().clear();
			}
			for (ITargetCard targetCards : model.gameBoard.getTargetCards()) {
				if (targetCards.getGameCard().isRiebmann()) {
					zeileTarget = 0;
				} else if (targetCards.getGameCard().isYeti()) {
					zeileTarget = 1;
				} else if (targetCards.getGameCard().isLemming()) {
					zeileTarget = 2;
				} else if (targetCards.getGameCard().isProffessoren()) {
					zeileTarget = 3;
				} else if (targetCards.getGameCard().isDino()) {
					zeileTarget = 4;
				}
				targetCardsPane.add(getImageView(targetCards.getForegroundImage()),
						(targetCards.getGameCard().getCardNumber() - 1) % 5, zeileTarget);
			}
		}
		// Todeskarten werden gezeichnet
		int spalteDead = 0;
		int zeileDead = 1;
		int iteratorDead = 1;
		if (model.deadCardsNeedsUpdate) {
			model.deadCardsNeedsUpdate = false;
			if (this.deadCardsPane.getChildren() != null) {
				this.deadCardsPane.getChildren().clear();
			}
			for (IDeadCard deadCards : model.gameBoard.getDeadCards()) {
				iteratorDead++;
				deadCardsPane.add(getImageView(deadCards.getForegroundImage()), spalteDead, zeileDead++);
				if (iteratorDead == 4) {
					spalteDead = 1;
					zeileDead = 1;
				}
			}
		}
		// Spezialkarten werden gezeichnet
		int spalteSpecial = 0;
		int zeileSpecial = 1;
		int iteratorSpecial = 1;
		if (model.specialCardsNeedsUpdate) {
			model.specialCardsNeedsUpdate = false;
			if (this.specialCardsPane.getChildren() != null) {
				this.specialCardsPane.getChildren().clear();
			}
			for (ISpecialCard specialCards : model.gameBoard.getSpecialCards()) {
				iteratorSpecial++;
				specialCardsPane.add(getImageView(specialCards.getForegroundImage()), spalteSpecial, zeileSpecial++);
				if (iteratorSpecial == 4) {
					spalteSpecial = 1;
					zeileSpecial = 1;
				}
			}
		}

	}

	/**
	 * 
	 * Diese Methode zeichnet die PlayerButtons - je nach Anzahl Mitspieler. Der
	 * aktive Spieler wird farblich hervorgehoben.
	 * 
	 */
	protected void buildPlayer() {
		for (Button oldButton : this.btnArray) {
			this.playerPane.getChildren().remove(oldButton);
			oldButton = null;
		}
		if (model.gameBoard != null) {
			for (IPlayer player : model.gameBoard.getPlayers()) {
				btnPlayer = GameBoardView.initializeButton(player.getPlayerNumber() + ". " + player.getPlayerName());
				// Player wird mit setUserData() an Button "gebunden", um diesen
				// im Controller auszulesen
				btnPlayer.setUserData(player);
				// Hebt den aktiven Spieler hervor
				if (Client.getInstance().getActivePlayer() != null
						&& player.getPlayerNumber() == Client.getInstance().getActivePlayer().getPlayerNumber()) {
					btnPlayer.setId("btnActivePlayer");
				// Wenn der Spieler nicht mehr im spiel ist grau hinterlegen
				} else if (!player.getConnected()) {
					btnPlayer.setId("btnDisconnectedPlayer");
				}
				this.btnArray.add(btnPlayer);
				btnPlayer.setPrefWidth(txtChatInput.getWidth()/2-3);
				this.playerPane.getChildren().add(btnPlayer);
			}
		}
	}

	/**
	 * Diese Methode zeichnet die Würfel neu, wenn sich eine Änderung eregeben
	 * hat.
	 */
	protected void buildDices() {
		// Entfernt jedes Mal die Nodes von der Pane - ansonsten würden sie
		// immer darüber gelegt werden wegen der Methode add()
		Iterator<Node> iterator = dicesPane.getChildren().iterator();
		while (iterator.hasNext()) {
			if (iterator.next() instanceof DiceControl) {
				iterator.remove();
			}
		}
		// Fügt die DiceControlls zum Würfeln hinzu
		diceControlArray = new DiceControl[7];
		ICube[] cubes = model.getCubes();
		for (int i = 0; i < cubes.length; i++) {
			DiceControl diceControl = new DiceControl(cubes[i]);
			diceControlArray[i] = diceControl;
			if (cubes[i].getIsFixed()) {
				dicesPane.add(diceControl, cubes[i].getCubeNumber(), 1);
			} else {
				dicesPane.add(diceControl, cubes[i].getCubeNumber(), 0);
			}
		}
	}

	/**
	 * Setzt dem Label den Text neu, damit die Anzahl übrige Würfelversuche
	 * aktuell angezeigt wird.
	 * 
	 */
	protected void updateTextOnLabelRollDices() {
		String text = ServiceLocator.getInstance().getTranslator().getString(LangText.GameBoardRollDices);
		Platform.runLater(() -> labelRollDices.setText(String.format(text, model.remainingDices)));
	}

	/**
	 * Aktualisiert die Sprachtexte auf allen GUI-Elementen
	 */
	protected void updateTexts() {

		// Translator holen
		Translator tl = ServiceLocator.getInstance().getTranslator();

		// Texte holen
		stage.setTitle(tl.getString(LangText.GameBoardTitle));
		this.labelSpielfeld.setText(String.format(tl.getString(LangText.GameBoardSpielfeld), model.player.getPlayerName()));
		this.btnSenden.setText(tl.getString(LangText.GameBoardBtnSenden));
		this.labelSelectedDices.setText(tl.getString(LangText.GameBoardSelectedDices));
		this.btnWuerfeln.setText(tl.getString(LangText.GameBoardBtnWuerfeln));
		this.btnStart.setText(tl.getString(LangText.GameBoardStart));
	}
}
