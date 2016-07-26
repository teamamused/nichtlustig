package teamamused.common.models.cubes;

import java.io.Serializable;
import java.util.logging.Level;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import teamamused.common.ResourceLoader;
import teamamused.common.ServiceLocator;
import teamamused.common.interfaces.ISpecialCard;

/**
 * 
 * Datenhaltungsobjekt für die Würfel anzeigen. Ein CubeValue ist ein
 * gewürfelter Wert, er wird durch eine Farbe und eine Augenzahl definiert.
 * Jeder Würfel hat genau 6 CubeValue's
 * 
 * Diese Klasse wurde eingeführt um einfach prüfen zu können ob eine Zielkarte
 * erwürfelt wurde.
 * 
 * @author Daniel Hirsbrunner
 *
 */
public class CubeValue implements Serializable {

	/** Versionsnummer des Transport Objektes */
	private static final long serialVersionUID = 1;
	/**
	 * Konstruktor zur direkten initialisierung
	 * 
	 * @param color
	 *            Color
	 * @param faceValue
	 *            FaceValue
	 */
	public CubeValue(CubeColor color, int faceValue) {
		this.Color = color;
		this.FaceValue = faceValue;
	}

	/**
	 * Farbe des Würfel's
	 */
	public CubeColor Color;
	/**
	 * Anzeigewert des Würfels
	 */
	public int FaceValue;

	/**
	 * Spezialkarte (gesetzt bei FaceValue 0)
	 */
	public ISpecialCard SpecialCard;

	/**
	 * Vergleichs Methode, prüft die Farbe und den Anzeigewert der CubeValues
	 * 
	 * @param otherCubeValue
	 *            zu vergleichender CubeValue
	 * @return sind die CubeValues identisch Ja/Nein
	 */
	@Override
	public boolean equals(Object obj) {
		CubeValue otherCubeValue = (CubeValue)obj;
		return this.Color.getBackgroundColor().equals(otherCubeValue.Color.getBackgroundColor()) && this.FaceValue == otherCubeValue.FaceValue;
	}

	@Override
	public String toString() {
		return this.Color.toString() + " - " + this.FaceValue;
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
	public Canvas toCanvas(int size, int borderWitdh) {
		Canvas canvas = new Canvas(size + (2 * borderWitdh), size + (2 * borderWitdh));
		GraphicsContext gc = canvas.getGraphicsContext2D();
		drawCanvas(gc, size, borderWitdh);
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
	private void drawCanvas(GraphicsContext g, int size, int border) {
		int canvasWidth = size + (2 * border);
		// Würfel HIntergund
		g.setFill(this.Color.getForegroundColor());
		g.fillRect(0, 0, canvasWidth, canvasWidth);
		// Würfel ausfüllen und mit der Vorderfabe noch einen Rahmen
		// zeichnen
		g.setFill(this.Color.getBackgroundColor());
		g.fillRoundRect(border, border, size, size, size / 3, size / 3);
		g.setFill(this.Color.getForegroundColor());

		// Bei Wert 0 Das Spezialkartenlogo zurückgeben
		if (this.FaceValue == 0) {
			Image bild = null;
			if (this.SpecialCard != null && this.SpecialCard.getCubeSymbol() != null) {
				bild = this.SpecialCard.getCubeSymbol();
			} else {
				// Wenn Wert 0 und keine Spezialkarte hinterlegt ist, is es der Würfel des Todes
				try {
					bild = ResourceLoader.getImage("PudelDesTodes.png");
				} catch (Exception ex) {
				    ServiceLocator.getInstance().getLogger().log(Level.SEVERE, ex.getMessage(), ex);
				}
			}
			int randUmsBild = (size / 10) + border;
			g.drawImage(bild, randUmsBild, randUmsBild,
					canvasWidth - (randUmsBild * 2), canvasWidth - (randUmsBild * 2));
			
			// Wenn Wert nicht null die Augen zeichnen
		} else {
			// der Durchmesser jedes Punktes soll ein Viertel von der
			// Würfelgrösse sein
			int pointSize = size / 4;
			// alle Distanzen vom Rand sollen ein drittel punkt sein
			// da immer Koordinate links oben angegeben werden muss die anderen
			// Punkte berechnen
			int leftAndUperDrawStart = pointSize / 3;
			// Für Rechte und untere Punkte abstand + ein Punkt nach innen
			int rightAndBottomDrawStart = size - pointSize - leftAndUperDrawStart;
			int middleDrawStart = (leftAndUperDrawStart + rightAndBottomDrawStart) / 2;

			// Punkte zeichnen
			if (this.FaceValue > 1) {
				// Oben Links und unten rechts immer sobald Zahl grösser
				// 2,3,4,5)
				g.fillOval(border + leftAndUperDrawStart, border + leftAndUperDrawStart, pointSize, pointSize);
				g.fillOval(border + rightAndBottomDrawStart, border + rightAndBottomDrawStart, pointSize, pointSize);
			}
			if (this.FaceValue > 3) {
				// Oben Rechts und unten Links für die Zahlen 4 und 5
				g.fillOval(border + rightAndBottomDrawStart, border + leftAndUperDrawStart, pointSize, pointSize);
				g.fillOval(border + leftAndUperDrawStart, border + rightAndBottomDrawStart, pointSize, pointSize);
			}
			if (this.FaceValue % 2 == 1) {
				// Bei allen ungeraden Zahlen kommt der Punkt in der Mitte
				// (1,3,5)
				g.fillOval(border + middleDrawStart, border + middleDrawStart, pointSize, pointSize);
			}
		}
	}
}
