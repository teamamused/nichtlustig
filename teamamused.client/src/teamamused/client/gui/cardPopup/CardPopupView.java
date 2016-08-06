package teamamused.client.gui.cardPopup;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import teamamused.client.gui.gameboard.GameBoardModel;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractView;
import teamamused.common.gui.LangText;
import teamamused.common.gui.Translator;
import teamamused.common.interfaces.IDeadCard;
import teamamused.common.interfaces.ISpecialCard;
import teamamused.common.interfaces.ITargetCard;

/**
 * Diese Klasse stellt die grafische Oberfläche für die Anzeige der Gegnerkarten
 * dar.
 * 
 * @author Michelle
 *
 */
public class CardPopupView extends AbstractView<GameBoardModel> {

	private final int IMAGE_HEIGHT = 120;
	private final int POPUP_SIZE = 240;
	protected GridPane root;
	protected VBox titlePane;
	protected HBox cardTxtPane, specialCardTxtPane, deathCardTxtPane, buttonPane;
	protected FlowPane cardFlowPane, specialCardFlowPane, deathCardFlowPane;
	protected Label labelTitle, labelText, cardsRival, specialCardsRival, deathCardsRival, noCardsRival,
			noSpecialCardsRival, noDeathCardsRival;
	protected Button btnClose;
	protected ImageView playerTargetCardView, playerSpecialCardView, playerDeathCardView;
	protected ScrollPane scrollPane;

	public CardPopupView(Stage stage, GameBoardModel model) {
		super(stage, model);
	}

