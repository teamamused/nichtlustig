package teamamused.common.models.cards;

import teamamused.common.interfaces.IDeadCard;

/**
 * Diese Klasse bildet die Todeskarten ab.
 * @author Daniel Hirsbrunner
 */
class DeadCard extends AbstractCard implements IDeadCard {

	private int cardValue;
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

}
