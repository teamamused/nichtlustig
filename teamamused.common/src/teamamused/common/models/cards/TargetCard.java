package teamamused.common.models.cards;

import java.util.logging.Level;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import teamamused.common.ResourceLoader;
import teamamused.common.ServiceLocator;
import teamamused.common.interfaces.ITargetCard;
import teamamused.common.models.cubes.CubeValue;

/**
 * Diese Klasse bildet die Zielkarten ab
 * 
 * @author Daniel Hirsbrunner
 *
 */
class TargetCard extends AbstractCard implements ITargetCard {

	/** Versionsnummer des Transport Objektes */
	private static final long serialVersionUID = 1;

	private int cardValue;
	private int requiredPoints;
	private boolean isValuated = false;
	private boolean isCoveredByDead = false;
	CubeValue[] requiredCubeValues;

	/**
	 * Initialisiert eine neue Zielkarte
	 * 
	 * @param card
	 *            Kartenkennung
	 * @param cardValue
	 *            Kartenwert (für die Wertung)
	 * @param requiredPoints
	 *            Punkte welche benötigt werden um diese Karte zu bekommen
	 * @param cubeValues
	 *            Würfel kombinationen welche benötigt werden um diese Karte zu
	 *            bekommen
	 */
	public TargetCard(GameCard card, int cardValue, int requiredPoints, CubeValue[] cubeValues) {
		super(card);
		this.cardValue = cardValue;
		this.requiredPoints = requiredPoints;
		this.requiredCubeValues = cubeValues;
	}

	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.ITargetCard#getCardCalue()
	 */
	@Override
	public int getCardValue() {
		return this.cardValue;
	}

	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.ITargetCard#getIsValuated()
	 */
	@Override
	public boolean getIsValuated() {
		return this.isValuated;
	}

	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.ITargetCard#setIsValuated()
	 */
	@Override
	public void setIsValuated(boolean isValuated) {
		this.isValuated = isValuated;
	}

	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.ITargetCard#getRequiredPoints()
	 */
	@Override
	public int getRequiredPoints() {
		return this.requiredPoints;
	}

	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.ITargetCard#getRequiredCubeValues()
	 */
	@Override
	public CubeValue[] getRequiredCubeValues() {
		return this.requiredCubeValues;
	}

	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.ITargetCard#getIsCoveredByDead()
	 */
	@Override
	public boolean getIsCoveredByDead() {
		return this.isCoveredByDead;
	}

	/**
	 * Implementierung von:
	 * 
	 * @see teamamused.common.interfaces.ITargetCard#setIsCoveredByDead()
	 */
	@Override
	public void setIsCoveredByDead(boolean isCovered) {
		this.isCoveredByDead = isCovered;
	}

	/**
	 * Methode um superdynamisch abhängig von der Grösse einen Würfel zu
	 * zeichnen mit dem aktuellen Cube Value
	 * 
	 * @param size
	 *            höhe und Breite des Würfels
	 * @param borderWitdh
	 *            Rahmen grösse um den Würfel herum
	 * @return ein Canvas welches die Höhe und Breite size + 2 mal border hat
	 */
	public Canvas toCanvas(int size) {
		Canvas canvas = new Canvas(size, size);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		drawCanvas(gc, size);
		return canvas;
	}

	/**
	 * Hilfsmethode für toCanvas
	 * 
	 * @param g
	 *            Der Grafikcontext
	 * @param size
	 *            höhe und Breite des Würfels
	 * @param borderWitdh
	 *            Rahmen grösse um den Würfel herum
	 */
	private void drawCanvas(GraphicsContext g, int size) {
		// Wenn die Karte gewertet ist die Rückseite anzeigen
		if (this.isValuated) {
			g.drawImage(this.getBackgroundImage(), 0, 0, size, size);
		} else {
			// wenn karte noch nicht gewertet die Vordergrundseite anzeigen
			g.drawImage(this.getForegroundImage(), 0, 0, size, size);
		}
		// Wenn Spielkarte gestorben ist kommt der Tod darüber (whoohooo)
		if (this.isCoveredByDead) {
			try {
				// Version mit bösem shartierten Tod über ganze Karte
				/*Image tod = ResourceLoader.getImage("Tod.png");
				g.drawImage(tod, 0, 0, size, size);*/
				// Version mit liebem Tod unten links
				int todsize = size /2;
				Image tod = ResourceLoader.getImage("Tod2.png");
				g.drawImage(tod, 0, todsize, todsize, todsize);
			} catch (Exception ex) {
				ServiceLocator.getInstance().getLogger().log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
	}

}
