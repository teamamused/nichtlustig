package teamamused.client.gui.gameboard;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ListChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import teamamused.common.LogHelper;
import teamamused.common.ResourceLoader;
import teamamused.common.gui.AbstractView;
import teamamused.common.interfaces.ICube;
import teamamused.common.interfaces.IDeadCard;
import teamamused.common.interfaces.ISpecialCard;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.Player;

/**
 * Diese Klasse stellt die grafische Oberfläche für das Spielfeld dar.
 * 
 * @author Michelle
 *
 */
public class GameBoardView extends AbstractView<GameBoardModel> {

	protected GridPane root, cardPane, dicePane, targetCardsPane, specialCardsPane, deadCardsPane;
	protected VBox navigation;
	protected ImageView logo, linkIcon, exitIcon;
	protected Image linkImage, exitImage;
	protected Hyperlink linkAnleitung;
	protected Button btnPlayer1, btnPlayer2, btnPlayer3, btnPlayer4, btnWuerfeln, btnUebernehmen, btnBestaetigen,
			btnLink, btnExit, btnSenden;
	protected TextArea txtChatInput, txtChatScreen;
	protected ScrollPane scrollTxt, scrollPane;
	protected Label labelSpielfeld, labelRollDices, labelSelectedDices, labelSelectCards;
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
		root = GameBoardView.initializeGridPane();
		scrollPane = new ScrollPane();
		scrollPane.setContent(root);

		Scene scene = new Scene(root, 1500, 1000);

		// Instanziierung und Zuweisung der Controlls zur Haupt-Pane
		labelSpielfeld = new Label("Spielfeld");
		labelSpielfeld.setId("labelSpielfeld");
		labelSpielfeld.setAlignment(Pos.CENTER_RIGHT);
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

		root.add(labelSpielfeld, 1, 0);
		root.add(btnLink, 4, 0);
		root.add(btnExit, 5, 0);
		GridPane.setHalignment(labelSpielfeld, HPos.CENTER);

		// Definition der Pane für die linke Navigationsspalte
		navigation = new VBox(5);
		navigation.setPadding(new Insets(20, 20, 20, 20));

		// Instanziierung und Zuordnung der Controlls zur navigation-Pane
		try {
			logo = new ImageView(ResourceLoader.getImage("Logo_1.png"));
		} catch (FileNotFoundException e) {
			LogHelper.LogException(e);
		}
		logo.setFitWidth(200);
		logo.setPreserveRatio(true);
		btnPlayer1 = GameBoardView.initializeButton("Spieler 1");
		btnPlayer2 = GameBoardView.initializeButton("Spieler 2");
		btnPlayer3 = GameBoardView.initializeButton("Spieler 3");
		btnPlayer4 = GameBoardView.initializeButton("Spieler 4");
		btnArray = new ArrayList<>();
		btnArray.add(btnPlayer1);
		btnArray.add(btnPlayer2);
		btnArray.add(btnPlayer3);
		btnArray.add(btnPlayer4);
		txtChatScreen = new TextArea();
		txtChatScreen.setPrefSize(200, 400);
		txtChatScreen.setEditable(false);
		txtChatInput = new TextArea();
		txtChatInput.setPrefSize(200, 150);
		txtChatScreen.setId("chatArea");
		txtChatInput.setId("chatArea");
		btnSenden = new Button("Nachricht senden");
		btnSenden.setPrefSize(200, 40);
		btnSenden.setAlignment(Pos.CENTER);
		scrollTxt = new ScrollPane();
		scrollTxt.setContent(txtChatScreen);
		Tooltip chatInputTool = new Tooltip("Hier kannst du deine Chatnachrichten eingeben");
		Tooltip.install(txtChatInput, chatInputTool);
		navigation.getChildren().addAll(logo, btnPlayer1, btnPlayer2, btnPlayer3, btnPlayer4, txtChatScreen,
				txtChatInput, btnSenden);

		// Definition der Pane für die Spielkarten
		targetCardsPane = GameBoardView.initializeGridPane();
		deadCardsPane = GameBoardView.initializeGridPane();
		specialCardsPane = GameBoardView.initializeGridPane();
		cardPane = GameBoardView.initializeGridPane();
		cardPane.add(specialCardsPane, 0, 0);
		cardPane.add(targetCardsPane, 1, 0);
		cardPane.add(deadCardsPane, 2, 0);
		buildCards();
		
