package teamamused.server.lib;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

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
	private LocalDateTime gameStart;
	private int gameId;
	private Logger log;

	private IPlayer activePlayer;
	
	private Game() {
		super();
		this.log = ServiceLocator.getInstance().getLogger();
	}

	/**
	 * Statischer Getter für Instanz (Singleton Pattern)
	 * 
	 * @return Instanz des Games
	 */
	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}

	/**
	 * Setzt das aktuelle Spiel zurück.
	 * Kein Speichern des Spielstandes
	 * Spiel startet von vorne
	 */
	public static void resetGame() {
		instance = new Game();
	}

	/**
	 * Spieler zu Spiel hinzufügen
	 * 
	 * @param player
	 *            Spieler, welcher hinzugefügt werden soll
	 */
	public void addPlayer(IPlayer player) {
		this.log.info("Füge Spieler " + player.getPlayerName() + " hinzu");
		if (this.getPlayersFromGameboard().size() <= 4) {
			player.initForGame(this.getPlayersFromGameboard().size()+1);
			this.getPlayersFromGameboard().add(player);
			ClientNotificator.notifyGameMove("Spieler " + player.getPlayerName() + " ist dem Spiel beigetreten.");
			ClientNotificator.notifyUpdateGameBoard(BoardManager.getInstance().getGameBoard());
		} else {
			throw new UnsupportedOperationException(
					"Die maximale Spieleranzahl wurde bereits erreicht, bitte versuchen Sie es später erneut.");
		}
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
	 * Gibt den Startzeitpunkt des Spiels zurück.
	 * 
	 * @return Startzeitpunkt als LocalDateTime
	 */
	public LocalDateTime getGameStart() {
		return this.gameStart;
	}
	
	/**
	 * Beendet die aktuelle Runde
	 */
	public void finishRound() {
		this.log.info("Beende aktuelle Spielrunde");
		// Wertung anhand des Pinken Würfels durchführen
		this.log.info("Wertung wird durchgeführt");
		BoardManager.getInstance().valuate();
		// Prüfen welche Karten der Spieler erhalten darf
		this.log.info("Holle Karten zum vorschlagen");
		BoardManager.getInstance().valuatePlayerDice();
		Hashtable<Integer, List<ITargetCard>> cardsToPropose = BoardManager.getInstance().getCardsToPropose();
		int optionCount = cardsToPropose.size();

		// Wenn mehrere Optionen zur Auswal:
		if (optionCount > 1) {
			this.log.info("Sende dem Spieler die Kartenauswahl Optionen");
			// Dem Client mitteilen, das er sich für eine Möglichkeit entscheien
			// soll.
			ClientNotificator.notifyCardsToChoose(cardsToPropose);

		} else {
			this.log.info("Teile dem Spieler die Karten zu, anzahl Optionen: " + optionCount);
			// Wenn nur eine Auswahlmöglichkeit oder sogar keine (dann Tod)
			// Karten direkt zuteilen und nächste Runde starten
			if (optionCount == 1) {
				// Wenn es nur eine möglichkeit gibt diese Option nehmen
				if (cardsToPropose.containsKey(1)) {
					BoardManager.getInstance().takeProposedCards(cardsToPropose.get(1));
				} else {
					this.log.info("CardsToPropose enthält kein Vorschlag");
					System.out.println(cardsToPropose);
					System.out.println(cardsToPropose.keySet());
					System.out.println(cardsToPropose.values());
				}
			}
			BoardManager.getInstance().deployCards();
			this.startNextRound();
		}

	}

	/**
	 * Startet die nächste Runde
	 * 
	 */
	public void startNextRound() {
		this.log.info("Prüfe Spielstatus");
		this.checkGameState();
		this.log.info("Starte nächse Spielrunde");
		if (this.gameStatus != GameState.finished) {
			// Nächsten Spieler aktivieren
			this.changeActivePlayer();
			ClientNotificator.notifyGameMove("Die Spielrunde wurde für Spieler " + activePlayer + " aktiviert.");
			// Prüfen ob der Spieler Spezialkarten hat welche er spielen muss
			ISpecialCard[] specialCardByCurrentPlayer = this.activePlayer.getSpecialCards();
			boolean playerHasToSkip = false;
			// Es gibt mehrere Spezialkarten welche die Anzahl würfelversüche
			// beeinflussen könnten, daher eine List
			List<ISpecialCard> additionalDicingCards = new ArrayList<ISpecialCard>();
			for (ISpecialCard card : specialCardByCurrentPlayer) {
				// Wenn der Spieler aussetzen (Spezialkarte UFO) ist er mit der Spielrunde fertig
				if (card.getHasToSkip()) {
					playerHasToSkip = true;
					ClientNotificator.notifyGameMove("Der Spieler " + this.activePlayer.getPlayerName()
							+ " wurde von einem UFO entführt und muss diese Runde aussetzen! " + 
							"Die Sonderkarte UFO wurde vom Spieler " + this.activePlayer.getPlayerName() +
							" auf das Spielbrett verschoben.");
					BoardManager.getInstance().switchSpecialcardOwner(card, null);

				}
				//Wenn Spezialkarte Ente (+1 Wurf) oder Roboter (-1 Wurf) bei Spieler vorhanden
				else if (card.getAdditionalDicing() != 0) {
					additionalDicingCards.add(card);
				}
			}
			// Würfel zurücksetzen und zusätzliche Würfeln übergeben
			int additionalDicings = 0;
			for (ISpecialCard dicingCard : additionalDicingCards) {
				// zusätzliche Würfelwürfe Merken
				additionalDicings += dicingCard.getAdditionalDicing();
				// Karte zurück aufs Gameboard legen
				BoardManager.getInstance().switchSpecialcardOwner(dicingCard, null);
			}
			CubeManager.getInstance().initForNextRound(additionalDicings);
			ClientNotificator.notifyPlayerChanged(this.activePlayer);
			// Falls der Spieler überspringen muss, nächste Runde direkt wieder starten
			// (Muss am schluss sein damit alle notificationen sauber durchlaufen)
			if (playerHasToSkip) {
				this.startNextRound();
				return;
			}
			ClientNotificator.notifyUpdateGameBoard(BoardManager.getInstance().getGameBoard());
		}
	}

	/**
	 * Spieler auflistung vom Gameboard holen
	 * @return Liste der Spieler
	 */
	private List<IPlayer> getPlayersFromGameboard() {
		return BoardManager.getInstance().getGameBoard().getPlayers();
	}

	/**
	 * Initialisieren des Spiels (Game)
	 */
	public void startGame() {
		ServiceLocator.getInstance().getLogger().info("Initialisiere Spiel");
		gameId = GameInfoRepository.getNextGameId();
		gameStart = LocalDateTime.now();
		BoardManager.getInstance().getGameBoard().setGameStartet(true);
		// Spielstatus auf "gestartet" setzen
		gameStatus = GameState.running;
		Game.getInstance().startNextRound();
	}

	/**
	 * Prüft das Spiel ob es beendet werden soll und beendet es falls es soweit ist
	 */
	private void checkGameState() {
		if (this.gameStatus == GameState.running) {
			// Das Spiel ist beendet wenn noch maximal 5 Zielkarten auf dem Tisch liegen
			if (BoardManager.getInstance().getGameBoard().getTargetCards().length <= 5) {
				GameFinisher.getInstance().finishGame();
				GameFinisher.getInstance().closeGame();
				this.setGameIsFinished();
			}
		}
	}

	/**
	 * Game-Status auf "beendet" ändern.
	 */
	private void setGameIsFinished() {
		this.log.info("Setze Spiel beendet");
		gameStatus = GameState.finished;
	}

	/**
	 * Legt fest, welcher Spieler mit dem Spiel starten darf.
	 */
	private void defineStartPlayer() {
		this.log.info("Bestimme Startspieler");
		List<IPlayer> players = this.getPlayersFromGameboard();
		this.activePlayer = players.get((int) (Math.random() * players.size()));
		ClientNotificator.notifyGameMove("Spieler " + this.activePlayer.getPlayerName() + " fängt mit dem Spiel an");
	}

	/**
	 * Der aktive Spieler wird geändert, sobald ein Spieler seinen Spielzug
	 * abgeschlossen und die Wertung inkl. Kartenausteilung beendet wurde.
	 */
	private void changeActivePlayer() {
		this.log.info("Wechsle den aktiven Spieler");
		List<IPlayer> players = this.getPlayersFromGameboard();
		// Wenn noch kein Spieler aktiv, einen Startspieler bestimmen
		if (this.activePlayer == null) {
			this.defineStartPlayer();
		} else {
			// Wenn bereits ein Spieler aktiv ist, den nächsten aktivieren
			if (this.activePlayer.getPlayerNumber() < players.size()) {
				this.activePlayer = players.get(this.activePlayer.getPlayerNumber());
			} else {
				this.activePlayer = players.get(0);
			}
		}
	}
}