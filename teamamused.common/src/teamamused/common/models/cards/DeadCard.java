package teamamused.common.models.cards;

import teamamused.common.interfaces.IDeadCard;

/**
 * Diese Klasse bildet die Todeskarten ab.
 * @author Daniel Hirsbrunner
 */
class DeadCard extends AbstractCard implements IDeadCard {

	/** Versionsnummer des Transport Objektes */
	private static final long serialVersionUID = 1;
	
	private int cardValue;
	private boolean isOnTargetCard;
	/**
	 * Konstruktur zur Initialisierung der Todeskarten
	 * @param card GameKarte
	 * @param cardValue Wert der Karte (0 = Pudel, 1 = erster Tod...)
	 */
	public DeadCard(GameCard card, int cardValue) {
		super(card);
		this.cardValue = cardValue;
	}
	
	/**
	 * Implementierung von 
	 * @see teamamused.common.interfaces.IDeadCard#getCardCalue()
	 */
	@Override
	public int getCardCalue() {
		return this.cardValue;
	}

	/**
	 * Implementierung von 
	 * @see teamamused.common.interfaces.IDeadCard#getIsOnTargetCard()
	 */
	@Override
	public boolean getIsOnTargetCard() {
		return this.isOnTargetCard;
	}

	/**
	 * Implementierung von 
	 * @see teamamused.common.interfaces.IDeadCard#setIsOnTargetCard(boolean isOnTargetCard)
	*/
	@Override
	public void setIsOnTargetCard(boolean isOnTargetCard) {
		this.isOnTargetCard = isOnTargetCard;
	}

}
