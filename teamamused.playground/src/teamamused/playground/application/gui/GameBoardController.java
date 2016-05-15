package teamamused.playground.application.gui;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractController;
import teamamused.common.interfaces.IClientListener;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.GameBoard;
import teamamused.playground.application.Client;

public class GameBoardController extends AbstractController<GameBoardModel, GameBoardView> implements IClientListener {
	
	public GameBoardController(GameBoardModel model, GameBoardView view) {
		super(model, view);
		// Dem Client Mitteilen dass man über aktualisierungen informiert werden will
		Client.getInstance().registerGui(this);
		// Im Konstruktor werden alle Gui Events mit den zugehörigen Aktionen
		// verknüpft
		// Button Würfeln
		view.btnDice.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int remainingRolls = Client.getInstance().rollDices();
				model.spielbrett = Client.getInstance().getSpielbrett();
				if (remainingRolls > 0) {
					view.drawCubes(remainingRolls);
				}
			}
		});
		// Button Spiel starten
		view.btnStartGame.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Client.getInstance().startGame();
			}
		});
		// Button Spiel starten
		view.btnChooseCards.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Hier müsste dem Spieler eine mölgichkeit gegeben werden die Karten die er will auszuwählen
				// Danach geprüft werden ob mit seinem erwürfeltem die Auswahl legitim ist
				// Dann die ausgewählten zuteilen und nicht einfach alle
				Client.getInstance().chooseCards(model.cardsToChoose);
				view.wuerfel.getChildren().clear();
			}
		});
	}

	/**
	 * Der Spieler muss eine Karte auswählen
	 * 
	 * @param cards
	 */
	@Override
	public void onPlayerHasToCooseCards(ArrayList<ITargetCard> allowedCards) {
		model.cardsToChoose = Arrays.copyOf(allowedCards.toArray(), allowedCards.size(), ITargetCard[].class);
		ServiceLocator.getInstance().getLogger().info("GameboardController: zeige auszuwählende Karten an");
		this.view.drawCards("Bitte wählen Sie \ndie gewünschten Zielkarten aus:", model.cardsToChoose);
	}

	@Override
	public void onPlayerIsActivedChanged(boolean isActive) {
		this.model.isPlayerActive = isActive;
		if (isActive) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Du bist am Zug");
			alert.setHeaderText(null);
			alert.setContentText("Du bist an der Reihe mit Würfeln!");
			alert.showAndWait();
			view.drawCubes(3);
		}
	}
	
	@Override
	public void onGameBoardChanged(GameBoard newGameBoard) {
		model.spielbrett = newGameBoard;
		view.drawCards();
	}

}
