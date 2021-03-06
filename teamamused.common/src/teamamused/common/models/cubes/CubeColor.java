package teamamused.common.models.cubes;

import javafx.scene.paint.Color;

/**
 * Mögliche Farb Schemas welche ein CubeValue haben kann Definiert durch
 * Vordergrund und Hintergrund Farbe.
 * 
 * @author Daniel Hirsbrunner
 *
 */
public enum CubeColor {
	Red(Color.RED, Color.WHITE), White(Color.WHITE, Color.BLACK), Black(Color.BLACK, Color.WHITE), Pink(Color.MAGENTA,
			Color.WHITE);

	private Color backColor;
	private Color foreColor;

	CubeColor(Color backColor, Color foreColor) {
		this.backColor = backColor;
		this.foreColor = foreColor;
	}

	/**
	 * Gibt die Hintergrundfarbe des Würfels zurück
	 * 
	 * @return Color des Hintergrundes
	 */
	public Color getBackgroundColor() {
		return this.backColor;
	}

	/**
	 * Gibt die Vordergrundfarbe des Würfels zurück
	 * 
	 * @return Color des Vordergrundes
	 */
	public Color getForegroundColor() {
		return this.foreColor;
	}
	
	@Override
	public String toString() {
		if (this.backColor.equals(Color.RED)) {
			return "RED";
		} else if (this.backColor.equals(Color.WHITE)) {
			return "WHITE";
		} else if (this.backColor.equals(Color.BLACK)) {
			return "BLACK";
		}
		return "No Color";
	}
}