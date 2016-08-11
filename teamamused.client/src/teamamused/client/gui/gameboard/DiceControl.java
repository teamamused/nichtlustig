package teamamused.client.gui.gameboard;

import javafx.scene.layout.Region;
import teamamused.common.interfaces.ICube;

/**
 * Diese Klasse stellt ein UI-Controll für den Würfel dar.
 * 
 * @author Michelle
 *
 */
public class DiceControl extends Region {

	private ICube cube;

	/**
	 * Die Methode nimmt ein ICube-Objekt entgegen und wandelt dies in ein
	 * Canvas um. Weiterhin kann so durch das DiceControll-Objekt auf das
	 * ICube-Objekt zugegriffen werden und die entsprechende Zeichenfläche wird
	 * gezeichnet.
	 * 
	 * @param cube
	 *            Nimmt als Parameter den entsprechenden ICube entgegen
	 */
	public DiceControl(ICube cube) {
		this.cube = cube;
		getChildren().add(cube.getCurrentValue().toCanvas(45, 1));
	}

	/**
	 * Getter für den ICube-Würfel, welcher sich hinter dem Canvas verbirgt
	 * 
	 * @return cube Gibt den ICube-Würfel zurück
	 */
	public ICube getCube() {
		return cube;
	}

}