	protected Scene createGUI() {
		stage.setTitle("Nicht Lustig: Karten der Gegner");

		// Definition der Haupt-Pane
		root = new GridPane();
		root.setPadding(new Insets(20, 20, 20, 20));
		root.setHgap(10);
		root.setVgap(10);
		root.setAlignment(Pos.TOP_LEFT);
		root.setGridLinesVisible(false);
		scrollPane = new ScrollPane();
		scrollPane.setContent(root);

		Scene scene = new Scene(root, 1000, 700);

		// Definition der Titel-Pane inkl. Instanziierung und Zuweisung der
		// Controlls
		titlePane = new VBox();
		labelTitle = new Label(String.format("Karten von Spieler %s", this.model.getPlayer().getPlayerName()));
		labelText = new Label("Bereits gewertete Karten sind umgedreht.");
		labelTitle.setId("labelTitle");
		labelText.setId("subtitle");
		titlePane.getChildren().addAll(labelTitle, labelText);
		titlePane.setPrefWidth(1000);

		// Definition der Pane für den Text zu den Zielkarten inkl.
		// Instanziierung und Zuweisung der Controlls
		cardTxtPane = new HBox();
		cardsRival = new Label("Zielkarten");
		cardsRival.setId("labelUnderline");
		cardTxtPane.getChildren().add(cardsRival);
		noCardsRival = new Label("Der Spieler hat keine Zielkarten");
		
		// Definition der Pane für die Zielkarten inkl. Instanziierung und
		// Zuweisung der Controlls
		cardFlowPane = new FlowPane();
		cardFlowPane.setHgap(10);
		cardFlowPane.setVgap(10);
		
		if (this.model.getPlayer() != null) {
			for (ITargetCard card : model.getPlayer().getTargetCards()) {
				Button btnTargetCard = new Button();
				btnTargetCard.setGraphic(card.toCanvas(IMAGE_HEIGHT - 5));
				btnTargetCard.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Popup popup = new Popup();
						popup.hideOnEscapeProperty().set(true);
						Button btnSchliessen = new Button("schliessen");
						btnSchliessen.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								popup.hide();
							}
						});
						VBox box = new VBox(5);
						box.getChildren().addAll(card.toCanvas(POPUP_SIZE), btnSchliessen);
						popup.getContent().addAll(box);
						popup.show(stage);
					}
				});
				cardFlowPane.getChildren().add(btnTargetCard);
			}

			if (cardFlowPane.getChildren().size() == 0) {
				cardFlowPane.getChildren().add(noCardsRival);
			}
		}

		// Definition der Pane für den Text zu den Spezialkarten inkl.
		// Instanziierung und Zuweisung der Controlls
		specialCardTxtPane = new HBox();
		specialCardsRival = new Label("Spezialkarten");
		specialCardsRival.setId("labelUnderline");
		specialCardTxtPane.getChildren().add(specialCardsRival);
		noSpecialCardsRival = new Label("Der Spieler hat keine Spezialkarten");

		// Definition der Pane für die Spezialkarten inkl. Instanziierung
		// und Zuweisung der Controlls
		specialCardFlowPane = new FlowPane();
		specialCardFlowPane.setHgap(10);
		specialCardFlowPane.setVgap(10);

		if (this.model.getPlayer() != null) {
			for (ISpecialCard card : model.getPlayer().getSpecialCards()) {
				Button btnSpecialCard = new Button();
				btnSpecialCard.setGraphic(getImageView(card.getForegroundImage()));
				btnSpecialCard.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Popup popup = new Popup();
						popup.hideOnEscapeProperty().set(true);
						Button btnSchliessen = new Button("schliessen");
						btnSchliessen.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								popup.hide();
							}
						});
						VBox box = new VBox(5);
						ImageView view = getImageView(card.getForegroundImage());
						box.getChildren().addAll(view, btnSchliessen);
						view.setFitHeight(POPUP_SIZE);
						popup.getContent().addAll(box);
						popup.show(stage);
					}
				});
				specialCardFlowPane.getChildren().add(btnSpecialCard);
			}

			if (specialCardFlowPane.getChildren().size() == 0) {
				specialCardFlowPane.getChildren().add(noSpecialCardsRival);
			}
		}

		// Definition der Pane für den Text zu den Spezialkarten inkl.
		// Instanziierung und Zuweisung der Controlls
		deathCardTxtPane = new HBox();
		deathCardsRival = new Label("Todeskarten");
		deathCardsRival.setId("labelUnderline");
		deathCardTxtPane.getChildren().add(deathCardsRival);
		noDeathCardsRival = new Label("Der Spieler hat keine Todeskarten");

		// Definition der Pane für die Todeskarten inkl. Instanziierung
		// und Zuweisung der Controlls
		deathCardFlowPane = new FlowPane();
		deathCardFlowPane.setHgap(10);
		deathCardFlowPane.setVgap(10);

		if (this.model.getPlayer() != null) {
			for (IDeadCard card : model.getPlayer().getDeadCards()) {
				Button btnDeathCard = new Button();
				btnDeathCard.setGraphic(getImageView(card.getForegroundImage()));
				btnDeathCard.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Popup popup = new Popup();
						popup.hideOnEscapeProperty().set(true);
						Button btnSchliessen = new Button("schliessen");
						btnSchliessen.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								popup.hide();
							}
						});
						VBox box = new VBox(5);
						ImageView view = getImageView(card.getForegroundImage());
						box.getChildren().addAll(view, btnSchliessen);
						view.setFitHeight(POPUP_SIZE);
						popup.getContent().addAll(box);
						popup.show(stage);
					}
				});
				deathCardFlowPane.getChildren().add(btnDeathCard);
			}
			
			if (deathCardFlowPane.getChildren().size() == 0) {
				deathCardFlowPane.getChildren().add(noDeathCardsRival);
			}
		}

		// Definition der Pane für die Buttons inkl.
		// Instanziierung und Zuweisung des Controlls
		buttonPane = new HBox();
		btnClose = CardPopupView.initializeButton("schliessen");
		buttonPane.getChildren().add(btnClose);
		buttonPane.setSpacing(10);
		buttonPane.setAlignment(Pos.BASELINE_RIGHT);

		root.add(titlePane, 0, 0);
		root.add(cardTxtPane, 0, 1);
		root.add(cardFlowPane, 0, 2, 1, 3);
		root.add(specialCardTxtPane, 0, 5);
		root.add(specialCardFlowPane, 0, 6, 1, 3);
		root.add(deathCardTxtPane, 0, 10);
		root.add(deathCardFlowPane, 0, 11, 1, 3);
		root.add(buttonPane, 0, 15);

		// Zuweisung des Stylesheets
		try {
			scene.getStylesheets().add(getClass().getResource("..\\application.css").toExternalForm());
		} catch (Exception e) {
			e.printStackTrace();
		}

		updateTexts();

		return scene;
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
		btn.setPrefSize(180, 40);
		btn.setAlignment(Pos.CENTER);
		return btn;
	}

	/**
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
		imageView.setFitHeight(IMAGE_HEIGHT);
		imageView.setPreserveRatio(true);
		return imageView;
	}

	public Scene getScene() {
		return scene;
	}

	public void start() {
		stage.show();
	}

	public void stop() {
		stage.hide();
	}

	protected void updateTexts() {

		// Translator holen
		Translator tl = ServiceLocator.getInstance().getTranslator();

		// Texte holen
		stage.setTitle(tl.getString(LangText.CardPopupTitle));
		this.labelTitle.setText(String.format(tl.getString(LangText.CardPopupCardsOf), model.getPlayer().getPlayerName()));
		this.labelText.setText(tl.getString(LangText.CardPopupValued));
		this.cardsRival.setText(tl.getString(LangText.CardPopupTarget));
		this.noCardsRival.setText(tl.getString(LangText.CardPopupNoTarget));
		this.specialCardsRival.setText(tl.getString(LangText.CardPopupSpecial));
		this.noSpecialCardsRival.setText(tl.getString(LangText.CardPopupNoSpecial));
		this.deathCardsRival.setText(tl.getString(LangText.CardPopupDeath));
		this.noDeathCardsRival.setText(tl.getString(LangText.CardPopupNoDeath));
		this.btnClose.setText(tl.getString(LangText.CardPopupBtnClose));
	}

}
