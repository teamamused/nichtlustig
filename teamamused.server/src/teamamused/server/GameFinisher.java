package teamamused.server;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import teamamused.common.ServiceLocator;
import teamamused.common.db.GameInfo;
import teamamused.common.db.Ranking;
import teamamused.common.db.RankingRepository;
import teamamused.common.interfaces.IDataBaseContext;
import teamamused.common.interfaces.IDeadCard;
import teamamused.common.interfaces.IPlayer;
import teamamused.common.interfaces.ISpecialCard;
import teamamused.common.interfaces.ITargetCard;

/**
 * 
 * Die Klasse GameFinisher beendet das Spiel und führt entsprechende Aktionen
 * durch, wie die Punkte der Spieler zu kalkulieren und die Rangliste
 * nachzuführen. Ausserdem beendet die Klasse effektiv das Spiel.
 * 
 * @author Maja Velickovic
 *
 */

public class GameFinisher {
	private static GameFinisher instance;
	private Hashtable<IPlayer, Integer> ranking = new Hashtable<IPlayer, Integer>();
	private List <IDeadCard> deadCards = new ArrayList<IDeadCard>();
	private List<ISpecialCard> specialCards = new ArrayList<ISpecialCard>();
	private List<ITargetCard> targetCards = new ArrayList<ITargetCard>();
	private List<IPlayer> players;
	private int valuatedLemmingCards = 0;
	private int valuatedYetiCards = 0;
	private int valuatedRiebmannCards = 0;
	private int valuatedProfessorenCards = 0;
	private int dinoCardValue = 0;
	private int notValuatedCards = 0;
	private int singleDeadCards = 0;
	private int playerPoints = 0;
	private int gameID;
	
	
	public GameFinisher(){
		
	}
	
	/**
	 * Statischer Getter für Instanz (Singleton Pattern)
	 * @return Instanz des Boardmanagers
	 */
	public static GameFinisher getInstance() {
		if (instance == null) {
			instance = new GameFinisher();
		}
		return instance;
	}
	
	/**
	 * Schliesst das Spiel ab, indem die Punkte berechtet und das Ranking gesetzt
	 * wird.
	 */
	public void finishGame(){
		//Liest die Spieler aus
		this.players = BoardManager.getInstance().getGameBoard().getPlayers();
		
		//Liest die Karten der einzelnen Spieler aus
		for(IPlayer player : players){
			for(IDeadCard deadCard : player.getDeadCards()){
				if(!deadCard.getIsOnTargetCard()){
					deadCards.add(deadCard);
					singleDeadCards++;
				}						
			}
			for(ISpecialCard specialCard : player.getSpecialCards()){
				specialCards.add(specialCard);
			}
			for(ITargetCard targetCard : player.getTargetCards()){
				targetCards.add(targetCard);
				
				//nicht gewertete Karten
				if(!targetCard.getIsValuated()){
					notValuatedCards++;
				}
				//Lemming-Karte
				else if(!targetCard.getIsCoveredByDead()){
					if(targetCard.getGameCard().isLemming()){
						valuatedLemmingCards++;
					}
					//Yeti-Karte
					else if(targetCard.getGameCard().isYeti()){
						valuatedYetiCards++;
					}
					//Riebmann-Karte
					else if(targetCard.getGameCard().isRiebmann()){
						valuatedRiebmannCards++;
					}
					//Dino-Karte
					else if(targetCard.getGameCard().isDino()){
						dinoCardValue += targetCard.getCardValue();
					}
					/*Prüft ob ein Spieler eine Professoren-Karte hat{
					* und erzeugt automatisch eine zufällige Punktzahl von 0-5
					* für die Karte, welche der Spieler erhält
					*/	
					else if(targetCard.getGameCard().isProffessoren()){
						valuatedProfessorenCards++;
					}
				}
				//Todes-Karten, welche auf keinen Zielkarten liegen
				else if(targetCard.getIsCoveredByDead()){
					//Nichts passiert, da keine Punkte verteilt werden in diesem Fall
				}else{}
			}
			
			calcPoints(player);
			resetCounters();

		}
		
	}
	