		// Definition der Pane für den Würfel-Bereich
		dicePane = GameBoardView.initializeGridPane();

		// Instanziierung und Zuordnung der Controlls zur "dicePane"
		labelRollDices = new Label(
				"Du darfst insgesamt dreimal würfeln. Wähle die Würfel an, welche du setzen möchtest.");
		labelSelectedDices = new Label("Deine gesetzten Würfel:");
		labelSelectCards = new Label("Wähle deine Karten und schliesse deinen Zug mit \"bestätigen\" ab.");

		btnWuerfeln = GameBoardView.initializeButton("würfeln");
		// TODO: setDisable vom Server steuern lassen
		btnWuerfeln.setDisable(false);
		btnUebernehmen = GameBoardView.initializeButton("übernehmen");
		btnBestaetigen = GameBoardView.initializeButton("bestätigen");

		dicePane.add(labelRollDices, 0, 0, 9, 1);

		buildDices();

		dicePane.add(btnWuerfeln, 10, 1);
		dicePane.add(labelSelectedDices, 0, 3, 9, 1);
		dicePane.add(btnUebernehmen, 10, 4);
		dicePane.add(labelSelectCards, 0, 6, 9, 1);
		dicePane.add(btnBestaetigen, 10, 6);

		// Zuordnung der Sub-Panes zur Haupt-Pane "root"
		root.add(navigation, 0, 0, 1, 10);
		root.add(cardPane, 1, 1);
		root.add(dicePane, 1, 2);

		// Zuweisung des Stylesheets
		scene.getStylesheets().add(getClass().getResource("..\\application.css").toExternalForm());

		disablePlayer();

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

