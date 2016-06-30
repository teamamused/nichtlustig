package teamamused.server;

import java.util.Hashtable;
import java.util.List;

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
					if(targetCard.getGameCard().name() == "ZK_Lemming_"){
						valuatedLemmingCards++;
					}
					//Yeti-Karte
					else if(targetCard.getGameCard().name() == "ZK_Yeti_"){
						valuatedYetiCards++;
					}
					//Riebmann-Karte
					else if(targetCard.getGameCard().name() == "ZK_Riebmann_"){
						valuatedRiebmannCards++;
					}
					//Dino-Karte
					else if(targetCard.getGameCard().name() == "ZK_Dino_"){
						dinoCardValue += targetCard.getCardValue();
					}
					/*Prüft ob ein Spieler eine Professoren-Karte hat{
					* und erzeugt automatisch eine zufällige Punktzahl von 0-5
					* für die Karte, welche der Spieler erhält
					*/	
					else if(targetCard.getGameCard().name() == "ZK_Professoren_"){
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
	}
	
	public void closeGame(){
		//Maja: klären: Wie soll ich das Spiel genau abschliessen?
	}
}
