package teamamused.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import teamamused.common.ServiceLocator;
import teamamused.common.db.GameInfoRepository;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.ISpecialCard;
import teamamused.common.interfaces.ITargetCard;

/**
 * 
 * Die Klasse Game steuert das Spiel gesamtahaft und ist zuständig dafür das
 * Spiel zu aktivieren, die aktiven Spieler zu ändern und das Spiel
 * abzuschliessen, wenn nur noch 5 oder weniger Zielkarten auf dem Spielbrett
 * vorhanden sind.
 * 
 * @author Maja Velickovic
 *
 */

public class Game implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Game instance;

	private enum GameState {
		// nicht gestartet
		notStarted,
		// gestartet
		running,
		// beendet
		finished;
	}

	// gameStatus: 0 = nicht gestartet, 1 = gestartet, 2 = beendet
	private GameState gameStatus = GameState.notStarted;
	private int gameId;

	private IPlayer activePlayer;

	private Game() {
		super();
		this.startGame();
	}

	/**
	 * Initialisieren des Spiels (Game)
	 */
	private void startGame() {
		ServiceLocator.getInstance().getLogger().info("Initialisiere Spiel");
		gameId = GameInfoRepository.getNextGameId();
		// Spielstatus auf "gestartet" setzen
		gameStatus = GameState.running;
	}

	/**
	 * Statischer Getter für Instanz (Singleton Pattern)
	 * 
	 * @return Instanz des Games
	 */
	public static Game getInstance() {
		// Dani an Maja: wieso willst du ein neues Spiel machen falls es noch
		// nicht beendet ist? So verliere ich immer den active Player
		if (instance == null /* || instance.gameStatus != 2 */) {
			instance = new Game();
		}
		return instance;
	}

	/**
	 * Spieler zu Spiel hinzufügen
	 * 
	 * @param player
	 *            Spieler, welcher hinzugefügt werden soll
	 */
	public void addPlayer(IPlayer player) {
		if (this.getPlayersFromGameboard().size() <= 4) {
			player.setPlayerNumber(this.getPlayersFromGameboard().size());
			this.getPlayersFromGameboard().add(player);
			ClientNotificator.notifyGameMove("Spieler " + player.getPlayerName() + " ist dem Spiel beigetreten.");
			ClientNotificator.notifyUpdateGameBoard(BoardManager.getInstance().getGameBoard());
		} else {
			throw new UnsupportedOperationException(
					"Die maximale Spieleranzahl wurde bereits erreicht, bitte versuchen Sie es später erneut.");
		}
	}

	/**
	 * Legt fest, welcher Spieler mit dem Spiel starten darf.
	 */
	public void defineStartPlayer() {
		this.activePlayer = this.getPlayersFromGameboard().get((int) (Math.random() * 4));
	}

	/**
	 * Der aktive Spieler wird geändert, sobald ein Spieler seinen Spielzug
	 * abgeschlossen und die Wertung inkl. Kartenausteilung beendet wurde.
	 */
	public void changeActivePlayer() {
		if (this.activePlayer.getPlayerNumber() != 3) {
			this.activePlayer = this.getPlayersFromGameboard().get(this.activePlayer.getPlayerNumber() + 1);
		} else {
			this.activePlayer = this.getPlayersFromGameboard().get(0);
		}
		ClientNotificator.notifyPlayerChanged(this.activePlayer);
	}

	/**
	 * Game-Status auf "beendet" ändern.
	 */
	public void setGameIsFinished() {
		gameStatus = GameState.finished;
	}

	/**
	 * Gibt den aktiven Spieler zurück, welcher am Würfeln ist.
	 * 
	 * @return aktiver Spieler
	 */
	public IPlayer getActivePlayer() {
		return activePlayer;
	}

	/**
	 * Gibt die Game-ID des Spiels zurück.
	 * 
	 * @return Game-ID als Integer
	 */
	public Integer getGameID() {
		return this.gameId;
	}

	/**
	 * TODO: Dani an Maja: Mal so eingeführt um Server fertig zu stellen, Maja
	 * bitte sagen ob ok und anpassen / ergänzen Diverse Unklarheiten / Fragen
	 * im Code an Maja
	 */
	public void finishRound() {
		// Wertung anhand des Pinken Würfels durchführen
		Valuation val = new Valuation();
		val.valuate(BoardManager.getInstance());
		// Prüfen welche Karten der Spieler erhalten darf
		BoardManager.getInstance().valuatePlayerDice();

		// Hallo Maja :)
		Hashtable<Integer, List<ITargetCard>> cardsToPropose = BoardManager.getInstance().getCardsToPropose();
		int optionCount = cardsToPropose.size();

		// Dani an Maja: ev. der Teil hier in valuatePlayerDice nehmen?
		// Prüfen ob der Spieler eine Todeskarte nehmen muss. Dazu Spezialkarten
		// prüfen
		ISpecialCard playerIsForcedToDead = null;
		ISpecialCard playerIsBewaredOfDead = null;
		for (ISpecialCard card : this.activePlayer.getSpecialCards()) {
			if (card.getIsForcedOfDead()) {
				playerIsForcedToDead = card;
			}
			if (card.getIsBewaredOfDead()) {
				playerIsBewaredOfDead = card;
			}
		}
		// Wenn keine Optionen zur Auswahl Tod zuteilen
		if (optionCount == 0 || playerIsForcedToDead != null) {
			// Spezialkarte Killervirus wieder entfernen
			if (playerIsForcedToDead != null) {
				ClientNotificator.notifyGameMove("Der Spieler " + this.activePlayer.getPlayerName()
						+ " starb durch einen Killervirus");
				BoardManager.getInstance().switchCardOwner(playerIsForcedToDead, null);
			}
			// Falls der Spieler die Spezialkarte IsBewaredOfDead hat diese
			// entfernen und keinen Tod zuteilen
			if (playerIsBewaredOfDead != null) {
				ClientNotificator.notifyGameMove("Der Spieler " + this.activePlayer.getPlayerName()
						+ " entging dem Tod indem er ihm eine Torte ins Gesicht warf!");
				BoardManager.getInstance().switchCardOwner(playerIsForcedToDead, null);
			} else {
				// Spieler den Tod zuteilen
				int deadNumber = CubeManager.getInstance().getCurrentPinkCube().FaceValue;
				BoardManager.getInstance().addDeadCardToDeploy(deadNumber);
			}
		}
		//
		// Wenn mehrere Optionen zur Auswal:
		if (optionCount > 1) {
			// Dem Client mitteilen, das er sich für eine Möglichkeit entscheien
			// soll.
			ClientNotificator.notifyCardsToChoose(cardsToPropose);

		} else if (optionCount == 1) {
			// Wenn nur eine Auswahlmöglichkeit karten direkt zuteilen und
			// nächste Runde starten
			BoardManager.getInstance().deployCards();
			this.startNextRound();
		}

	}

	/**
	 * TODO: Dani an Maja: Mal so eingeführt um Server fertig zu stellen, Maja
	 * bitte sagen ob ok und anpassen / ergänzen
	 */
	public void startNextRound() {
		if (this.gameStatus != GameState.finished) {
			// Nächsten Spieler aktivieren
			this.changeActivePlayer();
			// Prüfen ob der Spieler Spezial karten hat welche er spielen muss
			ISpecialCard[] specialCardByCurrentPlayer = this.activePlayer.getSpecialCards();
			boolean playerHasToSkip = false;
			// Es gibt mehrere Spezialkarten welche die Anzahl würfelversüche
			// beeinflussen könnten, daher eine List
			List<ISpecialCard> additionalDicingCards = new ArrayList<ISpecialCard>();
			for (ISpecialCard card : specialCardByCurrentPlayer) {
				// Wenn der Spieler aussetzen muss ist fertig
				if (card.getHasToSkip()) {
					playerHasToSkip = true;
					ClientNotificator.notifyGameMove("Der Spieler " + this.activePlayer.getPlayerName()
							+ " wurde von einem UFO entführt und muss diese Runde aussetzen");
					BoardManager.getInstance().switchCardOwner(card, null);

				} else if (card.getAdditionalDicing() != 0) {
					additionalDicingCards.add(card);
				}
			}
			if (playerHasToSkip) {
				this.startNextRound();
				return;
			}
			// Würfel zurücksetzen und zusätzliche Würfeln übergeben
			int additionalDicings = 0;
			for (ISpecialCard dicingCard : additionalDicingCards) {
				// zusätzliche Würfelwürfe Merken
				additionalDicings += dicingCard.getAdditionalDicing();
				// Karte zurück aufs Gameboard legen
				BoardManager.getInstance().switchCardOwner(dicingCard, null);
			}
			CubeManager.getInstance().initForNextRound(additionalDicings);
		}
	}

	private List<IPlayer> getPlayersFromGameboard() {
		return BoardManager.getInstance().getGameBoard().getPlayers();
	}

}
