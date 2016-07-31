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

	private int index;
	private ICube cube;

	/**
	 * Die Methode nimmt ein ICube-Objekt entgegen und wandelt dies in ein
	 * Canvas um. Weiterhin kann so durch das DiceControll-Objekt auf das
	 * ICube-Objekt zugegriffen werden und die entsprechende Zeichenfläche wird gezeichnet.
	 * 
	 * @param cube
	 */
	public DiceControl(ICube cube) {
		this.cube = cube;
		getChildren().add(cube.getCurrentValue().toCanvas(50, 1));
	}

	public int getIndex() {
		return index;
	}

	public ICube getCube() {
		return cube;
	}

}
