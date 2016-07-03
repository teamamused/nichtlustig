package teamamused.common.interfaces;

/**
 * Interface für alle Klasse welche Karten besitzen können.
 * Insbesondere Spieler und GameBoard
 * @author Daniel
 *
 */
public interface ICardHolder {

    /**
     * Geter Methode für die Spezial Karten
     * 
     * @return Ein Array mit allen auf dem Tisch liegenden Spezialkarten
     */
	ISpecialCard[] getSpecialCards();
    
    /**
     * Geter Methode für die Todes Karten
     * 
     * @return Ein Array mit allen auf dem Tisch liegenden Todes Karten
     */
    IDeadCard[] getDeadCards();
    
    /**
     * Geter Methode für die Ziel Karten
     * 
     * @return Ein Array mit allen auf dem Tisch liegenden Ziel Karten
     */
    ITargetCard[] getTargetCards();


	/**
	 * Enftern eine Spezialkarte vom Spieler
	 * 
	 * @param card
	 *            Karte zum entfernen
	 * @return die entfernte Karte / null falls sie nicht vorhanden war
	 */
	ISpecialCard removeSpecialCard(ISpecialCard card);

	/**
	 * Ordnet eine Spezialkarte dem Spieler zu
	 * 
	 * @param specialCard
	 *            Karte zum hinzufügen
	 */
	void addSpecialCard(ISpecialCard specialCard);

	/**
	 * Nimmt dem Spieler eine Zielkarte weg
	 * 
	 * @param targetCard
	 *            Karte zum zuordnen
	 * @return die entfernte Karte / null falls sie nicht vorhanden war
	 */
	ITargetCard removeTargetCard(ITargetCard targetCard);

	/**
	 * Ordnet dem Spieler eine Zielkarte zu
	 * 
	 * @param targetCard
	 *            Karte zum zuordnen
	 */
	default void addTargetCard(ITargetCard targetCard) {
		throw new UnsupportedOperationException("Dem Kartenhalter können keine Zielkarten hinzugefügt werden.");
	}

	/**
	 * Nimmt dem Spieler eine Todeskarte weg
	 * 
	 * @param deadCard
	 *            Karte zum entfernen
	 * @return die entfernte Karte / null falls sie nicht vorhanden war
	 */
	IDeadCard removeDeadCard(IDeadCard deadCard);

	/**
	 * Ordnet dem eine Todeskarte zu
	 * 
	 * @param deadCard
	 *            Karte zum zuordnen
	 *            
	 * @param targetCard
	 *            Karte welche vom Tod abgedeckt wird. Wenn NULL wird Tod auf ein eigenes Feld gelegt.
	 */
	default void addDeadCard(IDeadCard deadCard, ITargetCard targetCard) {
		throw new UnsupportedOperationException("Dem Kartenhalter können keine Todeskarten hinzugefügt werden.");
	}
}
