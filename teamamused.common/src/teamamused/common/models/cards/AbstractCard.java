package teamamused.common.models.cards;

import java.io.Serializable;
import java.util.logging.Level;

import teamamused.common.ResourceLoader;
import teamamused.common.ServiceLocator;
import teamamused.common.interfaces.IGameCard;
import javafx.scene.image.Image;

/**
 * Dies ist die Basisklasse aller Spielkarten.
 * Hier ist die zentrale Logik wie das Laden der Hinter / Fordergrundbilder definiert.
 * @author Daniel Hirsbrunner
 *
 */
public abstract class AbstractCard implements IGameCard, Serializable {

	/** Versionsnummer des Transport Objektes */
	private static final long serialVersionUID = 1;
	private GameCard card;
	
	public AbstractCard(GameCard card) {
		this.card = card;
	}

	/**
	 * Implementierung von: 
	 * @see teamamused.common.interfaces.IGameCard#getGameCard()
	 */
	@Override
	public GameCard getGameCard() {
		return this.card;
	}

	/**
	 * Implementierung von: 
	 * @see teamamused.common.interfaces.IGameCard#getBackgroundImage()
	 */
	@Override
	public Image getBackgroundImage() {
		try {
			return ResourceLoader.getImage(card.getBackgroundImageName());
		} catch (Exception e) {
			ServiceLocator.getInstance().getLogger().log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Implementierung von: 
	 * @see teamamused.common.interfaces.IGameCard#getForegroundImage()
	 */
	@Override
	public Image getForegroundImage() {
		try {
			return ResourceLoader.getImage(card.getForgroundImageName());
		} catch (Exception e) {
			ServiceLocator.getInstance().getLogger().log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}
	/**
	 * Gibt den Namen der aktuellen Karte zurück
	 */
	public String toString() {
		return this.card.toString();
	}
	/**
	 * vergleicht die aktuelle Karte mit der Übergebenen Anhand der Kartennummer 
	 */
	public int compareTo(IGameCard card2) {
	    return Integer.compare(this.card.cardNumber, card2.getGameCard().cardNumber);
	}

}
