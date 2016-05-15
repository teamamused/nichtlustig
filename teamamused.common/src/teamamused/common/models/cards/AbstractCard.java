package teamamused.common.models.cards;

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
public abstract class AbstractCard implements IGameCard{

	private Image foregroundImage;
	private Image backgroundImage;
	private GameCard card;
	
	public AbstractCard(GameCard card) {
		this.card = card;
		try {
			this.foregroundImage = ResourceLoader.getImage(card.getForgroundImageName());
			this.backgroundImage = ResourceLoader.getImage(card.getBackgroundImageName());
		} catch (Exception e) {
			ServiceLocator.getInstance().getLogger().log(Level.SEVERE, e.getMessage(), e);
		}
		
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
		return this.backgroundImage;
	}

	/**
	 * Implementierung von: 
	 * @see teamamused.common.interfaces.IGameCard#getForegroundImage()
	 */
	@Override
	public Image getForegroundImage() {
		return this.foregroundImage;
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
