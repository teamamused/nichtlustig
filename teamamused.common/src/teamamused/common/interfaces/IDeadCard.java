package teamamused.common.interfaces;

/**
 * 
 * Interface für die Todeskarten welche im Spiel vorhanden sind.
 * 
 * @author Daniel Hirsbrunner
 *
 */
public interface IDeadCard extends IGameCard {
	/**
	 * Welche Augenzahl hat diese Todeskarte
	 * @return Augenzahl (int zwischen 0 und 5)
	 */
	int getCardCalue();


	/**
	 * Wurde diese Karte auf eine Zielkarte gelegt.
	 * @return Karte auf Zielkarte ja / nein
	 */
	boolean getIsOnTargetCard();


	/**
	 * Setzt das Flag ob diese Karte auf eine Zielkarte gelegt wurde.
	 * @param isOnTargetCard Karte auf Zielkarte ja / nein
	 */
	void setIsOnTargetCard(boolean isOnTargetCard);
}