	/**
	 * Schliesst das Spiel komplett ab und speichert das Ergebnis in der DB
	 */
	public void closeGame(){
		//Maja: klären: Wie soll ich das Spiel genau abschliessen?

		// Einführung in die Datenspeicherung für Maja, Ausschnit aus der Package-info vom teammamused.common.db:
		// Es gibt 3 Hautpentitäten zum verwalten:
		//	  - PlayerInfo
		//	  - GameInfo
		//	  - Ranking
		// Zu jeder Entität wird ein Repository zur Verfügung gestelt. In diesem sind die zentralen Funktionen welche sich auf diese Entitäten beziehen.
		//
		// Du kannst in der Datei teamamused.common.teamamused.config den Pfad zur DB Datei festlegen.
		
		
		// Spiel Speichern
		int gameId = Game.getInstance().getGameID();
		LocalDateTime spielStart = Game.getInstance().getGameStart();
		LocalDateTime spielEnde = LocalDateTime.now(); 
		// 1. Datenbank Context aus dem ServiceLocator holen
		IDataBaseContext db = ServiceLocator.getInstance().getDBContext();
		
		// 2. GameInfo Objekt erstellen:
		GameInfo gi = new GameInfo(gameId, spielStart, spielEnde);
		// Spieler zum GameInfo Objekt hinzufügen
		for (IPlayer player : this.players) {
			gi.Players.add(player.getPlayerName());
		}
		// 4. in der Datenbank eine GameInfo hinzufügen:
		db.addGame(gi);
		
		// 5. Ranking speichern und den Spielern anzeigen
		this.setRanking();
		
		// 6. Alle Änderungen an der Datenbank sind bis jetzt nur im Memory
		//    Um die Daten effektiv zu speichern machst du
		db.saveContext();
	}
	
	/**
	 * Berechnet die Punkte für jeden Spieler und setzt diese im Ranking.
	 * @param player Spieler
	 * @param playerPrePoints Vorpunkte von Professoren-Karte
	 */
	private void calcPoints(IPlayer player){
		playerPoints += valuatedProfessorenCards * (int)(Math.random() * 6.0); //für Professoren-Karten
		playerPoints += valuatedLemmingCards * 4;
		
		if(valuatedYetiCards > 0){
			if(valuatedYetiCards > 1){
				playerPoints += valuatedYetiCards * 3;
			}else{
				playerPoints += 1;
			}
		}
		
		playerPoints += valuatedRiebmannCards * 2;
		playerPoints += dinoCardValue;
		playerPoints -= singleDeadCards;
		playerPoints += notValuatedCards;
		
		ranking.put(player, playerPoints);	
		
	}
	
	/**
	 * Setzt die Zähler vom GameFinisher zurück.
	 */
	private void resetCounters(){
		deadCards.clear();
		specialCards.clear();
		targetCards.clear();
		valuatedLemmingCards = 0;
		valuatedYetiCards = 0;
		valuatedRiebmannCards = 0;
		valuatedProfessorenCards = 0;
		dinoCardValue = 0;
		notValuatedCards = 0;
		singleDeadCards = 0;
		playerPoints = 0;
	}
	
	/**
	 * Ranking setzen.
	 */
	private void setRanking(){
		gameID = Game.getInstance().getGameID();
		Ranking[] inGameRanking = RankingRepository.getInGameRanking(gameID, this.ranking);
		showRankingToPlayer(inGameRanking);
	}
	
	/**
	 * Liest das Ranking aus.
	 * @return Ranking Hashtabelle <IPlayer, Integer>
	 */
	private Hashtable<IPlayer, Integer> getRanking(){
		return ranking;
	}
	
	/**
	 * Zeigt den Spielern das Ranking an.
	 * @param inGameRanking RankingRepository übergeben
	 */
	private void showRankingToPlayer(Ranking[] inGameRanking){
		ClientNotificator.notifyGameFinished(inGameRanking);
	}
}