		return scene;
	}

	/**
	 * TODO
	 * 
	 * @param diceControl
	 * @return
	 */
	public int displayDice(DiceControl diceControl) {
		int index = diceControlArray.length % 7;
		diceControlArray[index] = diceControl;
		dicePane.add(diceControl, index, 5);
		return index;
	}

	/**
	 * Diese Support-Methode deaktiviert anhand der Anzahl Spieler die nicht
	 * verwendeten Button-Objekte.
	 */
	public void disablePlayer() {
		List<Player> playerList = model.getPlayerList();
		for (Button btn : btnArray) {
			btn.setDisable(true);
		}
		for (int index = 0; index < playerList.size(); index++) {
			// TODO: Unterer Teil nur für Testing! Wieder auskommentieren...
			// for (int index = 0; index < 3; index++) {
			Button btn = btnArray.get(index);
			btn.setDisable(false);
		}

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
		imageView.setFitHeight(100);
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
		pane.setPadding(new Insets(20, 20, 20, 20));
		pane.setHgap(10);
		pane.setVgap(10);
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
		btn.setDisable(true);
		return btn;
	}

	protected void buildCards() {
		System.out.println("Zeichne Karten"+ model.targetCardsNeedsUpdate);
		int zeileTarget = 0;
		if (model.targetCardsNeedsUpdate) {
			if (this.targetCardsPane.getChildren() != null) {
				this.targetCardsPane.getChildren().clear();
			}
			System.out.println("mir si do :-)");
			for (ITargetCard targetCards : model.gameBoard.getTargetCards()) {
				if (targetCards.getGameCard().isRiebmann()){
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
				targetCardsPane.add(getImageView(targetCards.getForegroundImage()), (targetCards.getGameCard().getCardNumber()-1)%5, zeileTarget);
			}
		}
		int spalteDead = 7;
		int zeileDead = 1;
		int iteratorDead = 1;
		if (model.deadCardsNeedsUpdate) {
			if (this.deadCardsPane.getChildren() != null) {
				this.deadCardsPane.getChildren().clear();
			}
			for (IDeadCard deadCards : model.gameBoard.getDeadCards()) {
				iteratorDead++;
				deadCardsPane.add(getImageView(deadCards.getForegroundImage()), spalteDead, zeileDead++);
				if (iteratorDead == 4) {
					spalteDead = 8;
					zeileDead = 1;
				}
			}
		}
		
		int spalteSpecial = 7;
		int zeileSpecial = 1;
		int iteratorSpecial = 1;
		if (model.specialCardsNeedsUpdate) {
			if (this.specialCardsPane.getChildren() != null) {
				this.specialCardsPane.getChildren().clear();
			}
			for (ISpecialCard specialCards : model.gameBoard.getSpecialCards()) {
				iteratorSpecial++;
				specialCardsPane.add(getImageView(specialCards.getForegroundImage()), spalteSpecial, zeileSpecial++);
				if (iteratorSpecial == 4) {
					spalteDead = 8;
					zeileDead = 1;
				}
			}
		}

		// cardPane.add(getImageView("Riebmann1Vorne.png"), 2, 0);
		// cardPane.add(getImageView("Riebmann2Vorne.png"), 3, 0);
		// cardPane.add(getImageView("Riebmann3Vorne.png"), 4, 0);
		// cardPane.add(getImageView("Riebmann4Vorne.png"), 5, 0);
		// cardPane.add(getImageView("Riebmann5Vorne.png"), 6, 0);
		// cardPane.add(getImageView("Yeti1Vorne.png"), 2, 1);
		// cardPane.add(getImageView("Yeti2Vorne.png"), 3, 1);
		// cardPane.add(getImageView("Yeti3Vorne.png"), 4, 1);
		// cardPane.add(getImageView("Yeti4Vorne.png"), 5, 1);
		// cardPane.add(getImageView("Yeti5Vorne.png"), 6, 1);
		// cardPane.add(getImageView("Lemming1Vorne.png"), 2, 2);
		// cardPane.add(getImageView("Lemming2Vorne.png"), 3, 2);
		// cardPane.add(getImageView("Lemming3Vorne.png"), 4, 2);
		// cardPane.add(getImageView("Lemming4Vorne.png"), 5, 2);
		// cardPane.add(getImageView("Lemming5Vorne.png"), 6, 2);
		// cardPane.add(getImageView("Professoren1Vorne.png"), 2, 3);
		// cardPane.add(getImageView("Professoren2Vorne.png"), 3, 3);
		// cardPane.add(getImageView("Professoren3Vorne.png"), 4, 3);
		// cardPane.add(getImageView("Professoren4Vorne.png"), 5, 3);
		// cardPane.add(getImageView("Professoren5Vorne.png"), 6, 3);
		// cardPane.add(getImageView("Dino1Vorne.png"), 2, 4);
		// cardPane.add(getImageView("Dino2Vorne.png"), 3, 4);
		// cardPane.add(getImageView("Dino3Vorne.png"), 4, 4);
		// cardPane.add(getImageView("Dino4Vorne.png"), 5, 4);
		// cardPane.add(getImageView("Dino5Vorne.png"), 6, 4);
		// cardPane.add(getImageView("Tod0Vorne.png"), 7, 1);
		// cardPane.add(getImageView("Tod2Vorne.png"), 7, 2);
		// cardPane.add(getImageView("Tod4Vorne.png"), 7, 3);
		// cardPane.add(getImageView("Tod1Vorne.png"), 8, 1);
		// cardPane.add(getImageView("Tod3Vorne.png"), 8, 2);
		// cardPane.add(getImageView("Tod5Vorne.png"), 8, 3);
		// cardPane.add(getImageView("Ente.png"), 0, 1);
		// cardPane.add(getImageView("Clown.png"), 0, 2);
		// cardPane.add(getImageView("Zeitmaschine.png"), 0, 3);
		// cardPane.add(getImageView("Ufo.png"), 1, 1);
		// cardPane.add(getImageView("Roboter.png"), 1, 2);
		// cardPane.add(getImageView("Killervirus.png"), 1, 3);
	}

	protected void buildPlayer() {

	}

	protected void buildDices() {
		diceControlArray = new DiceControl[7];
		ICube[] cubes = model.getCubes();
		for (int i = 0; i < cubes.length; i++) {
			DiceControl diceControl = new DiceControl(cubes[i]);
			diceControlArray[i] = diceControl;
			if (cubes[i].getIsFixed()) {
				dicePane.add(diceControl, cubes[i].getCubeNumber(), 4);
			} else {
				dicePane.add(diceControl, cubes[i].getCubeNumber(), 1);
			}
			diceControl.showDice();
		}
	}
}
