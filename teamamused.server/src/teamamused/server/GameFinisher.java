package teamamused.server;

import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.List;

import teamamused.common.ServiceLocator;
import teamamused.common.db.GameInfo;
import teamamused.common.db.GameInfoRepository;
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
	private int playerPrePoints = 0;
	private Hashtable<IPlayer, Integer> ranking;
	private List <IDeadCard> deadCards;
	private List<ISpecialCard> specialCards;
	private List<ITargetCard> targetCards;
	private List<IPlayer> players;
	private int valuatedLemmingCards = 0;
	private int valuatedYetiCards = 0;
	private int valuatedRiebmannCards = 0;
	private int dinoCardValue = 0;
	private int notValuatedCards = 0;
	private int singleDeadCards = 0;
	private int playerPoints = 0;
	
	
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
	 * Berechnet die Punkte für jeden Spieler und setzt diese im Ranking.
	 * @param player Spieler
	 * @param playerPrePoints Vorpunkte von Professoren-Karte
	 */
	public void calcPoints(IPlayer player, int playerPrePoints){
		playerPoints += playerPrePoints;
		playerPoints += valuatedLemmingCards * 4;
		
		if(valuatedYetiCards > 0){
			if(valuatedYetiCards > 1){
				playerPoints += valuatedYetiCards * 3;
			}else{
				
			}playerPoints += 1;
		}
		
		playerPoints += valuatedRiebmannCards * 2;
		playerPoints += dinoCardValue;
		playerPoints -= singleDeadCards;
		playerPoints += notValuatedCards;
		
		ranking.put(player, playerPoints);	
		
	}
	
	/**
	 * Schliesst das Spiel ab, indem die Punkte berechtet und das Ranking gesetzt
	 * wird.
	 */
	public void finishGame(){
		//Liest die Spieler aus
		for(IPlayer player: Game.getInstance().getPlayers()){
			players.add(player);
		}
		
		//Liest die Karten der einzelnen Spieler aus
		for(IPlayer player : players){
			for(IDeadCard deadCard : player.getDeadCards()){
				deadCards.add(deadCard);
				singleDeadCards++;
				/*maja: klären:
				 * Wie stelle ich sicher, dass diese auf keiner Zielkarte liegt?*/
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
					if(targetCard.getGameCard().name().matches("ZK_Lemming"+"[1-5]")){
						valuatedLemmingCards++;
					}
					//Yeti-Karte
					else if(targetCard.getGameCard().name().matches("ZK_Yeti"+"[1-5]")){
						valuatedYetiCards++;
					}
					//Riebmann-Karte
					else if(targetCard.getGameCard().name().matches("ZK_Riebmann"+"[1-5]")){
						valuatedRiebmannCards++;
					}
					//Dino-Karte
					else if(targetCard.getGameCard().name().matches("ZK_Dinosaurier"+"[1-5]")){
						dinoCardValue += targetCard.getCardValue();
					}
					/*Prüft ob ein Spieler eine Professoren-Karte hat{
					* und erzeugt automatisch eine zufällige Punktzahl von 0-5
					* für die Karte, welche der Spieler erhält
					*/	
					else if(targetCard.getGameCard().name().matches("ZK_Professoren"+"[1-5]")){
						playerPrePoints = (int) Math.random() * 6;
					}
				}
				//Todes-Karten, welche auf keinen Zielkarten liegen
				else if(targetCard.getIsCoveredByDead()){
					//Nichts passiert, da keine Punkte verteilt werden in diesem Fall
				}else{}
			}
			
			calcPoints(player, playerPrePoints);
			resetCounters();

		}
		
	}
	
	public void resetCounters(){
		deadCards.clear();
		specialCards.clear();
		targetCards.clear();
		playerPrePoints = 0;
		valuatedLemmingCards = 0;
		valuatedYetiCards = 0;
		valuatedRiebmannCards = 0;
		dinoCardValue = 0;
		notValuatedCards = 0;
		singleDeadCards = 0;
		playerPoints = 0;
	}
	
	public void setRanking(){
		//Maja: klären: Wie und wo genau Ranking nachführen?
	}
	
	//Ranking auslesen
	public Hashtable<IPlayer, Integer> getRanking(){
		return ranking;
	}
	
	public void showRankingToPlayer(){
		//Maja: klären: Wird das in GUI gemacht? Muss ich was tun?
		
		// Dem Ranking muss du auch die gameId mitgeben, damit abgespeichrt werden kann in welchem Spiel diese Platzierungen waren
		int gameId = 1; //etwa so: Game.getInstance().getGameId();
		// Den hashtable und die gameId dem RankingRepository übergeben, dies macht dir das Ranking Array und fügt die Objekte dem DB Context hinzu
		Ranking[] inGameRanking = RankingRepository.getInGameRanking(gameId, this.ranking);
		// Clients sagen das das Game fertig ist und als Parameter die Platzierungen Übergeben
		ClientNotificator.notifyGameFinished(inGameRanking);
	}
	
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
		
		
		// Du solltest in der Klasse Game noch diese 3 Attribute ergänzen: GameId, Startzeit, Endzeit
		// Um das Spiel speichern zu können machst du dies in etwa so:
		int gameId = GameInfoRepository.getNextGameId(); // GameId sollte von Game kommen, aber dort kannst du so eine neue zuweisen. 
		LocalDateTime spielStart = LocalDateTime.now(); // auch vom game, merken wenn das Spiel begann
		LocalDateTime spielEnde = LocalDateTime.now(); // Das ist wohl jetzt
		// 1. Datenbank Context aus dem ServiceLocator holen
		IDataBaseContext db = ServiceLocator.getInstance().getDBContext();
		// 2. GameInfo Objekt erstellen:
		GameInfo gi = new GameInfo(gameId, spielStart, spielEnde);
		// Spieler zum GameInfo Objekt hinzufügen
		for (IPlayer player : Game.getInstance().getPlayers()) {
			gi.Players.add(player.getPlayerName());
		}
		// 4. in der Datenbank eine GameInfo hinzufügen:
		db.addGame(gi);
		
		// 5. Ranking speichern und den Spielern anzeigen
		this.showRankingToPlayer();
		
		// 6. Alle Änderungen an der Datenbank sind bis jetzt nur im Memory
		//    Um die Daten effektiv zu speichern machst du
		db.saveContext();
	}
}
